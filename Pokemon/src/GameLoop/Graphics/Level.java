package GameLoop.Graphics;

import GameLoop.Units.Red;
import java.io.FileReader;
import java.io.IOException;

/*
1 - Land
2 - Grass
8 - Seashore
 */
public class Level {

    private static final int LAND = 1;
    private static final int GRASS = 2;
    private static final int SHORE = 8;

    public static int currTile;
    public static int mapHeight = 50;
    public static int mapWidth = 136;
    public static int[] dataMap;

    public Level() {
        currTile = LAND;
        dataMap = new int[mapWidth * mapHeight];
        readMap();
    }

    public static void updateCurrTile(int x, int y) {
        currTile = dataMap[x + y * mapWidth];
    }

    public static boolean spawnPokemon() {
        if (currTile == GRASS || currTile == SHORE) {
            Random rand = new Random();
            int max = 400;
            int min = 1;
            int randomNum = rand.nextInt((max - min) + 1) + min;
            if (randomNum == 11) {
                return true;
            }
        }
        return false;
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
        if (dataMap[x + y * mapWidth] == 1 || dataMap[x + y * mapWidth] == 2) {
            return false;
        }
        return true;
    }
}
