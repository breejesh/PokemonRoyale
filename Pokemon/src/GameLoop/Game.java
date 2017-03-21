// Author: Sagar Kamdar & Breejesh Rathod
package GameLoop;

import GameLoop.Graphics.Level;
import GameLoop.Graphics.Screen;
import GameLoop.Input.Keyboard;
import GameLoop.Input.Mouse;
import GameLoop.Units.Pokemon;
import GameLoop.Units.Red;
import Utilities.Attack;
import Utilities.Cursor;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

    public enum State {
        Moving, Fighting
    }
    public static State state = State.Fighting;
    public static int width = 640;
    public static int height = 360;
    public static int scale = 1;
    public static double FPS = 120.0;

    private Thread thread;
    private JFrame frame;
    private boolean running = false;

    private Screen screen;
    private Cursor cursor;
    private Keyboard key;
    private Mouse mouse;
    private Level map;
    private Red red;

    private BufferStrategy bs;
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        cursor = new Cursor(4);
        frame = new JFrame();
        key = new Keyboard();
        mouse = new Mouse();
        map = new Level();
        Attack.readAttacksJSON();
        Pokemon.readPokemonsJSON();
        red = Red.deserializeRead();

        addKeyListener(key);
        //addMouseListener(mouse);
        //addMouseMotionListener(mouse);
    }

    public synchronized void start() {
        running = true;
        System.out.println("x: " + red.x + " | y: " + red.y);
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        final double ns = 1000000000.0 / FPS;
        double delta = 0;

        int frames = 0;
        int updates = 0;

        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (Game.state == State.Moving) {
                    update();
                } else if (Game.state == State.Fighting) {
                    updateBattleArena();
                }
                updates++;
                delta--;
            }
            if (Game.state == Game.State.Moving) {
                render();
            } else if (Game.state == Game.State.Fighting) {
                renderBattleArena();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                frame.setTitle("Game  |  " + updates + "ups , " + frames + "fps");
                //System.out.println("PacMan  |  " + updates + "ups , " + frames + "fps");
                timer += 1000;
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    public void update() {
        key.update();
        red.update(key);
        red.serializeWrite();
    }

    public void render() {
        screen.renderArena(red.x - (width / 2), red.y - (height / 2));
        red.render(screen);

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public void updateBattleArena() {
        key.update();
        cursor.update(key);
    }

    public void renderBattleArena() {
        screen.renderBattleArena();
        red.renderPokemon(screen);
        screen.renderBattleFrame();
        cursor.render(screen);

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public static void main(String args[]) {
        Game game = new Game();
        game.frame.setResizable(false);
        //game.frame.setUndecorated(true);      //Uncomment to remove border of frame
        game.frame.setTitle("Game");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }
}
