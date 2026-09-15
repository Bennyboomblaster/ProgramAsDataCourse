## Exercise 3.5
We generated the lexer and the parser. There after we tried to use ```FromString``` on the different strings, and saw how it created the expressions from strings with the right syntax and failed when the syntax werent true to the parser. Some other expressions we created were:
<br>
```fromString "2 * (3 + 4)";;```
<br>
```fromString "let x = 1 in let x = 2 in x + x end end";;```
<br>
```fromString "let x = 5 in y + x end";;```
<br> 
