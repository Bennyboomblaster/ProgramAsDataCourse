# Assignment 7

**Due:** _TODO_

**Exercises:** 8.5, 8.6, 9.1, 9.3

**Source folders:** `MicroC/`, `Virtual/`

**Hand-in:** one zip named `PrgDat-07-XXX-YYY.zip` (initials/names of the group members). Own code must be annotated with comments so it is easy to tell apart from the handed-out code.

---

## 8.5

Files: _TODO_

_TODO: description of the solution and how it was tested._

## 8.6

Files: _TODO_

_TODO: description of the solution and how it was tested._

## 9.1

Files: _TODO_

_TODO: description of the solution and how it was tested._

## 9.3 (from lecture slides, `QueueWithMistake.java`)

Files: `QueueWithMistake.java` (original), `Queue.java` (fixed) _(TODO: adjust names)_

**Problem:** the program keeps references that prevent the garbage collector from reclaiming memory, so it uses excessive memory and may run out of memory depending on the machine.

**Cause:** _TODO: which reference is kept alive and why (see comments in the file)._

**Fix:** _TODO: what was changed._

**Demonstration:** _TODO: run both versions and compare memory use, e.g._

```
javac QueueWithMistake.java && java -Xmx64m QueueWithMistake
javac Queue.java            && java -Xmx64m Queue
```

_TODO: observations (e.g. jconsole/VisualVM, `-verbose:gc`, or `Runtime` memory printouts)._
