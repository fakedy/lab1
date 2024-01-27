package src;

import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;
import java.util.Random;

public class TruckGame extends Game {

    private final int carScale=1;
    private final int truckScale=2;

    // car graphics
    final private Utils.Vector2i carBodySize = new Utils.Vector2i(40*carScale, 15*carScale);
    final private Utils.Vector2i carWheelSize = new Utils.Vector2i(10*carScale, 5*carScale);

    final private Utils.Vector2i truckBodySize = new Utils.Vector2i(40*truckScale, 15*truckScale);
    final private Utils.Vector2i truckWheelSize = new Utils.Vector2i(10*truckScale, 5*truckScale);

    //car default position = 100;

    int carX;
    int carY;

    private ArrayList<Car> parkedCars = new ArrayList<>();

    VolvoVAH300 playerTruck = new VolvoVAH300();

    Workshop[] workshops = new Workshop[]{
            new Workshop(new Utils.Vector2i(900,20),4, new String[]{"Volvo240"}),
            new Workshop(new Utils.Vector2i(60,200),4, new String[]{"Saab95"}),
            new Workshop(new Utils.Vector2i(1300,400),4, new String[]{})
    };


    public TruckGame () {

        fillParkingLot();
    }


    @Override
    public void update(){

        if(keyH.upPressed) {
            playerTruck.gas(0.1);
        }

        else if(keyH.downPressed) {
            playerTruck.brake(0.1);
        }

        else if(keyH.leftPressed) {
            playerTruck.turnLeft();
        }

        else if(keyH.rightPressed) {
            playerTruck.turnRight();
        }

        else if(keyH.twoPressed){
            playerTruck.raiseRamp();
            System.out.println(playerTruck.getRampAngle());
            keyH.twoPressed = false;
        }

        else if(keyH.onePressed){
            playerTruck.lowerRamp();
            System.out.println(playerTruck.getRampAngle());
            keyH.onePressed = false;
        }

        else if(keyH.spacePressed){
            if(!parkedCars.isEmpty()) {
                Car car = findClosestCar();
                if(playerTruck.loadCar(car)){
                    parkedCars.remove(car);
                } else {
                    System.out.println("failed to load car");
                }
            }
            keyH.spacePressed = false;
        }

        else if(keyH.controlPressed){
            playerTruck.unloadCar();
            System.out.println("unloading car");
            keyH.controlPressed = false;
        }


        collisionDetection();


        // update graphics according to car
        carX = (int) playerTruck.position.x;
        carY = (int) playerTruck.position.y;

        // update car position
        playerTruck.move();

    }

    private Car findClosestCar(){
        Car closestCar = null;
        double minDistance = 0;
        for(Car car : parkedCars){
            if(minDistance == 0 || car.position.dist(playerTruck.position) < minDistance){
                closestCar = car;
                minDistance = car.position.dist(playerTruck.position);
            }
        }
        return closestCar;
    }

    private void collisionDetection(){
        // collision checks
        if (playerTruck.position.x > screenWidth-10) {
            playerTruck.position.x = screenWidth-10;
        }
        if(playerTruck.position.x < 0) {
            playerTruck.position.x=0;
        }

        if (playerTruck.position.y > screenHeight-10) {
            playerTruck.position.y = screenHeight-10;
        }
        if(playerTruck.position.y < 0) {
            playerTruck.position.y = 0;
        }
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

        drawTruck(g2, playerTruck);

        for(Car parkedCar : parkedCars){
            drawCar(g2, parkedCar);
        }

        drawWorkshops(g2);

    }

    public void drawWorkshops(Graphics2D g2){

        for(Workshop workshop : workshops){
            Utils.Vector2i pos = workshop.getPosition();
            g2.fillRect(pos.x,pos.y, 100, 50);
        }

    }

    public void drawHUD(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);

