# Assignment 10

**Exercises:** 12.1, 12.2, 12.3, 12.4

**Source folders:** `MicroC/`, `Assignment10/`

---

## 12.1

Files: 

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
