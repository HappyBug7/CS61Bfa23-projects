package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Menu {
    private final String[] OPTIONS;
    private final char[][] OPTION_KEYS;
    private final String TITLE;
    private final int WIDTH;
    private final int HEIGHT;
    private final int TITLE_FONT_SIZE;
    private final int OPTIONS_FONT_SIZE;
    private final int MARGIN;
    public Menu(int width, int height, String[] options, String title, char[][] optionKeys) {
        WIDTH = width;
        HEIGHT = height;
        TITLE = title;
        OPTIONS = options;
        OPTION_KEYS = optionKeys;
        TITLE_FONT_SIZE = 50;
        OPTIONS_FONT_SIZE = 20;
        MARGIN = 20;
    }
    public void init() {
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
    }
    public int show() {
        Display display = new Display(WIDTH, HEIGHT);
        Display.Element title = new Display.Element(TITLE_FONT_SIZE, MARGIN, TITLE);
        Display.Element[] options = new Display.Element[OPTIONS.length];
        for (int i = 0; i < OPTIONS.length; i++) {
            options[i] = new Display.Element(OPTIONS_FONT_SIZE, MARGIN, OPTIONS[i]);
        }
        return display.makeOption(title, options, OPTION_KEYS);
    }

    public int getSeed() {
        Display display = new Display(WIDTH, HEIGHT);
        Display.Element[] elements = new Display.Element[3];
        elements[0] = new Display.Element(30, MARGIN, "Enter the seed u want (leave blank for random seed)");
        elements[1] = new Display.Element(20, MARGIN, "");
        elements[2] = new Display.Element(30, MARGIN, "OK");
        display.columnCenterDisplay(elements);
        double[] positions = display.getPosition(elements);
        while (true) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            while (!StdDraw.isMousePressed()) {
                mouseX = StdDraw.mouseX();
                mouseY = StdDraw.mouseY();
                while (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    if (c != '\n') {
                        elements[1].content += c;
                        display.columnCenterDisplay(elements);
                    } else {
                        if (!elements[1].content.isEmpty()) {
                            return Integer.parseInt(elements[1].content);
                        } else {
                            Random rand = new Random();
                            return rand.nextInt(Integer.MAX_VALUE);
                        }
                    }
                }
            }
            if (mouseY >= positions[2] - elements[2].fontSize/2.0 - elements[2].margin && mouseY <= positions[2] + elements[2].fontSize/2.0 + elements[2].margin) {
                if (mouseX >= WIDTH/2.0 - elements[2].content.length() * elements[2].fontSize * 0.5 && mouseX <= WIDTH/2.0 + elements[2].content.length() * elements[2].fontSize * 0.5) {
                    if (!elements[1].content.isEmpty()) {
                        return Integer.parseInt(elements[1].content);
                    } else {
                        Random rand = new Random();
                        return rand.nextInt(Integer.MAX_VALUE);
                    }
                }
            }
        }
    }
}
