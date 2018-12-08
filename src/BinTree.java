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
        BinTree bt, wt, wtf, ctree;
        bt = new BinTree(5,new BinTree(3,new BinTree(1,null,null), new BinTree(2,null,null)),new BinTree(7,new BinTree(8,null,null),null));
        wt = new BinTree("and", new BinTree("or", new BinTree(false, null, null), new BinTree(true, null, null)), new BinTree("not",null, new BinTree(false, null,null)));
        wtf = new BinTree("xor", new BinTree(true, null, null), new BinTree(false, null, null));
        ctree = new BinTree("or", new BinTree("not", null, new BinTree("and", new BinTree(true, null, null), new BinTree(false, null, null))), new BinTree("xor", new BinTree(false, null, null), new BinTree(true, null, null)));
        System.out.println(bt.avg(bt));
        System.out.println("-----------------------");
        preOut(wt);
        System.out.println();
        postOut(wt);
        System.out.println();
        inOut(wt);
        System.out.println();
        System.out.println("-----------------------");
        System.out.println(bt.evaluate(wt));
        System.out.println(bt.infix(wt));
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
    public static void preOut(BinTree bt){
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

    public static void postOut(BinTree bt) {
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
}
