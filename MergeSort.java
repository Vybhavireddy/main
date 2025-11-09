public class MergeSort {
    // Function to merge two halves
    void merge(int arr[], int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        // Merge the temp arrays
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // Recursive merge sort
    void sort(int arr[], int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(arr, left, mid);
            sort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    // Print array
    void printArray(int arr[]) {
        for (int i : arr) System.out.print(i + " ");
        System.out.println();
    }

    // Main
    public static void main(String[] args) {
        int arr[] = {5, 2, 9, 1, 6, 3};
        MergeSort ms = new MergeSort();
        System.out.println("Original array:");
        ms.printArray(arr);

        ms.sort(arr, 0, arr.length - 1);

        System.out.println("Sorted array:");
        ms.printArray(arr);
    }
}