        g2.drawString("Speed of car",20, screenHeight-100);
        g2.drawString("ramp angle: " + playerTruck.rampAngle,20, 100);
        g2.setColor(Color.blue);
        g2.fillRect(30,screenHeight-50, (int) playerTruck.getCurrentSpeed()*20, 40);
    }

    private void fillParkingLot() {

        Random rand = new Random(12312);
        for (int x = 0; x < screenWidth; x = x + 50) {
            for (int y = 0; y < screenHeight; y = y + 250) {
                if(rand.nextInt(0,10) > 8) {
                    Car parkedCar = new Volvo240();
                    parkedCar.setColor(Color.yellow);
                    parkedCar.position = new Utils.Vector2d(x - carBodySize.x / 2.5, y + carBodySize.y * 2.8); // 2.8 magic number wohoo
                    parkedCar.angle = 90 * Math.PI / 180;
                    parkedCars.add(parkedCar);
                }
            }
        }
    }
    // Please ignore all this mess :)
    private void drawParkingLot(Graphics2D g2){

        for(int x = 0; x < screenWidth; x = x+50){
            for(int y = 0; y < screenHeight; y = y+250){
                g2.setColor(Color.WHITE);
                g2.fillRect(x+2, y, 5, 50);
                g2.fillRect(x+2, y, 25, 5);
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

    private void drawTruck(Graphics2D g2, Car car){

        int carX = (int) car.position.x;
        int carY = (int) car.position.y;

        AffineTransform transform = new AffineTransform();
        AffineTransform oldTransform = g2.getTransform();

        transform.rotate(car.angle, carX+(truckBodySize.x), carY+(truckBodySize.y/2));

        g2.setTransform(transform);

        drawTruckShadow(g2, car);


        // body
        g2.setColor(car.getColor());
        g2.fillRect(carX, carY, truckBodySize.x,truckBodySize.y);

        // wheels
        g2.setColor(Color.GRAY);
        g2.fillRect(carX, carY + truckBodySize.y , truckWheelSize.x, truckWheelSize.y); // Back right wheel
        g2.fillRect(carX, carY- truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // back left wheel

        g2.fillRect(carX + truckBodySize.x - truckWheelSize.x, carY + truckBodySize.y , truckWheelSize.x, truckWheelSize.y); // Front right wheel
        g2.fillRect(carX + truckBodySize.x - truckWheelSize.x, carY - truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // Front left wheel

        drawTruckBed(g2,car);
        g2.setTransform(oldTransform);

    }

    private void drawTruckBed(Graphics2D g2, Car car){

        // headache system

        int carX = (int) car.position.x;
        int carY = (int) car.position.y;

        Utils.Vector2i  truckBedSize = new Utils.Vector2i((carBodySize.x+10) * playerTruck.getLoadCapacity(), carBodySize.y+10);
        Utils.Vector2i  truckBedAnchor = new Utils.Vector2i((int) (carX + truckBodySize.x + Math.cos(car.angle)*-truckBodySize.x), (int) (carY + Math.sin(car.angle)*-truckBodySize.x));

        AffineTransform transform = new AffineTransform();
        AffineTransform oldTransform = g2.getTransform();


        double rotateFactor = -car.angle;

        transform.rotate(rotateFactor, truckBedAnchor.x, truckBedAnchor.y);


        drawTruckBedShadow(g2, car);
        g2.setTransform(transform);



        // body
        g2.setColor(Color.lightGray);
        g2.fillRect(truckBedAnchor.x - truckBedSize.x-10, truckBedAnchor.y, truckBedSize.x,truckBedSize.y);

        // wheels
        g2.setColor(Color.GRAY);
        g2.fillRect(truckBedAnchor.x-truckBedSize.x-10, truckBedAnchor.y + truckBedSize.y , truckWheelSize.x, truckWheelSize.y); // Back right wheel
        g2.fillRect(truckBedAnchor.x-truckBedSize.x-10, truckBedAnchor.y- truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // back left wheel

        g2.fillRect(truckBedAnchor.x - truckWheelSize.x-10, truckBedAnchor.y + truckBedSize.y , truckWheelSize.x, truckWheelSize.y); // Front right wheel
        g2.fillRect(truckBedAnchor.x - truckWheelSize.x-10, truckBedAnchor.y - truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // Front left wheel


        drawCarsOnbed(g2, car, truckBedAnchor);
        g2.setTransform(oldTransform);

    }

    private void drawCarsOnbed(Graphics2D g2, Car vehicle, Utils.Vector2i anchor){

        // number chaos

        int carIndex = 0;
        for(Car car : playerTruck.getCargo()){


            //drawCarOnBedShadows(g2, vehicle, anchor, carIndex);


            // body
            g2.setColor(car.getColor());
            g2.fillRect((anchor.x - 55 - (carBodySize.x+10)*carIndex), anchor.y+5, carBodySize.x,carBodySize.y); // magic -55 number, tired.

            // wheels
            g2.setColor(Color.GRAY);

            g2.fillRect((anchor.x - 55 - (carBodySize.x+10)*carIndex), anchor.y+5 + carBodySize.y , carWheelSize.x, carWheelSize.y); // Back right wheel
            g2.fillRect((anchor.x - 55 - (carBodySize.x+10)*carIndex), anchor.y+5 - carWheelSize.y, carWheelSize.x, carWheelSize.y); // back left wheel

            g2.fillRect(((anchor.x - 55 - (carBodySize.x+10)*carIndex) + carBodySize.x - carWheelSize.x), anchor.y+5 + carBodySize.y , carWheelSize.x, carWheelSize.y); // Front right wheel
            g2.fillRect(((anchor.x - 55 - (carBodySize.x+10)*carIndex) + carBodySize.x - carWheelSize.x), anchor.y+5 - carWheelSize.y, carWheelSize.x, carWheelSize.y); // Front left wheel


            carIndex++;

        }



    }

    private void drawCarOnBedShadows(Graphics2D g2, Car car, Utils.Vector2i anchor, int carIndex){

        AffineTransform transform = new AffineTransform();
        AffineTransform oldTransform = g2.getTransform();

        int carX = (int) car.position.x;
        int carY = (int) car.position.y;
        int carShadowX = (anchor.x - 55 - (carBodySize.x+10)*carIndex)-carBodySize.x/4;
        int carShadowY = carY;

        transform.rotate(-car.angle, anchor.x-(carBodySize.x), anchor.y-(carBodySize.y/2));

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

    private void drawTruckBedShadow(Graphics2D g2, Car car){

        AffineTransform transform = new AffineTransform();
        AffineTransform oldTransform = g2.getTransform();

        Utils.Vector2i  truckBedSize = new Utils.Vector2i((carBodySize.x+10) * playerTruck.getLoadCapacity(), carBodySize.y+10);
        Utils.Vector2i  truckBedAnchor = new Utils.Vector2i((int) (carX + truckBodySize.x + Math.cos(car.angle)*-truckBodySize.x), (int) (carY + Math.sin(car.angle)*-truckBodySize.x));

        int carX = (int) car.position.x;
        int carY = (int) car.position.y;
        int carShadowX = truckBedAnchor.x-truckBedSize.x-10-carBodySize.x/4;
        int carShadowY = carY;

        transform.rotate(-car.angle, truckBedAnchor.x-carBodySize.x/4, truckBedAnchor.y-carBodySize.y/2);

        g2.setTransform(transform);
        // body
        g2.setColor(Color.BLACK);
        g2.fillRect(carShadowX, truckBedAnchor.y, truckBedSize.x,truckBedSize.y);

        // wheels
        g2.setColor(Color.BLACK);
        g2.fillRect(carShadowX, truckBedAnchor.y + truckBedSize.y , truckWheelSize.x, truckWheelSize.y); // Back right wheel
        g2.fillRect(carShadowX, truckBedAnchor.y- truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // back left wheel

        g2.fillRect(carShadowX, truckBedAnchor.y + truckBedSize.y , truckWheelSize.x, truckWheelSize.y); // Front right wheel
        g2.fillRect(carShadowX, truckBedAnchor.y - truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // Front left wheel

        g2.setTransform(oldTransform);

    }

    private void drawTruckShadow(Graphics2D g2, Car car){

        AffineTransform transform = new AffineTransform();
        AffineTransform oldTransform = g2.getTransform();

        int carX = (int) car.position.x;
        int carY = (int) car.position.y;
        int carShadowX = carX-truckBodySize.x/4;
        int carShadowY = carY;

        transform.rotate(car.angle, carShadowX+(truckBodySize.x), carShadowY+(truckBodySize.y/2));

        g2.setTransform(transform);
        // body
        g2.setColor(Color.BLACK);
        g2.fillRect(carShadowX, carShadowY, truckBodySize.x,truckBodySize.y);

        // wheels
        g2.setColor(Color.BLACK);
        g2.fillRect(carShadowX, carShadowY + truckBodySize.y , truckWheelSize.x, truckWheelSize.y); // Back right wheel
        g2.fillRect(carShadowX, carShadowY- truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // back left wheel

        g2.fillRect(carShadowX + truckBodySize.x - truckWheelSize.x, carShadowY + truckBodySize.y , truckWheelSize.x, truckWheelSize.y); // Front right wheel
        g2.fillRect(carShadowX + truckBodySize.x - truckWheelSize.x, carShadowY - truckWheelSize.y, truckWheelSize.x, truckWheelSize.y); // Front left wheel

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

