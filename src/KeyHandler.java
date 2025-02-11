package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed,leftPressed,rightPressed, twoPressed, onePressed, controlPressed, spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        if(code == KeyEvent.VK_UP) {
            upPressed=true;

        }
        if(code == KeyEvent.VK_DOWN) {
            downPressed=true;

        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressed=true;

        }

        if(code == KeyEvent.VK_1)
            onePressed = true;

        if(code == KeyEvent.VK_2)
            twoPressed = true;

        if(code == KeyEvent.VK_SPACE)
            spacePressed = true;

        if(code == KeyEvent.VK_CONTROL)
            controlPressed = true;



    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();


        if(code == KeyEvent.VK_UP) {
            upPressed=false;

        }
        if(code == KeyEvent.VK_DOWN) {
            downPressed=false;

        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressed=false;

        }

    }
}