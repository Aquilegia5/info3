import java.awt.*;

public class Sorting {

    public Sorting(){}

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
    public int heapinsert(int[] a, int r, int x) {
        a[r] = x;
        int ret = r;
        int vaterInd = (r-1)/2;

        while(vaterInd >= 0) {
            if(a[r]>a[vaterInd]){
                a[r] = a[vaterInd];
                a[vaterInd] = x;
            }
            else {
                break;
            }
            r = vaterInd;
            vaterInd = (r-1)/2;
        }
        return ret;
    }

    public static void main(String args[]) {
        //Sortieren
        Sorting sort = new Sorting();

        //Testvars
        Point[] points = {new Point(1,3), new Point(2,5), new Point(2,1), new Point(1,5), new Point(1,1), new Point(2,3)};
        int heapinsertarr[] = {9, 5, 7, 3, 1, 6, 0, 0, 0, 0};
        int inf[] =  new int[] { 2, 3, 4, 1, 13, 9, 7, 8, 6, 0, 5 };
        int values[] = {5, 3, 6, 7, 1, 8, 0, 9, 4};

        System.out.println("Sortieren");
        sort.sort_points(points);
        for(int i=0;i<points.length;i++){
            System.out.print("(" +points[i].x +"|" +points[i].y + ")");
        }
        for(int i=0;i<inf.length;i++){
            System.out.print(inf[i]);
        }
        System.out.println();
        sort.selection_sort(inf);
        for(int i=0;i<inf.length;i++){
           System.out.print(inf[i]);
        }
        System.out.println();
        System.out.println(sort.quickSelect(values, 5));

        for(int i=0;i<heapinsertarr.length;i++){
            System.out.print(heapinsertarr[i]);
        }
        sort.heapinsert(heapinsertarr, 6, 10);
        System.out.println();
        for(int i=0;i<heapinsertarr.length;i++){
            System.out.print(heapinsertarr[i]);
        }
    }
}
