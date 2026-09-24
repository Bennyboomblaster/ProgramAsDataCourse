// Exercise 7.2 (ii) -- squares: fill array with i*i, then sum with arrsum

void squares(int n, int arr[]) {
  int i;
  i = 0;
  while (i < n) {
    arr[i] = i * i;
    i = i + 1;
  }
}

void arrsum(int n, int arr[], int *sump) {
  int i;
  int s;
  i = 0;
  s = 0;
  while (i < n) {
    s = s + arr[i];
    i = i + 1;
  }
  *sump = s;
}

void main(int n) {
  int arr[20];
  int sum;
  squares(n, arr);
  arrsum(n, arr, &sum);
  print sum;
}
