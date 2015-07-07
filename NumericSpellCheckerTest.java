package numericSpellChecker;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

//import numericSpellChecker1.NumericSpellChecker;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the NumericSpellChecker class.
 * 
 * @author Simon Carter
 *
 * Program Name: BSc Computing
 * Submission date: 16-02-14
 */
public class NumericSpellCheckerTest {

	private NumericSpellChecker nsc;
	
	@Before
	public void init(){
		nsc = new NumericSpellChecker();
	}
/*	
	@Test
	public void testGenerateRandomArrayWithZero(){
		// supply 0, -ve, +ve, number on/bigger/smaller than int limits
		int size = 0;
		int[] expected = {};
		assertEquals(expected, NumericSpellChecker.generateRandomArray(size));
	}*/
	
	@Test
	public void testGenerateRandomArray(){
		// supply 0, -ve, +ve, number on/bigger/smaller than int limits
		int size = 3;
		int[] expected = {1553932502,-2090749135,-287790814};
		assertArrayEquals(expected, NumericSpellChecker.generateRandomArray(size));
	}
	
	@Test
	public void testCreateDictionary(){
		int[] input = {1,2,3,4,5};		
		int[] expected = {1,2,3,4,5};
		nsc.createDictionary(input);
		assertArrayEquals(expected,nsc.dictionary);	// dictionary should contain clone of input
	}
		
	@Test
	public void testCreateRandomDictionary(){
		int size = 3;		
		int[] expected = {1553932502, -2090749135, -287790814};
		nsc.createRandomDictionary(size);
		assertArrayEquals(expected,nsc.dictionary);
	}
	
	@Test
	public void testExactMatchValueInDictionary(){
		nsc.createRandomDictionary(2);
		assertEquals(true, nsc.exactMatch(1553932502));
	}
	
	@Test
	public void testExactMatchZero(){
		nsc.createRandomDictionary(2);
		assertEquals(false, nsc.exactMatch(0));
	}
	
	@Test
	public void testExactMatchLargePos(){
		nsc.createRandomDictionary(2);
		assertEquals(false, nsc.exactMatch(Integer.MAX_VALUE));
	}
	
	@Test
	public void testExactMatchLargeNeg(){
		nsc.createRandomDictionary(2);
		assertEquals(false, nsc.exactMatch(Integer.MIN_VALUE));
	}
	
	@Test
	public void testCountDifferencesTwoPos(){
		int b = 1553932502;
		int a = 155;
		int expected = 10;
		int actual = NumericSpellChecker.countDifferences(a, b);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCountDifferencesTwoNeg(){
		int b = -1553932502;
		int a = -155;
		int expected = 10;
		int actual = NumericSpellChecker.countDifferences(a, b);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCountDifferencesOneNegOnePos(){
		int b = 1553932502;
		int a = -155;
		int expected = 11;
		int actual = NumericSpellChecker.countDifferences(a, b);
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testCountDifferences(){
		int b = Integer.MAX_VALUE;
		int a = Integer.MIN_VALUE;
		int expected = 2;
		int actual = NumericSpellChecker.countDifferences(a, b);
		assertEquals(expected, actual);
	}
			
	@Test
	public void testFindBestMatches(){
        int numberToCheck = 942105;
        nsc.dictionary = new int[]{932502,-949135,-790814};
        int[] expected = {-949135,932502};
        int[] actual = nsc.findBestMatches(numberToCheck);
        assertArrayEquals(expected, actual);
	}

	@Test
	public void testConvertNumberToArrayZero(){
		int num = 0;
		int[] actual = NumericSpellChecker.convertNumberToArray(num);
		int[] expected = {0};
		Arrays.equals(expected,actual);
		assertArrayEquals(expected,actual);	
	}
	
	@Test
	public void testConvertNumberToArray(){
		int num = 942105;
		int[] actual = NumericSpellChecker.convertNumberToArray(num);
		int[] expected = {9,4,2,1,0,5};
		Arrays.equals(expected,actual);
		assertArrayEquals(expected,actual);	
	}
	
	@Test
	public void testConvertArrayListToArrayEmpty(){
		// 
		ArrayList<Integer> list = new ArrayList<Integer>();	// arraylist to hold arguments for method call
		int[] actual = NumericSpellChecker.convertArrayListToArray(list);
		int[] expected = {};
		assertArrayEquals(expected,actual);	
	}
	
	@Test
	public void testConvertArrayListToArray(){
		// 
		ArrayList<Integer> list = new ArrayList<Integer>();	// arraylist to hold arguments for method call
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);		
		int[] actual = NumericSpellChecker.convertArrayListToArray(list);
		int[] expected = {1,2,3,4,5};
		assertArrayEquals(expected,actual);	
	}
}
