import junit.framework.TestCase;
import src.Saab95;

public class Saab95Test extends TestCase {

    static Saab95 saab = new Saab95();
    public void testSetTurboOn() {
        saab.setTurboOn();
        assert(saab.getTurbo());
    }

    public void testSetTurboOff() {
        saab.setTurboOff();
        assertFalse(saab.getTurbo());
    }

    public void testSpeedFactor() {
    }
}