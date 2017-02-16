package GameLoop.Graphics;

import GameLoop.Game;
import GameLoop.Graphics.Tile.Tile;
import GameLoop.Units.Red;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Level {

    private Red red;
    public static int mapHeight = 50;
    public static int mapWidth = 136;
    public static int[] dataMap;

    public Level(Red red) {
        this.red = red;
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
                if (c == ' ' || c == '\r' || c == '\n') {
                    continue;
                }
                dataMap[i] = Character.getNumericValue(c);
                ++i;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean collide(int x, int y) {
        if(dataMap[x + y * mapWidth] == 1 || dataMap[x + y * mapWidth] == 2){
            return false;
        }
        return true;
    }
}
