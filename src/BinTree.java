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

    public void inOut(BinTree bt) {
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
        for(i=0;i<infix.length;i++){
            if(rootVal = infix[i])
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
        System.arraycopy(infix, 0, indInfix+1, rinfix, 0, restSize);


        return new BinTree(rootVal, mk_tree_pi(lpref, linfix),mk_tree_pi(rpref, rinfix));
    }

}
