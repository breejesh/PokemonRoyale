package GameLoop.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Screen {

    private int width, height;
    public int[] pixels;
    public int[] pixelsArena;
    BufferedImage imgMap;
    private int mapWidth, mapHeight;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        File file = new File("graphics/map1.png");
        try {
            imgMap = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(Screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        mapWidth = imgMap.getWidth();
        mapHeight = imgMap.getHeight();
        //System.out.println("Map Width: " + mapWidth + "Map Height: " + mapHeight);
        pixelsArena = new int[mapWidth * mapHeight];

        for (int y = 0; y < imgMap.getHeight(); y++) {
            for (int x = 0; x < imgMap.getWidth(); x++) {
                pixelsArena[x + y * imgMap.getWidth()] = imgMap.getRGB(x, y);
            }
        }
    }

    public void renderArena(int x, int y) {
        for (int yy = 0; yy < height; yy++) {
            for (int xx = 0; xx < width; xx++) {
                pixels[xx + yy * width] = pixelsArena[(xx + x) + (yy + y) * mapWidth];
            }
        }
        /*for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((x/8 + y/8) % 2 == 0) {
                    pixels[x + y * width] = 0xff0000;
                }
                else
                {
                    pixels[x + y * width] = 0x000000;
                }
            }
        }*/
    }

    public void renderSprite(Sprite sprite, int x, int y) {
        int color;
        for (int yy = 0; yy < sprite.SIZE; yy++) {
            for (int xx = 0; xx < sprite.SIZE; xx++) {
                color = sprite.pixels[xx + yy * sprite.SIZE];
                if ((color & 0xff000000) != 0) {
                    pixels[xx + x + (yy + y) * width] = color;
                }
            }
        }
        //pixels[x + 7 + (y + 7) * width] = 0xff0000;
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = 0x00ffff;
            }
        }
    }
}
