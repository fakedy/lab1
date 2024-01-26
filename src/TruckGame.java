package src;

import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.Random;

public class TruckGame extends Game {

    final int scale=1;

    // car graphics
    final private Utils.Vector2i carBodySize = new Utils.Vector2i(40*scale, 15*scale);
    final private Utils.Vector2i carWheelSize = new Utils.Vector2i(10*scale, 5*scale);

    //car default position = 100;

    int carX;
    int carY;

    int lastCarX;
    int lastCarY;

    Truck playerCar = new VolvoVAH300();

    public TruckGame () {
    }


    @Override
    public void update(){

        if(keyH.upPressed) {
            playerCar.gas(0.1);
            //out.println("GAS, speed: " + playerCar.currentSpeed);


        }
        else if(keyH.downPressed) {
            playerCar.brake(0.1);
            //out.println("BRAKE, speed: " + playerCar.currentSpeed);

        }

        else if(keyH.leftPressed) {
            playerCar.turnLeft();


        }
        else if(keyH.rightPressed) {
            playerCar.turnRight();


        } else if(keyH.twoPressed){
            playerCar.raiseRamp();
            System.out.println(playerCar.getRampAngle());
            keyH.twoPressed = false;
        } else if(keyH.onePressed){
            playerCar.lowerRamp();
            System.out.println(playerCar.getRampAngle());
            keyH.onePressed = false;
        }



        // collision checks
        if (playerCar.position.x > screenWidth-10) {
            playerCar.position.x = screenWidth-10;
        }
        if(playerCar.position.x < 0) {
            playerCar.position.x=0;
        }

        if (playerCar.position.y > screenHeight-10) {
            playerCar.position.y = screenHeight-10;
        }
        if(playerCar.position.y < 0) {
            playerCar.position.y = 0;
        }

        // update graphics according to car
        carX = (int) playerCar.position.x;
        carY = (int) playerCar.position.y;

        // update car position
        playerCar.move();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        drawHUD(g);
        g.dispose();
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;


        g2.setBackground(Color.DARK_GRAY);
        g2.clearRect(0,0,screenWidth,screenHeight);


        drawParkingLot(g2);



        drawCar(g2, playerCar);


    }

    public void drawHUD(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.black);
        g2.drawString("Speed of car",20, screenHeight-100);
        g2.setColor(Color.blue);
        g2.fillRect(30,screenHeight-50, (int)playerCar.getCurrentSpeed()*20, 40);
    }

    private void drawParkingLot(Graphics2D g2){

        // Pretty bad code.
        // we use a seed to not get flickering cars because cars would spawn at random locations every loop.
        // we create new objects instead of using same cars every time.
        // we should instead have a list of cars in parking lot that we can iterate over to render.
        // maybe create parking lot object
        Random rand = new Random(12312);

        for(int x = 0; x < screenWidth; x = x+50){
            for(int y = 0; y < screenHeight; y = y+250){
                g2.setColor(Color.WHITE);
                g2.fillRect(x+2, y, 5, 50);
                g2.fillRect(x+2, y, 25, 5);
                if(rand.nextInt(0,10) > 8){
                    Car parkedCar = new Volvo240();
                    parkedCar.position = new Utils.Vector2d(x- carBodySize.x/2.5,y + carBodySize.y*2.8); // 2.8 magic number wohoo
                    parkedCar.angle = 90*Math.PI/180;
                    if(rand.nextInt(0,10) > 4) {
                        parkedCar.setColor(Color.green);
                    } else if (rand.nextInt(0,10) > 1) {
                        parkedCar.setColor(Color.yellow);
                    } else {
                        parkedCar.setColor(Color.blue);
                    }
                    drawCar(g2, parkedCar);
                    parkedCar = null; // prevent what I assume is a memory leak
                }
            }
        }

    }


    private void drawCar(Graphics2D g2, Car car){

        int carX = (int) car.position.x;
        int carY = (int) car.position.y;

        AffineTransform transform = new AffineTransform();
        AffineTransform oldTransform = g2.getTransform();

        transform.rotate(car.angle, carX+(carBodySize.x), carY+(carBodySize.y/2));

        g2.setTransform(transform);

        drawCarShadow(g2, car);


        // body
        g2.setColor(car.getColor());
        g2.fillRect(carX, carY, carBodySize.x,carBodySize.y);

        // wheels
        g2.setColor(Color.GRAY);
        g2.fillRect(carX, carY + carBodySize.y , carWheelSize.x, carWheelSize.y); // Back right wheel
        g2.fillRect(carX, carY- carWheelSize.y, carWheelSize.x, carWheelSize.y); // back left wheel

        g2.fillRect(carX + carBodySize.x - carWheelSize.x, carY + carBodySize.y , carWheelSize.x, carWheelSize.y); // Front right wheel
        g2.fillRect(carX + carBodySize.x - carWheelSize.x, carY - carWheelSize.y, carWheelSize.x, carWheelSize.y); // Front left wheel

        g2.setTransform(oldTransform);


    }

    private void drawCarShadow(Graphics2D g2, Car car){

        AffineTransform transform = new AffineTransform();
        AffineTransform oldTransform = g2.getTransform();

        int carX = (int) car.position.x;
        int carY = (int) car.position.y;
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

        g2.setTransform(oldTransform);

    }
}

