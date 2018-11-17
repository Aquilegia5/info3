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

    public static void main(String args[]){
        BinTree bt;
        bt = new BinTree(5,new BinTree(3,new BinTree(1,null,null), new BinTree(2,null,null)),new BinTree(7,new BinTree(8,null,null),null));
        System.out.println(bt.avg(bt));
        preOut(bt);
        System.out.println();
        postOut(bt);
        System.out.println();
        inOut(bt);
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

    //Ausgabe
    public static void preOut(BinTree bt){
        BinTree l = bt.lson();
        BinTree r = bt.rson();
        if(bt != null){
            System.out.print((int)bt.val());
            if(l!=null)
                preOut(l);
            if(r!=null)
                preOut(r);
        }

    }

    public static void postOut(BinTree bt) {
        BinTree l = bt.lson();
        BinTree r = bt.rson();

        if(bt!=null){
            if(l!=null)
                postOut(l);
            if(r!=null)
                postOut(r);
            System.out.print((int)bt.val());
        }
    }

    public static void inOut(BinTree bt) {
        BinTree l = bt.lson();
        BinTree r = bt.rson();

        if(bt!=null) {
            if(l!=null)
                inOut(l);
            System.out.print((int) bt.val());
            if(r!=null)
                inOut(r);
        }
    }

    public BinTree leftmost() {
        BinTree p = this;
        BinTree q;
        while((q = p.lson()) != null) {
            p = q;
        }

        return p;
    }
}
