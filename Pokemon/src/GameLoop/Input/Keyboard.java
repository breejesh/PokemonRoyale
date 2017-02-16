package GameLoop.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Keyboard implements KeyListener {

    private boolean[] keys = new boolean[120];
    private boolean[] flags = new boolean[120];
    public boolean up, down, left, right,space,shift;

    public Keyboard() {
        for (int i = 0; i < 120; i++) {
            flags[i] = false;
        }
    }
    
    public void update() {
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        shift = keys[KeyEvent.VK_SHIFT];
        space = keys[KeyEvent.VK_SPACE];
    }

    public boolean deBounce(int key) {
        if (keys[key]) {
            if (!flags[key]) {
                flags[key] = true;
                return true;
            } else {
                return false;
            }
        }
        flags[key] = false;
        return false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

}
