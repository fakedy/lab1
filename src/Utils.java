package src;

public class Utils {


    public static class Vector2d{
        public double x;
        public double y;

        public Vector2d(double x, double y){
            this.x = x;
            this.y = y;
        }


        public double dist(Vector2d target){
            double dist = 0;

            double dx = target.x - this.x;
            double dy = target.y - this.y;

            dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy,2));

            return dist;
        }

        public void setX(double x){
            this.x = x;
        }
        public void setY(double y){
            this.y = y;
        }
    }

    public static class Vector2i{
        public int x;
        public int y;

        public Vector2i(int x, int y){
            this.x = x;
            this.y = y;
        }


        public double dist(Vector2i target){
            double dist = 0;

            double dx = target.x - this.x;
            double dy = target.y - this.y;

            dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy,2));

            return dist;
        }


        public void setX(int x){
            this.x = x;
        }
        public void setY(int y){
            this.y = y;
        }
    }
}
