import junit.framework.TestCase;
import src.Scania;

public class ScaniaTest extends TestCase {

    Scania truck = new Scania();
    public void testSpeedFactor() {
        while(truck.getRampAngle() != 0){
            truck.lowerRamp();
        }
        truck.gas(0.1);
        assert(truck.getCurrentSpeed() > 0);
    }
}