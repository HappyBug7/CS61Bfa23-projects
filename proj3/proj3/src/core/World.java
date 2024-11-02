package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import ucb.gui.Widget;

public class World {

    private final Map map;
    private final Menu menu;

    private final int TILESIZE = 16;
    private final String[] OPTIONS = {"New Game (n)", "Load Game (l)", "Quit (q)"};
    private static final String TITLE = "Happy Bug's Journey";
    private int WIDTH;
    private int HEIGHT;


    public World(int width, int height, int seed) {
        WIDTH = width;
        HEIGHT = height;
        map = new Map(width, height, seed);
        menu = new Menu(width * TILESIZE, height * TILESIZE, OPTIONS, TITLE);
    }

    public void play() {
        int option = menu.show();
        if (option == 1) {
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(map.getMap());
        }
    }
}
