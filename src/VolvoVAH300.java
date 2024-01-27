package src;

import java.awt.*;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.Deque;

public class VolvoVAH300 extends Truck{



    private final Deque<Car> cargo = new ArrayDeque<>();

    private final int loadCapacity = 8;



    VolvoVAH300(){
        super("VolvoVAH300", 4, Color.GREEN, 150);
    }


    public void loadCar(Car car){
        if(getCurrentSpeed() == 0 && cargo.size() < loadCapacity){
            cargo.add(car);
        }
    }

    public void unloadCar(){
        if(getCurrentSpeed() == 0 && !cargo.isEmpty())
            cargo.pop();
    }

    public int getLoadCapacity(){
        return this.loadCapacity;
    }


    // The VolvoVAH300 got an automatic ramp although its crazy fast at raising and lowering :)
    @Override
    public void raiseRamp(){
        while(!rampIsUp && getCurrentSpeed() == 0) {
            if( rampAngle < 70) {
                rampAngle += 0.01f;
            } else {
                rampAngle = 70;
                rampIsUp = true;
            }
        }
    }
    @Override
    public void lowerRamp(){
        while(rampIsUp && getCurrentSpeed() == 0) {
            if (rampAngle > 0) {
                rampAngle -= 0.01f;
            } else {
                rampAngle = 0;
                rampIsUp = false;
            }
        }
    }

}
