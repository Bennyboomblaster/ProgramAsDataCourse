// Ex 8.5: conditional expressions e1 ? e2 : e3
void main(int n) {
    int x;
    int y;
    x = 0;
    y = 0;
    print n > 0 ? 1 : 2;                  // 1 if n>0 else 2
    print n == 0 ? 10 : n < 0 ? 20 : 30;  // right-assoc: 10, 20 or 30
    print n > 0 || n < -5 ? 100 : 200;    // ?: binds weaker than ||
    n > 0 ? ++x : ++y;                    // only one branch is evaluated
    print x;
    print y;
    x = n > 0 ? n * 2 : 0 - n;            // ?: binds stronger than =
    print x;
    println;
}
