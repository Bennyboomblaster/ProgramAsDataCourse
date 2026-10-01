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

<!-- Files: -->

## 8.1

<!-- Files: -->

## 8.3

<!-- Files: -->

## 8.4

<!-- Files: -->
