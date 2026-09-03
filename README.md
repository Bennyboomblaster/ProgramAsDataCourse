## 2.4
For 2.4 we have modified 'scomp' to match the new definition of Let with the help of an inner helper function. 
<br>
Then we created 'sinstrToInt' that converts a 'sinstr' to the corresponding int list using the convertion table from the 
exercise pdf. 
<br> 
at last we made the 'assemble' function which folds over a list of sinstr expressions and converting them using the 
'sinstrToInt' function, so that it can be used in the Machine.java file.

## 2.5
To create the file 'is1.txt' we used the function intsToFile with the following command: 
intsToFile (assemble (scomp e1 [])) "is1.txt";;
<br>
Then we compiled and ran the java program 'Machine.java' with following commands:
<br>
% javac Machine.java
<br>
% java Machine is1.txt
<br>
and got the result: 
<br>
Result: 34
<br>
which means our program works as intended

## 3.2

The regular expression is ``b*(ab+)*a?``

## 3.3 

let z = (17) in z + 2 * 3 end EOF

Main  

=> Expr EOF (A)

=> LET NAME EQ Expr IN Expr END EOF (F)

=> LET NAME EQ Expr IN Expr PLUS Expr END EOF (H)

=> LET NAME EQ Expr IN Expr PLUS Expr TIMES Expr END EOF (G)

=> LET NAME EQ Expr IN Expr PLUS Expr TIMES CSTINT END EOF (C)

=> LET NAME EQ Expr IN Expr PLUS CSTINT TIMES CSTINT END EOF (C)

=> LET NAME EQ Expr IN NAME PLUS CSTINT TIMES CSTINT END EOF (B)

=> LET NAME EQ LPAR Expr RPAR IN NAME PLUS CSTINT TIMES CSTINT END EOF (E)

=> LET NAME EQ LPAR CSTINT RPAR IN NAME PLUS CSTINT TIMES CSTINT END EOF (C)


