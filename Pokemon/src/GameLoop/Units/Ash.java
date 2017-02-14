package GameLoop.Units;

import GameLoop.Game;
import GameLoop.Graphics.Screen;
import GameLoop.Graphics.Sprite;
import GameLoop.Graphics.SpriteSheet;
import GameLoop.Input.Keyboard;

/*
1: down
2: left
3: right
4: up
 */
public class Ash {

    public static SpriteSheet ashSheet = new SpriteSheet("graphics/red.png", 256);
    public int x, y;
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

    public Ash() {
        x = 1024;
        y = 576;
        head = 1;
        moving = false;
        tick = 0;
        ashStandDown = new Sprite(64, 0, 0, ashSheet);
        ashMovingDownL = new Sprite(64, 1, 0, ashSheet);
        ashMovingDownR = new Sprite(64, 3, 0, ashSheet);
        ashStandLeft = new Sprite(64, 0, 1, ashSheet);
        ashMovingLeftL = new Sprite(64, 1, 1, ashSheet);
        ashMovingLeftR = new Sprite(64, 3, 1, ashSheet);
        ashStandRight = new Sprite(64, 0, 2, ashSheet);
        ashMovingRightL = new Sprite(64, 1, 2, ashSheet);
        ashMovingRightR = new Sprite(64, 3, 2, ashSheet);
        ashStandUp = new Sprite(64, 0, 3, ashSheet);
        ashMovingUpL = new Sprite(64, 1, 3, ashSheet);
        ashMovingUpR = new Sprite(64, 3, 3, ashSheet);
    }

    public void update(Keyboard key) {
        moving = false;
        if (key.up) {
            head = 4;
            y-=3;
            moving = true;
        } else if (key.down) {
            head = 1;
            y+=3;
            moving = true;
        }
        else if (key.left) {
            head = 2;
            x-=3;
            moving = true;
        } else if (key.right) {
            head = 3;
            x+=3;
            moving = true;
        }
        if (moving) {
            tick = (tick + 1) % 40;
        } else {
            tick = 0;
        }
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
