package deduplicater;

import record.Record;

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
        ArrayList<Record> sortedRecords = getSortedRecords();
        Stack<Node> nodes = getNodes(sortedRecords);
        setEdges(nodes);

        ArrayList<Record> result = new ArrayList<Record>();
        while (!nodes.empty()) {
            Node node = nodes.pop();
            result.add(node.record);
            for (int i = 0; i< node.neighbors.size(); i++){
                nodes.removeElement(node.neighbors.get(i));
            }
        }
        Collections.reverse(result);
        return result;
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
