import java.awt.*;
public class Car implements Movable{

    public int nrDoors; // Number of doors on the car

    public double enginePower; // Engine power of the car
    public double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    public String modelName; // The car model name

    Utils.Vector2d position = new Utils.Vector2d(0,0);
    double angle;
    Utils.Vector2d rotation = new Utils.Vector2d(1,0);

    Utils.Vector2d forceDirection = new Utils.Vector2d(1,0);


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
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount,enginePower);
    }

    private void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }

    // TODO fix this method according to lab pm
    public void gas(double amount){
        incrementSpeed(amount);
    }

    // TODO fix this method according to lab pm
    public void brake(double amount){
        decrementSpeed(amount);
    }

    private double speedFactor(){
        return enginePower * 0.01;
    }

    @Override
    public void move() {

        Utils.Vector2d diff = new Utils.Vector2d(forceDirection.x - rotation.x, forceDirection.y - rotation.y);
        rotation = new Utils.Vector2d(rotation.x + (diff.x/50), rotation.y + (diff.y/50));
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
