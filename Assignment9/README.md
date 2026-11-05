# Assignment 9

**Exercises:** 11.1, 11.2, 11.3, 11.4, 11.8

**Source folders:** `Cont/`, `Assignment9/`

---

## 11.1

Files: `Assignment9/Len.fsx`

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

Files: `Assignment9/Rev.fsx`

**(i)**
<br>
This is the CPS version of rev:
```fsharp
let rec revc xs c =
    match xs with
    | [] -> c []
    | x::xr -> revc xr (fun v -> c(v @ [x]))
```
When we call `revc [1;2;3] id` we get `val it: [3;2;1]`, which is the expected result.
<br>

**(ii)**
<br>
If we call `revc xs (fun v -> v @ v)` we would append the full list to the full list, so `revc [1;2;3] (fun v -> v @ v)` would give us `val it: [3;2;1;3;2;1]`

**(iii)**
<br> 
This is the tail recursive version of rev with an accumulating parameter:
```fsharp
let rec revi xs a =
    match xs with
    | [] -> a
    | x::xr -> revi xr (x :: a)
```

## 11.3

Files: ``

## 11.4

Files: ``

## 11.8

Files: `Icon.fs`

**(i)**
<br>
To get `3,5,7,9` we can write the following expression: `run (Every(Write(Prim("+", Prim("*", CstI 2, FromTo(1, 4)), CstI 1))));;`
<br>
To get `21 22 31 32 41 42` we can write the following expression: 
```fsharp
run (Every(Write(Prim("+", Prim("*", CstI 10, FromTo(2, 4)), FromTo(1, 2)))));;
```

**(ii)**
<br>
To get the least multiple of 7 that is greater than 50, we can write the following expression: `run (Write(Prim("<", CstI 50, Prim("*", CstI 7, FromTo(1, 10)))));;` which gives 56. 

**(iii)**
<br> 
We have added Prim1 to the `type expr` and then extended the `eval` with the following: 
```fsharp
| Prim1(ope, e) -> 
        eval e (fun v1 -> fun econt1 -> 
            match (ope, v1) with
            | ("sqr", Int i) -> 
                cont (Int(i*i)) econt1
            | ("even", Int i) -> 
                if i % 2 = 0 then
                    cont (Int i) econt1
                else
                    econt1 ()
            | _ -> Str "Unknown prim1. idiot.")
            econt
```
We have tested the two additions with the following: 
```fsharp
run (Every(Write(Prim1("sqr", FromTo(1, 4)))));; 
1 4 9 16 val it: value = Int 0

run (Every(Write(Prim1("even", FromTo(1, 10)))));;
2 4 6 8 10 val it: value = Int 0
```
Which is the expected results, meaning our additions work.

**(iv)**
<br>
We extended the `eval` with the following: 
```fsharp 
| ("multiples", Int i) -> 
        let rec loop n = 
              cont (Int n) (fun () -> loop (n+i))
        loop i
```
We then tested with the following:
```fsharp
run (Every(Write(Prim1("multiples", CstI 3))));;
```
This runs forever as expected.

