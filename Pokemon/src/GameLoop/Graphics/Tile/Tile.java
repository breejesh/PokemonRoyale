package GameLoop.Graphics.Tile;

import GameLoop.Graphics.Screen;
import GameLoop.Graphics.Sprite;
import GameLoop.Graphics.SpriteSheet;

public class Tile {

    public int x, y;
    public Sprite sprite;

    public static int TILE_SIZE = 48;
    public static Tile grassTile = new GrassTile(new Sprite(Tile.TILE_SIZE, 4, 0, SpriteSheet.ground));
    public static Tile sandTile = new SandTile(new Sprite(Tile.TILE_SIZE, 1, 1, SpriteSheet.ground));

    public Tile(Sprite s) {
        sprite = s;
    }

    public void render(Screen screen, int x, int y) {
    }

    public boolean solid() {
        return false;
    }
}
