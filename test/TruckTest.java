import junit.framework.TestCase;
import src.Scania;
import src.Truck;

public class TruckTest extends TestCase {

    Truck truck = new Scania();

    public void testRaiseFlatbed() {
        float angle = truck.getRampAngle();
        truck.raiseRamp();
        assert(truck.getRampAngle() > angle);
    }

    public void testLowerFlatbed() {
        truck.raiseRamp();
        float angle = truck.getRampAngle();
        truck.lowerRamp();
        assert(truck.getRampAngle() < angle);
    }

    public void testGetFlatbedAngle() {
        assertEquals(0, truck.getRampAngle());
    }
}