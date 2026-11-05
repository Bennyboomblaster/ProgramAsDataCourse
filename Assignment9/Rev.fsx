// Exercise 11.2

let rec rev xs =
    match xs with
    | [] -> []
    | x::xr -> rev xr @ [x];;

let rec revc xs c =
    match xs with
    | [] -> c []
    | x::xr -> revc xr (fun v -> c(v @ [x]))
    
let rec revi xs a =
    match xs with
    | [] -> a
    | x::xr -> revi xr (x :: a)
