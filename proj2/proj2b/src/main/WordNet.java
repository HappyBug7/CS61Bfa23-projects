package main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class WordNet {
    private final HyponymGraph graph;
    private final HashMap<String, ArrayList<Integer>> wordToIdMap;
    public WordNet(String synsetPath, String hyponymPath) {
        graph = new HyponymGraph();
        wordToIdMap = new HashMap<>();
        In synset = new In(synsetPath);
        while (synset.hasNextLine()) {
            String nextLine = synset.readLine();
            String[] splitLine = nextLine.split(",");
            String[] words = splitLine[1].split(" ");
            int id = Integer.parseInt(splitLine[0]);
            graph.addNode(id, words);
            for (String word : words) {
                if (!wordToIdMap.containsKey(word)) {
                    wordToIdMap.put(word, new ArrayList<>());
                }
                wordToIdMap.get(word).add(id);
            }
        }
        In hyponym = new In(hyponymPath);
        while (hyponym.hasNextLine()) {
            String nextLine = hyponym.readLine();
            String[] splitLine = nextLine.split(",");
            int word = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                graph.addEdge(word, Integer.parseInt(splitLine[i]));
            }
        }
    }

    public ArrayList<String> getHyponym(List<String> words) {
        if (words.isEmpty()) {
            return new ArrayList<>();
        }
        if (words.size() == 1) {
            return getHyponym(words.get(0));
        }
        ArrayList<String> returnList = new ArrayList<>();
        returnList.addAll(getHyponym(words.get(0)));
        for (String word : words) {
            returnList.retainAll(getHyponym(word));
        }
        return returnList;
    }

    public ArrayList<String> getHyponym(String word) {
        ArrayList<Integer> ids = wordToIdMap.get(word);
        ArrayList<String> hyponyms = new ArrayList<>();
        if (ids == null) {
            return hyponyms;
        }
        for (Integer id : ids) {
            ArrayList<String> words = graph.traversal(id);
            for (String w : words) {
                if (!hyponyms.contains(w)) {
                    hyponyms.add(w);
                }
            }
        }
        hyponyms.sort(Comparator.naturalOrder());
        return hyponyms;
    }

}
