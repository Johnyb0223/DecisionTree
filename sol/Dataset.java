package sol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import src.AttributeSelection;
import src.IDataset;
import src.Row;

/**
 * A class representing a training dataset for the decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the IDataset interface!
public class Dataset implements IDataset  {
    //Dataset instance fields
    private List<String> attributeList;
    private List<Row> dataObjects;
    private AttributeSelection selectionType;

    /**
     * Constructor for a Dataset object
     * @param attributeList - a list of attributes
     * @param dataObjects -  a list of rows
     * @param attributeSelection - an enum for which way to select attributes
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects, AttributeSelection attributeSelection) {
        this.attributeList = new ArrayList<String>(attributeList);
        this.dataObjects = new ArrayList<Row>(dataObjects);
        this.selectionType = attributeSelection;
    }

    /**
     * public Dataset generateSubset(String attribute, String value);
     *
     * Function:
     * Given an attribute and a value, return a dataSet whose dataObjects list now contains only the rows that had the specified
     * value for the specified attribute
     * Also removes the attribute that was used to sort from the new Datasets attribute list
     *
     * @param attribute - the attribute we will generate the subset for
     * @param value - the value the row must have to be put in the subset
     * @return a dataset whose dataObjects list contains the rows that meet the (attribute, value) relation
     * @throw RuntimeException if no rows satisfy the (attribute, value) relation
     * */
    public Dataset generateSubset(String attribute, String value){
        //Objects that will be used to create new Dataset
        List<String> newAttributeList = this.attributeList;
        newAttributeList.remove(attribute);
        ArrayList<Row> newDataObjectsList = new ArrayList<>();

        //Throw exception is DataObjects list is empty
        if(this.dataObjects.isEmpty()){
            throw new RuntimeException("Can't generate a subset of an empty DataObjects list");
        }
        //Populate our new list with all rows that satisfy the attribute value relation
        for (Row row : this.dataObjects){
            if(row.getAttributeValue(attribute).equals(value)){
                newDataObjectsList.add(row);
            }
        }
        //If no rows satisfied the relation throw a RuntimeException
        if(newDataObjectsList.isEmpty()){
            throw new RuntimeException("No row matched the given attribute value relation");
        }
        //Return our subset
        return new Dataset(newAttributeList, newDataObjectsList, this.selectionType);
    }

    /**
     * public boolean isHomogeneous(String attribute, String value);
     *
     * Function:
     * Given an attribute return true if every row in the dataset has the same value for that
     * attribute else false
     *
     * @param attribute - a String that represents the attribute to be checked for homogeneity
     * @param value - a way for us to recursively pass the previous rows attribute value to compare to
     * @param index - a way to keep track of the next position to check in the list
     * @return true if a values are equal and false otherwise
     * @throw a runtime exception if the list of rows is empty
     * */
    public boolean isHomogeneous(String attribute, String value, int index){
        //If there are no rows in our set throw an exception.
        //System.out.println("debug line 86 value param" + value);
        if(this.dataObjects.isEmpty()){
            throw  new RuntimeException("Cant determine if an empty set is homogeneous");
        }
        //If we have reached the end of the list then we know all of the values have matched up.

        if(index == this.dataObjects.size()){
            return true;
        }
        //If this is the first recursive call check get the first value and recur.
        if(value == null){
           String foundValue = this.dataObjects.get(0).getAttributeValue(attribute);
            return this.isHomogeneous(attribute, foundValue, ++index);
        }
        //If the value of the current row matches our value then recur, else return false.
        if(this.dataObjects.get(index).getAttributeValue(attribute).equals(value)){
            return this.isHomogeneous(attribute, value, ++index);
        }else {
            return false;
        }
    }

    /**
     * public String maxValue(String attribute);
     *
     * Function:
     * Returns the value that occurs the most given an attribute. If two values occur the same number
     * of times we will randomly select one
     *
     * @param attribute - the attributes whose values we want the max of
     * @return a string which represents the value we just calculated
     * @throw a RuntimeException if the DataObjects list is empty
     * */
    public String maxValue(String attribute){
        if(this.dataObjects.isEmpty()){
            throw new RuntimeException("Set is empty, No max value to get");
        }

        //field to hold the mode of our list
        ArrayList<String> mode = new ArrayList<>();
        //field to hold the occurrences of our mode in the list
        int occurrences = 0;

        for(Row row : this.dataObjects){
            String currentValue = row.getAttributeValue(attribute);
            int currentCount = 0;
            if(mode.contains(currentValue)){
                continue;
            }
            for(Row r : this.dataObjects){
                if(r.getAttributeValue(attribute).equals(currentValue)){
                    ++currentCount;
                }
            }
            if(currentCount == occurrences){
                mode.add(currentValue);
            }
            if(currentCount > occurrences){
                mode.clear();
                mode.add(currentValue);
                occurrences = currentCount;
            }
        }
        if(mode.size() >= 2){
            Random rand = new Random();
            return mode.get(rand.nextInt(mode.size()));
        }else {
            return mode.get(0);
        }
    }

    /**
     * getAttributeToSplitOn implementation
     *
     * @return the string representation of an attribute that is next to be evaluated dependent on the policy for how
     * we go about deciding which attribute to evaluate next.
     * */
    public String getAttributeToSplitOn() {
        switch (this.selectionType) {
            case ASCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(0);
            }
            case DESCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);
            }
            case RANDOM -> {
                // TODO: Implement random attribute selection!
                Random rand = new Random();
                return this.attributeList.get(rand.nextInt(this.attributeList.size()));
            }
        }
        throw new RuntimeException("Non-Exhaustive Switch Case");
    }

    /**
     * Gets list of attributes in the dataset
     *
     * @return a list of strings
     */
    @Override
    public List<String> getAttributeList(){

        return this.attributeList;
    }

    /**
     * Gets list of data objects (row) in the dataset
     *
     * @return a list of Rows
     */
    @Override
    public List<Row> getDataObjects(){

        return this.dataObjects;
    }

    /**
     * Returns the attribute selection type (alphabetical, reverse alphabetical, random) for this Dataset
     *
     * @return the attribute selection type
     */
    @Override
    public AttributeSelection getSelectionType(){

        return this.selectionType;
    }

    /**
     * finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    @Override
    public int size(){
        return this.dataObjects.size();
    }

    /***/
    public Dataset newDataset(){
        Dataset copy = new Dataset(this.attributeList, this.dataObjects, this.selectionType);
        return copy;

    }

}
