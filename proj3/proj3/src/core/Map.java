package core;

import edu.princeton.cs.algs4.In;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private TETile[][] map;
    private final int WIDTH;
    private final int HEIGHT;
    private final long SEED;
    private final Random RANDOM;
    private final int MAX_ROOM_SIZE_HEIGHT = 8;
    private final int MIN_ROOM_SIZE_HEIGHT = 4;
    private final int MAX_ROOM_SIZE_WIDTH = 10;
    private final int MIN_ROOM_SIZE_WIDTH = 6;
    private final double CROWDED_FACTOR = 0.5;
    private int MAX_ROOM_NUM;
    private ArrayList<Room> rooms;
    private ArrayList<Tuple<Room>> edges;

    public Map(int width, int height, long seed) {
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(SEED);
        map = new TETile[WIDTH][HEIGHT];
        rooms = new ArrayList<>();
        edges = new ArrayList<>();
        MAX_ROOM_NUM =  (int)(CROWDED_FACTOR * WIDTH * HEIGHT / ((double) ((MAX_ROOM_SIZE_HEIGHT + MIN_ROOM_SIZE_HEIGHT) / 2) * (double) ((MAX_ROOM_SIZE_WIDTH + MIN_ROOM_SIZE_WIDTH) / 2)));
        initialMap();
        generateRandomRoomHandler();
        generateEdges();
        generateHallWay();
    }

    static class Tuple<T> {
        public T x;
        public T y;
        public Tuple(T x, T y) {
            this.x = x;
            this.y = y;
        }
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    private class Room {
        public int x;
        public int y;
        public int xMax;
        public int yMax;
        public int xMin;
        public int yMin;
        public Room(int xMax, int xMin, int yMax, int yMin) {
            this.x = (xMax + xMin)/2;
            this.y = (yMax + yMin)/2;
            this.xMax = xMax;
            this.yMax = yMax;
            this.xMin = xMin;
            this.yMin = yMin;
        }
    }

    private void initialMap() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                map[x][y] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getMap() {
        return map;
    }

    private void generateRoom(Tuple<Integer> position, int width, int height) {
        if (map[position.x][position.y] == Tileset.WALL || map[position.x][position.y] == Tileset.FLOOR) {
            return;
        }
        int xMax = position.x;
        int yMax = position.y;
        int xMin = position.x;
        int yMin = position.y;

        // get the boundary of x
        boolean isOkeyLeft = true;
        boolean isOkeyRight = true;
        for (int i = 0 ; i <= width; i++) {
            for (int j = -height; j <= height; j++) {
                int xLeft = position.x - i;
                int xRight = position.x + i;
                int y = position.y + j;
                if (xLeft < 0) {
                    isOkeyLeft = false;
                    continue;
                }
                if (xRight >= WIDTH) {
                    isOkeyRight = false;
                    continue;
                }
                if (y >= 0 && y < HEIGHT && map[xLeft][y] != Tileset.NOTHING) {
                    isOkeyLeft = false;
                    continue;
                }
                if (y >= 0 && y < HEIGHT && map[xRight][y] != Tileset.NOTHING) {
                    isOkeyRight = false;
                    continue;
                }
            }
            if (isOkeyLeft) {
                xMin = position.x - i;
            }
            if (isOkeyRight) {
                xMax = position.x + i;
            }
        }

        // get the boundary of y
        boolean isOkeyTop = true;
        boolean isOkeyBottom = true;
        for (int i = 0; i <= height; i++) {
            for (int j = xMin; j <= xMax; j++) {
                int x = j;
                int yTop = position.y + i;
                int yBottom = position.y - i;
                if (yBottom < 0) {
                    isOkeyBottom = false;
                    continue;
                }
                if (yTop >= HEIGHT) {
                    isOkeyTop = false;
                    continue;
                }
                if (map[x][yTop] != Tileset.NOTHING) {
                    isOkeyTop = false;
                    continue;
                }
                if (map[x][yBottom] != Tileset.NOTHING) {
                    isOkeyBottom = false;
                    continue;
                }
            }
            if (isOkeyTop) {
                yMax = position.y + i;
            }
            if (isOkeyBottom) {
                yMin = position.y - i;
            }
        }

        if (xMax - xMin <= MIN_ROOM_SIZE_WIDTH) {
            return;
        }
        if (yMax - yMin <= MIN_ROOM_SIZE_HEIGHT) {
            return;
        }

        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                int x = i;
                int y = j;
                if (x > xMin && x < xMax && y > yMin && y < yMax) {
                    map[x][y] = Tileset.FLOOR;
                } else {
                    map[x][y] = Tileset.WALL;
                }
            }
        }
        Room newRoom = new Room(xMax, xMin, yMax, yMin);
        rooms.add(newRoom);
    }

    private void setTile(int x, int y, TETile tile) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return;
        }
        map[x][y] = tile;
    }

    private void generateRandomRoomHandler() {
        Random random = new Random(SEED + RANDOM.nextInt() + 10);
        int roomNum = MAX_ROOM_NUM / 2 + random.nextInt((int)(MAX_ROOM_NUM / 2));
        for (int i = 0; i < roomNum; i++) {
            Tuple<Integer> pos = new Tuple<>(random.nextInt(WIDTH), random.nextInt(HEIGHT));
            int width = MIN_ROOM_SIZE_WIDTH + random.nextInt(MAX_ROOM_SIZE_WIDTH - MIN_ROOM_SIZE_WIDTH);
            int height = MIN_ROOM_SIZE_HEIGHT + random.nextInt(MAX_ROOM_SIZE_HEIGHT - MIN_ROOM_SIZE_HEIGHT);
            generateRoom(pos, width, height);
        }
        System.out.println(rooms);
    }

    private boolean isInRoom(Tuple<Integer> position) {
        for (Room room : rooms) {
            if (position.x < room.xMax && position.x > room.xMin && position.y < room.yMax && position.y > room.yMin) {
                return true;
            }
        }
        return false;
    }

    private void generateEdges() {
        ArrayList<Room> visited = new ArrayList<>();
        visited.add(rooms.get(0));
        mstHelper(rooms.get(0), visited);
        System.out.println(edges);
    }

    private double getMinDistance(Room room1, Room room2) {
        int xMin = Math.abs(room1.x - room2.x) - (room1.xMax - room1.xMin)/2 - (room2.xMax - room2.xMin)/2;
        int yMin = Math.abs(room1.y - room2.y) - (room1.yMax - room1.yMin)/2 - (room2.yMax - room2.yMin)/2;
        if (xMin < 0) {
            xMin = 0;
        }
        if (yMin < 0) {
            yMin = 0;
        }
        return Math.sqrt(Math.pow(xMin, 2) + Math.pow(yMin, 2));
    }

    // recursively generate MST
    private void mstHelper(Room roomCurr, ArrayList<Room> visited) {
        double minDistance = Double.MAX_VALUE;
        Room roomNext = roomCurr;
        for (Room room : rooms) {
            if (!visited.contains(room)) {
                double distance = getMinDistance(room, roomCurr);
                if (distance < minDistance) {
                    minDistance = distance;
                    roomNext = room;
                }
            }
        }
        if (minDistance == Double.MAX_VALUE) {
            return;
        } else {
            Tuple<Room> newEdge = new Tuple<>(roomNext, roomCurr);
            edges.add(newEdge);
            visited.add(roomNext);
            mstHelper(roomNext, visited);
        }
    }

    private void generateHallWay() {
        int POSITION_TOLERANCE = 2;
        // first: create wall
        for (Tuple<Room> edge : edges) {
            Room room1 = edge.x;
            Room room2 = edge.y;
            int deltaX = room1.x - room2.x;
            int deltaY = room1.y - room2.y;
            if (deltaX != 0) {
                for (int i = 0; Math.abs(i) <= Math.abs(deltaX); i -= deltaX / Math.abs(deltaX)) {
                    Tuple<Integer> pos = new Tuple<>(room1.x + i, room1.y);
                    if (!isInRoom(pos)) {
                        setTile(room1.x + i, room1.y - 1, Tileset.WALL);
                        setTile(room1.x + i, room1.y + 1, Tileset.WALL);
                    }
                }
            }
            if (deltaY != 0) {
                for (int i = 0; Math.abs(i) <= Math.abs(deltaY); i += deltaY / Math.abs(deltaY)) {
                    Tuple<Integer> pos = new Tuple<>(room2.x, room2.y + i);
                    if (!isInRoom(pos)) {
                        setTile(room2.x + 1, room2.y + i, Tileset.WALL);
                        setTile(room2.x - 1, room2.y + i, Tileset.WALL);
                    }
                }
            }
            Tuple<Integer> pos = new Tuple<>(room2.x, room1.y);
            if (!isInRoom(pos)) {
                setTile(room2.x + 1, room1.y + 1, Tileset.WALL);
                setTile(room2.x - 1, room1.y + 1, Tileset.WALL);
                setTile(room2.x + 1, room1.y - 1, Tileset.WALL);
                setTile(room2.x - 1, room1.y - 1, Tileset.WALL);
            }
        }
        // then: dig hole
        for (Tuple<Room> edge : edges) {
            Room room1 = edge.x;
            Room room2 = edge.y;
            int deltaX = room1.x - room2.x;
            int deltaY = room1.y - room2.y;
            // then: dig hole
            if (deltaX != 0) {
                for (int i = 0; Math.abs(i) <= Math.abs(deltaX); i -= deltaX / Math.abs(deltaX)) {
                    setTile(room1.x + i, room1.y, Tileset.FLOOR);
                }
            }
            if (deltaY != 0) {
                for (int i = 0; Math.abs(i) <= Math.abs(deltaY); i += deltaY / Math.abs(deltaY)) {
                    setTile(room2.x, room2.y + i, Tileset.FLOOR);
                }
            }
        }
    }
}
