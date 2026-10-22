// Ex 8.6: switch statement (no fall-through, no break, no default)

// Book example: days in a month (leap year if y % 4 == 0)
int days(int month, int y) {
    int d;
    d = 0;
    switch (month) {
        case 1:  { d = 31; }
        case 2:  { d = 28; if (y % 4 == 0) d = 29; }
        case 3:  { d = 31; }
        case 4:  { d = 30; }
    }
    return d;
}

// Return from inside a case, with a local declared in the case block
int classify(int n) {
    switch (n) {
        case 0: { return 100; }
        case 1: { int t; t = 7; return t * 10 + n; }
    }
    return -1;
}

void main(int n) {
    int count;
    print days(1, 2023);       // 31
    print days(2, 2023);       // 28
    print days(2, 2024);       // 29
    print days(4, 2024);       // 30
    print days(9, 2024);       // 0  (no matching case)
    println;
    print classify(0);         // 100
    print classify(1);         // 71
    print classify(5);         // -1
    println;
    // Nested switch, and the switch expression is evaluated only once
    count = 0;
    switch (++count + n) {
        case 1: { print 1; }
        case 2: { switch (n) { case 1: { print 21; } case 2: { print 22; } } }
        case 3: { print 3; }
    }
    print count;               // 1
    println;
}
