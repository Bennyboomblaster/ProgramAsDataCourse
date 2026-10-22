# Assignment 7

**Exercises:** 8.5, 8.6, 9.1, 9.3

**Source folders:** `MicroC/`, `Virtual/`

**Hand-in:** one zip named `PrgDat-07-XXX-YYY.zip` (initials/names of the group members). Own code must be annotated with comments so it is easy to tell apart from the handed-out code.

---

## 8.5

Files: `Absyn.fs`, `CLex.fsl`, `CPar.fsy`, `Comp.fs`, `Interp.fs`, test program `CEx/ex85.c`

We added conditional expressions `e1 ? e2 : e3` to micro-C. This required:

- a new expression node in the abstract syntax,
- two new tokens (`?` and `:`) in the lexer,
- a grammar rule in the parser, with precedence chosen as in C: lower than `||`, higher than assignment, and right-associative.

. We also extended the interpreter to support the new expression.
## 8.6

Files: `Absyn.fs`, `CLex.fsl`, `CPar.fsy`, `Comp.fs`, `Interp.fs`, test program `CEx/ex86.c`

We added a `switch` statement to micro-C. Each case consists of an integer constant followed by a block. As the exercise specifies, there is no fall-through, no `break` and no `default` branch. This required:

- a new statement node in the abstract syntax,
- two new keywords (`switch` and `case`) in the lexer,
- grammar rules in the parser for the switch statement and its list of cases.


## 9.1

Files: `Virtual/Selsort/Selsort.il`, `Virtual/Selsort/Selsort.jvmbytecode`

See files and the attached comments.

## 9.3 (from lecture slides, `QueueWithMistake.java`)

Files: `Assignment7/QueueWithMistaek/QueueWithMistake.java` 
When we run the program with: 
```
javac QueueWithMistake.java 
java -Xmx64m QueueWithMistake.java
```
We get the expected error: 
```
Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "Thread-0"
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at QueueWithMistake.runThreads(QueueWithMistake.java:58)
        at QueueWithMistake.main(QueueWithMistake.java:28)
```

The mistake is the `dummy` field in `SentinelLockQueue`. It always points to the first node and never changes, because its marked with "final". Since every node points to the next one, the garbage collector can reach every node ever added through `dummy`, so it can never free them, even after they are removed with `get()`.

We fixed it by removing the `dummy` field and creating the sentinel node directly in `head` (lines 89–90):
```java
private Node head = new Node(-444, null), tail = head;
```
Now `head` is the only reference to the start of the queue. When `get()` moves `head` forward, nothing points to the old nodes anymore, so the garbage collector can free them.

Running it again with `java -Xmx64m QueueWithMistake.java` the program no longer runs out of memory:
```
SentinelLockQueue          1       5,85 199999994
SentinelLockQueue          2      65,10 199999994
SentinelLockQueue          3      60,63 199999994
SentinelLockQueue          4      82,39 199999994
SentinelLockQueue          5      85,85 199999994
```


