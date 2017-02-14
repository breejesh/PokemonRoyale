package GameLoop.Graphics.Tile;

import GameLoop.Graphics.Screen;
import GameLoop.Graphics.Sprite;

public class SandTile extends Tile {

    public SandTile(Sprite s) {
        super(s);
    }

    @Override
    public void render(Screen screen, int x, int y) {
        screen.renderSprite(sprite, x, y);
    }

    @Override
    public boolean solid() {
        return false;
    }
}
