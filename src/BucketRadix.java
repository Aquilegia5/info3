import java.util.ArrayList;

public class BucketRadix {
    public static void BucketSort(int[] a, int k, boolean reversed) {
        int mask = 0x00000001 << k;
        int insertIndex = 0;
        ArrayList<Integer> big= new ArrayList<Integer>();

        for(int i=0; i<a.length;i++){
            if(((~a[i] & mask) == 0) ^ reversed) {
                a[insertIndex++] = a[i];
            } else {
                big.add(a[i]);
            }
        }

        for(int i=0;i<big.size();i++){
            a[insertIndex++] = big.get(i);
        }
    }


    public static void radixSort(int a[]){
        for(int i=0;i<31;i++){
            BucketSort(a, i, false);
        }
        BucketSort(a, 31, true);
    }


    public static void main(String args[]) {
        int a[] = new int[] {5, 7, 2, -8, -14, -7, -1, 0};
        radixSort(a);

        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
}
