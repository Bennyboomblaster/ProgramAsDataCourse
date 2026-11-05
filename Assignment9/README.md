# Assignment 9

**Exercises:** 11.1, 11.2, 11.3, 11.4, 11.8

**Source folders:** `Cont/`, `Assignment9/`

---

## 11.1

Files: `Assignment9/Lenc.fsx`

**(i)**
<br>
This is the CPS version of len:
```fsharp
let rec lenc (xs : 'a list) (c : int -> 'b) : 'b = 
    match xs with 
    | [] -> c 0
    | x :: xr -> lenc xr (fun r -> c(1 + r))
```
When calling `lenc [2; 5; 7] id` we get `val it: int = 3` which is the expected result. 
When calling `lenc [2; 5; 7] (printf "The answer is ’%d’\n")` we get `The answer is ’3’
val it: unit = ()`
<br>

**(ii)**
<br>
Assuming we have to call the function with the same list ([2; 3; 5]) instead of xs (which will give an error), we get the following result: `val it: int = 6` which is expected, because the length of  the list is 3 and the continuation function acts on the final result.
<br>

**(iii)**
<br>
This is the tail-recursive version of len with an accumulating parameter:
```fsharp
let rec leni (xs : int list) (acc : int) : int =
    match xs with
    | [] -> acc
    | _ :: xs -> leni xs (acc + 1)
```
When calling `leni [2; 3; 5] 0` we get `val it: int = 3` which is the expected result.
<br>
Both leni and lenc are tail recursive functions which pass an extra argument that grows on every call. 

## 11.2

Files: ``

## 11.3

Files: `Cont/prod.fsx`

This is the CPS version of prod:
```fsharp
let rec prodc xs k =
    match xs with
    | [] -> k 1
    | x::xr -> prodc xr (fun v -> k(v*x))
```
The base case passes 1 (the product of the empty list) to the continuation. In the recursive case, the work that `prod` leaves waiting on the stack (`x * ...`) is moved into a new continuation `fun v -> k(v*x)`, so the recursive call is a tail call.
<br>
When calling `prodc [2; 3; 5] id` we get `val it: int = 30` which is the expected result.
When calling `prodc [2; 3; 5] (printf "The answer is '%d'\n")` we get `The answer is '30'
val it: unit = ()`
<br>

## 11.4

Files: `Cont/prod.fsx`

**(i)**
<br>
```fsharp
let rec prodc xs k =
    match xs with
    | [] -> k 1
    | 0 :: _ -> 0
    | x::xr -> prodc xr (fun v -> k(v*x))
```
On a 0 we return 0 without calling `k`, skipping all pending multiplications. This is only correct if `k` just multiplies (so `k 0 = 0`).
<br>
`prodc [2; 3; 5] id` gives `30` and `prodc [2; 0; 5] id` gives `0`. The `printf` call from 11.1 no longer type checks, since the zero case forces `k : int -> int`.
<br>

**(ii)**
<br>
```fsharp
let rec prodi xs acc =
    match xs with
    | []    -> acc
    | 0::_ -> 0
    | x::xr -> prodi xr (x * acc)
```
`prodi [2; 3; 5] 1` gives `30` and `prodi [2; 0; 5] 1` gives `0`.

## 11.8

Files: ``
