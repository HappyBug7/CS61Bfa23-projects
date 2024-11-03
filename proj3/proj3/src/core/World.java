package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import ucb.gui.Widget;

import java.awt.*;

public class World {

    private Map map;
    private final Menu menu;

    private final int TILESIZE = 16;
    private final String[] OPTIONS = {"New Game (n)", "Load Game (l)", "Quit (q)"};
    private final char[][] OPTION_KEYS = new char[OPTIONS.length][2];
    private static final String TITLE = "HappyBug's Journey";
    private final int WIDTH;
    private final int HEIGHT;
    private int SEED;
    private final TERenderer ter;
    private String tileDescription = "";
    private String prevTileDescription = "";


    public World(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        OPTION_KEYS[0][0] = 'n';
        OPTION_KEYS[0][1] = 'N';
        OPTION_KEYS[1][0] = 'l';
        OPTION_KEYS[1][1] = 'L';
        OPTION_KEYS[2][0] = 'e';
        OPTION_KEYS[2][1] = 'E';
        menu = new Menu(width * TILESIZE, height * TILESIZE, OPTIONS, TITLE, OPTION_KEYS);
        ter = new TERenderer();
    }

    public void play() {
        menu.init();
        int option = menu.show();
        if (option == 1) {
            SEED = menu.getSeed();
            map = new Map(WIDTH, HEIGHT, SEED);
            ter.initialize(WIDTH, HEIGHT + 4, 0, 0);
            StdDraw.clear(new Color(255,255,255));
            ter.renderFrame(map.getScene());
            listenUserEvent();
        }
    }

    public void listenUserEvent() {
        while (true) {
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();
            if (x < HEIGHT) {
                TETile tile = map.getTile(x, y);
                if (tile != null && !tile.description().equals(prevTileDescription)) {
                    prevTileDescription = tileDescription;
                    tileDescription = tile.description();
                    updateDescription();
                }
            }
            if (StdDraw.hasNextKeyTyped()) {
                char command = StdDraw.nextKeyTyped();
                map.moveBug(command);
                renderFrame();
            }
        }
    }

    public void renderFrame() {
        Font font = new Font("Monaco", Font.BOLD, TILESIZE - 2);
        StdDraw.setFont(font);
        ter.renderFrame(map.getScene());
        updateDescription();
    }

    public void updateDescription() {
        Font font = new Font("Comic Sans MS", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(WIDTH/2.0, HEIGHT + 2, WIDTH/2.0, 2);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(5, HEIGHT + 2, tileDescription);
        StdDraw.show();
    }

    
}
