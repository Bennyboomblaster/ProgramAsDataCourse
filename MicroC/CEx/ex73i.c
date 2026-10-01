// Exercise 7.3 (i) -- arrsum: sum array elements via pointer return

void arrsum(int n, int arr[], int *sump) {
  int i;
  int s;
  s = 0;
  for (i = 0; i < n; i = i + 1) {
    s = s + arr[i];
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
