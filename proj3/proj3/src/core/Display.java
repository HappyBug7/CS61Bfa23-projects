package core;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import javax.imageio.stream.ImageOutputStream;
import java.awt.*;

public class Display {
    private final int WIDTH;
    private final int HEIGHT;
    private final double WIDTH_FACTOR;
    public static class Element {
        public final int fontSize;
        public final int margin;
        public String content;
        public Element(int fontSize, int margin, String content) {
            this.fontSize = fontSize;
            this.margin = margin;
            this.content = content;
        }
    }

    public Display(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        WIDTH_FACTOR = 0.5;
    }

    public double[] getPosition(Element[] elements) {
        double totalHeight = 0;
        for (Element element : elements) {
            totalHeight += element.fontSize + element.margin * 2;
        }
        double spaceLeft = HEIGHT - totalHeight;
        double[] positions = new double[elements.length];
        positions[0] = HEIGHT - spaceLeft/2.0 - elements[0].margin - elements[0].fontSize/2.0;
        for (int i = 1; i < elements.length; i++) {
            positions[i] = positions[i - 1] - elements[i - 1].fontSize / 2.0 - elements[i - 1].margin - elements[i].margin - elements[i].fontSize/2.0;
        }
        return positions;
    }

    public void columnCenterDisplay(Element[] elements) {
        StdDraw.clear(new Color(0,0,0));
        StdDraw.setPenColor(new Color(255,255,255));

        double[] positions = getPosition(elements);
        for (int i = 0; i < elements.length; i++) {
            Font font = new Font("Comic Sans MS", Font.BOLD, elements[i].fontSize);
            StdDraw.setFont(font);
            StdDraw.text(WIDTH/2.0, positions[i], elements[i].content);
        }
        StdDraw.show();
    }

    public int makeOption(Element title, Element[] options, char[][] optionKeyboardRepresent) {
        Element[] elements = new Element[options.length + 1];
        elements[0] = title;
        for (int i = 0; i < options.length; i++) {
            elements[i + 1] = options[i];
        }
        columnCenterDisplay(elements);
        double[] positions = getPosition(elements);
        while (true) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            while (!StdDraw.hasNextKeyTyped()&&!StdDraw.isMousePressed()) {
                mouseX = StdDraw.mouseX();
                mouseY = StdDraw.mouseY();
            }
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                for (int i = 1; i < elements.length; i++) {
                    for (char key : optionKeyboardRepresent[i-1]) {
                        if (c == key) {
                            return i;
                        }
                    }
                }
            } else {
                for (int i = 1; i < elements.length; i++) {
                    if (mouseY >= positions[i] - elements[i].margin - elements[i].fontSize/2.0 && mouseY <= positions[i] + elements[i].margin + elements[i].fontSize/2.0) {
                        if (mouseX >= WIDTH / 2.0 - elements[i].fontSize * elements[i].content.length() * WIDTH_FACTOR/2.0 && mouseX <= WIDTH / 2.0 + elements[i].fontSize * elements[i].content.length() * WIDTH_FACTOR/2.0) {
                            return i;
                        }
                    }
                }
            }
        }
    }
}
