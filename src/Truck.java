package src;

import java.awt.*;

public abstract class Truck extends Car{

    protected float rampAngle = 0;
    protected boolean rampIsUp = false;

    Truck(String model, int nrDoors, Color color, double enginePower){
        super(model, nrDoors, color, enginePower);
        turnSpeed = 0.03; // All trucks have this turnspeed
    }

    public void raiseRamp(){
        if( rampAngle < 70 && getCurrentSpeed() == 0)
            rampAngle += 0.01f;
    }

    public void lowerRamp(){
        if( rampAngle > 0 && getCurrentSpeed() == 0){
            rampAngle -= 0.01f;
        } else {
            rampAngle = 0;
        }
    }

    public float getRampAngle(){
        return rampAngle;
    }
    @Override
    protected double speedFactor(){
        if(rampAngle != 0)
            return enginePower*0;

        return enginePower * 0.01;
    }

    @Override
    public void move() {    // Because we dont want a truck to handle like a car
        diff = new Utils.Vector2d(forceDirection.x - rotation.x, forceDirection.y - rotation.y);
        rotation = new Utils.Vector2d(rotation.x + (diff.x/4), rotation.y + (diff.y/4));    // divisor is our drift factor.
        position.setX((position.x + rotation.x*currentSpeed)); // this lets us keep our original reference
        position.setY((position.y+ rotation.y*currentSpeed));
    }
}
