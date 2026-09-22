## 4.5

We have extended the micro-ML lexer and parser to support && and || as infix operators with appropriate precedence. The parser rules desugar e1 && e2 into 
``If(e1, e2, CstB false)`` and e1 || e2 into ``If(e1,    
CstB true, e2).``