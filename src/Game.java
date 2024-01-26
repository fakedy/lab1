package src;

import javax.swing.*;
import java.awt.*;

public abstract class Game extends JPanel implements Runnable {

    protected Thread gameThread;
    KeyHandler keyH = new KeyHandler();

    protected final int screenWidth = 1660;
    protected final int screenHeight = 720;

    int FPS = 60;

    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    Game(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            update();

            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime=remainingTime/1000000;

                if(remainingTime<0) {
                    remainingTime=0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            }

            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update(){

    }

    public void render(){

    }


}
