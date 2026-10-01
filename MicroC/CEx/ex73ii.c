// Exercise 7.2 (ii) -- squares: fill array with i*i, then sum with arrsum

void squares(int n, int arr[]) {
  int i;
  for (i = 0; i < n ; i = i + 1) {
    arr[i] = i * i;
  }
}

void arrsum(int n, int arr[], int *sump) {
  int i;
  int s;
  s = 0;
  for (i = 0 ; i < n ; i = i + 1) {
    s = s + arr[i];
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
