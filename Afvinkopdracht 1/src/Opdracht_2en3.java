import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Humeyra Copoglu
 * Date created: 11/9/2022
 */

public class Opdracht_2en3 {

    public static void main(String[] args) {
        int length = 10;
        // Arraylist with random numbers
        long startTime = System.nanoTime();
        for (int i=0; i < 10; i++) {
            ArrayList<Integer> numbers = generateRandomArray(length);
            long endTime = System.nanoTime();
            //System.out.println(numbers);
            long time = (endTime - startTime) / 1000;
            System.out.println("Generating random numbers time: " + time + " milliseconds");

            // Arraylist with sorted numbers
            startTime = System.nanoTime();
            ArrayList<Integer> sortedNumbers = sorter(numbers);
            //Snellere bestaande methode:
            //numbers.sort(null);
            endTime = System.nanoTime();
            //System.out.println(sortedNumbers);
            time = (endTime - startTime) / 1000;
            System.out.println("Sorting time for lenght "+ length +": " + time + " milliseconds");
            length *= 2;
        }
    }

    /**
     * Sorts the given integer arraylist from maximum to minimum using the ?? algorithm.
     * Returns a new sorted integer arraylist.
     *
     * @param numbers Integer ArrayList: Random numbers
     * @return Integer ArrayList: Sorted numbers
     */
    public static ArrayList<Integer> sorter(ArrayList<Integer> numbers) {
        ArrayList<Integer> sortedNumbers = new ArrayList<>();
        while (numbers.size() > 0) {
            int maxIndex = indexMaxNumberInArray(numbers);
            int maxNumber = numbers.get(maxIndex);
            sortedNumbers.add(maxNumber);
            numbers.remove(maxIndex);
        }
        return sortedNumbers;
    }

    /**
     * Gets the index of the maximum number in the integer arraylist.
     *
     * @param numbers Integer ArrayList: random numbers
     * @return int: Index of the maximum number
     */
    public static int indexMaxNumberInArray(ArrayList<Integer> numbers) {
        int max = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) > numbers.get(max)) {
                max = i;
            }
        }
        return max;
    }

    /**
     * Makes an integer arraylist with the @param length as the length of the array. The
     * arraylist is then filled with random integers between 0 and 10. Returns the generated
     * integer arraylist.
     *
     * @param length int: Length of the arraylist
     * @return Integer ArrayList: Random numbers
     */
    public static ArrayList<Integer> generateRandomArray(int length) {
        ArrayList<Integer> randomArray = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomArray.add(random.nextInt(10));
        }
        return randomArray;
    }


}
