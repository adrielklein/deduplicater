package deduplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

        ArrayList<Record> uniqueRecords = new ArrayList<Record>();
        ArrayList<Change> changes = new ArrayList<Change>();
        findUniqueRecords(nodes, uniqueRecords, changes);
        Collections.sort(uniqueRecords);

        return new DeduplicationResult(uniqueRecords, changes);
    }

    private static void findUniqueRecords(Stack<Node> nodes, ArrayList<Record> uniqueRecords, ArrayList<Change> changes) {
        HashSet<Node> removedNodes = new HashSet<Node>();
        while (!nodes.empty()) {
            Node node = nodes.pop();
            uniqueRecords.add(node.record);
            removeNeighbors(nodes, changes, removedNodes, node);
        }
    }

    private static void removeNeighbors(Stack<Node> nodes, ArrayList<Change> changes, HashSet<Node> removedNodes, Node node) {
        for (Node neighbor : node.neighbors) {
            if (!removedNodes.contains(neighbor)) {
                changes.add(new Change(neighbor.record, node.record));
                nodes.removeElement(neighbor);
                removedNodes.add(neighbor);
            }
        }
    }


    private static Stack<Node> getNodes(ArrayList<Record> records) {
        Collections.sort(records);
        Stack<Node> nodes = new Stack<Node>();
        for (Record record : records) {
            nodes.push(new Node(record));
        }
        setEdges(nodes);
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
