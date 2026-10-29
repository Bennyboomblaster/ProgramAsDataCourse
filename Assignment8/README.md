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
1. Read the top of the stack and cast it to an address we can dereference with p[1] `word* p = (word*)s[sp]`
2. Check if it is a NULL pointer `if (p == 0) ...` -> If p == 0, then the list is empty, so there is no head to return. So instead it returns -1 which ends the execcode.
3. Write the value of `p[1]` (the head of the cell) into the top slot on the stack `s[sp] = (word)(p[1])` - the value replaces the address at place in the stack, so we do not make any changes to the stack pointer.

SETCAR:
1. Read the first word and store it in `v` and then decrements the stackpointer
2. Cast the new top into a cons cell address
3. Write the value of `p[1]` (the head of the cell) to be `v`


**(ii)**
<br>
**Length:** is defined as `#define Length(hdr) (((hdr)>>2)&0x003FFFFFFFFFFFFF)`. this shifts the header right by 2, removing the colour bits (gg). The tag (tttttttt) is removed with the &0x003FFFFFFFFFFFFF bit, leaving only the 'n' bit, which is the count of the number of words in the block. 
<br>
**Color:** is defined as `#define Color(hdr) ((hdr)&3)`. This only keeps the 2 lowest bits (gg). The result is 0-3, which is either White, grey, black or blue)
<br>
**Paint:** is defined as `Paint(hdr, color) (((hdr)&(0xFFFFFFFFFFFFFFFC))|(color))`. This clears the colour bits and puts in the new colour. The tag and length stay the same. 

**(iv)**
The garbage collecter `collect()` is only called in `allocate()`, and it is only called, if the `allocate()` function cannot find any free space on the freelist.
## 10.2

Files: `MicroVM/microvm.c` `MicroVM/microvm.exe` 

We implemented a mark-sweep GC in `microvm.c` (lines 508-548). `IsRef(v)` is used to define a word as a heap pointer if it isnt tagged int and isnt NIL.
<br>
`markPhase`: goes through `s[0]` to `s[sp]` and call `mark` on every pointer. 
<br>
`mark`: if the block is already black it returns. Otherwise paint it black and call `mark` on every field that is a pointer
<br> 
`sweepPhase`: Go through every block in the heap. White blocks are garbage, so they get painted blue and is put on the freelist. 
Black blocks are painted white for the next GC. Blue blocks are skipped. 
<br> 
we compiled the VM in the `MicroVM` folder with

```
clang --target=x86_64-pc-windows-msvc -Wall microvm.c -o microvm.exe
```
and compiled the List-C programs in the `ListC` folder with:

```
dotnet run ListcEx/ex30.lc
dotnet run ListcEx/ex35.lc
dotnet run ListcEx/ex36.lc
```

`ex30`: Without the GC it stops with an out of memory error. With the GC, it now counts all the way down to 1. The last part of the output is:
```
GC[M,BS]Heap: 6667 blocks (13333 words); of which 6666 free (13331 words, largest 2 words); 0 orphans
249 248 247 246 245 244 243 242 241 240 239 238 237 236 235 234 233 232 231 230 229 228 227 226 225 224 223 222 221 220 219 218 217 216 215 214 213 212 211 210 209 208 207 206 205 204 203 202 201 200 199 198 197 196 195 194 193 192 191 190 189 188 187 186 185 184 183 182 181 180 179 178 177 176 175 174 173 172 171 170 169 168 167 166 165 164 163 162 161 160 159 158 157 156 155 154 153 152 151 150 149 148 147 146 145 144 143 142 141 140 139 138 137 136 135 134 133 132 131 130 129 128 127 126 125 124 123 122 121 120 119 118 117 116 115 114 113 112 111 110 109 108 107 106 105 104 103 102 101 100 99 98 97 96 95 94 93 92 91 90 89 88 87 86 85 84 83 82 81 80 79 78 77 76 75 74 73 72 71 70 69 68 67 66 65 64 63 62 61 60 59 58 57 56 55 54 53 52 51 50 49 48 47 46 45 44 43 42 41 40 39 38 37 36 35 34 33 32 31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 
Result value: 0 
Used 1625 cpu milli-seconds
Number of GC: 150
```

`ex35 and ex36` are run with a heapsize of 1000, otherwise they never trigger af GC. This was changed in `microvm.c` on line 140 and then set back to 20000 after the tests were run.
These are the results for `ex35`: 
```
33 33 GC[M,BS]Heap: 333 blocks (666 words); of which 330 free (660 words, largest 2 words); 1 orphans
44 44 
Result value: 0 
Used 0 cpu milli-seconds
Number of GC: 1
```
and for `ex36`:
```
1 GC[M,BS]Heap: 333 blocks (666 words); of which 331 free (662 words, largest 2 words); 1 orphans
1 
Result value: 0 
Used 0 cpu milli-seconds
Number of GC: 1
```
## 10.3

Files: `MicroVM/microvm.c` (`sweepPhase`, lines 529-553)

When `sweepPhase` finds a white block of length `n` at `p`, it checks if the block at `p + 1 + n` is also white, and if so joins them into one blue block of length `n + 1 + m` on the freelist.

As in 10.2, `ex35` and `ex36` were run with `HEAPSIZE 1000`.

`ex35`:
```
33 33 GC[M,BS]Heap: 168 blocks (831 words); of which 165 free (825 words, largest 5 words); 1 orphans
44 44 
Result value: 0 
Number of GC: 1
```
`ex36`:
```
1 GC[M,BS]Heap: 168 blocks (831 words); of which 166 free (827 words, largest 5 words); 1 orphans
1 
Result value: 0 
Number of GC: 1
```
Output is still correct, and the heap now has 168 blocks instead of 333 (largest free block 5 words instead of 2).



