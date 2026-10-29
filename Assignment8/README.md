# Assignment 8

**Exercises:** 10.1, 10.2, 10.3

**Source folders:** `ListC/`, `MicroVM/`

**Hand-in:** one zip named `PrgDat-08-XXX-YYY.zip` (initials/names of the group members). Own code must be annotated with comments so it is easy to tell apart from the handed-out code.

---

## 10.1

Files: `microvm.c`

ADD:
1. Read the two top words on the stack -> (right = `s[sp]` & left = `s[sp-1]`)
2. UnTag both words to get their true value -> `(Untag(s[sp-1]) + Untag(s[sp])`
3. Add the values
4. Tag the result to ensure that it will be read as an integer on the stack -> `Tag(...)`
5. Write the result into `s[sp-1]` and reduce the stack pointer, so we point to the result
<br>

CSTI i:
1. At the current word, `CSTI`, we read the constant `i` from the next word -> `(p[pc++])`
2. Tag it so it will appear as a tagged integer on the stack -> `Tag(...)`
3. Write this into `s[sp+1]` and increment the stack pointer, so we point at the new tagged integer `i`

NIL:
1. Write NILVALUE into `s[sp+1]` and increment the stack pointer so we point to the NILVALUE. Nothing is tagged, so the NILVALUE is read as a pointer on the stack.
2. CSTI tags its operand, so when we tag 0 we leave a 1 on the stack since `Tag(0) = 1`. NIL is just a pointer pointing at nothing.

IFZERO:
1. Read the top word into local variable `v` and decrement the stack pointer
2. In an inner condition, check whether `v` is an integer with `IsInt(v)`
3. If v is an integer, then we can test if it is 0 by untagging it and seeing whether v is 0 -> `Untag(v) == 0`
4. If v is a pointer, then we can check if it is a NULL pointer with -> `v == 0`
5. In an outer condition, if `true` then we jump to the address stored in `p[pc]`, and if `false` we continue to the next instruction `pc+1`

CONS:
1. Acquire space on the heap by using `allocate(...)` where we create a word for the header (including tag, length, and color) and two words for the data fields `p[1] & p[2]`
2. In the newly allocated space `allocate()` automatically allocates the first index to the header and two open slots for two more words, which CONS then writes to the head `p[1] = (word)s[sp-1]` and tail `p[2] = (word)s[sp]`
3. Write the pointer to the CONS cell into the second slot from the top on the stack `s[sp-1] = (word)p`. The word is untagged, which means it appears on the stack as a pointer, rather than an integer.
4. Decrement the stack pointer to make the new cell the top of the stack

CAR:
1.

## 10.2

Files: ``

## 10.3

Files: ``



