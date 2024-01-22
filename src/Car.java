package src;

import java.awt.*;
public abstract class Car implements Movable{

    protected final int nrDoors; // Number of doors on the car

    protected final double enginePower; // Engine power of the car
    protected double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    public final String modelName; // The car model name

    // following methods were added to implement the game.
    // Would prefer to have get and set functions.
    public Utils.Vector2d position = new Utils.Vector2d(0,0);
    public double angle;
    public Utils.Vector2d rotation = new Utils.Vector2d(1,0);
    public Utils.Vector2d diff;
    public Utils.Vector2d forceDirection = new Utils.Vector2d(1,0);



    public Car(String model, int nrDoors, Color color, double enginePower){
        this.modelName = model;
        this.nrDoors = nrDoors;
        this.setColor(color);
        this.enginePower = enginePower;
        this.stopEngine();
    }


    public int getNrDoors(){
        return nrDoors;
    }
    public double getEnginePower(){
        return enginePower;
    }

    public double getCurrentSpeed(){
        return currentSpeed;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color clr){
        color = clr;
    }

    public void startEngine(){
        currentSpeed = 0.1;
    }

    public void stopEngine(){
        currentSpeed = 0;
    }

    private void incrementSpeed(double amount){
        if(currentSpeed < 0){
            currentSpeed = 0;
        } else if(currentSpeed > getEnginePower()){
            currentSpeed = getEnginePower();
        } else {
            currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount,enginePower);
        }
    }

    private void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }

    // TODO fix this method according to lab pm
    public void gas(double amount){
        if(amount < 0 || amount > 1){
            throw new IllegalArgumentException("Gas amount must be between [0,1]");
        }

        double speedBefore = this.getCurrentSpeed();
        incrementSpeed(amount);
        if(speedBefore > this.getCurrentSpeed()){
            throw new IllegalStateException("Speed should not decrease after gas");
        }

    }

    // TODO fix this method according to lab pm
    public void brake(double amount){
        if(amount < 0 || amount > 1){
            throw new IllegalArgumentException("Brake amount must be between [0,1]");
        }

        decrementSpeed(amount);
        double speedBefore = this.getCurrentSpeed();
        if(speedBefore < this.getCurrentSpeed()){
            throw new IllegalStateException("Speed should not increase after gas");
        }
    }

    protected double speedFactor(){
        return enginePower * 0.01;
    }

    @Override
    public void move() {
        diff = new Utils.Vector2d(forceDirection.x - rotation.x, forceDirection.y - rotation.y);
        rotation = new Utils.Vector2d(rotation.x + (diff.x/80), rotation.y + (diff.y/80));
        position = new Utils.Vector2d((float) (position.x + rotation.x*currentSpeed), (float) (position.y+ rotation.y*currentSpeed));
    }

    @Override
    public void turnLeft() {
        angle -= 0.2;
        forceDirection = new Utils.Vector2d(Math.cos(angle), Math.sin(angle));
    }

    @Override
    public void turnRight() {
        angle += 0.2;
        forceDirection = new Utils.Vector2d(Math.cos(angle), Math.sin(angle));
    }

}
