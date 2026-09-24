// Exercise 7.2 (i) -- arrsum: sum array elements via pointer return

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

void main() {
  int arr[4];
  int sum;
  arr[0] = 7;
  arr[1] = 13;
  arr[2] = 9;
  arr[3] = 8;
  arrsum(4, arr, &sum);
  print sum;
}
