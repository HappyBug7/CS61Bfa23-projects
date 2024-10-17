package main;

import java.util.ArrayList;
import java.util.HashMap;

public class HyponymGraph {
    private static class Node {
        int id;
        String[] words;
        ArrayList<Node> hyponyms;
        public Node(String[] words, int id) {
            this.words = words;
            hyponyms = new ArrayList<>();
            this.id = id;
        }
    }
    private final HashMap<Integer, Node> map;

    public HyponymGraph() {
        map = new HashMap<>();
    }

    public void addEdge(int from, int to) {
        Node fromNode = map.get(from);
        Node toNode = map.get(to);
        fromNode.hyponyms.add(toNode);
    }

    public void addNode(int id, String[] words) {
        if (!map.containsKey(id)) {
            Node node = new Node(words, id);
            map.put(id, node);
        }
    }

    public ArrayList<String> traversal(int id) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        return traversal(id, words, visited);
    }

    public ArrayList<String> traversal(int id, ArrayList<String> words, ArrayList<Integer> visited) {
        if (visited.contains(id)) {
            return words;
        }
        Node root = map.get(id);
        if (root == null) {
            return words;
        }
        visited.add(id);
        for (String word : root.words) {
            if (!words.contains(word)) {
                words.add(word);
            }
        }
        for (Node node : root.hyponyms) {
            traversal(node.id, words, visited);
        }
        return words;
    }
}
