package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Game extends JPanel implements KeyListener {


    private Snake snake1;


    private Snake snake2;


    private Apples apple;


    private Apples specialApple;

    // Obtížnost hry (1 = Easy, 2 = Hard).
    private int difficulty;


    private int snakeSpeed;


    private boolean startGame = true;


    private boolean gameForTwo = false;


    private Timer timer;


    public Game(int difficulty, boolean gameForTwo) {
        // Nastavíme obtížnost a typ hry.
        this.difficulty = difficulty;
        this.gameForTwo = gameForTwo;

        // Podle obtížnosti nastavíme rychlost pohybu hada.
        if (difficulty == 1) {
            snakeSpeed = 100; // Easy
        } else {
            snakeSpeed = 50; // Hard
        }

        snake1 = new Snake(100, 100);
        if (gameForTwo) {
            snake2 = new Snake(200, 200);
        }

        apple = new Apples(new Random().nextInt(39) * 10, new Random().nextInt(39) * 10);
        specialApple = new Apples(new Random().nextInt(39) * 10, new Random().nextInt(39) * 10);

        while (specialApple.getBounds().intersects(apple.getBounds())) {
            specialApple = new Apples(new Random().nextInt(39) * 10, new Random().nextInt(39) * 10);
        }

        addKeyListener(this);
        setFocusable(true);

        // Inicializujeme timer pro aktualizaci hry.
        timer = new Timer(snakeSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (startGame) {
                    update();
                    repaint();
                } else {

                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Konec hry!");
                }
            }
        });

        timer.start();
    }

    private void update() {
        snake1.move();

        if (gameForTwo) {
            snake2.move();
        }

        // Zkontrolujeme, zda první had snědl standardní jablko.
        if (snake1.getBounds().intersects(apple.getBounds())) {

            snake1.gettingBigger();

            apple = new Apples(new Random().nextInt(39) * 10, new Random().nextInt(39) * 10);

            while (apple.getBounds().intersects(specialApple.getBounds())) {
                apple = new Apples(new Random().nextInt(39) * 10, new Random().nextInt(39) * 10);
            }
        }

        // Zkontrolujeme, zda druhý had snědl standardní jablko (pouze pro hru pro dva hráče).
        if (gameForTwo && snake2.getBounds().intersects(apple.getBounds())) {
            snake2.gettingBigger();

            apple = new Apples(new Random().nextInt(39) * 10, new Random().nextInt(39) * 10);

            while (apple.getBounds().intersects(specialApple.getBounds())) {
                apple = new Apples(new Random().nextInt(39) * 10, new Random().nextInt(39) * 10);
            }
        }


        // Zkontrolujeme, zda první had snědl speciální jablko.
        if (snake1.getBounds().intersects(specialApple.getBounds())) {
            startGame = false;
        }

        // Zkontrolujeme, zda druhý had snědl speciální jablko (pouze pro hru pro dva hráče).
        if (gameForTwo && snake2.getBounds().intersects(specialApple.getBounds())) {
            startGame = false;
        }
        // Zkontrolujeme, zda první had opustil hranice obrazovky.
        if (snake1.getX() < 0 || snake1.getX() >= 600|| snake1.getY() < 0 || snake1.getY() >= 600) {
            startGame = false;
        }

        // Zkontrolujeme, zda druhý had opustil hranice obrazovky (pouze pro hru pro dva hráče).
        if (gameForTwo && (snake2.getX() < 0 || snake2.getX() >= 600 || snake2.getY() < 0 || snake2.getY() >= 600)) {
            startGame = false;
        }

        // Zkontrolujeme, zda se hady vzájemně protnuly (pouze pro hru pro dva hráče).
        if (gameForTwo && snake1.getBounds().intersects(snake2.getBounds())) {
            startGame = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        snake1.paint(g);
        g.setColor(Color.GRAY);

        if (gameForTwo) {
            snake2.paint(g);
        }

        apple.paint(g);

        specialApple.paintSpecial(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT && snake1.getDirection() != 'R') {
            snake1.setDirection('L');
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake1.getDirection() != 'L') {
            snake1.setDirection('R');
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && snake1.getDirection() != 'D') {
            snake1.setDirection('U');
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && snake1.getDirection() != 'U') {
            snake1.setDirection('D');
        }

        if (gameForTwo) {
            if (e.getKeyCode() == KeyEvent.VK_A && snake2.getDirection() != 'R') {
                snake2.setDirection('L');
            }
            if (e.getKeyCode() == KeyEvent.VK_D && snake2.getDirection() != 'L') {
                snake2.setDirection('R');
            }
            if (e.getKeyCode() == KeyEvent.VK_W && snake2.getDirection() != 'D') {
                snake2.setDirection('U');
            }
            if (e.getKeyCode() == KeyEvent.VK_S && snake2.getDirection() != 'U') {

                snake2.setDirection('D');
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        int difficulty = JOptionPane.showConfirmDialog(null, "Chcete hrát na jednoduchou obtížnost?", "Obtížnost", JOptionPane.YES_NO_OPTION);

        int gameForTwo = JOptionPane.showConfirmDialog(null, "Chcete hrát hru pro dva hráče?", "Hra pro dva hráče", JOptionPane.YES_NO_OPTION);

        Game game = new Game(difficulty == 0 ? 1 : 2, gameForTwo == 0);

        frame.add(game);

        frame.setVisible(true);
    }
}
