public class Main {

    public static void main(String[] args) {
        //Testbäume
        BinTree bt, bzt, wt, wtf, ctree;
        bt = new BinTree(5,new BinTree(3,new BinTree(1,null,null), new BinTree(2,null,null)),new BinTree(7,new BinTree(8,null,null),new BinTree(10, null, null)));
        bzt = new BinTree(7,new BinTree(5,new BinTree(4,null,null), new BinTree(6,null,null)),new BinTree(8,null, new BinTree(9,null,null)));
        wt = new BinTree("and", new BinTree("or", new BinTree(false, null, null), new BinTree(true, null, null)), new BinTree("not",null, new BinTree(false, null,null)));
        wtf = new BinTree("xor", new BinTree(true, null, null), new BinTree(false, null, null));
        ctree = new BinTree("or", new BinTree("not", null, new BinTree("and", new BinTree(true, null, null), new BinTree(false, null, null))), new BinTree("xor", new BinTree(false, null, null), new BinTree(true, null, null)));

        //Funktionentest
        System.out.println(bt.avg(bt));
        System.out.println("-----------------------");
        bt.preOut(bt);
        System.out.println();
        bt.preOut(bt.lrot(bt.rrot(bt))); //Links- und Rechtsrotation -> gleicher Baum wie vorher
    }
}
