package deduplicater;

import change.Change;
import record.Record;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class Node {
    Record record;
    ArrayList<Node> neighbors = new ArrayList<Node>();

    public Node(Record newRecord) {
        record = newRecord;
    }

    public void addNeighbor(Node node) {
        neighbors.add(node);
    }
}



public class Deduplicater {

    ArrayList<Record> records;

    public Deduplicater(ArrayList<Record> newRecords) {
        records = newRecords;
    }

    public ArrayList<Record> getUniqueRecords() {
        return getDeduplicationResult().records;

    }

    public DeduplicationResult getDeduplicationResult(){
        ArrayList<Record> sortedRecords = getSortedRecords();
        ArrayList<Change> changes = new ArrayList<Change>();
        Stack<Node> nodes = getNodes(sortedRecords);
        setEdges(nodes);

        ArrayList<Record> uniqueRecords = new ArrayList<Record>();
        while (!nodes.empty()) {
            Node node = nodes.pop();
            uniqueRecords.add(node.record);
            for (int i = 0; i < node.neighbors.size(); i++) {
                Node removalNode = node.neighbors.get(i);
                changes.add(new Change(removalNode.record, node.record));
                nodes.removeElement(removalNode);
            }
        }
        Collections.reverse(uniqueRecords);

        return new DeduplicationResult(uniqueRecords, changes);
    }

    public ArrayList<Change> getChanges() {
        return getDeduplicationResult().changes;

    }

    private ArrayList<Record> getSortedRecords() {
        ArrayList<Record> recordsCopy = new ArrayList<Record>();
        for (int i = 0; i < records.size(); i++)
            recordsCopy.add(records.get(i).clone());
        Collections.sort(recordsCopy);
        return recordsCopy;
    }

    private Stack<Node> getNodes(ArrayList<Record> recordsCopy) {
        Stack<Node> nodes = new Stack<Node>();
        for (int i = 0; i < recordsCopy.size(); i++) {
            nodes.push(new Node(recordsCopy.get(i)));
        }
        return nodes;
    }

    private void setEdges(Stack<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            for (int j = 0; j < nodes.size(); j++) {
                if (i != j) {
                    if (node.record.isDuplicate(nodes.get(j).record)) {
                        node.addNeighbor(nodes.get(j));
                    }
                }
            }
        }
    }

}
