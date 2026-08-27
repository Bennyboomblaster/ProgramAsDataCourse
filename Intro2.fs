(* Programming language concepts for software developers, 2010-08-28 *)

(* Evaluating simple expressions with variables *)

module Intro2

(* Association lists map object language variables to their values *)

let env = [("a", 3); ("c", 78); ("baf", 666); ("b", 111)];;

let emptyenv = []; (* the empty environment *)

let rec lookup env x =
    match env with 
    | []        -> failwith (x + " not found")
    | (y, v)::r -> if x=y then v else lookup r x;;

let cvalue = lookup env "c";;


(* Object language expressions with variables *)


type expr = 
    | CstI of int
    | Var of string
    | Prim of string * expr * expr
    | If of expr * expr * expr



(*
let e1 = CstI 17;;

let e2 = Prim("+", CstI 3, Var "a");;

let e3 = Prim("+", Prim("*", Var "b", CstI 9), Var "a");;

//exercise 1.1.2
let e4 = Prim("min", CstI 5, Var "a")

let e5 = Prim("max", CstI 5, Var "a")

let e6 = Prim("==", CstI 10, Prim("+", CstI 5, CstI 5))

let e7 = Prim("==", CstI 10, Prim("*", CstI 5, CstI 5))

// exercise 1.2.2
let e8 = Sub(Var "v", Add(Var "w", Var "z"))

let e9 = Mul(CstI 2, Sub(Var "v", Add(Var "w", Var "z")))

let e10 = Add(Var "x", Add(Var "y", Add(Var "z", Var "v")))
*)
(* Evaluation within an environment *)

let rec eval e (env : (string * int) list) : int =
    match e with
    | CstI i            -> i
    | Var x             -> lookup env x 
    | If(e1, e2, e3) -> 
        let evale1 = eval e1 env
        if evale1 <> 0 then eval e2 env else eval e3 env
    | Prim(ope, e1, e2) -> 
        let i1 = eval e1 env
        let i2 = eval e2 env
        match ope with
            |"+" -> i1 + i2
            |"*" -> i1 * i2
            |"-" -> i1 - i2
            |"max" -> if i1 > i2 then i1 else i2
            |"min" -> if i1 < i2 then i1 else i2
            |"==" -> if i1 = i2 then 1 else 0
            |_ -> failwith  "unknown primitive"

type aexpr = 
    | CstI of int
    | Var of string
    | Add of aexpr * aexpr
    | Mul of aexpr * aexpr
    | Sub of aexpr * aexpr

let rec fmt (a : aexpr) : string = 
    match a with
    | CstI i -> string i
    | Var x -> string x
    | Add(a1, a2) -> "(" + fmt a1 + " + " + fmt a2 + ")"
    | Sub(a1, a2) -> "(" + fmt a1 + " - " + fmt a2 + ")"
    | Mul(a1, a2) -> "(" + fmt a1 + " * " + fmt a2 + ")"

let rec simplify (e : aexpr) : aexpr =
    match e with
    | Add(CstI 0, a) -> a 
    | Add(a, CstI 0) -> a
    | Sub(a, CstI 0) -> a
    | Mul(CstI 1, a) -> a
    | Mul(a, CstI 1) -> a
    | Mul(CstI 0, a) -> CstI 0
    | Mul(a, CstI 0) -> CstI 0
    | Sub(a1, a2) when a1 = a2 -> CstI 0
    | _ -> e   
 
let rec diff (s : string) (e : aexpr) : aexpr =
    match e with
    | CstI x -> CstI 0
    | Var y when y <> s -> CstI 0
    | Var x -> CstI 1
    | Add (e1, e2) -> 
        let d1 = diff s e1 
        let d2 = diff s e2
        Add(d1, d2)
    | Sub(e1, e2) ->
        let d1 = diff s e1
        let d2 = diff s e2
        Sub(d1, d2)
    | Mul(e1, e2) ->
        let d1 = diff s e1
        let d2 = diff s e2
        Add(Mul(d1,e2), Mul(e1,d2)) 

(*
let e1v  = eval e1 env;;
let e2v1 = eval e2 env;;
let e2v2 = eval e2 [("a", 314)];;
let e3v  = eval e3 env;;
*)
(*
// 1.1.2
let ev4 = eval e4 env;;
let ev5 = eval e5 env;;
let ev6 = eval e6 env;;
let ev7 = eval e7 env;;
*)