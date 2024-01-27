package src;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class VolvoVAH300 extends Truck{



    private final Deque<Car> cargo = new ArrayDeque<>();

    private final int loadCapacity = 8;



    public VolvoVAH300(){
        super("VolvoVAH300", 4, Color.GREEN, 150);
    }


    public boolean loadCar(Car car){
        if(!(car instanceof Truck)){
            if(rampAngle == 0 && getCurrentSpeed() == 0 && cargo.size() < loadCapacity && this.position.dist(car.position) < 300){
                car.position = this.position;
                cargo.add(car);
                System.out.println("car successfully added");
                return true;
            }
        }

        return false;
    }

    public void unloadCar(){
        if(rampAngle == 0 && getCurrentSpeed() == 0 && !cargo.isEmpty())
            cargo.pop();
    }

    public int getLoadCapacity(){
        return this.loadCapacity;
    }

    public Deque<Car> getCargo(){
        return cargo;
    }

    @Override
    protected double speedFactor(){
        if(rampAngle < 70)
            return enginePower*0;

        return enginePower * 0.01;
    }

}
