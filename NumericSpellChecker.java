package numericSpellChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.util.Map.Entry;

/**
 * This class compares integers entered by the user against a dictionary of random numbers.
 * 
 * @author Simon Carter
 * 
 * Program Name: BSc Computing
 * Submission date: 16-02-14
 */
public class NumericSpellChecker {

	Scanner scanner = new Scanner(System.in);
	private final static int DICTIONARY_SIZE = 3;		// constant to decide size of dictionary
	int[] dictionary = new int[DICTIONARY_SIZE];	// instance variable to hold dictionary
	
	/**
	 * This method gets the user input and initiates the comparison.
	 * 
	 * @param args Unused.
	 * @return Nothing.
	 */
	public static void main(String[] args) {
		NumericSpellChecker nsc = new NumericSpellChecker();
		nsc.createRandomDictionary(DICTIONARY_SIZE);
		System.out.println(Arrays.toString(nsc.dictionary));
		
		System.out.println("Welcome to the number checker!!");
		
		int inputtedNumber = nsc.getNumber();
		while(inputtedNumber != 0){				// entering zero ends the program
            if(nsc.exactMatch(inputtedNumber)){
                System.out.println("Exact match found in dictionary: " + inputtedNumber);  // if the program found exact match notify the user
            }
            else{
                System.out.println("Number not found in dictionary, here are the best matches: ");    // otherwise print the closest matches
                int[] matches = nsc.findBestMatches(inputtedNumber);
                for(int i : matches)
                	System.out.println(i);
            }
			inputtedNumber = nsc.getNumber();	// get another number
		}
		
		System.out.println("Bye!");		// notify user that program has terminated
	}

	/**
	 * This method generates an array of random integers.
	 * 
	 * @param size The number of integers to obtain.
	 * @return The array of random integers.
	 */
	public static int[] generateRandomArray(int size){
        Random random = new Random(12345);	// new random object with root 12345
        
        int[] array = new int[size];		// array to hold random numbers
        
        for(int i = 0 ; i < size ; i++)
            array[i] = random.nextInt();
        
        return array;
	}
	
	/**
	 * This method copies the array of random integers into the "dictionary" instance variable.
	 * 
	 * @param numbers The array of random integers.
	 * @return Nothing.
	 */
	public void createDictionary(int[] numbers){
		dictionary = numbers.clone();		// copy array of random numbers into instance variable
	}

	/**
	 * This method initiates creation of the random dictionary and places the result into the instance variable.
	 * @param size The number of integers the dictionary will hold.
	 * @return Nothing.
	 */
	public void createRandomDictionary(int size){
		createDictionary(generateRandomArray(size));
	}
	
	/**
	 * This method tests whether the dictionary contains the inputed number.
	 * 
	 * @param inputNumber The number entered by the user.
	 * @return True if entered number is in dictionary.
	 */
	public boolean exactMatch(int inputNumber){
		for(int i : dictionary){
			if(i == inputNumber)
				return true;
		}
		return false;
	}
	
	/**
	 * This method counts how many digits are different between the two numbers passed in.
	 * 
	 * @param a A number from the dictionary.
	 * @param b The number inputed by the user.
	 * @return Count of digits that are different.
	 */
	public static int countDifferences(int a, int b){
		
        int count = 0;
        
        if((a < 0 && b > 0) || (a > 0 && b < 0)){   // add one to count if the two numbers are not equal in sign
            count ++;
        }
        
        if(a == Integer.MIN_VALUE){		// these 2 IFs make adjustments for the coming abs function which can't be used on MIN_VALUE, as it will roll over
        	count++;
        	a++;
        }else if(b == Integer.MIN_VALUE){
        	count++;
        	b++;
        }
        
        a = Math.abs(a);		// remove negative if any, already counted above
        b = Math.abs(b);
        
        int[] aArr = convertNumberToArray(a);		// put each number into an array
        int[] bArr = convertNumberToArray(b);
        
        count += compareDigitsOfArrays(aArr, bArr);	// compare each index and increment if different
        
        return count;
	}

