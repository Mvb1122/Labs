package com.company;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals; //for integer arrays only
import org.junit.rules.Timeout;
import org.junit.Rule;


/**
 * JUnit4 test class com.company.Array2DExercisesTest.
 * To use the tester on unfinished code, comment out any tests that are not ready yet.
 * @author  Rachel Cardell-Oliver
 * @version April 2011
 */
public class Array2DExercisesTest {

 /**
 * Set a 500 millisecond timeout for each test
 */
 @ClassRule public static final Timeout GLOBAL_TIMEOUT = new Timeout(500);

// example arrays for testing
private int[][] basic, allneg, nonsquare, negatives, rowmagic, colmagic, 
                magic, notmagic1, notmagic2, latin, notlatin;

    /**
     * Sets up the test fixture with some arrays to test.
     * This method is called before every test case method.
     */
   @Before public void setUp() {
        basic = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
        allneg = new int[][] { {-10,-12,-3}, {-4,-5,-6,-8}, {-7,-8} }; //all neg and ragged
        nonsquare = new int[][] { {1,2,3}, {4,5}, {6,7,8,9} };
        negatives = new int[][] { {1,-2,3}, {4,5,6}, {-7,8,-9} };
        rowmagic = new int[][] { {1,2,3}, {-1,5,2}, {4,0,2} };
        colmagic = new int[][] { {1,-1,4,10}, {3,5,0,-6} };
        latin = new int[][] { {1,2,3}, {2,3,1}, {3,1,2} };
        notlatin = new int[][] { {2,1,3}, {2,3,1}, {3,1,2} };
        magic = new int[][] { {2,2,2}, {2,2,2}, {2,2,2}   };
        notmagic1 = new int[][] { {1,2,3}, {4,5,6}, {6,8,9} }; //diag sums are not equal
        notmagic2 = new int[][] { {1,5,3}, {4,5,6}, {7,8,9} }; //diag sums are equal but rows are not
    }

/** 
 * Test max is found correctly (last element in the search)
 */
@Test public void testMaxNormal() {
    assertEquals(9,Array2DExercises.max(basic));
}

/**
 * Test max correct when all vals are negative 
 */
@Test public void testMaxAllNeg() {
    assertEquals(-3,Array2DExercises.max(allneg));
}

/**
 * Test row sum calculated correctly including for nonsquare arrays
 */
@Test public void testRowSum() {
    assertEquals(6, Array2DExercises.rowSum(basic, 0));
    assertEquals(15, Array2DExercises.rowSum(basic, 1));
    assertEquals(24, Array2DExercises.rowSum(basic, 2));
    assertEquals(30, Array2DExercises.rowSum(nonsquare, 2));
}

/**
 * Test column sum calculated correctly for standard cases
 */
@Test public void testColumnSum() {
    assertEquals(12, Array2DExercises.columnSum(basic, 0));
    assertEquals(15, Array2DExercises.columnSum(basic, 1));
    assertEquals(18, Array2DExercises.columnSum(basic, 2));
}


/**
 * Test column sum calculated correctly for nonsquare arrays
 * This checks for sum of incomplete columns (from ragged arrays)
 */
@Test public void testColumnSumRagged() {
    assertEquals(11, Array2DExercises.columnSum(nonsquare, 2));
    assertEquals(9, Array2DExercises.columnSum(nonsquare, 3));
}

/**
 * Checks array of row sums correctly calculated
 */
@Test public void testAllRowSums() {
    int[] expected = new int[] {6,15,24};
    int[] actual = Array2DExercises.allRowSums(basic);
        assertArrayEquals(expected, actual);
}


/**
 * Test for row magic with a valid magic square
 */
@Test public void testIsRowMagicTrue() {
    assertEquals(true, Array2DExercises.isRowMagic(rowmagic) );
}

/**
 * Test for row magic where row sums are not the same
 */
@Test public void testIsRowMagicFalse() {
   assertEquals(false, Array2DExercises.isRowMagic(basic) );

}

/**
 * Test col magic where col sums are the same
 */
@Test public void testIsColumnMagicTrue() {
    assertEquals(true, Array2DExercises.isColumnMagic(colmagic) );
}

/**
 * Test col magic where col sums are not the same
 */
@Test public void testIsColumnMagicFalse() {
    assertEquals(false, Array2DExercises.isColumnMagic(rowmagic) );
}

/**
 * Test for square arrays
 */
@Test public void testIsSquareTrue() {
  assertEquals(true, Array2DExercises.isSquare(basic));
}

/**
 * Test for non-square arrays
 */
@Test public void testIsSquareFalse() {
  assertEquals(false, Array2DExercises.isSquare(nonsquare));
}

/**
 * Test where all conditions for magic square are met
 */
@Test public void testIsMagicTrue() {
    assertEquals(true, Array2DExercises.isMagic(magic) );
}

/**
 * Test magic square false because row and col sums are not the same
 */
@Test public void testIsMagicNotSquare() {
    assertEquals(false, Array2DExercises.isMagic(allneg) );
}


/**
 * Test magic square false because row and col sums are the same BUT diags are not
 */
@Test public void testIsMagicFalseBadDiags() {
   assertEquals(false, Array2DExercises.isMagic(notmagic1) );
}

/**
 * Test magic square false because (only) row sums are not the same
 */
@Test public void testIsMagicFalseBadRows() {
       assertEquals(false, Array2DExercises.isMagic(notmagic2) );
    }

/**
 * Test isLatin is true for a valid latin square
 */
@Test public void testIsLatinTrue() {
  assertEquals(true, Array2DExercises.isLatin(latin) );
}

/**
 * Test isLatin is false for the invalid cases
 */
@Test public void testIsLatinFalse() {
  assertEquals(false, Array2DExercises.isLatin(basic) );
  assertEquals(false, Array2DExercises.isLatin(nonsquare) );
  assertEquals(false, Array2DExercises.isLatin(notlatin) );
}

/**
 * Test isSequence for a valid 3x3 array containing 1..9
 */
@Test public void testIsSequenceTrue() {
   assertEquals(true, Array2DExercises.isSequence(basic) );
}

/**
 * Test isSequence false because array not square
 */
@Test public void testIsSequenceNotSquare() {
       assertEquals(false, Array2DExercises.isSequence(nonsquare) );
    }

/**
 * Test isSequence false because array contains duplicates
 */
@Test public void testIsSequenceRepeatsFalse() {
   assertEquals(false, Array2DExercises.isSequence(magic) );
}

/**
 * Test isSequence false because array vals do not form a sequence
 */
@Test public void testIsSequenceBadValsFalse() {
   assertEquals(false, Array2DExercises.isSequence(negatives) );
}

}
