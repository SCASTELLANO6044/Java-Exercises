package BubbleSort;

public class BubbleSort {

    /**
     * Sort numbers in ascending (nondecreasing) order using bubble sort.
     * Modify the supplied array in place, preserving every value and duplicate.
     * A null, empty, or single-element array requires no changes.
     *
     * Implement bubble sort yourself without library sorting methods.
     * Target O(n^2) worst-case time and O(1) extra space.
     * Optional improvement: stop early if a complete pass makes no swaps.
     *
     * @param numbers the array to sort, or null
     */
    public static void sort(int[] numbers) {
        if(numbers!= null){
            for (int end = numbers.length-1; end>0; end--){
                for (int i = 0; i<end;i++){
                    if(numbers[i] > numbers[i+1]){
                        swap(numbers, i, i+1);
                    }
                }
            }
        }

    }

    public static void swap(int[] numbers, int i, int j){
        int aux = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = aux;
    }
}
