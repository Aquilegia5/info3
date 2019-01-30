/**
 * Union-Find-Datenstruktur. <br />
 * Informatik III, UniversitĂ¤t Augsburg <br />
 * Wintersemester 2009/10
 * @author ?
 * @version 2014-11-25
 */
public class UnionFind {

    int[] p;
    int[] k;

    public UnionFind(int n) {
        p = new int[n];
        k = new int[n];
    }

    public void make_set(int e) {
        p[e] = e;
        k[e] = 1;
    }

    public void union(int e, int f) {
        if (k[e] < k[f]) {
            int swap = e;
            e = f;
            f = swap;
        }
        p[f] = e;
        k[e] = k[e] + k[f];
    }

    public void union_simple(int e, int f) {
        p[f] = e;
    }

    public int find(int e) {
        while (p[e] != e) {
            e = p[e];
        }
        return e;
    }

    public int find_splitting(int e) {
        int t,a;
        while(p[e] != e) {
            a = e;                      // Anfangsindex
            e = p[e];
            if(p[e] != e){
                t = p[e];
                p[a] = t;
            }
        }
        return e;
    }

    public int find_halving(int e) {
        int t,a;
        int c = 0;
        while(p[e] != e) {
            a = e;                      // Anfangsindex
            e = p[e];
            if(p[e] != e){
                if((c++ % 2) == 0) {
                    t = p[e];
                    p[a] = t;
                }
            }
        }
        return e;
    }

    public static void printarr(int a[]){
        for(int i=0;i<a.length;i++){
            if(i==6 || i==7)
                continue;
            System.out.print(i +" " +a[i] + " | ");
        }
    }

    public static void main(String args[]){
        UnionFind t1 = new UnionFind(9);
        t1.make_set(8);
        t1.make_set(5);
        t1.make_set(3);
        t1.make_set(1);
        t1.union(8,5);
        t1.union(5,3);
        t1.union(3,1);
        printarr(t1.p);

        System.out.println();
        System.out.println(t1.find_halving(5));
        printarr(t1.p);
        System.out.println();
        System.out.println();


    }

}
