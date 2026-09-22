## 4.5

We have extended the micro-ML lexer and parser to support && and || as infix operators with appropriate precedence. The parser rules desugar e1 && e2 into 
``If(e1, e2, CstB false)`` and e1 || e2 into ``If(e1,
CstB true, e2).``

## 5.7
We extended the type checker in `TypeInference.fs` to support list types by adding `TypL of typ` to the `typ` type.
We updated `freeTypeVars` to recurse into list element types, `typeToString` and `showType` to print list types, `unify` to unify two list types. `copyType` to substitute through list types, and added a `"::"` case to the `Prim` match.