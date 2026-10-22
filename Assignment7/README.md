# Assignment 7

**Due:** _TODO_

**Exercises:** 8.5, 8.6, 9.1, 9.3

**Source folders:** `MicroC/`, `Virtual/`

**Hand-in:** one zip named `PrgDat-07-XXX-YYY.zip` (initials/names of the group members). Own code must be annotated with comments so it is easy to tell apart from the handed-out code.

---

## 8.5

Files: `Absyn.fs`, `CLex.fsl`, `CPar.fsy`, `Comp.fs`, `Interp.fs`, test program `CEx/ex85.c`

We added conditional expressions `e1 ? e2 : e3` to micro-C. This required:

- a new expression node in the abstract syntax,
- two new tokens (`?` and `:`) in the lexer,
- a grammar rule in the parser, with precedence chosen as in C: lower than `||`, higher than assignment, and right-associative.

. We also extended the interpreter to support the new expression.
## 8.6

Files: `Absyn.fs`, `CLex.fsl`, `CPar.fsy`, `Comp.fs`, `Interp.fs`, test program `CEx/ex86.c`

We added a `switch` statement to micro-C. Each case consists of an integer constant followed by a block. As the exercise specifies, there is no fall-through, no `break` and no `default` branch. This required:

- a new statement node in the abstract syntax,
- two new keywords (`switch` and `case`) in the lexer,
- grammar rules in the parser for the switch statement and its list of cases.


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
