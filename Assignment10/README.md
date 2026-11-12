# Assignment 10

**Exercises:** 12.3, 12.4

**Source folders:** `MicroC/`

---

## 12.3

Files: `MicroC/Contcomp.fs` (lines 305-311), `MicroC/CEx/ex123.c`

`Cond` was already in the abstract syntax, lexer and parser from exercise 8.5, so we only extended `cExpr` in `Contcomp.fs`:
```fsharp
| Cond(e1, e2, e3) ->
  let (jumpend, c1) = makeJump c
  let (labelse, c2) = addLabel (cExpr e3 varEnv funEnv c1)
  cExpr e1 varEnv funEnv
    (IFZERO labelse
       :: cExpr e2 varEnv funEnv (addJump jumpend c2))
```
It follows the same scheme as `If` in `cStmt`, except each branch leaves a value on the stack. Because `IFZERO` is consed directly onto the code, `addCST` removes the test when `e1` is a constant, and `addJump` removes the dead branch.
<br>
Calling `cExpr` directly gives:
```fsharp
cExpr (Cond(CstI 1, CstI 1111, CstI 2222)) ([], 0) [] [];;
val it: Machine.instr list = [CSTI 1111; GOTO "L0"; Label "L1"; CSTI 2222; Label "L0"]

cExpr (Cond(CstI 0, CstI 1111, CstI 2222)) ([], 0) [] [];;
val it: Machine.instr list = [Label "L3"; CSTI 2222; Label "L2"]
```
The test is removed in both cases. The unreachable `Label L1; CSTI 2222` can't be removed without modifying already generated code.
<br>
We also tested `CEx/ex123.c` (variable and nested conditionals) with `machine.exe` for n = 0, 1, 7 and 12, which gave the expected results.

## 12.4

Files: `MicroC/CPar.fsy` (lines 140-141), `MicroC/CEx/ex124.c`

We changed the parser to build `Cond` instead of `Andalso`/`Orelse`:
```
| Expr SEQAND Expr    { Cond($1, $3, CstI 0) }   /* e1 && e2 = e1 ? e2 : 0 */
| Expr SEQOR  Expr    { Cond($1, CstI 1, $3) }   /* e1 || e2 = e1 ? 1 : e2 */
```
`CEx/ex124.c` checks that the operators still short-circuit, and `ex13.c` gives the same output as before (`1892 1896 1904 ... 1996 2000` for n = 2000).
<br>

Old compilation of `ex13.c` (62 instructions):
```fsharp
... CSTI 4; MOD; IFNZRO "L3"; GETBP; CSTI 1; ADD; LDI; CSTI 100; MOD; IFNZRO "L4"; GETBP;
 CSTI 1; ADD; LDI; CSTI 400; MOD; IFNZRO "L3"; Label "L4"; GETBP; CSTI 1; ADD; LDI; PRINTI; ...
```
New compilation (69 instructions):
```fsharp
... CSTI 4; MOD; IFNZRO "L5"; GETBP; CSTI 1; ADD; LDI; CSTI 100; MOD; IFZERO "L6"; CSTI 1;
 GOTO "L4"; Label "L6"; GETBP; CSTI 1; ADD; LDI; CSTI 400; MOD; NOT; GOTO "L4";
 Label "L5"; CSTI 0; Label "L4"; IFZERO "L3"; GETBP; CSTI 1; ADD; LDI; PRINTI; ...
```
**Code quality:** The new code is worse. The old `Andalso`/`Orelse` look at the following code, and when it is a conditional jump they jump straight to the target without computing a value. Our `Cond` always computes 0/1 on the stack (`CSTI 1`, `NOT`, `CSTI 0`) and then tests it again with `IFZERO "L3"`, which gives extra instructions in every loop iteration. The approach is simpler, but loses this optimization.
