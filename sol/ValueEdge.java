package sol;

import src.ITreeNode;

import java.util.ArrayList;

/**
 * A class that represents the edge of an attribute node in the decision tree
 */
public class ValueEdge {
    // TODO: add more fields if needed
    private String value;           //value of the ValueEdge
    private ITreeNode child;        //Subtree of the given value

    /**
     * Implementation of the valueEdge constructor
     *
     * given an attribute and a dataset
     *
     * @param value - value of the edge
     * @param child - either an attribute node or a DecisionLeaf
     * */
    public ValueEdge(String value, ITreeNode child){
        this.value = value;
        this.child = child;

    }

    /**
     * getValue method
     * @return string
     * */
    public String getValue(){
        return this.value;
    }

    /**
     * getChild method
     * @return the child node
     * */
    public ITreeNode getChild(){
        return  this.child;
    }


}
