import java.awt.*;

public class Sorting {

    public Sorting(){

    }

    //Blatt5 Sortieren
    public void sort_points(Point[] points){
        Point swap;
        int i = 0;
        int j = points.length-1;

        while(i<j){
            while(points[i].x != 2 && i<j) i++;
            while(points[j].x != 1 && i<j) j--;

            if(i!=j) {
                swap = points[i];
                points[i] = points[j];
                points[j] = swap;
            }
        }
    }
}
