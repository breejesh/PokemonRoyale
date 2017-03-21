package Utilities;

import GameLoop.Graphics.Screen;
import GameLoop.Graphics.Sprite;
import GameLoop.Graphics.SpriteSheet;
import GameLoop.Input.Keyboard;

public class Cursor {

    public Sprite cursor = new Sprite(40, 0, 0, new SpriteSheet("graphics/Battle/cursor.png", 40));
    public int x;
    public int y;
    public int option;
    public int type;

    public Cursor(int type) {
        this.type = type;
        if (type == 4) {
            x = 435;
            y = 283;
        }
        option = 1;
    }

    public void update(Keyboard key) {
        if (type == 4) {
            if (key.up) {
                if (option == 3 || option == 4) {
                    option -= 2;
                }
            } else if (key.down) {
                if (option == 1 || option == 2) {
                    option += 2;
                }

            } else if (key.left) {
                if (option == 2 || option == 4) {
                    option -= 1;
                }

            } else if (key.right) {
                if (option == 1 || option == 3) {
                    option += 1;
                }
            }
        }
        convOptToXY();
    }
    
    public void convOptToXY() {
        if (type == 4) {
            if (option == 1) {
                x = 435;
                y = 283;
            } else if (option == 2) {
                x = 545;
                y = 283;
            } else if (option == 3) {
                x = 435;
                y = 314;
            } else if (option == 4) {
                x = 545;
                y = 314;
            }
        }
    }

    public void render(Screen screen) {
        screen.renderSprite(cursor, x, y);
    }

}
