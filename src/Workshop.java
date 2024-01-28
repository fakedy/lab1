package src;

import java.util.ArrayList;

public class Workshop {

    private final int capacity;
    private final ArrayList<Car> cars;
    private final Car[] allowedCars;



    private final Utils.Vector2i position;

    Workshop(Utils.Vector2i position, int capacity, Car[] allowedCars){
        this.position = position;
        this.capacity = capacity;
        cars = new ArrayList<>(capacity);
        this.allowedCars = allowedCars;
    }

    public void loadCar(Car car){

    }
    public void loadCar(Volvo240 car){

    }

    public Utils.Vector2i getPosition() {
        return position;
    }


}
