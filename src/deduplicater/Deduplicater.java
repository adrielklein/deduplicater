package deduplicater;

import change.Change;
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


    public static DeduplicationResult getDeduplicationResult(ArrayList<Record> records) {
        Stack<Node> nodes = getNodes(records);
        setEdges(nodes);

        ArrayList<Record> uniqueRecords = new ArrayList<Record>();
        ArrayList<Change> changes = new ArrayList<Change>();
        visitNodes(nodes, uniqueRecords, changes);
        Collections.sort(uniqueRecords);

        return new DeduplicationResult(uniqueRecords, changes);
    }

    private static void visitNodes(Stack<Node> nodes, ArrayList<Record> uniqueRecords, ArrayList<Change> changes) {
        while (!nodes.empty()) {
            Node node = nodes.pop();
            uniqueRecords.add(node.record);
            for (Node neighbor: node.neighbors) {
                changes.add(new Change(neighbor.record, node.record));
                nodes.removeElement(neighbor);
            }
        }
    }


    private static Stack<Node> getNodes(ArrayList<Record> records) {
        Collections.sort(records);
        Stack<Node> nodes = new Stack<Node>();
        for (Record record : records) {
            nodes.push(new Node(record));
        }
        return nodes;
    }

    private static void setEdges(Stack<Node> nodes) {
        for (Node firstNode : nodes) {
            for (Node secondNode : nodes) {
                if (firstNode != secondNode && firstNode.record.isDuplicate(secondNode.record))
                    firstNode.addNeighbor(secondNode);
            }
        }
    }

}
