package GameLoop.Units;

import GameLoop.Game;
import GameLoop.Graphics.Level;
import GameLoop.Graphics.Screen;
import GameLoop.Graphics.Sprite;
import GameLoop.Graphics.SpriteSheet;
import GameLoop.Input.Keyboard;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
 1: down
 2: left
 3: right
 4: up
 */
public class Red implements Serializable {

    private static final long serialVersionUID = 1L;

    public final int SPRITE_SIZE = 32;
    public static SpriteSheet ashSheet = new SpriteSheet("graphics/red32.png", 128);
    public int x, y;
    public int moveSpeed;
    public boolean moving;
    public int head;
    public int tick;
    public Sprite ashStandDown;
    public Sprite ashMovingDownL;
    public Sprite ashMovingDownR;
    public Sprite ashStandLeft;
    public Sprite ashMovingLeftL;
    public Sprite ashMovingLeftR;
    public Sprite ashStandRight;
    public Sprite ashMovingRightL;
    public Sprite ashMovingRightR;
    public Sprite ashStandUp;
    public Sprite ashMovingUpL;
    public Sprite ashMovingUpR;

    public Red() {
        x = 1024;
        y = 576;
        head = 1;
        moveSpeed = 2;
        moving = false;
        tick = 0;
        ashStandDown = new Sprite(SPRITE_SIZE, 0, 0, ashSheet);
        ashMovingDownL = new Sprite(SPRITE_SIZE, 1, 0, ashSheet);
        ashMovingDownR = new Sprite(SPRITE_SIZE, 3, 0, ashSheet);
        ashStandLeft = new Sprite(SPRITE_SIZE, 0, 1, ashSheet);
        ashMovingLeftL = new Sprite(SPRITE_SIZE, 1, 1, ashSheet);
        ashMovingLeftR = new Sprite(SPRITE_SIZE, 3, 1, ashSheet);
        ashStandRight = new Sprite(SPRITE_SIZE, 0, 2, ashSheet);
        ashMovingRightL = new Sprite(SPRITE_SIZE, 1, 2, ashSheet);
        ashMovingRightR = new Sprite(SPRITE_SIZE, 3, 2, ashSheet);
        ashStandUp = new Sprite(SPRITE_SIZE, 0, 3, ashSheet);
        ashMovingUpL = new Sprite(SPRITE_SIZE, 1, 3, ashSheet);
        ashMovingUpR = new Sprite(SPRITE_SIZE, 3, 3, ashSheet);
    }

    public void update(Keyboard key) {
        moving = false;
        if (key.shift) {
            moveSpeed = 6;
        } else {
            moveSpeed = 2;
        }
        if (key.up) {
            if (!Level.collide(x / SPRITE_SIZE, (y - moveSpeed) / SPRITE_SIZE)) {
                y -= moveSpeed;
                moving = true;
            }
            head = 4;
        } else if (key.down) {
            if (!Level.collide(x / SPRITE_SIZE, (y + moveSpeed) / SPRITE_SIZE)) {
                y += moveSpeed;
                moving = true;
            }
            head = 1;
        } else if (key.left) {
            if (!Level.collide((x - moveSpeed) / SPRITE_SIZE, y / SPRITE_SIZE)) {
                x -= moveSpeed;
                moving = true;
            }
            head = 2;
        } else if (key.right) {
            if (!Level.collide((x + moveSpeed) / SPRITE_SIZE, y / SPRITE_SIZE)) {
                x += moveSpeed;
                moving = true;
            }
            head = 3;
        }
        if (moving) {
            tick = (tick + 1) % 40;
            Level.updateCurrTile(x / SPRITE_SIZE, y / SPRITE_SIZE);
            if (Level.spawnPokemon()){
                System.out.println("A Wild Pokemon Appears");
            }
        } else {
            tick = 0;
        }
        //System.out.println("x: " + x + " | y: " + y);
    }

    public void renderSprite(Screen screen, Sprite sprite) {
        screen.renderSprite(sprite, (Game.width - sprite.SIZE) / 2, (Game.height - sprite.SIZE) / 2);
    }

    public void spriteSelect(Screen screen, Sprite spriteS, Sprite spriteL, Sprite spriteR) {
        if (tick < 10) {
            renderSprite(screen, spriteL);
        } else if (tick < 20) {
            renderSprite(screen, spriteS);
        } else if (tick < 30) {
            renderSprite(screen, spriteR);
        } else if (tick < 40) {
            renderSprite(screen, spriteS);
        }
    }

    public static Red deserializeRead() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/red.ser"));
            return (Red) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(new FileInputStream("data/redBack.ser"));
                return (Red) ois.readObject();
            } catch (IOException | ClassNotFoundException ex1) {
            }
        }
        System.out.println("New Game");
        return new Red();
    }

    public void serializeWrite() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/red.ser"));
            oos.writeObject(this);
            oos = new ObjectOutputStream(new FileOutputStream("data/redBack.ser"));
            oos.writeObject(this);
            //System.out.println("Done Serializing");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void render(Screen screen) {
        switch (head) {
            case 1:
                if (moving) {
                    spriteSelect(screen, ashStandDown, ashMovingDownL, ashMovingDownR);
                } else {
                    renderSprite(screen, ashStandDown);
                }
                break;
            case 2:
                if (moving) {
                    spriteSelect(screen, ashStandLeft, ashMovingLeftL, ashMovingLeftR);
                } else {
                    renderSprite(screen, ashStandLeft);
                }
                break;
            case 3:
                if (moving) {
                    spriteSelect(screen, ashStandRight, ashMovingRightL, ashMovingRightR);
                } else {
                    renderSprite(screen, ashStandRight);
                }
                break;
            case 4:
                if (moving) {
                    spriteSelect(screen, ashStandUp, ashMovingUpL, ashMovingUpR);
                } else {
                    renderSprite(screen, ashStandUp);
                }
                break;
            default:
                break;
        }
    }
}
