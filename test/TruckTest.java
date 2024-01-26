import junit.framework.TestCase;
import src.Scania;
import src.Truck;

public class TruckTest extends TestCase {

    Truck truck = new Scania();

    public void testRaiseFlatbed() {
        float angle = truck.getFlatbedAngle();
        truck.raiseFlatbed();
        assert(truck.getFlatbedAngle() > angle);
    }

    public void testLowerFlatbed() {
        truck.raiseFlatbed();
        float angle = truck.getFlatbedAngle();
        truck.lowerFlatbed();
        assert(truck.getFlatbedAngle() < angle);
    }

    public void testGetFlatbedAngle() {
        assertEquals(0, truck.getFlatbedAngle());
    }
}