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

    public int quickSelect(int[] a, int k){
        return quickSelect(a, 0, a.length-1, k);
    }
    public int quickSelect(int[] a, int l, int r, int k) {
        if(l==r)
            return a[l];
        if(l==r-1){
            if(k==1)
                return (Math.min(a[l], a[r]));
            if(k==2)
                return (Math.max(a[l], a[r]));
        }

        int i,j;
        int m = pivInd(a, l, r);
        int pivot = a[m];
        swap(a,m,r);

        i=l;
        j=r;
        for(;;){
            while(a[++i]<pivot);
            while(a[--j]>pivot);
            if(i>=j) break;
            swap(a, i, j);
        }
        swap(a, i, r);

        if(i == l+k-1){
            return a[i];
        }
        if(i < l+k-1){
            return quickSelect(a,i+1, r, k-1-i+l);
        }
        if(i > l+k-1) {
            return quickSelect(a, l, i-1, k);
        }
        return -1;
    }
    private static int pivInd(int[] a, int l, int r) {
        int m = (l+r)/2;
        if(a[m] < a[l]) swap(a, l, m);
        if(a[r] < a[l]) swap(a, l, r);
        if(a[r] < a[m]) swap(a, m, r);
        return m;
    }

    private static void swap(int[] a , int l, int r) {
        int temp = a[l];
        a[l] = a[r];
        a[r] = temp;
    }
}
