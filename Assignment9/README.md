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

Files: ``

## 11.4

Files: ``

## 11.8

Files: ``
