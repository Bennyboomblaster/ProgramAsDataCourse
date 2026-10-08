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

<!-- Files: -->

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

<!-- Files: -->
