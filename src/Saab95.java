package src;

import java.awt.*;

public class Saab95 extends Car{

    private boolean turboOn;


    
    public Saab95(){
        super("Saab95", 2, Color.red, 125);
	    turboOn = false;
    }
    


    public void setTurboOn(){
	    turboOn = true;
    }

    public void setTurboOff(){
	    turboOn = false;
    }

    public boolean getTurbo(){
        return turboOn;
    }


    @Override
    protected double speedFactor(){
        double turbo = 1;
        if(turboOn) turbo = 1.3;
        return enginePower * 0.01 * turbo;
    }

}
