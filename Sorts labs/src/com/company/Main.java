package com.company;

import java.io.FileWriter;
import java.util.*;

class Main {
    /**
     * Determines the number of random numbers to generate.
     */
    static int testLength = 100;

    /**
     * Determines the place that the outputted data is saved.
     */
    static String saveLocation = "./output.txt";

    /**
     * Determines which sorting algorithm to use.
     */
    static int algorithm = 0;

    /**
     * Contains a list of all properly-implemented algorithms.
     */
    static com.company.Main.algorithm[] algorithms = getAlgorithms();

    public static void main(String[] args) {
        // Get user input.
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("How many random numbers do you want to generate?");
            testLength = scanner.nextInt();

            System.out.println("Where do you want to save the sorted data?\n(Put \"no\" if you don't want to save.)");
            saveLocation = scanner.next();

            System.out.printf("Selected save location: %s%n", saveLocation);

            System.out.println("Which algorithm do you want to choose?");

            for (int i = 0; i < algorithms.length; i++) {
                System.out.printf("\t(%d) %s%n", i, algorithms[i].getName());
            }
            System.out.println("Enter the number of the algorithm you want to use: ");
            algorithm = scanner.nextInt();
        }

        // Create the random numbers to sort.
        List<Integer> Sorted_Numbers = getRandIntsList(testLength);
        List<Integer> Unsorted_Numbers = List.copyOf(Sorted_Numbers);

        // Use the user's selected sorting algorithm.
        Sorted_Numbers = List.of(algorithms[algorithm].run(Sorted_Numbers.toArray(new Integer[0])));

