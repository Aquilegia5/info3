import java.awt.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //Sortieren
        Sorting sort = new Sorting();

        //Testvars
        int inf[] =  new int[] { 2, 3, 4, 1, 13, 9, 5, 7, 8, 6, 0 };
        int pref[] = new int[] { 1, 3, 2, 4, 13, 7, 9, 8, 6, 5, 0 };
        Point[] points = {new Point(1,3), new Point(2,5), new Point(2,1), new Point(1,5), new Point(1,1), new Point(2,3)};
        int values[] = {5, 3, 6, 7, 1, 8, 0, 9, 4};
        int values2[] = {5, 2, 1, 9, 10, 4, 10, 8, 8, 3, 7};
        int heapinsertarr[] = {9, 5, 7, 3, 1, 6, 0, 0, 0, 0};

        //Testbäume
        BinTree bt, bzt, wt, wtf, ctree;
        bt = new BinTree(5,new BinTree(3,new BinTree(1,null,null), new BinTree(2,null,null)),new BinTree(7,new BinTree(8,null,null),new BinTree(10, null, null)));
        bzt = new BinTree(7,new BinTree(5,new BinTree(4,null,null), new BinTree(6,null,null)),new BinTree(8,null, new BinTree(9,null,null)));
        wt = new BinTree("and", new BinTree("or", new BinTree(false, null, null), new BinTree(true, null, null)), new BinTree("not",null, new BinTree(false, null,null)));
        wtf = new BinTree("xor", new BinTree(true, null, null), new BinTree(false, null, null));
        ctree = new BinTree("or", new BinTree("not", null, new BinTree("and", new BinTree(true, null, null), new BinTree(false, null, null))), new BinTree("xor", new BinTree(false, null, null), new BinTree(true, null, null)));

        //Funktionentest
        /* Baumzeug
        System.out.println(bt.avg(bt));
        System.out.println("-----------------------");
        bt.preOut(bt);
        System.out.println();
        bt.preOut(bt.lrot(bt.rrot(bt))); //Links- und Rechtsrotation -> gleicher Baum wie vorher
        System.out.println();
        System.out.println("--------Blatt5----------");
        BinTree test = bt.mk_tree_pi(pref, inf);
        test.postOut(test);
        System.out.println();
        int postarry[] = test.postfix(pref, inf);
        for(int i=0;i<postarry.length;i++){
            System.out.print(postarry[i]);
        }
        System.out.println();
        BinTree temp = bt.createTree(values);
        bt.inOut(temp);
        System.out.println("------------------");

        BinTree unsorted = bt.createTree(values2);
        BinTree sorted = bt.createSortedTree(values2);
        System.out.println("Sortiert: " + bt.isSorted(unsorted));
        System.out.println("Sortiert: " + bt.isSorted(sorted));
        */

        //Sortieren
        /*System.out.println("Sortieren");
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
        */
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
