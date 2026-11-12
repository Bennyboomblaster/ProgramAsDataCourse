# Assignment 10

**Exercises:** 12.1, 12.2, 12.3, 12.4

**Source folders:** `MicroC/`, `Assignment10/`

---

## 12.1

Files: `MicroC/Contcomp.fs`
<br>

We added the functions `addIFZERO` and `addIFNZRO` and replaced them anywhere in our code 
where we used `IFZERO labelse ::` or `IFNZRO ...` to ensure that unnecessary machine code is not generated.
These make sure that we invert the test and switch the label they go to along with removing
the `GOTO` instruction, if we encounter a structure that looks like `IFZERO L3; GOTO L2; Label L3`. 
Because our compiler works backwards, when we cons a conditional jump, the part we jump over
already exists, so we can inspect it. If the pattern doesn't match anything, they just return
the original cons.
<br>

These are the added functions:
```fsharp
let addIFZERO lab3 c =
    match c with
    | GOTO lab2 :: Label l :: c1 when l = lab3 -> IFNZRO lab2 :: Label lab3 :: c1 
    | _ -> IFZERO lab3 :: c
    
let addIFNZRO lab3 c =
    match c with
    | GOTO lab2 :: Label l :: c1 when l = lab3 -> IFZERO lab2 :: Label lab3 :: c1
    | _ -> IFNZRO lab3 :: c
```

## 12.2

Files: `MicroC/Contcomp.fs`
<br>

We extended `addCST` so comparisons between two constants are computed at compile time
and replaced by `1` (true) or `0` (false). Because the compiler works backwards, the 
right operand is already emitted as `CSTI j` when the left constant `i` is added, so
we match `CSTI j :: LT`, `CSTI j :: SWAP :: LT` and `CSTI j :: EQ`. These are the additions: 

```fsharp
| (i, CSTI j :: LT          :: c1) -> addCST (if i < j then 1 else 0) c1  (* i <  j *)
| (i, CSTI j :: SWAP :: LT  :: c1) -> addCST (if j < i then 1 else 0) c1  (* i >  j *)
| (i, CSTI j :: EQ          :: c1) -> addCST (if i = j then 1 else 0) c1  (* i == j *)
```

## 12.3

Files:

## 12.4

Files: 
