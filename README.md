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

## 3.7
In the Absyn.fs file we added "If" to the syntax. In the ExprLex.fsl we added "if", "then", and "else" to the keywords.
In ExprPar.fsy we added the "IF THEN ELSE" token as well as placing the else as the lowest precedence. Finally, we added the structure of an if, then, else among with the other expressions in the bottom of the ExprPar.fsi file.

## 4.1
We followed the instructions in the handed out README.

## 4.2
In the Fun.fs file, we have added the function "sum", which recursively sums every number from 1000 down to 1 decrementing by n-1 every time.
We also added the function "pow", which takes the number three and puts it to the power of 8. Again, we recursively multiply and decrement at each step.
We then combined the two functions, so we first put 3 to the power of 11 and then used sum to add every iteration at the end.
Finally, we added a "sumEights" function, which recurses down from 10, adding ``n^8 + sumEights(n-1)`` at each step. So at each step the number decrements by 1 while still being lifted to the power of 8. 

## 4.3
We changed it so that Letfun now holds a string list of parameters instead of a single string, and Call now holds an expr list of arguments instead of a single expr. Then we also changed the Closure  
type to store string list, and in the evaluator Call now evaluates all arguments and uses List.zip to pair each parameter name with its value when building the function body environment

## 4.4
We changed it so that the parser now collects all parameter names into a string list for Letfun instead of only accepting a single name. Then we also changed AppExpr so that f a b c produces Call(f,  
[a; b; c]) instead of the curried Call(Call(Call(f, a), b), c) which the evaluator couldn't handle