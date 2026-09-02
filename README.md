## 2.1
We changed the let from being a ```string * expr * expr``` to```(string * expr) list * expr```
so that there in the let binding is included a list of tuples containing the variable names and the expr they are connected to.

In the eval we changed the let case to have a recursive function inside it, where it recursive through the list of string * expr and evaluates the expr assosiated with the variable so that the right enviroments can be used when the expr is to be calculated. 