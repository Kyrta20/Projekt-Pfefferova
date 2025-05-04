package org.example;

import java.awt.*;
import java.util.ArrayList;


public class Snake {

    private ArrayList<Point> parts;

    private char direction;

    public Snake(int x, int y) {

        parts = new ArrayList<>();

        parts.add(new Point(x, y));

        direction = 'R';
    }
    public void move() {

        Point head = parts.get(0);

        int newX = head.x;
        int newY = head.y;
        switch (direction) {
            case 'L':
                newX -= 10;
                break;
            case 'R':
                newX += 10;
                break;
            case 'U':
                newY -= 10;
                break;
            case 'D':
                newY += 10;
                break;
        }

        parts.add(0, new Point(newX, newY));

        if (parts.size() > 1 && !intersects()) {
            parts.remove(parts.size() - 1);
        }
    }

    public void gettingBigger() {
        parts.add(new Point(parts.get(parts.size() - 1)));
    }

    public Rectangle getBounds() {
        return new Rectangle(parts.get(0).x, parts.get(0).y, 10, 10);
    }

    public int getX() {
        return parts.get(0).x;
    }

    public int getY() {
        return parts.get(0).y;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean intersects() {

        Point head = parts.get(0);

        for (int i = 1; i < parts.size(); i++) {
            if (head.x == parts.get(i).x && head.y == parts.get(i).y) {
                return true;
            }
        }

        return false;
    }
    public Point getHead() {
        return parts.get(0);  // Return the first part (head)
    }
    public ArrayList<Point> getParts() {
        return parts;  // Return all parts of the snake
    }
    public void paint(Graphics g) {

        for (Point part : parts) {
            g.fillRect(part.x, part.y, 10, 10);
        }
    }
}
