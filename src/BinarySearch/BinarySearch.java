package BinarySearch;

public class BinarySearch {

    /**
     * Find target in an array sorted in ascending (nondecreasing) order.
     * Return its zero-based index, or -1 if it is absent.
     * If target appears more than once, any matching index is valid.
     * Return -1 for a null or empty array. Do not modify the array.
     *
     * Implement binary search yourself without library search methods.
     * Aim for O(log n) time and O(1) extra space.
     * You may assume the input is sorted; no sorting or validation is needed.
     *
     * @param numbers the sorted array, or null
     * @param target the value to find
     * @return a matching index, or -1
     */
    public static int search(int[] numbers, int target) {
        if (numbers == null || numbers.length==0){
            return -1;
        }

        return binarySearch(numbers,0,numbers.length-1, target);
    }

    public static int binarySearch(int[] numbers, int i, int j, int target){

        while (i<=j){
            int mid = i + (j-i)/2;
            if (numbers[mid]==target){
                return mid;
            } else if (numbers[i]==target) {
                return i;
            } else if (numbers[j]==target) {
                return j;
            } else if (target > numbers[mid]) {
                i = mid+1;
            }else {
                j = mid-1;
            }
        }
        return -1;
    }
}
