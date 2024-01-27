import junit.framework.TestCase;
import src.Car;
import src.Saab95;
import src.VolvoVAH300;

import java.util.Deque;

public class VolvoVAH300Test extends TestCase {

    VolvoVAH300 truck = new VolvoVAH300();
    public void testLoadCar() {
        Car car = new Saab95();
        car.position.setX(truck.position.x+30); // place car somewhat close
        car.position.setY(truck.position.y+30);

        while(truck.getRampAngle() != 0){ // lower ramp to allow loading
            truck.lowerRamp();
        }

        truck.loadCar(car);
        assert(truck.getCargo().peek() == car);
        //assert(truck.position == car.position);

    }

    public void testUnloadCar() {
        Car car = new Saab95();
        car.position.setX(truck.position.x+30); // place car somewhat close
        car.position.setY(truck.position.y+30);

        while(truck.getRampAngle() != 0){ // lower ramp to allow unload
            truck.lowerRamp();
        }
        truck.loadCar(car);
        int cargoSize = truck.getCargo().size();
        truck.unloadCar();
        assert(truck.getCargo().size() < cargoSize);

    }

    public void testGetLoadCapacity() {

        int maxCars = truck.getLoadCapacity();

        assertEquals(8, maxCars);

    }

    public void testGetCargo() {

        Deque<Car> maxCars = truck.getCargo();

        assert(maxCars != null);
    }

    public void testSpeedFactor() {

        while(truck.getRampAngle() != 0){
            truck.lowerRamp();
        }
        truck.gas(0.1);
        assert(truck.getCurrentSpeed() == 0);

    }
}