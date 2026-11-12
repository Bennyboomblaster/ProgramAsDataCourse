// micro-C example for Ex 12.3 -- conditional expressions e1 ? e2 : e3

void main(int n) {
  print true ? 1111 : 2222;
  print false ? 1111 : 2222;
  print n ? 1111 : 2222;
  print n < 10 ? n < 5 ? 1 : 2 : 3;
}
