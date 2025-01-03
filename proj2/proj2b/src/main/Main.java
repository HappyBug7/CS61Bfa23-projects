package main;

import browser.NgordnetServer;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        
        /* The following code might be useful to you.

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap ngm = new NGramMap(wordFile, countFile);

        */

        String hyponymPath = "./data/wordnet/hyponyms.txt";
        String synsetPath = "./data/wordnet/synsets.txt";
        WordNet wn = new WordNet(synsetPath, hyponymPath);

        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new HyponymsHandler(wn));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
