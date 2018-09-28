/**
 * Name : Hector Herrera
 * PennKey : Hectorh
 * Recitation : 216
 * 
 * Execution: java Phonebook
 * 
 * This program will search and sort through a phonebook to output the desired
 * characteristic and will print all the possibilities that match 
 * the given characteristic
 */
public class Phonebook {
    public static void main(String [] args) {
        String [][] data = readFromFile(args[0]);
        int characteristic = Integer.parseInt(args[1]);
        String matcher = args[2];
        
        bubbleSort(data, characteristic);
        findMatches(data, matcher, characteristic); 
    }
    
    /**
     * Description: Reads in person characteristic from a file and outputs it
     * as a 2D array.
     * 
     * Input: the name of the file to read in the data from.
     * 
     * Output: an array of array of strings where each individual array 
     * represents a person with string characteristics.
     */
    public static String[][] readFromFile(String filename) {
        In inStream = new In(filename);
        
        //represents the number of rows
        int numRows = inStream.readInt();
        
        //represents the number of characteristics
        int numChar = inStream.readInt(); 
        
        //Declares the 2D array 
        String [][] readFromFile = new String [numRows][numChar];
        
        //iterates through 2D array putting the characteristics into 2D array
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numChar; j++) {
                String characteristics = inStream.readString();
                readFromFile[i][j] = characteristics;
            }
        }
        return readFromFile;
    }
    /**
     * Description: It will iterate through a list of data to swap the array
     * if the array on the left is greater than the array on the right until 
     * everything is sorted.
     * 
     * Input: It will take in a 2D array and a characteristic as an integer.
     * 
     * Output: There is no output because the function is void
     */
    public static void bubbleSort(String[][] data, int characteristic) {
        //This will nested loop will iterate through the 2D array
        for (int i = 1; i < data.length; i++) {
            for (int j = i; j > 0; j--) {
                //Checks if the left array is greater than the right array
                if (isInversion(data[j - 1], data[j], characteristic)) {
                    //will swap the arrays if the condition is true
                    swap(data, j - 1, j);
                }
            }
        }
    }
    /**
     * Description: It swaps two rows of index left and index right.
     *
     * Input: 2D arrays of strings and an index left represented by an integer
     * and an index right represented by an integer.
     *
     * Output: There is no output because the return type is void.
     */
    public static void swap(String[][] data, int left, int right) {
        //This will create a temporary array that will hold the value data[left]
        String [] temp = data [left];
        data [left] = data [right];
        data [right] = temp;
    }
    /**
     * Description: It will output true if the element on the left is "greater"
     * than the element on the right based on a given characteristic.
     * 
     * Input: Two strings and the characteristic represented by an integer.
     * 
     * Output: A boolean that determines if the element on the left is greater
     * or less than the element on the right based on the characteristic inputed.
     */
    public static boolean isInversion(String[] left, String[] right, 
                                      int characteristic) {
        int x = left[characteristic].compareToIgnoreCase(right[characteristic]);
        if (x > 0) return true;
        else return false;
    }
    /**
     * Description: This function will print a specific row of a 2D array.
     * 
     * Input: A 2D String array and a row to print represented by an integer
     * 
     * Output: There is no output because the return type is void.
     */
    public static void printRow(String[][] data, int row) {
        String s = "";
        for (int i = 0; i < data[row].length; i++) {
            s += data[row][i] + " ";
        }
        s += "\n";
        System.out.println(s);
    }  
    /**
     * Description: It will return the index of the first person whose 
     * characteristic starts with the substring matcher and if there is no match
     * it will return -1.
     * 
     * Input: It ill take in a 2D String array, a substring match
     * 
     * Output: An integer that represent the index of the first person whose 
     * characteristic starts with the substring matcher and if there is no match
     * it will return -1.
     */
    public static int findInitialIndex(String[][] data, String matcher, 
                                       int characteristic) {
        int lo = 0;
        int hi = data.length;
        return search(matcher, data, lo, hi, characteristic);
    }
    /**
     * Description: It will find the first index of any matching value but it is
     * a helper function.
     * 
     * Input: It takes a substring match and a 2D String array and a 
     * characteristic represented by an integer. It will also take in lo and hi
     * that are integers.
     * 
     * Output: It will find the int of the last index that matches the 
     * characteristic.
     */
    public static int search(String match, String[][] data, int lo, int hi, 
                             int characteristic) {
        //base case
        if (hi <= lo) return -1;
        
        //declare variables
        int mid = lo + (hi - lo) / 2;
        String matcher = match.toUpperCase();
        String charact = data[mid][characteristic].toUpperCase();
        int comp = charact.compareTo(matcher);
        
        //look in the middle of the 2D array
        if (charact.startsWith(matcher)) {
            //do not look to the left of the array if mid = 0
            if (mid != 0) {
                String charact1 = data[mid - 1][characteristic].toUpperCase();
                //if the index to the left matches then apply binary search
                if (charact1.startsWith(matcher)) {
                    mid = search(match, data, lo, mid, characteristic);
                }
                //if the index to the left doesn't match return mid
                else return mid;
            }
        }
        
        //if the middle index of the array doesn't match
        else {
            //search right half
            if (comp < 0) {
                mid = search(match, data, mid + 1, hi, characteristic);
            }
            //search left half
            if (comp > 0) {
                mid = search(match, data, lo, mid, characteristic);
            }
        }
        return mid;
    }
    /**
     * Description: This function will find the index of the last person that
     * matches the given condition.
     * 
     * Input: It takes in a 2D array, a substring match, a characteristic as an
     * integer, and the start of the first person that matches the condition
     * as an integer 
     * 
     * Output: The last index that matches the given condition as an integer.
     */
    public static int findLastIndex(String[][] data, String match, 
                                    int characteristic, int start) {
        //declare int variables
        int length = data.length;
        int findLastIndex = -1;
        
        if (start != -1 && start == length - 1) findLastIndex = start;
        
        if (start != -1 && start != length - 1) {
            //will iterate through the array to find the first match
            for (int i = start; i < length; i++) {
                //declare String variables
                String charact = data[i][characteristic].toUpperCase();
                String matcher = match.toUpperCase();
                
                //this will find the first match
                if (charact.startsWith(matcher) == false) {
                    findLastIndex = i - 1;
                    break;
                }
            }
            return findLastIndex;
        }
        else return findLastIndex;
    }
    /**
     * Description: This function will prints out all the persons that match the 
     * condition using the printRow function
     * 
     * Input: It will take in a 2D string array, a substring matcher, and a 
     * characteristic as an integer.
     * 
     * Output: There is on output because the return type is void.
     */
    public static void findMatches(String[][] sortedData, String matcher, 
                                   int characteristic) {
        //declare the int variables
        int start = findInitialIndex(sortedData, matcher, characteristic);
        int last = findLastIndex(sortedData, matcher, characteristic, start);
        
        //will print all the rows that match the given condition
        if (start != -1) {
            for (int i = start; i < last + 1; i++) {
                printRow(sortedData, i);
            }
        }
        else return;    
    }
}     