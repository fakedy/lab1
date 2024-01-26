package src;

import java.awt.*;

public class Scania extends Truck {


    public Scania(){
        super("Scania", 2, Color.blue, 90);

    }



    @Override
    protected double speedFactor(){
        if(flatBedAngle != 0)
            return enginePower*0;

        return enginePower * 0.01;
    }


}
