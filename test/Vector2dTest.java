import junit.framework.TestCase;
import src.Utils;

public class Vector2dTest extends TestCase {

    Utils.Vector2d vector1 = new Utils.Vector2d(1,2);
    Utils.Vector2d vector2 = new Utils.Vector2d(2,3);



    public void testDist(){
        //cheap
        assertEquals(Math.sqrt(2),vector1.dist(vector2));


    }

    public void testSetX(){

        vector1.setX(5.0);
        assertEquals(5.0, vector1.x);
    }
    public void testSetY(){
        vector1.setY(3.0);
        assertEquals(3.0, vector1.y);
    }



}