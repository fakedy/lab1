package src;

import java.awt.*;

public abstract class Truck extends Car{

    protected float flatBedAngle = 0;

    Truck(String model, int nrDoors, Color color, double enginePower){
        super(model, nrDoors, color, enginePower);
    }

    public void raiseFlatbed(){
        if( flatBedAngle < 70 && getCurrentSpeed() == 0)
            flatBedAngle += 0.01f;
    }

    public void lowerFlatbed(){
        if( flatBedAngle > 0 && getCurrentSpeed() == 0){
            flatBedAngle -= 0.01f;
        } else {
            flatBedAngle = 0;
        }
    }

    public float getFlatbedAngle(){
        return flatBedAngle;
    }
}
