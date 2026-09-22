
## 4.5
Files:
FunPar.fsy,
FunLex.fsi

We have extended the micro-ML lexer and parser to support && and || as infix operators with appropriate precedence. The parser rules desugar e1 && e2 into 
``If(e1, e2, CstB false)`` and e1 || e2 into ``If(e1,
CstB true, e2).``

## 5.7
Files:
TypeInference.fs

We extended the type checker in `TypeInference.fs` to support list types by adding `TypL of typ` to the `typ` type.
We updated `freeTypeVars` to recurse into list element types, `typeToString` and `showType` to print list types, `unify` to unify two list types. `copyType` to substitute through list types, and added a `"::"` case to the `Prim` match.

## 6.1
Files /Fun1

The third function returns a Closure because add 2 only applies add to its first argument x=2. The body of add is ``let f y = x+y in f end``, which evaluates to the function f, which is a closure.   
Since there is no second argument, nothing further is evaluated and the closure is returned as it is. This shows that functions are first-class values, since they can now be the result of an expression.

run (fromString @"let add x = let f y = x+y in f end
        in add 2 5 end");;

run (fromString @"let add x = let f y = x+y in f end 
in let addtwo = add 2
    in addtwo 5 end
end");;

run (fromString @"let add x = let f y = x+y in f end
in let addtwo = add 2
    in let x = 77 in addtwo 5 end
    end
end");;


run (fromString @"let add x = let f y = x+y in f end
in add 2 end");;

## 6.2
Files:
Fun2/Absyn.fs, Fun2/HigherFun.fs, Fun2/FunLex.fsl, Fun2/FunPar.fsy

We extended the micro-ML language with anonymous functions by adding ``Fun of string * expr`` to the abstract syntax and a new closure value ``Clos of string * expr * value env`` to the evaluator. 
The lexer extended with the ``fun`` keyword and ``->`` token, and the parser extended with the rule ``FUN NAME ARROW Expr`` which produces ``Fun($2, $4)``. 
Evaluating a ``Fun(x, body)`` produces  ``Clos(x, body, env)``, and the ``Call`` case was extended to handle ``Clos`` by binding the argument without adding a self-reference, 
since anonymous functions cannot recurse by name.

## 6.3
Files:
Fun2/FunLex.fsl, Fun2/FunPar.fsy

The concrete syntax for anonymous functions added in 6.2 allows the micro-ML examples from 6.1 to be written using ``fun x -> expr`` instead of nested let-bindings. Both ``let add x = fun y -> x+y in add 2 5 end`` and ``let add = fun x -> fun y -> x+y in add 2 5 end`` evaluate to ``Int 7``, confirming that curried anonymous functions work correctly.