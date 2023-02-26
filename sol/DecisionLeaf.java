package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class representing a leaf in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class DecisionLeaf implements ITreeNode  {
    // TODO: add fields as needed
    private String value;

    // TODO: Implement the ITreeNode interface

    /**
     * implement the DecisionLeaf constructor
     *
     * The value of a decision must be set at thee creation of that object
     *
     * @param value - a String representing the value
     * */
    public DecisionLeaf(String value){
        this.value = value;
    }

    /**
     * getDecision() implementation
     * @param row row to base decision on
     * @return the decision field of the leaf node
     * */
    @Override
    public String getDecision(Row row){
        return this.value;
    }




}
