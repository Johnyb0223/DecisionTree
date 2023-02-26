package sol;

import com.sun.source.tree.Tree;
import src.AttributeSelection;
import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeGenerator interface!
public class TreeGenerator implements ITreeGenerator<Dataset> {

    // TODO: document this field

    //Hello this is my comment
    private ITreeNode root;
    
    // TODO: Implement methods declared in ITreeGenerator interface!

    /**
     * here we implement the generateTree method
     *
     * @param trainingData - a Dataset
     * @param  targetAttribute - the attribute we are looking to make decisions on
     * */
    public void generateTree(Dataset trainingData, String targetAttribute){
        //Copy of our dataSet
        Dataset copyDataset = trainingData;
        //current depth in the tree
        //If it's a new tree, remove targetAttribute from our attributeList
        copyDataset.getAttributeList().remove(targetAttribute);
        //Base case when it's time to make a decision
        if(copyDataset.isHomogeneous(targetAttribute, null, 0) || copyDataset.getAttributeList().isEmpty()){
            this.root = new DecisionLeaf(copyDataset.maxValue(targetAttribute));
        } else {
            //Attribute selected to split on
            String splitAttribute = copyDataset.getAttributeToSplitOn();

            //Now lets make generate a list of value edges
            ArrayList<String> seenValues = new ArrayList<>();
            ArrayList<ValueEdge> valueEdges = new ArrayList<>();
            for (Row row : copyDataset.getDataObjects()) {
                String rowValue = row.getAttributeValue(splitAttribute);
                if (seenValues.contains(rowValue)) {
                    continue;
                }
                TreeGenerator generator = new TreeGenerator();
                generator.generateTree(copyDataset.generateSubset(splitAttribute, rowValue), targetAttribute);
                ValueEdge newEdge = new ValueEdge(rowValue, generator.getRoot());
                valueEdges.add(newEdge);
            }

            this.root = new AttributeNode(splitAttribute, trainingData.maxValue(targetAttribute), valueEdges);
        }
    }

    /**
     * getDecision() implementation
     * @param row to make decision about
     * @return a String representing the decision
     * */
    @Override
    public String getDecision(Row row){
       return this.root.getDecision(row);
    }


    /**
     * Implementation of getRoot();
     * @return the root node of the tree generator
     * */
    public ITreeNode getRoot(){
        return this.root;
    }

}
