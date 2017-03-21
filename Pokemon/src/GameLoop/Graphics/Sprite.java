package GameLoop.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Sprite implements Serializable {
    
    private static final long serialVersionUID = 3L;

    public final int SIZE;
    private int x, y;
    private SpriteSheet sheet;
    public int[] pixels;

    public Sprite(String path, int size){
        SIZE = size;
        this.pixels = new int[SIZE * SIZE];
        load(path, size);
    }
    
    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        this.SIZE = size;
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.pixels = new int[SIZE * SIZE];
        this.sheet = sheet;
        load();
    }
    
    public void load(String path, int size) {
        try {
            File file = new File(path);
            //System.out.println(path);
            BufferedImage image = ImageIO.read(file);
            int w = size;
            int h = size;
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    pixels[x + y * w] = image.getRGB(x, y);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                this.pixels[x + y * SIZE] = this.sheet.pixels[(this.x + x) + (this.y + y) * sheet.SIZE_X];
            }
        }
    }
}
