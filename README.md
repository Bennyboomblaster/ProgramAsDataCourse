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