        System.out.println("\nThe unsorted list is: ");
        try {
            print(Unsorted_Numbers, integerListToComparableArray(Sorted_Numbers), "The sorted list is: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @implNote Integer[] cast of getRandIntsList.
     */
    public static Integer[] getRandIntsArray(int numInts) {
        return (Integer[]) getRandIntsList(numInts).toArray();
    }

    /**
     * Gets an Integer list of 100 random Integers.
     * @param numInts The number of random integers to get.
     * @return A list with the length of numInts, filled with Random Integers between 0 and 100.
     */
    public static List<Integer> getRandIntsList(int numInts) {
        List<Integer> randInts = new ArrayList<Integer>(numInts);

        while (randInts.size() != numInts)
            randInts.add((int) Math.floor(Math.random() * 100));

        return randInts;
    }

    /**
     * A method that sorts a list using the Insertion Sort method.
     * @param a A list to sort, passed by reference.
     * @implNote Make <strong>SURE</strong> to pass by reference, it doesn't return anything.
     */
    public static void insertionSortList(List<Integer> a) {
        // Loop from the first element to the last element in the array.
        for (int i = 1; i < a.size(); i++) {
            // Create a variable to hold the value that's being moved.
            Integer selected = a.get(i);
            // Create a variable to hold the index of the value that we're checking against.
            int j = i - 1;

            // While the value we're checking against is within the list and the checked value is greater than the one we're moving,
            // move the selected value up and move the selection iterator down.
            while (j >= 0 && a.get(j) > selected) {
                a.set(j + 1, a.get(j)); j--;
            }

            // Move the selected value down into the index that we chose.
            a.set(j + 1, selected);
        }
    }

    /**
     * A method that sorts a list using the Selection Sort method.
     * @param a A list to sort, passed by reference.
     * @implNote Make <strong>SURE</strong> to pass by reference, it doesn't return anything.
     */
    public static void selectionSort(List<Integer> a) {
        // Repeatedly find and move the smallest element to the beginning of the list.
        int sortedIndex = -1;
        while (true) {
            // Find the first out-of-order and lowest element.
            int minIndex = -1;
            {
                int min = Integer.MAX_VALUE;
                for (int i = sortedIndex + 1; i < a.size(); i++) if (a.get(i) < min) {
                    minIndex = i; min = a.get(i);
                }
            }

            // Move that lowest value to the beginning.
            if (++sortedIndex == a.size()) break;
            swapElements(a, sortedIndex, minIndex);
        }
    }

    /**
     * @implNote Integer[] cast of selectionSort.
     */
    public static void selectionSort(Comparable<Integer>[] a) {
        selectionSort((Integer[]) Arrays.stream(a).toArray());
    }

    /**
     * Swaps the elements of a list at indexes one and two.
     * @param a An object list to use.
     * @param indexOne The first index to swap with.
     * @param indexTwo The second index to swap with.
     */
    public static void swapElements(List<Integer> a, int indexOne, int indexTwo) {
        Integer index1 = a.get(indexOne);
        Integer index2 = a.get(indexTwo);

        a.set(indexOne, index2); a.set(indexTwo, index1);
    }

    /**
     * Outputs the arrays, with the msg variable in the middle.
     * @param msg A message to be printed in the middle.
     */
    public static void print(List a, Comparable[] b, String msg) throws Exception {
        StringBuilder output = new StringBuilder();
        output.append("[\n");
        for (int i = 0; i < a.size(); i++)
            if (i % 10 == 0 && i != 0)
                output.append(String.format("\n %-3s", a.get(i)));
            else
                output.append(String.format(" %-3s", a.get(i)));

        output.append("\n]");

        output.append("\n").append(msg).append("\n[\n");
        for (int i = 0; i < a.size(); i++)
            if (i % 10 == 0 && i != 0)
                output.append(String.format("\n %-3s", b[i]));
            else
                output.append(String.format(" %-3s", b[i]));

        output.append("\n]\n");

        // Show output on screen.
        System.out.println(output);

        // Write to the file.
        if (!saveLocation.equals("no")) {
            FileWriter saver = new FileWriter(saveLocation);
            saver.write(output.toString());
            saver.close();
            System.out.println("Wrote to file: " + saveLocation);
        } else
            System.out.println("Skipping save!");
    }

    /**
     * Sorts an array using Merge Sort.
     * @param array The array to be sorted.
     * @return The sorted array.
     * @implNote This overloads the normal mergeSort() method by running it on the entire array.
     * <br> Additionally, This method returns by reference; your original passed array is not destroyed.
     */
    public static Integer[] mergeSort(Integer[] array) {
        return mergeSort(array, 0, array.length);
    }

    /**
     * Sorts an array using Merge Sort.
     * @param array The array to sort.
     * @param left The left side of the array.
     * @param right The right side of the array.
     * @return The array, hopefully sorted.
     */
    public static Integer[] mergeSort(Integer[] array, int left, int right) {
      if (right > left + 1) {
        // Finds the middle of the array.
        int middle = left + (right - left) / 2;


        // Split into the left and right halves of the array.
        Integer[] leftHalf = new Integer[middle], rightHalf = new Integer[array.length - middle];
        System.arraycopy(array, 0, leftHalf, 0, middle);
        System.arraycopy(array, middle, rightHalf, 0, array.length - middle);

        // Call mergesort on both of the halves, if they're longer than two. Otherwise, put the smaller item on the bottom.
        rightHalf = mergeSort(rightHalf);
        leftHalf = mergeSort(leftHalf);

        // Merge the two arrays back together.
        Integer[] output = new Integer[right + left];

        int rightIndex = 0, leftIndex = 0, filledIndexes = 0; // Creates variables to track where we are on each list.
        for (int i = 0; i < output.length; i++) try {
            // Put the smaller element of the two as the location at index i.
            if ((rightHalf[rightIndex] != null && leftHalf[leftIndex] != null) && rightHalf[rightIndex] < leftHalf[leftIndex]) {
                output[i] = rightHalf[rightIndex];
                rightIndex++;
                filledIndexes++;
            } else if (leftHalf[leftIndex] != null){
                output[i] = leftHalf[leftIndex];
                leftIndex++;
                filledIndexes++;
            }


            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }


        // Add any remaining items to the list.
        if (filledIndexes < output.length) {
            for (int i = rightIndex; i < rightHalf.length; i++, filledIndexes++)
                output[filledIndexes] = rightHalf[i];

            for (int i = leftIndex; i < leftHalf.length; i++, filledIndexes++)
                output[filledIndexes] = leftHalf[i];
        }

        return output;
      }

      // Assuming that this is where the elements are as individual items.
      return array;
    }

    public static Comparable<Integer>[] integerListToComparableArray(List<Integer> l) {
        Comparable<Integer>[] output = new Comparable[l.size()];
        for (int i = 0; i < l.size(); i++)
            output[i] = l.get(i);
        return output;
    }

    /**
     * Implements a stable platform for me to base the choosing of an algorithm off of.
     * @implNote I could've used an internal class here, but I wanted to get more experience with
     * interfaces, so I used one.
     */
    private interface algorithm {
        /**
         * @return The name of the algorithm.
         */
        String getName();

        /**
         * Runs the algorithm on the passed data
         * <p>This method handles type conversions and returns the sorted data.</p>
         * @param numbers The data to sort.
         * @return The sorted data.
         */
        abstract Integer[] run(Integer[] numbers);
    }

    /**
     * Gets a list of algorithms currently available.
     * @return An array of algorithms.
     * @implNote Use the run() method to run the selected algorithm on a dataset.
     */
    private static algorithm[] getAlgorithms() {
        return new algorithm[]{
          new algorithm() {
              @Override
              public String getName() {
                  return "Merge Sort";
              }

              @Override
              public Integer[] run(Integer[] numbers) {
                  return mergeSort(numbers);
              }
          },
          new algorithm() {
              @Override
              public String getName() {
                  return "Insertion Sort";
              }

              @Override
              public Integer[] run(Integer[] numbers) {
                  List<Integer> x1 = IntegerArrayToList(numbers);
                  insertionSortList(x1);
                  return x1.toArray(new Integer[0]);
              }
          },
          new algorithm() {
              @Override
              public String getName() {
                  return "Selection Sort";
              }

              @Override
              public Integer[] run(Integer[] numbers) {
                  List<Integer> x1 = IntegerArrayToList(numbers);
                  selectionSort(x1);
                  return x1.toArray(new Integer[0]);
              }
          }
        };
    }

    /**
     * Deep-Copies an array of Integers into a list.
     * @param numbers The list to be used.
     * @return A deep-copied list.
     */
    private static List<Integer> IntegerArrayToList(Integer[] numbers) {
        List<Integer> op = new ArrayList<>(numbers.length);
        Collections.addAll(op, numbers);
        return op;
    }
}
