import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Binärbaum <br />
 * Informatik III, Universität Augsburg <br />
 * Wintersemester 2018/19
 * @author Moritz Laudahn (nach Vorlage aus dem Skript von Prof. Möller)
 * @version 2014-12-18
 */
public class BinTree {
    private Object value;
    private BinTree leftson;
    private BinTree rightson;

    public BinTree(Object v, BinTree l, BinTree r) {
        this.value = v;
        this.leftson = l;
        this.rightson = r;
    }

    public Object val() {
        return this.value;
    }

    public BinTree lson() {
        return this.leftson;
    }

    public BinTree rson() {
        return this.rightson;
    }

    public void setVal(Object v) {
        this.value = v;
    }

    public void setLson(BinTree l) {
        this.leftson = l;
    }

    public void setRson(BinTree r) {
        this.rightson = r;
    }

    public boolean isleaf() {
        return (this.leftson == null) && (this.rightson == null);
    }

    public int countNodes(BinTree bt){
        return bt==null?0:1+countNodes(bt.lson())+countNodes(bt.rson());
    }

    public int countVal(BinTree bt){
        return bt==null?0:(int)bt.val()+countVal(bt.lson())+countVal(bt.rson());
    }

    public double avg(BinTree bt){
        return (double)countVal(bt)/countNodes(bt);
    }

    public BinTree leftmost() {
        BinTree p = this;
        BinTree q;
        while((q = p.lson()) != null) {
            p = q;
        }

        return p;
    }

    //Ausgabe
    public void preOut(BinTree bt){
        BinTree l = bt.lson();
        BinTree r = bt.rson();
        if(bt != null){
            System.out.print(bt.val());
            if(l!=null)
                preOut(l);
            if(r!=null)
                preOut(r);
        }

    }

    public void postOut(BinTree bt) {
        BinTree l = bt.lson();
        BinTree r = bt.rson();

        if(bt!=null){
            if(l!=null)
                postOut(l);
            if(r!=null)
                postOut(r);
            System.out.print(bt.val());
        }
    }

    public static void inOut(BinTree bt) {
        BinTree l = bt.lson();
        BinTree r = bt.rson();

        if(bt!=null) {
            if(l!=null)
                inOut(l);
            System.out.print(bt.val());
            if(r!=null)
                inOut(r);
        }
    }
    //Ausgaben Ende

    //Blatt 3
    public boolean isValid(BinTree wt){
        if(wt == null)
            return true;
        if(wt.isleaf() && !(wt.val() instanceof Boolean)) {
            System.out.println("kein boolean");
            return false;
        }
        if(!(wt.val() instanceof String) && !wt.isleaf()) {
            System.out.println("kein string");
            return false;
        }
        BinTree l, r;
        l = wt.lson();
        r = wt.rson();
        if((wt.val().toString() == "and" || wt.val().toString() == "or" || wt.val().toString() == "xor") && (l == null || r == null)) {
            System.out.println("logik fehler andorxor");
            return false;
        }
        if((wt.val().toString() == "not") && (r == null || l != null))
            return false;
        if(isValid(l) && isValid(r))
            return true;
        return false;
    }

    public boolean evaluate(BinTree wt) {
        if (!isValid(wt)) {
            System.out.println("kein valider Baum!");
            return false;
        }
        if (wt.isleaf())
            return (boolean) wt.val();
        BinTree l, r;
        l = wt.lson();
        r = wt.rson();
        switch (wt.val().toString()) {
            case "and":
                return evaluate(l) && evaluate(r);
            case "or":
                return evaluate(l) || evaluate(r);
            case "not":
                return !evaluate(r);
            case "xor":
                return (evaluate(l) && !evaluate(r)) || (!evaluate(l) && evaluate(r));
        }
    return false;
    }

    public String infix(BinTree wt) {
        if(wt == null)
            return "";
        if(wt.isleaf())
            return wt.val().toString();
        if(wt.val().toString() == "not")
            return klammern(wt.val().toString()+" "+infix(wt.rson()));
        return klammern(infix(wt.lson())+" "+wt.val().toString()+" "+infix(wt.rson()));
    }
    private String klammern(String input) {
        return "("+input+")";
    }


