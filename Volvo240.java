import java.awt.*;

public class Volvo240 extends Car{

    public final static double trimFactor = 1.25;


    
    public Volvo240(){
        nrDoors = 4;
        setColor(Color.black);
        enginePower = 100;
        modelName = "Volvo240";
        stopEngine();
    }

    @Override
    protected double speedFactor(){
        return enginePower * 0.01 * trimFactor;
    }


}
