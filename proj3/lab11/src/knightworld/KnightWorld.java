package knightworld;

import org.apache.hc.core5.annotation.Internal;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private final TETile[][] tiles;
    private final int WIDTH;
    private final int HEIGHT;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public KnightWorld(int width, int height, int holeSize) {
        WIDTH = width;
        HEIGHT = height;
        tiles = new TETile[WIDTH][HEIGHT];
        fillAll();
        int randomPointX = RANDOM.nextInt(WIDTH);
        int randomPointY = RANDOM.nextInt(HEIGHT);
        digHoleRecursive(randomPointX, randomPointY, holeSize);
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public void digHoleRecursive(int startPointX, int startPointY, int size) {
        if (startPointX + size - 1 < 0 || startPointY + size - 1 < 0 || startPointX >= WIDTH || startPointY >= HEIGHT) {
            return;
        }
        int checkPointX;
        int checkPointY;
        if (startPointX < 0) {
            checkPointX = startPointX + size - 1;
        } else {
            checkPointX = startPointX;
        }
        if (startPointY < 0) {
            checkPointY = startPointY + size - 1;
        } else {
            checkPointY = startPointY;
        }
        if (tiles[checkPointX][checkPointY] == Tileset.NOTHING) {
            return;
        }

        digHole(startPointX, startPointY, size);

        // left top
        int newStartPointX = startPointX - 2 * size;
        int newStartPointY = startPointY + size;
        digHoleRecursive(newStartPointX, newStartPointY, size);

        // right top
        newStartPointX = startPointX + size;
        newStartPointY = startPointY + 2 * size;
        digHoleRecursive(newStartPointX, newStartPointY, size);

        // right bottom
        newStartPointX = startPointX + 2 * size;
        newStartPointY = startPointY - size;
        digHoleRecursive(newStartPointX, newStartPointY, size);

        // left bottom
        newStartPointX = startPointX - size;
        newStartPointY = startPointY - 2 * size;
        digHoleRecursive(newStartPointX, newStartPointY, size);
    }

    public void digHole(int startPointX, int startPointY, int size) {
        for (int x = startPointX; x <= startPointX + size - 1; x++) {
            for (int y = startPointY; y <= startPointY + size - 1; y++) {
                if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }
    }

    public void fillAll() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                tiles[x][y] = Tileset.LOCKED_DOOR;
            }
        }
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 50;
        int height = 30;
        int holeSize = 2;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
