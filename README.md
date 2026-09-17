## 3.5
We generated the lexer and the parser. There after we tried to use ```FromString``` on the different strings, and saw how it created the expressions from strings with the right syntax and failed when the syntax werent true to the parser. Some other expressions we created were:
<br>
```fromString "2 * (3 + 4)";;```
<br>
```fromString "let x = 1 in let x = 2 in x + x end end";;```
<br>
```fromString "let x = 5 in y + x end";;```
<br> 

## 3.6 
First we added Expr.fs to the parse.fsproj file in order to compile it properly. Then we added the function ``compString`` at the end of the Expr.fs file. This function first parses the string to AST, then compiles the AST to stack-machine instructions.
<br>
We tested this with the following function calls: 
<br>
``compString "1 + 2 * 3";;``
<br>
``val it: sinstr list = [SCstI 1; SCstI 2; SCstI 3; SMul; SAdd]``
<br>
<br>
``compString "let z = 17 in z + z end";;``
<br>
``val it: sinstr list = [SCstI 17; SVar 0; SVar 1; SAdd; SSwap; SPop]``
<br>
<br>
``compString "let x = 1 in let y = 2 in x + y end end";;``
<br>
``val it: sinstr list =
  [SCstI 1; SCstI 2; SVar 1; SVar 1; SAdd; SSwap; SPop; SSwap; SPop]
``
4.3
 We changed it so that Letfun now holds a string list of parameters instead of a single string, and Call now holds an expr list of arguments instead of a single expr. Then we also changed the Closure  
type to store string list, and in the evaluator Call now evaluates all arguments and uses List.zip to pair each parameter name with its value when building the function body environment
4.4
We changed it so that the parser now collects all parameter names into a string list for Letfun instead of only accepting a single name. Then we also changed AppExpr so that f a b c produces Call(f,  
[a; b; c]) instead of the curried Call(Call(Call(f, a), b), c) which the evaluator couldn't handle