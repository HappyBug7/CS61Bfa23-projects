package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.KeyEvent;

public class Menu {
    private final String[] OPTIONS;
    private final String TITLE;
    private final int WIDTH;
    private final int HEIGHT;
    private final int TITLE_FONT_SIZE;
    private final int OPTIONS_FONT_SIZE;
    public Menu(int width, int height, String[] options, String title) {
        WIDTH = width;
        HEIGHT = height;
        TITLE_FONT_SIZE = 50;
        OPTIONS_FONT_SIZE = 20;
        OPTIONS = options;
        TITLE = title;
    }
    public int show() {
        // Initialize
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(new Color(0, 0, 0));
        Font titleFont = new Font("Comic Sans MS", Font.BOLD, TITLE_FONT_SIZE);
        Font optionFont = new Font("Comic Sans MS", Font.BOLD, OPTIONS_FONT_SIZE);
        StdDraw.setPenColor(new Color(255, 255, 255));

        // Centering the contents
        int MARGIN = 20;
        int contentHeight = TITLE_FONT_SIZE + OPTIONS.length * ( MARGIN + OPTIONS_FONT_SIZE );
        int spaceLeft = HEIGHT - contentHeight;
        double[] yPositions = new double[OPTIONS.length + 1];
        yPositions[0] = HEIGHT - spaceLeft/2.0;
        yPositions[1] = yPositions[0] - MARGIN - TITLE_FONT_SIZE;
        for (int i = 1; i < OPTIONS.length; i++) {
            yPositions[i + 1] = yPositions[i] - MARGIN - OPTIONS_FONT_SIZE;
        }

        // Start drawing
        StdDraw.setFont(titleFont);
        StdDraw.text(WIDTH / 2.0, yPositions[0], TITLE);
        for (int i = 0; i < OPTIONS.length; i++) {
            StdDraw.setFont(optionFont);
            StdDraw.text(WIDTH / 2.0, yPositions[i + 1], OPTIONS[i]);
        }
        StdDraw.show();

        // Intercept mouse event
        double mousePositionX = StdDraw.mouseX();
        double mousePositionY = StdDraw.mouseY();
        while (!StdDraw.isMousePressed()&&!StdDraw.hasNextKeyTyped()) {
            mousePositionX = StdDraw.mouseX();
            mousePositionY = StdDraw.mouseY();
        }
        int Option = 0;
        if (StdDraw.hasNextKeyTyped()) {
            char keyPressed = StdDraw.nextKeyTyped();
            if (keyPressed == 'N' || keyPressed == 'n') {
                Option = 1;
            }
            if (keyPressed == 'N' || keyPressed == 'l') {
                Option = 2;
            }
            if (keyPressed == 'Q' || keyPressed == 'q') {
                Option = 3;
            }
        } else {
            for (int i = 0; i < OPTIONS.length; i++) {
                double widthTolerance =  OPTIONS[i].length() * OPTIONS_FONT_SIZE * 0.5;
                if (mousePositionY >= yPositions[i + 1] - OPTIONS_FONT_SIZE / 2.0 && mousePositionY <= yPositions[i + 1] + OPTIONS_FONT_SIZE / 2.0 && mousePositionX >= WIDTH / 2.0 - widthTolerance && mousePositionX <= WIDTH / 2.0 + widthTolerance) {
                    Option = i + 1;
                    break;
                }
            }
        }
        return Option;
    }
}
