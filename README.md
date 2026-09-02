## 2.1
For 2.1 we have changed the 'Let' in the type 'expr' to take '(string * expr) list * expr, so a single let can carry 
several bindings. In the function 'eval' we have made an inner helper function 'envMaker' which recursively goes through
the list of bindings, adds them to the environment and finally evaluates the resulting environment. 
<br> 
The expressions e1-e11 has been changed to follow the new version of binding variables with let


## 2.2
For 2.2, we have changed the 'freevars' functoin to work with the new version of 'let'. 
Here, we have made an inner helper function, which walks through the let bindings. 
If the list of the bindings is empty, we just return the free variables in the body.
Otherwise, we take the first binding (x, erhs), where we first find the free variables in 'erhs' and
then we the helper on the rest of the bindings. Then we remove x, because x is bound i.e. any x that shows up later is
not free anymore. 
Finally we combine the two lists with union so we dont get dupliactes, which gives us all the free variables of the 
let-expression. 

## 2.3
For 2.3, we have changed the 'tcomp' function on lines 259-269, to work with the new version of 'let'. 
