# Assignment 6

**Due:** Thursday, 8 October 2026, 23:59

**Exercises:** 7.4, 7.5, 8.1, 8.3, 8.4

**Source folder:** `MicroC/`

---

## 7.4

Files: `MicroC/Absyn.fs`, `MicroC/Interp.fs`

Added `PreInc of access` and `PreDec of access` to the `expr` type in `Absyn.fs` (lines 20–21). These represent the C/C++/Java/C# preincrement (`++i`) and predecrement (`--i`) operators, which operate on lvalues (variables and array elements).

Added two cases to the `eval` function in `Interp.fs` (lines 161–170):
- `PreInc acc`: resolves the access to a location, reads the current value with `getSto`, increments it by 1, writes it back with `setSto`, and returns the new value.
- `PreDec acc`: same but decrements by 1.

Tested in fsi: starting from `x = 5`, `PreInc` returns and stores `6`, `PreDec` returns and stores `4`.

## 7.5

Files: `MicroC/CLex.fsl`, `MicroC/CPar.fsy`, `MicroC/CEx/ex74.c`

Extended the lexer and parser to accept `++e` and `--e` in concrete micro-C syntax.

**CLex.fsl (lines 53–54):** Added two new token rules before `+` and `-` so the lexer greedily matches `++` and `--` as single tokens:
- `"++"` → `INC`
- `"--"` → `DEC`

Order matters: `++` must appear before `+` or the lexer would tokenise `++` as two separate `PLUS` tokens.

**CPar.fsy:**
- Added `INC` and `DEC` to the `%token` declaration (line 20).
- Added two rules to `ExprNotAccess` (lines 136–137) that build the abstract syntax from 7.4:
  - `INC Access` → `PreInc $2`
  - `DEC Access` → `PreDec $2`

Tested with `CEx/ex74.c`: `++x` on `x = 5` prints `6`.

## 8.1

Files: `MicroC/CEx/ex03.out` `MicroC/CEx/ex05.out` `MicroC/ex03trace.txt`

(i) These steps were done in a previous assignment

(ii) To compile ex3.c and ex5.c we ran the following in CLI, in the MicroC folder. :
`dotnet fsi -r bin/Debug/net10.0/FsLexYacc.Runtime.dll Absyn.fs CPar.fs CLex.fs Parse.fs Machine.fs Comp.fs ParseAndComp.fs`

In interactive, we compiled ex3.c and ex5.c with: `compileToFile (fromFile "CEx/ex03.c") "CEx/ex03.out";;` and `compileToFile (fromFile "CEx/ex05.c") "CEx/ex05.out";;`.

We get the results:
ex03.c:`[LDARGS 1; CALL (1, "L1"); STOP; Label "L1"; INCSP 1; GETBP; CSTI 1; ADD;
   CSTI 0; STI; INCSP -1; GOTO "L3"; Label "L2"; GETBP; CSTI 1; ADD; LDI;
   PRINTI; INCSP -1; GETBP; CSTI 1; ADD; GETBP; CSTI 1; ADD; LDI; CSTI 1; ADD;
   STI; INCSP -1; INCSP 0; Label "L3"; GETBP; CSTI 1; ADD; LDI; GETBP; CSTI 0;
   ADD; LDI; LT; IFNZRO "L2"; INCSP -1; RET 0]`

ex05.c: `[LDARGS 1; CALL (1, "L1"); STOP; Label "L1"; INCSP 1; GETBP; CSTI 1; ADD;
   GETBP; CSTI 0; ADD; LDI; STI; INCSP -1; INCSP 1; GETBP; CSTI 0; ADD; LDI;
   GETBP; CSTI 2; ADD; CALL (2, "L2"); INCSP -1; GETBP; CSTI 2; ADD; LDI;
   PRINTI; INCSP -1; INCSP -1; GETBP; CSTI 1; ADD; LDI; PRINTI; INCSP -1;
   INCSP -1; RET 0; Label "L2"; GETBP; CSTI 1; ADD; LDI; GETBP; CSTI 0; ADD;
   LDI; GETBP; CSTI 0; ADD; LDI; MUL; STI; INCSP -1; INCSP 0; RET 1]`

rewritten in a more structured way with the symbolic bytecode on the left along with the corresponding MicroC code on the right:

Ex03.c:

| Label | Symbolic bytecode                                                         | micro-C                            |
|-------|---------------------------------------------------------------------------|------------------------------------|
|       | `LDARGS 1; CALL (1, L1); STOP`                                            | load arg `n`, call `main(n)`, stop |
| `L1:` | `INCSP 1`                                                                 | `void main(int n) { int i;`        |
|       | `GETBP; CSTI 1; ADD; CSTI 0; STI; INCSP -1`                               | `i = 0;`                           |
|       | `GOTO L3`                                                                 | `while (i < n) {`                  |
| `L2:` | `GETBP; CSTI 1; ADD; LDI; PRINTI; INCSP -1`                               | `print i;`                         |
|       | `GETBP; CSTI 1; ADD; GETBP; CSTI 1; ADD; LDI; CSTI 1; ADD; STI; INCSP -1` | `i = i + 1;`                       |
|       | `INCSP 0`                                                                 | `}`                                |
| `L3:` | `GETBP; CSTI 1; ADD; LDI; GETBP; CSTI 0; ADD; LDI; LT; IFNZRO L2`         | `i < n` jump back to `L2` if true  |
|       | `INCSP -1; RET 0`                                                         | `}` (free i, return from main)     |


