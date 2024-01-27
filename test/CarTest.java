import junit.framework.TestCase;
import src.Car;
import src.Saab95;
import src.Utils;

import java.awt.*;

public class CarTest extends TestCase {

    static Car saab = new Saab95();

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testGetNrDoors() {
        assertEquals(saab.getNrDoors(), 2);
    }

    public void testGetEnginePower() {
        assertEquals(saab.getEnginePower(), 125.0);
    }

    public void testGetCurrentSpeed() {
    }

    public void testGetColor() {
        assertEquals(saab.getColor(), Color.red);
    }

    public void testSetColor() {
        saab.setColor(Color.yellow);
        assertEquals(saab.getColor(), Color.yellow);
    }

    public void testStartEngine() {
        saab.startEngine();
        assert(saab.getCurrentSpeed() > 0);
    }

    public void testStopEngine() {
        saab.stopEngine();
        assert(saab.getCurrentSpeed() < 1);
    }

    public void testGas() {
        Utils.Vector2d pos = new Utils.Vector2d(saab.position.x, saab.position.y);
        saab.startEngine();
        saab.gas(1);
        saab.move();
        assert(saab.position.x != pos.x || saab.position.y != pos.y);
    }

    public void testBrake() {
        saab.startEngine();
        saab.gas(1);
        saab.move();
        double previousSpeed = saab.getCurrentSpeed();
        saab.brake(1);
        saab.move();
        assert(saab.getCurrentSpeed() < previousSpeed);
    }

    public void testSpeedFactor() {
    }

    public void testMove() {
        Utils.Vector2d pos = new Utils.Vector2d(saab.position.x, saab.position.y);
        saab.startEngine();
        saab.move();
        assert(saab.position.x != pos.x || saab.position.y != pos.y);
    }

    public void testTurnLeft() {
        float angle = (float) saab.angle;
        saab.turnLeft();
        assert(saab.angle < angle);
    }

    public void testTurnRight() {
        float angle = (float) saab.angle;
        saab.turnRight();
        assert(saab.angle > angle);
    }
}