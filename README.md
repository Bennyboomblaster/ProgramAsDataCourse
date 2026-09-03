
## 1.2
(i)
We made the aexpr by having all the arithmetic actions being aexpr * aexpr 

(ii)

v − (w + z)  = Sub(Var "v", Add(Var "w", Var "z"))

2 ∗ (v − (w + z)) =  Mul(CstI 2, Sub(Var "v", Add(Var "w", Var "z")))

x + y + z + v.Add(Var "x", Add(Var "y", Add(Var "z", Var "v")))

(iii)
We just followed the structure of eval and made  a recursive function for printing

(iv)
We just used pattern-matching to follow the table of simplifications.

(v)
We just made sure to cover the different differentational rules, so they are all covered through recursion on all the different aexpr's. E.g. ```add```We use differentation on both sides of the aexpr and then evalute by adding the two values we got from doing differential on the aepxr's 

## 2.1
For 2.1 we have changed the 'Let' in the type 'expr' to take '(string * expr) list * expr, so a single let can carry 
several bindings. In the function 'eval' we have made an inner helper function 'envMaker' which recursively goes through
the list of bindings, adds them to the environment and finally evaluates the resulting environment. 
<br> 
The expressions e1-e11 has been changed to follow the new version of binding variables with let. 
<br>
To let the load the file into dotnet fsi interactive environment, we have commented out all of the functions that still 
follows the old definition of Let, since we have not been asked to change these in the exercise descriptions. 


## 2.2
For 2.2, we have changed the 'freevars' functoin to work with the new version of 'let'. 
Here, we have made an inner helper function, which walks through the let bindings. 
If the list of the bindings is empty, we just return the free variables in the body.
Otherwise, we take the first binding (x, erhs), where we first find the free variables in 'erhs' and
then we the helper on the rest of the bindings. Then we remove x, because x is bound i.e. any x that shows up later is
not free anymore. 
Finally we combine the two lists with union so we don't get dupliactes, which gives us all the free variables of the 
let-expression. 

## 2.3
For 2.3, we have changed the 'tcomp' function on lines 259-269, to work with the new version of 'let'. Since 'texpr' 
and 'teval' still only supports single-binding Tlet, we decompose a multi-binding Let into a chain of nested Tlets.
<br> 
we made an inner helper function that goes through the bindings one by one. If there are no more bindings left, the 
body is compiled. Otherwise, we take the first binding (x, erhs), compile 'erhs' and wrap it in a 'TLet'. 
Then we recursively call the helper again on the rest of the bindings, but this time add x to the environment so the 
next bindigns know that x exists. 
<br>
The result is a bunch of TLets, where the first binding sits on the outside and the body ends up on the inside. 
