package test.Microsoft;

/**
 * Created by muzilan on 15/9/29.
 */
import java.util.LinkedList;
import java.util.Scanner;

class Point{
    public long x;
    public long y;
    public Point(long X, long Y){
        this.x = X;
        this.y = Y;
    }
}
public class T1 {
    public static boolean isInCircle(long x, long y, double x0, double y0, double r){
        if(distance_2(x, y, x0, y0) <= r*r){
            return true;
        } else {
            return false;
        }
    }
    public static double distance_2(long x, long y, double x0, double y0){
        double deltaX = x - x0;
        double deltaY = y - y0;
        return deltaX * deltaX + deltaY * deltaY;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double x,y,r;
        x = input.nextDouble();
        y = input.nextDouble();
        r = input.nextDouble();
        long left = (int)(x - r), right = (int)(x + r), top = (int)(y + r), bottom = (int)(y - r);
        double maxDistance = 0;

        LinkedList<Point> points = new LinkedList<Point>();

        for(long i = left; i <= right; i++){
            if(isInCircle(i, top, x, y, r)){
                double curDistance = distance_2(i, top, x, y);
                if(curDistance >= maxDistance){
                    maxDistance = curDistance;
                    points.add(new Point(i, top));
                }
            }
        }
        for(long i = left; i <= right; i++){
            if(isInCircle(i, bottom, x, y, r)){
                double curDistance = distance_2(i, bottom, x, y);
                if(curDistance >= maxDistance){
                    maxDistance = curDistance;
                    points.add(new Point(i, bottom));
                }
            }
        }
        for(long i = bottom; i <= top; i++){
            if(isInCircle(left, i, x, y, r)){
                double curDistance = distance_2(left, i, x, y);
                if(curDistance >= maxDistance){
                    maxDistance = curDistance;
                    points.add(new Point(left, i));
                }
            }
        }

        for(long i = bottom; i <= top; i++){
            if(isInCircle(right, i, x, y, r)){
                double curDistance = distance_2(right, i, x, y);
                if(curDistance >= maxDistance){
                    maxDistance = curDistance;
                    points.add(new Point(right, i));
                }
            }
        }

        for(Point pt : points){
            if(distance_2(pt.x,pt.y, x, y) < maxDistance){
                points.remove(pt);
            }
        }

        long maxX = points.get(0).x;
        LinkedList<Point> resultPts = new LinkedList<Point>();
        for(Point pt : points){
            if(pt.x > maxX){
                maxX = pt.x;
            }
        }
        for(Point pt : points){
            if(pt.x == maxX){
                resultPts.add(pt);
            }
        }


        Point resultPt = resultPts.get(0);

        for(Point pt : resultPts){
            if(pt.y > resultPt.y){
                resultPt = pt;
            }
        }
        System.out.println(resultPt.x+" "+resultPt.y);
    }

}
