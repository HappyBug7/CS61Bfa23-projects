package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // build your own world!
        int WIDTH = 80;
        int HEIGHT = 50;
        int SEED = 114514;
//        int SEED = 1919810;
//        int SEED = 24;
        World world = new World(WIDTH, HEIGHT, SEED);
        world.play();

    }
}
