package sol;

import java.util.List;
import java.util.Random;

import src.AttributeSelection;
import src.IDataset;
import src.Row;

/**
 * A class representing a training dataset for the decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the IDataset interface!
public class Dataset /* implements IDataset */ {

    /**
     * Constructor for a Dataset object
     * @param attributeList - a list of attributes
     * @param dataObjects -  a list of rows
     * @param attributeSelection - an enum for which way to select attributes
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects, AttributeSelection attributeSelection) {
        // TODO: implement the constructor! (Hint: take a look at `getAttributeToSplitOn`)
    }

//    TODO: Uncomment this once you've completed the constructor!
//    public String getAttributeToSplitOn() {
//        switch (this.selectionType) {
//            case AttributeSelection.ASCENDING_ALPHABETICAL -> {
//                return this.attributeList.stream().sorted().toList().get(0);
//            }
//            case AttributeSelection.DESCENDING_ALPHABETICAL -> {
//                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);
//            }
//            case AttributeSelection.RANDOM -> {
//                // TODO: Implement random attribute selection!
//            }
//        }
//        throw new RuntimeException("Non-Exhaustive Switch Case");
//    }
}
