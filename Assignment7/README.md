# Assignment 7

**Due:** _TODO_

**Exercises:** 8.5, 8.6, 9.1, 9.3

**Source folders:** `MicroC/`, `Virtual/`

**Hand-in:** one zip named `PrgDat-07-XXX-YYY.zip` (initials/names of the group members). Own code must be annotated with comments so it is easy to tell apart from the handed-out code.

---

## 8.5

Files: `MicroC/Absyn.fs`, `MicroC/CLex.fsl`, `MicroC/CPar.fsy`, `MicroC/Comp.fs`, `MicroC/Interp.fs`, test `MicroC/CEx/ex85.c` (own code marked `Ex 8.5`)

- **Abstract syntax:** new constructor `Cond of expr * expr * expr` for `e1 ? e2 : e3`.
- **Lexer:** new tokens `QUES` (`?`) and `COLON` (`:`).
- **Parser:** rule `Expr QUES Expr COLON Expr`, declared `%right QUES COLON`. It binds weaker than `||` and stronger than `=`, as in C, so `a ? b : c ? d : e` means `a ? b : (c ? d : e)`. It also binds stronger than `print`, so `print a ? b : c` prints the result of the whole conditional.
- **Compiler:** same scheme as `If`, but `e2`/`e3` are compiled with `cExpr` so the chosen branch leaves its value on the stack:

  ```
  <e1>  IFZERO Lelse  <e2>  GOTO Lend  Lelse: <e3>  Lend:
  ```

  Only one of `e2`, `e3` is evaluated.
- **Interpreter:** `Cond` evaluates `e1` and then only the selected branch.

**Test:** `CEx/ex85.c` tests precedence, nesting, and that only one branch is evaluated (`n > 0 ? ++x : ++y`).

```
dotnet build microcc.fsproj
bin/Debug/net10.0/microcc CEx/ex85.c
./machine CEx/ex85.out 5     # 1 30 100 1 0 10
./machine CEx/ex85.out 0     # 2 10 200 0 1 0
./machine CEx/ex85.out -3    # 2 20 200 0 1 3
./machine CEx/ex85.out -10   # 2 20 100 0 1 10
```

The interpreter (`Interp.run`) gives the same output. All existing `CEx/ex*.c` programs still compile to the same bytecode as their checked-in `.out` files.

## 8.6

Files: `MicroC/Absyn.fs`, `MicroC/CLex.fsl`, `MicroC/CPar.fsy`, `MicroC/Comp.fs`, `MicroC/Interp.fs`, test `MicroC/CEx/ex86.c` (own code marked `Ex 8.6`)

- **Abstract syntax:** new statement `Switch of expr * (int * stmt) list`.
- **Lexer:** new keywords `switch` and `case`.
- **Parser:** `SWITCH ( Expr ) { Cases }`, where `Cases` is zero or more `CASE Const : Block`. A case must therefore have an integer constant and a block body. As specified, there is no `break` and no `default`.
- **Compiler:** the switch expression is evaluated **once** and stays on the stack while the cases are tested. A switch with n cases uses n + 1 labels: one per case test, plus one at the end of the switch. After a body runs, the code jumps to the end, so there is no fall-through.

  ```
  <e>
  DUP CSTI k1 EQ IFZERO L1   <body1>  GOTO Lend
  L1: DUP CSTI k2 EQ IFZERO L2   <body2>  GOTO Lend
  L2: ...
  Lend: INCSP -1              // pop the switch value
  ```

  The case bodies are compiled with the frame depth increased by one, because the switch value occupies a stack slot below them. Locals declared inside a case then get the right offsets, and a `return` inside a case removes the switch value from the stack along with the rest of the frame.
- **Interpreter:** `Switch` runs the block of the first matching case, or does nothing if no case matches.

**Test:** `CEx/ex86.c` covers:
- the book's month example (`days`), including a month with no matching case;
- `return` from inside a case with a local declared in the case block (`classify`);
- a nested switch;
- a switch expression with a side effect (`++count + n`), to show it is evaluated only once (`count` ends at 1).

```
dotnet build microcc.fsproj
bin/Debug/net10.0/microcc CEx/ex86.c
./machine CEx/ex86.out 0     # 31 28 29 30 0 / 100 71 -1 / 1 1
./machine CEx/ex86.out 1     # ... / 21 1   (nested switch)
./machine CEx/ex86.out 2     # ... / 3 1
./machine CEx/ex86.out 5     # ... / 1      (no case matches)
```

All existing `CEx/ex*.c` programs still compile to the same bytecode as their checked-in `.out` files. The handed-out interpreter does not implement `return`, so it was checked with a separate version of the tests that does not use `return`, and it gave the same results as the compiled code.

## 9.1

Files: _TODO_

_TODO: description of the solution and how it was tested._

## 9.3 (from lecture slides, `QueueWithMistake.java`)

Files: `QueueWithMistake.java` (original), `Queue.java` (fixed) _(TODO: adjust names)_

**Problem:** the program keeps references that prevent the garbage collector from reclaiming memory, so it uses excessive memory and may run out of memory depending on the machine.

**Cause:** _TODO: which reference is kept alive and why (see comments in the file)._

**Fix:** _TODO: what was changed._

**Demonstration:** _TODO: run both versions and compare memory use, e.g._

```
javac QueueWithMistake.java && java -Xmx64m QueueWithMistake
javac Queue.java            && java -Xmx64m Queue
```

_TODO: observations (e.g. jconsole/VisualVM, `-verbose:gc`, or `Runtime` memory printouts)._
