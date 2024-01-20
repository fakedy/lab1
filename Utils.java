public class Utils {


    public static class Vector2d{
        public double x;
        public double y;

        Vector2d(double x, double y){
            this.x = x;
            this.y = y;
        }

        public Vector2d normalize(){ // normalizes a vector
            double vectorMagnitude = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
            Vector2d vector = new Vector2d(this.x/vectorMagnitude, this.y/vectorMagnitude);
            return vector;
        }
    }

    public static class Vector2i{
        public int x;
        public int y;

        Vector2i(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Vector2i normalize(){ // normalizes a vector
            double vectorMagnitude = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
            Vector2i vector = new Vector2i((int) (this.x/vectorMagnitude), (int) (this.y/vectorMagnitude));
            return vector;
        }
    }
}
