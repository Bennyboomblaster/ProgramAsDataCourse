let rec len xs =
    match xs with
    | [] -> 0
    | x::xr -> 1 + len xr;;

let rec lenc (xs : 'a list) (c : int -> 'b) : 'b = 
    match xs with 
    | [] -> c 0
    | _ :: xs -> lenc xs (fun r -> c(1 + r))

let rec leni (xs : int list) (acc : int) : int =
    match xs with
    | [] -> acc
    | _ :: xs -> leni xs (acc + 1)