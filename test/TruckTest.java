import junit.framework.TestCase;
import src.Scania;
import src.Truck;

public class TruckTest extends TestCase {

    Truck truck = new Scania();

    public void testRaiseRamp() {
        truck.lowerRamp();
        float angle = truck.getRampAngle();
        truck.raiseRamp();
        assert(truck.getRampAngle() > angle);
    }

    public void testLowerRamp() {
        truck.raiseRamp();
        float angle = truck.getRampAngle();
        truck.lowerRamp();
        assert(truck.getRampAngle() < angle);
    }

    public void testGetRampAngle() {
        assertEquals(70f, truck.getRampAngle());
    }


    public void testSpeedFactor(){

        while(truck.getRampAngle() != 0){
            truck.lowerRamp();
        }
        truck.gas(0.1);
        assert(truck.getCurrentSpeed() > 0);
    }




}