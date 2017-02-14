package GameLoop.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener,MouseMotionListener {

    boolean condition, clickFlag;
    public boolean click;
    public int x, y;

    public Mouse() {
        condition = click = clickFlag = false;
        x = y = 0;
    }

    public void update() {
        click = deBounce();
    }

    public boolean deBounce() {
        if (condition) {
            if (!clickFlag) {
                clickFlag = true;
                return true;
            } else {
                return false;
            }
        }
        clickFlag = false;
        return false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        //System.out.println("X: " + x + " | Y: " + y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        condition = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        condition = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
