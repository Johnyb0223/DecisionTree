package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;


import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.DecisionTreeTester;
import src.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * A class containing the tests for methods in the TreeGenerator and Dataset classes
 */
public class DecisionTreeTest {
    //TODO: Write more unit and system tests! Some basic guidelines that we will be looking for:
    // 1. Small unit tests on the Dataset class testing the IDataset methods
    // 2. Small unit tests on the TreeGenerator class that test the ITreeGenerator methods
    // 3. Tests on your own small dataset (expect 70% accuracy on testing data, 95% on training data)
    // 4. Test on the villains dataset (expect 70% accuracy on testing data, 95% on training data)
    // 5. Tests on the mushrooms dataset (expect 70% accuracy on testing data, 95% on training data)
    // Feel free to write more unit tests for your own helper methods -- more details can be found in the handout!

    /**
     * What are some situations I would really like to test
     *
     * Successfully create a valueEdge that contains a decision leaf
     * Successfully create a valueEdge that contains an attributeNode
     * Generate a valueEdge list based on a given attribute
     * Return true if a decision needs to be made and false otherwise
     * make a specific decision depending on the state of the dataset
     * get a random attribute to split on
     * remove targetAttribute from attribute(add a depth
     *
     **/

    /**
     * Here I'm going to create our testDataset that we will use to test our implementation
     * */

    Dataset testData;
    Dataset emptyData;

    /**
     * This method will instantiate our test Dataset before every training method is executed
     * */
    @Before
    public void datasetSetup(){
        //testData setup
        List<Row> dataObjects = DecisionTreeCSVParser.parse("data/fruits-and-vegetables.csv");
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.testData = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);

        //emptyData setup
        List<Row> emptyDataObject = new ArrayList<>();
        List<String> emptyAttributeList = new ArrayList<>();
        this.emptyData = new Dataset(emptyAttributeList, emptyDataObject, AttributeSelection.RANDOM);

    }

    //TODO: Dataset Testing

    /**
     * \\public Dataset generateSubset(String attribute, String value);
     *
     * \\Function:
     * Given an attribute and a value, return a dataSet whose dataObjects list now contains only the rows that had the specified
     * value for the specified attribute
     *
     * \\@param attribute - the attribute we will generate the subset for
     * \\@param value - the value the row must have to be put in the subset
     * \\@return a dataset whose dataObjects list contains the rows that meet the (attribute, value) relation
     * \\@throw RuntimeException if no rows satisfy the (attribute, value) relation
     * */
    @Test
    public void testGenerateSubset(){
        //Let's generate a subset of all the rows that have the value green for the color attribute
        Dataset subset = this.testData.generateSubset("color", "green");
        assertTrue(subset.isHomogeneous("color", null, 0));
        System.out.println("Success! testGenerateSubset1");

        //Let's generate a subset of all the rows that have the value vegetable for the attribute foodType
        Dataset subset2 = this.testData.generateSubset("foodType", "vegetable");
        assertTrue(subset2.isHomogeneous("foodType", null, 0));
        assertEquals(4, subset2.size());
        System.out.println("Success! testGenerateSubset2");
    }

    /**
     * test to ensure generateSubset throws a RuntimeException if no rows meet the (attribute, value) relation
     * */
    @Test(expected = RuntimeException.class)
    public void testGenerateSubsetExceptionNoMatches(){
        this.testData.generateSubset("color", "red");
    }

    /**
     * test to ensure generateSubset throws a RuntimeException if the dataObjects list is empty
     **/
    @Test(expected = RuntimeException.class)
    public void testGenerateSubsetExceptionEmptyObjectsList(){
        this.emptyData.generateSubset("color", "yellow");
    }

    /**
     * \\public boolean isHomogeneous(String attribute);
     *
     * \\Function:
     * Given an attribute return true if every row in the dataset has the same value for that
     * attribute else false
     *
     * \\@param attribute - a String that represents the attribute to be checked for homogeneity
     * \\@return true if a values are equal and false otherwise
     * \\@throw a runtime exception if the list of rows is empty
     * */
    @Test
    public void testIsHomogeneous(){
        // We have one row therefore the attribute value should be homogeneous
        Dataset subset = this.testData.generateSubset("color", "yellow");
        assertTrue(subset.isHomogeneous("color", null, 0));
        System.out.println("Success! testIsHomogeneous1");

        //What if the rows are not homogeneous for some attribute
        assertFalse(this.testData.isHomogeneous("color", null, 0));
        System.out.println("Success! testIsHomogeneous2");

    }

    /**
     * test to ensure isHomogeneous() throws a runtime exception if the dataObjects list is empty
     * */
    @Test(expected = RuntimeException.class)
    public void testIsHomogeneousException(){
        System.out.println("Success! testIsHomogeneousException");
        this.emptyData.isHomogeneous("color", null, 0);
    }

    /**
     * \\public String maxValue(String attribute);
     *
     * \\Function:
     * Returns the value that occurs the most given an attribute. If two values occur the same number
     * of times we will randomly select one
     *
     * \\@param attribute - the attributes whose values we want the max of
     * \\@return a string which represents the value we just calculated
     * \\@throws a RuntimeException if the DataObjects list is empty
     * */
    @Test
    public void testMaxValue(){
        //Test to see if we can return the value that occurs the most given an attribute
        assertEquals("true", this.testData.maxValue("highProtein"));
        System.out.println("Success! testMaxValue1");

        //Test to see if we return a value when there is a tie between two values
        //Here is a list that contains the possible outcome
        ArrayList<String> possibleValue = new ArrayList<>();
        possibleValue.add("green");
        possibleValue.add("orange");
        String value = this.testData.maxValue("color");
        assertTrue(possibleValue.contains(value));
        System.out.println("Success! testMaxValue2");
    }

    /**
     * Ensure maxValue() throws a RuntimeException if the list  of DataObjects is empty
     * */
    @Test(expected = RuntimeException.class)
    public void testMaxValueException(){
        this.emptyData.maxValue("color");
    }

    //TODO: TreeGenerator Testing

    /**
     * Test generateTree(Dataset, String targetAttribute);
     *
     * Generate a node with one decision leaf
     *
     * We will use our test dataSet to create an attributeNode,
     * with a DecisionLeaf that has the value fruit
     *
     * */
    @Test
    public void testGenerateTreeMethod(){
        //Generate tree for the following dataset
        /**
         * "color"      foodType
         * -------      --------
         * "yellow"     "fruit"
         * */
        TreeGenerator generator = new TreeGenerator();
        Dataset subset = this.testData.generateSubset("color", "yellow");
        generator.generateTree(subset, "foodType");
        //We expect the decision to be fruit based on our tree.
        assertEquals("fruit", generator.getRoot().getDecision(subset.getDataObjects().get(0)));
        System.out.println("Success! You predicted a banana was a fruit");





    }


}