    //Blatt 4
    public ArrayList<Integer> inOrdArray(BinTree bt) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(bt == null)
            return list;
        list = inOrdArray(bt.lson());
        list.add((int)bt.val());
        list.addAll(inOrdArray(bt.rson()));
        return list;
    }
    private boolean checkSorted(ArrayList<Integer> inOrd){
        int size = inOrd.size();
        for(int i=1; i<size; i++){
            if(inOrd.get(i-1) > inOrd.get(i))
                return false;
        }
        return true;
    }
    public boolean isSorted(BinTree bt) {
        return checkSorted(inOrdArray(bt));
    }
    //Rotationen
    public BinTree lrot(BinTree bt) {
        BinTree b = bt.rson();
        if(b==null)
            return bt;
        BinTree t2 = b.lson();
        b.setLson(bt);
        bt.setRson(t2);
        return b;
    }
    public BinTree rrot(BinTree bt) {
        BinTree a = bt.lson();
        if(a == null)
            return bt;
        BinTree t2 = a.rson();
        a.setRson(bt);
        bt.setLson(t2);
        return a;
    }

    //Blatt 5
    public BinTree mk_tree_pi(int[] prefix, int[] infix) {
        if(prefix.length == 0) return null;
        int rootVal = prefix[0];
        int indInfix = -1;
        //Finde Wurzelindex in Infix Array
        for(int i=0;i<infix.length;i++){
            if(rootVal == infix[i])
                indInfix = i;
        }

        int restSize = prefix.length -1 -indInfix;

        //Neue Arrays erstellen
        int lpref[] = new int[indInfix];
        int linfix[] = new int[indInfix];
        int rpref[] = new int[restSize];
        int rinfix[] = new int[restSize];

        System.arraycopy(prefix, 1, lpref, 0, indInfix);
        System.arraycopy(infix, 0, linfix, 0, indInfix);
        System.arraycopy(prefix, indInfix+1, rpref, 0, restSize);
        System.arraycopy(infix,  indInfix+1, rinfix, 0, restSize);


        return new BinTree(rootVal, mk_tree_pi(lpref, linfix),mk_tree_pi(rpref, rinfix));
    }

    public int[] postfix(BinTree bt) {
        if(bt == null)
            return new int[0];

        int[] erg;
        if(bt.isleaf()){
            erg = new int[1];
            erg[0] = (Integer) bt.val();
        } else {
            int[] l = postfix(bt.lson());
            int[] r = postfix(bt.rson());

            erg = new int[l.length+r.length+1];
            System.arraycopy(l,0,erg,0,l.length);
            System.arraycopy(r,0,erg,l.length,r.length);
            erg[l.length+r.length] = (Integer) bt.val();
        }
        return erg;
    }

    public int[] postfix(int[] prefix, int[] infix) {
        BinTree bt = mk_tree_pi(prefix, infix);
        return postfix(bt);
    }

    //Blatt 6
    public BinTree createTree(int[] values) {
        int mid, mid_pos, rest;
        if(values.length == 0)
            return null;
        if(values.length == 1)
            return new BinTree(values[0], null, null);
        mid_pos = values.length/2;
        mid = values[mid_pos];

        rest = values.length-1-mid_pos;
        int[] lT = new int[mid_pos];
        int[] rT = new int[rest];

        System.arraycopy(values, 0,lT,0,mid_pos);
        System.arraycopy(values, mid_pos+1,rT,0,rest);

        return new BinTree(mid, createTree(lT), createTree(rT));

    }

    //Richtung Quicksort
    public BinTree createSortedTree(int[] values){
        return createSortedTree(values, 0, values.length-1);
    }

    public BinTree createSortedTree(int[] values, int l, int r) {
        if(l>r)
            return null;
        if(l==r)
            return new BinTree(values[l],null,null);
        if(l==r-1){
            if (values[l] > values[r])
                return new BinTree(values[r], null, new BinTree(values[l], null, null));
            else
                return new BinTree(values[l], null, new BinTree(values[r], null, null));
        }
        //från tre
        int m = pivInd(values, l, r);
        int pivot = values[m];
        swap(values, m, r);

        int i = l;
        int j = r;
        for(;;){
            while(values[++i]<pivot);
            while(pivot<values[--j]);

            if(i>=j) break;
            swap(values, i, j);
        }
        swap(values, i, r);
        BinTree left = createSortedTree(values, l, i-1);
        BinTree right = null;
        if(i<r)
            right = createSortedTree(values, i+1, r);
        return new BinTree(values[i], left, right);
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

    public static void main(String args[]) {
        //Testvars
        int inf[] =  new int[] { 2, 3, 4, 1, 13, 9, 7, 8, 6, 0, 5 };
        int pref[] = new int[] { 1, 3, 2, 4, 13, 7, 9, 8, 6, 5, 0 };
        int values[] = {5, 3, 6, 7, 1, 8, 0, 9, 4};
        int values2[] = {5, 2, 1, 9, 10, 4, 10, 8, 8, 3, 7};

        //Testbäume
        BinTree bt, bzt, wt, wtf, ctree;
        bt = new BinTree(5,new BinTree(3,new BinTree(1,null,null), new BinTree(2,null,null)),new BinTree(7,new BinTree(8,null,null),new BinTree(10, null, null)));
        bzt = new BinTree(7,new BinTree(5,new BinTree(4,null,null), new BinTree(6,null,null)),new BinTree(8,null, new BinTree(9,null,null)));
        wt = new BinTree("and", new BinTree("or", new BinTree(false, null, null), new BinTree(true, null, null)), new BinTree("not",null, new BinTree(false, null,null)));
        wtf = new BinTree("xor", new BinTree(true, null, null), new BinTree(false, null, null));
        ctree = new BinTree("or", new BinTree("not", null, new BinTree("and", new BinTree(true, null, null), new BinTree(false, null, null))), new BinTree("xor", new BinTree(false, null, null), new BinTree(true, null, null)));

        //Funktionen
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
        inOut(temp);
        System.out.println();
        System.out.println("------------------");

        BinTree unsorted = bt.createTree(values2);
        BinTree sorted = bt.createSortedTree(values2);
        System.out.println("Sortiert(unsortierter Baum): " + bt.isSorted(unsorted));
        System.out.println("Sortiert(sortierter Baum): " + bt.isSorted(sorted));

    }
}
