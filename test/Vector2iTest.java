import junit.framework.TestCase;
import src.Utils;

public class Vector2iTest extends TestCase {






    Utils.Vector2i vector1 = new Utils.Vector2i(1,2);
    Utils.Vector2i vector2 = new Utils.Vector2i(2,3);



    public void testDist(){
        //cheap
        assertEquals(Math.sqrt(2),vector1.dist(vector2));


    }

    public void testSetX(){

        vector1.setX(5);
        assertEquals(5, vector1.x);
    }
    public void testSetY(){
        vector1.setY(3);
        assertEquals(3, vector1.y);
    }




}
