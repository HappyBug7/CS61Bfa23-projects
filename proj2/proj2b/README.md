# Project 2B

### Solution

(1) use `HashMap<Integer, Node>` to represent the wordnet map, where the `Node` be a nested class in `HyponymGraph`:

```
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
```

 therefore the `HyponymGraph` class can `addNode`, `addEdge`, `traversal` to build the word net.



(2) use `HashMap<String, ArrayList<Integer>> wordToIdMap` to map the relation between words and there ids, for example:

```
0, bug, dummy
1, happy bug, dummy
```

then `wordToIdMap.get("bug")` would be [0,1], which is useful when getting the hyponyms of a word



(3) when initialling:

- read `hyponyms.txt`, and:
  - add node to wordmap
  - for each word (e.g `1, happy bug, dummy`, then is `happy` and `bug`), add the id of the words (in this case`1`) to each word's array (`wordToIdMap.get(word).add(id)`)
- read `synset.txt`, and:
  - add edge between each node.



(4) when getting hyponyms:

- get the list if ids of the word (`wordToIdMap.get("bug")`)

- for each id (`0` and `1`) :

  - traversal the node
  - add all the list together: `returnList.addAll(getHyponym(word));`
- for multiple words:
  - get the hyponyms of all word
  - get the intersection of  all the list: `returnList.retainAll(getHyponym(word));`

