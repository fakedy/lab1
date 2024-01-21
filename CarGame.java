import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static java.lang.System.out;

public class CarGame extends JPanel implements Runnable{
    final int originalTileSize=10;
    final int scale=1;

    // car graphics
    final private Utils.Vector2i carBodySize = new Utils.Vector2i(40*scale, 15*scale);
    final private Utils.Vector2i carWheelSize = new Utils.Vector2i(10*scale, 5*scale);

    final int screenWidth = 1660;
    final int screenHeight = 720;

    Thread gameThread;

    KeyHandler keyH = new KeyHandler();

    int FPS = 60;

    //car default position = 100;

    int carX;
    int carY;

    int lastCarX;
    int lastCarY;

    ArrayList<Integer> trails = new ArrayList<>();


    Car car = new Saab95();

    public CarGame () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            //System.out.println("Looooping");


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

        if(keyH.upPressed) {
            car.gas(0.1);
            out.println("GAS, speed: " + car.currentSpeed);


        }
        else if(keyH.downPressed) {
            car.brake(0.1);
            out.println("BRAKE, speed: " + car.currentSpeed);

        }

        else if(keyH.leftPressed) {
            car.turnLeft();

            out.println("left");
            out.println(car.rotation);
            keyH.leftPressed=false;
        }
        else if(keyH.rightPressed) {
            car.turnRight();

            out.println("right");
            out.println(car.rotation);
            keyH.rightPressed=false;
        }



        // collision checks
        if (car.position.x > screenWidth-10) {
            car.position.x = screenWidth-10;
        }
        if(car.position.x < 0) {
            car.position.x=0;
        }

        if (car.position.y > screenHeight-10) {
            car.position.y = screenHeight-10;
        }
        if(car.position.y < 0) {
            car.position.y = 0;
        }

        // update graphics according to car
        carX = (int) car.position.x;
        carY = (int) car.position.y;

        // update car position
        car.move();

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        draw(g);
        draw2(g);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;


        g2.setBackground(Color.DARK_GRAY);
        g2.clearRect(0,0,screenWidth,screenHeight);


        drawParkingLot(g2);

        drawWheelTrails(g2);





        drawCarShadow(g2);
        drawCar(g2);





        g2.dispose();

    }

    public void draw2(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.black);
        g2.drawString(String.valueOf(car.getCurrentSpeed()),20, 20);
        g2.dispose();
    }

    private void drawParkingLot(Graphics2D g2){

        g2.setColor(Color.WHITE);

        for(int x = 0; x < screenWidth; x = x+50){
            for(int y = 0; y < screenHeight; y = y+250){
                g2.fillRect(x+2, y, 5, 50);
                g2.fillRect(x+2, y, 25, 5);
            }
        }

    }

    private void drawWheelTrails(Graphics2D g2){

        // wanted to add trails to tires but not following rotation of car.
        g2.setColor(Color.BLACK);
        if(trails.size() >= 4){
            for(int i = 0; i < trails.size(); i = i + 4){
                g2.drawLine(trails.get(0+i), trails.get(1+i),trails.get(2+i) , trails.get(3+i));
                g2.drawLine(trails.get(0+i)+1, (trails.get(1+i)+carWheelSize.y+carBodySize.y),trails.get(2+i) , trails.get(3+i)+carWheelSize.y+carBodySize.y);
            }
        }

        if(Math.abs(car.diff.x) + Math.abs(car.diff.y) > 0.9){  // force required for tires to skid
            trails.add(lastCarX);
            trails.add(lastCarY);
            trails.add(carX);
            trails.add(carY);

        }
        lastCarX = carX;
        lastCarY = carY;
    }

    private void drawCar(Graphics2D g2){

        AffineTransform transform = new AffineTransform();

        transform.rotate(car.angle, carX+(carBodySize.x), carY+(carBodySize.y/2));

        g2.setTransform(transform);
        // body
        g2.setColor(car.getColor());
        g2.fillRect(carX, carY, carBodySize.x,carBodySize.y);

        // wheels
        g2.setColor(Color.GRAY);
        g2.fillRect(carX, carY + carBodySize.y , carWheelSize.x, carWheelSize.y); // Back right wheel
        g2.fillRect(carX, carY- carWheelSize.y, carWheelSize.x, carWheelSize.y); // back left wheel

        g2.fillRect(carX + carBodySize.x - carWheelSize.x, carY + carBodySize.y , carWheelSize.x, carWheelSize.y); // Front right wheel
        g2.fillRect(carX + carBodySize.x - carWheelSize.x, carY - carWheelSize.y, carWheelSize.x, carWheelSize.y); // Front left wheel

    }

    private void drawCarShadow(Graphics2D g2){

        AffineTransform transform = new AffineTransform();

        int carShadowX = carX-carBodySize.x/4;
        int carShadowY = carY;

        transform.rotate(car.angle, carShadowX+(carBodySize.x), carShadowY+(carBodySize.y/2));

        g2.setTransform(transform);
        // body
        g2.setColor(Color.BLACK);
        g2.fillRect(carShadowX, carShadowY, carBodySize.x,carBodySize.y);

        // wheels
        g2.setColor(Color.BLACK);
        g2.fillRect(carShadowX, carShadowY + carBodySize.y , carWheelSize.x, carWheelSize.y); // Back right wheel
        g2.fillRect(carShadowX, carShadowY- carWheelSize.y, carWheelSize.x, carWheelSize.y); // back left wheel

        g2.fillRect(carShadowX + carBodySize.x - carWheelSize.x, carShadowY + carBodySize.y , carWheelSize.x, carWheelSize.y); // Front right wheel
        g2.fillRect(carShadowX + carBodySize.x - carWheelSize.x, carShadowY - carWheelSize.y, carWheelSize.x, carWheelSize.y); // Front left wheel

    }
}