	/**
	 * This method compares digits of an array.
	 * 
	 * @param aArr Number from dictionary, expressed as an array.
	 * @param bArr Number entered by user, expressed as an array.
	 * @return Count of elements that are different.
	 */
	private static int compareDigitsOfArrays(int[] aArr, int[] bArr) {
		int count = 0;
		count += Math.abs(aArr.length - bArr.length);	// get difference in length
				
		if(aArr.length > bArr.length){
			int ai = aArr.length - bArr.length;			// adjust index for aArr in order to avoid checking unnecessary digits
			for(int i = aArr.length - 1; i >= ai ; i--)
				if(aArr[i] != bArr[i-ai])
					count++;
		}
		else{
			int bi = bArr.length - aArr.length;			// adjust index for bArr in order to avoid checking unnecessary digits
			for(int i = bArr.length - 1; i >= bi ; i--)
				if(aArr[i-bi] != bArr[i])
					count++;
		}
		
		return count;
	}

	/**
	 * This method finds dictionary entries that are closest to number inputed by user.
	 * 
	 * @param inputNumber The number inputed by the user.
	 * @return An array of the closest numbers.
	 */
	public int[] findBestMatches(int inputNumber){
		HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
        
        for(int i = 0 ; i < dictionary.length ; i++){		// go through the dictionary
            map.put(dictionary[i], NumericSpellChecker.countDifferences(dictionary[i], inputNumber));
            		// map each entry in the dictionary to the #differences found between that value and input number
        }
        
        ArrayList<Integer> bestMatches = new ArrayList<Integer>();	// storage for best matches once ready to return
        boolean matchesFound = false;
        int numOfDifferences = 1;		// there is at least 1 difference otherwise this code would not be reached
        int differenceLimit = String.valueOf(Integer.MIN_VALUE).length();	// limit to prevent infinite loop (shouldn't be possible anyway)
        																	// MIN_VALUE has the most number of digits possible
    	
        do{
            for(Entry<Integer, Integer> entry : map.entrySet()){	// check all hashmap entries
                if((Integer)entry.getValue() == numOfDifferences){	// if the number of differences equals the value
                    matchesFound = true;
                    bestMatches.add((Integer) entry.getKey());		// add dictionary value to the matches store
                }											
            }
            if(!matchesFound)	// no entry found with current number of differences, increment and continue
            	numOfDifferences++;
            
        }while(!matchesFound && numOfDifferences <= differenceLimit);      
        
        return convertArrayListToArray(bestMatches);
	}
	
	/**
	 * This method creates an array representation of a number.
	 * 
	 * @param num The number to be converted.
	 * @return An array where each element is a digit of the passed number.
	 */
	public static int[] convertNumberToArray(int num){
		ArrayList<Integer> digits = new ArrayList<Integer>();
		splitTheDigits(num, digits);

		return convertArrayListToArray(digits);
	}

	/**
	 * This method splits digits of a number. 
	 * 
	 * @param num The number to be split.
	 * @param digits An arrayList representation of the number.
	 * @return Nothing.
	 */
	private static void splitTheDigits(int num, ArrayList<Integer> digits) {
		if(num / 10 > 0) {							// if there is more than one digit  
			splitTheDigits(num / 10, digits);		// recursive call with last digit removed
		}
		digits.add(num % 10);	// add the last digit
	}
	
	/**
	 * This method converts an arrayList to an array.
	 * 
	 * @param list The arrayList to be converted.
	 * @return An array representation of the arrayList.
	 */
	public static int[] convertArrayListToArray(ArrayList<Integer> list){
		int[] array = new int[list.size()];
		for(int i = 0 ; i < array.length ; i++)		// go through arrayList and copy elements into an array
			array[i] = list.get(i);
		return array;
	}
		
	/**
	 * This method obtains the input from the user.
	 * 
	 * @return An integer to be compared.
	 */
	public int getNumber() {
		System.out.print("Enter an integer (0 to exit): ");
		while(true){
			if (scanner.hasNextInt()) {				
				int result = scanner.nextInt();
				scanner.nextLine();
				return result;
			}else {
				System.out.print("\"" + scanner.nextLine() +
				"\" is not a valid integer. Try again: \n");	// display an error if input was not an integer
			}
		}
	}
}