ex05.c:

| Label | Symbolic bytecode                                                                               | micro-C                                      |
|-------|-------------------------------------------------------------------------------------------------|----------------------------------------------|
|       | `LDARGS 1; CALL (1, L1); STOP`                                                                  | load arg `n`, call `main(n)`, stop           |
| `L1:` | `INCSP 1`                                                                                       | `void main(int n) { int r;`                  |
|       | `GETBP; CSTI 1; ADD; GETBP; CSTI 0; ADD; LDI; STI; INCSP -1`                                    | `r = n;`                                     |
|       | `INCSP 1`                                                                                       | `{ int r;`                                   |
|       | `GETBP; CSTI 0; ADD; LDI; GETBP; CSTI 2; ADD; CALL (2, L2); INCSP -1`                           | `square(n, &r);`                             |
|       | `GETBP; CSTI 2; ADD; LDI; PRINTI; INCSP -1`                                                     | `print r;`                                   |
|       | `INCSP -1`                                                                                      | `}`                                          |
|       | `GETBP; CSTI 1; ADD; LDI; PRINTI; INCSP -1`                                                     | `print r;`                                   |
|       | `INCSP -1; RET 0`                                                                               | `}`                                          |
| `L2:` | `GETBP; CSTI 1; ADD; LDI; GETBP; CSTI 0; ADD; LDI; GETBP; CSTI 0; ADD; LDI; MUL; STI; INCSP -1` | `void square(int i, int *rp) { *rp = i * i;` |
|       | `INCSP 0; RET 1`                                                                                | `}`                                          |

We then executed the compiled programs with `java Machine CEx/ex03.out 10` and `java Machine CEx/ex05.out 10` and got the results:

ex03.out with 10:
```
0 1 2 3 4 5 6 7 8 9
Used 0.009 seconds
```

ex05.out with 10:
```
100 10
Used 0.007 seconds
```

To trace the execution, we ran `java Machinetrace CEx/ex03.out 4 > ex03trace.txt`. This file is located at `MicroC/ex03trace.txt`.

| Trace lines | Stack contents | micro-C         | What happens                                                                                     |
|-------------|----------------|-----------------|--------------------------------------------------------------------------------------------------|
| 1–2         | `[5 -999 4]`   | call `main(4)`  | LDARGS pushes the argument. CALL builds main's frame (return address, old bp, n) and jumps to L1 |
| 3–9         | `[5 -999 4 0]` | `int i; i = 0;` | INCSP 1 reserves i; GETBP; CSTI 1; ADD computes its address, STI stores 0.                       |
| 10          | `[5 -999 4 0]` | `while (i < n)` | GOTO 44 jumps straight to the loop test                                                          |
| 11–38       | `[5 -999 4 1]` | 1st iteration   | test 0 < 4. print i prints 0 and then i = i + 1.                                                 |
| 39–122      | `[5 -999 4 4]` | iterations 2–4  | same 28 instructions repeated. prints 1 2 3 4                                                    |
| 123–132     | `[5 -999 4 4]` | final test      | 4 < 4 is false.                                                                                  |
| 133–135     | `[4]`          | `}` and return  | INCSP -1 frees i; RET 0 removes the frame and returns to STOP.                                   |
## 8.3

Files: `MicroC/Comp.fs`, `MicroC/CEx/ex83.c`, `MicroC/CEx/ex83b.c`

Extended the compiler (`cExpr` in `Comp.fs`, lines 172–173) to generate code for `PreInc` and `PreDec`.

The key insight is that the address must only be computed once. For `++i` the instruction sequence is:

```
cAccess acc, DUP, LDI, CSTI 1, ADD, STI
```

- `cAccess acc` pushes the address of the lvalue
- `DUP` duplicates it — needed because `LDI` consumes the address, but `STI` also needs it later
- `LDI` reads the current value at that address
- `CSTI 1, ADD` computes the new value
- `STI` writes the new value back to the (duplicated) address and leaves the result on the stack

`PreDec` is identical but uses `SUB` instead of `ADD`.

The lexer and parser changes from exercise 7.5 (`INC`/`DEC` tokens) are reused here, so no additional parser changes were needed.

**Tests:**
- `CEx/ex83.c`: `++x` on `x = 5` prints `6`
- `CEx/ex83b.c`: `++arr[++i]` with `i = 3`, `arr[4] = 5` — prints `i = 4` and `arr[4] = 6`, confirming the address is computed only once and both `i` and the array element are correctly updated.

## 8.4
When compiling ex08.out we can see that from entering the loop we run 17 instructions (from the introduction of Label "L2" to the IFNZRO check), whereas prog1 only performs 4 instructions (from instruction 70/GOTO to instruction 72/IFNZERO) in the same loop. ex08 performs roughly 4 times as many instructions per loop as prog1 does, which is also verified in the run time, where ex08 uses 0.266 seconds to run the program compared to prog1's 0.066 seconds.
<br>

When looking the bytecode we can tell that we enter a loop where the value is incremented at each loop. Once inside the loop (GOTO L3) we can see that there are a several conditions/labels (Label 2, 4, 5, 6, etc). Based on the outcome of each condition we GOTO another condition. At the very end we check whether the value is zero with the IFNZERO. If not we jump back up to the start of the loop and run again. If it is zero we can exit the loop.

<!-- Files: -->
