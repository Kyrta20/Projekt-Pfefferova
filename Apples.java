package org.example;

import java.awt.*;

public class Apples {
    private int x, y;

    public Apples(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN); // Zelené jablko
        g.fillOval(x, y, 10, 10);
    }

    public void paintSpecial(Graphics g) {
        g.setColor(Color.RED); // Červené jablko
        g.fillOval(x, y, 10, 10);
    }
}
