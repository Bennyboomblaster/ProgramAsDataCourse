// micro-C example for Ex 12.4 -- && and || compiled as conditional expressions

int f(int x) {
  print x;
  return x;
}

void main(int n) {
  println;
  print f(0) && f(1);      // f(1) is not called
  println;
  print f(5) && f(0);      // both called
  println;
  print f(5) || f(3);      // f(3) is not called
  println;
  print f(0) || f(4);      // both called
  println;
  if (n > 0 && n < 10) print 77; else print 88;
  println;
}
