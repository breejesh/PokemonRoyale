package GameLoop.Graphics;

import GameLoop.Game;
import GameLoop.Graphics.Tile.Tile;
import java.util.Random;

public class Level {

    public int tileWidth = 32;
    public int tileHeight = 18;
    public int[] mapTiles;
    public SpriteSheet map;

    public Level() {
        mapTiles = new int[tileWidth * tileHeight];
        Random random = new Random();

        for (int i = 0; i < tileWidth * tileHeight; ++i) {
            mapTiles[i] = random.nextInt(2);
        }
    }

    public void render(Screen screen, int x, int y) {
        int startX = (x / Tile.TILE_SIZE);
        int startY = (y / Tile.TILE_SIZE);
        int endX = startX + (Game.width / Tile.TILE_SIZE);
        int endY = startY + (Game.height / Tile.TILE_SIZE);
        for (int currY = (y / Tile.TILE_SIZE); currY < endY; ++currY) {
            for (int currX = (x / Tile.TILE_SIZE); currX < endX; ++currX) {
                if (mapTiles[currX + currY * tileWidth] % 2 == 0) {
                    Tile.grassTile.render(screen, (currX - startX) * Tile.TILE_SIZE, (currY - startY) * Tile.TILE_SIZE);
                } else {
                    Tile.sandTile.render(screen, (currX - startX) * Tile.TILE_SIZE, (currY - startY) * Tile.TILE_SIZE);
                }
            }
        }
    }
}
