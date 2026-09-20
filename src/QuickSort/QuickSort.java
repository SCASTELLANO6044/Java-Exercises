package QuickSort;

public class QuickSort {

    /**
     * Sort numbers in ascending (nondecreasing) order using quicksort.
     * Modify the supplied array in place, preserving every value and duplicate.
     * Return the same array reference. A null, empty, or single-element array
     * requires no changes; null input must return null.
     *
     * Implement quicksort yourself without library sorting methods.
     * You may add helpers for sorting a range, partitioning, and swapping.
     * Do not allocate separate arrays for the partitions.
     * Target O(n log n) average time; O(n^2) worst-case time is acceptable.
     * Recursive calls may use stack space.
     *
     * @param numbers the array to sort, or null
     * @return the same array after sorting, or null for null input
     */
    public static int[] sort(int[] numbers) {
        if(numbers == null){
            return null;
        }

        if (numbers.length <= 1){
            return numbers;
        }

        if (numbers.length==2){
            if (numbers[0]<=numbers[1]){
                return numbers;
            }else {
                int aux = numbers[0];
                numbers[0] = numbers[1];
                numbers[1] = aux;
                return numbers;
            }
        }

        quickSort(numbers, 0, numbers.length - 1);
        return numbers;
    }

    private static void quickSort(int[] numbers, int left, int right) {
        if (left >= right) {
            return;
        }

        // Save the pivot value because swaps can move the element at this index.
        int pivot = numbers[left + (right - left) / 2];
        int i = left;
        int j = right;

        while (i <= j) {
            while (numbers[i] < pivot) {
                i++;
            }
            while (numbers[j] > pivot) {
                j--;
            }

            if (i <= j) {
                int aux = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = aux;
                // Advance even when both values equal the pivot.
                i++;
                j--;
            }
        }

        quickSort(numbers, left, j);
        quickSort(numbers, i, right);
    }
}
