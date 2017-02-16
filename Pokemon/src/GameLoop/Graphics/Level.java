package GameLoop.Graphics;

import GameLoop.Game;
import GameLoop.Graphics.Tile.Tile;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Level {

    public int mapHeight = 42;
    public int mapWidth = 134;
    public int[] dataMap;

    public Level() {
        dataMap = new int[mapWidth * mapHeight];
        readMap();
    }

    public void readMap() {
        FileReader inputStream;
        try {
            inputStream = new FileReader("data/map.txt");

            int c;
            int i = 0;

            while ((c = inputStream.read()) != -1) {
                dataMap[i] = Character.getNumericValue(c);
                System.out.print(Character.getNumericValue(c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < mapHeight * mapWidth; ++i) {
            if (i % mapWidth == 0) {
                //System.out.println("");
            }
            //System.out.print(dataMap[i]);
        }
    }

    public void render(Screen screen, int x, int y) {
        
    }
}
