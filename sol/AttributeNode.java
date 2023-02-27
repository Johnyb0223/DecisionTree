package sol;

import java.util.ArrayList;
import java.util.List;
import src.ITreeNode;
import src.Row;

/**
 * A class representing an inner node in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class AttributeNode implements ITreeNode  {
    // TODO: add more fields as needed
    private String value;
    private List<ValueEdge> outgoingEdges;
    private String defaultValue;

    // TODO: implement the ITreeNode interface

    /**
     * AttributeNode Constructor
     *
     * @param value - the attribute that this node splits
     * @param defaultValue - the most likely outcome
     * @param valueEdges - a list of value edges for the node
     * */
    public AttributeNode(String value, String defaultValue, List<ValueEdge> valueEdges){
        this.value = value;
        this.outgoingEdges = valueEdges;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDecision(Row row){
        String rowValue = row.getAttributeValue(this.value);
        for (ValueEdge valueEdge : this.outgoingEdges){
            if(valueEdge.getValue().equals(rowValue)){
                return valueEdge.getChild().getDecision(row);

            }
        }
        return this.defaultValue;
        }
}

