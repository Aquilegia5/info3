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
    public void insert_sort(int[] numbers) {
        int swap = 0;
        for(int i = 0; i<numbers.length;i++) {
            swap = numbers[i];
            for(int j=i;j>0;j--) {
                if(swap < numbers[j-1]){
                    numbers[j] = numbers[j-1];
                    numbers[j-1] = swap;
                } else {
                    break;
                }
            }
        }
    }
    public void selection_sort(int[] numbers) {
        int min, min_pos;

        for(int i=0; i<numbers.length;i++) {
            min = numbers[i];
            min_pos = i;
            for (int j = i+1; j < numbers.length; j++) {
                if(numbers[j] < min){
                    min = numbers[j];
                    min_pos = j;
                }
            }
            for(int j=min_pos;j>i;j--){
                numbers[j] = numbers[j-1];
            }
            numbers[i] = min;

        }
    }
}
