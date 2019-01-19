import java.io.*;

public class MergeSort {
    private String file, b, c;
    private DataInputStream fileIn, bIn, cIn ;
    private PrintStream fileOut, bOut, cOut ;

    public MergeSort(String files, String bs, String cs) throws Exception {
        file = files; b = bs; c = cs;
    }

    public void directmergesort () throws Exception {
    // sortiert Hauptdatei file mit Hilfsdateien b, c
        int k, n;
        fileIn = new DataInputStream(new FileInputStream(file));
        bOut = new PrintStream(new FileOutputStream(b));
        cOut = new PrintStream(new FileOutputStream(c));
        n = inputsize();
        k = 1;
        while (k < n) {
            distribute(k);
            directmerge(k);
            System.out.println(k);
            k = 2 * k;
        }
    }

    public void distribute (int k) throws Exception {
    // verteile St¨ucke der L¨ange k von file abwechselnd auf b, c
        while (! eof(fileIn)) {
            copyrun(k, fileIn, bOut); // zuerst nach b
            copyrun(k, fileIn, cOut) ; // dann nach c
        }
        fileIn.close() ; bOut.close() ; cOut.close() ;
        }

    public static void copyrun (int k, DataInputStream a, PrintStream b) throws Exception {
    // kopiert k Elemente von a nach b, falls m¨
        int y;
        while (k > 0 && !eof(a)) {
            y = readInt(a);
            writeInt(b,y);
            --k;
        }
    }

    public void directmerge(int k) throws Exception {
    /* mische Folgen der Laenge k>0 aus b und c (mit length(b) >= length(c) >= 1) nach file */
        fileOut = new PrintStream(new FileOutputStream(file));
        bIn = new DataInputStream(new FileInputStream(b));
        cIn = new DataInputStream(new FileInputStream(c));
        while (!(eof(bIn)||eof(cIn))) // !eof(bIn)&&!eof(cIn)
            mergerun(k);
        copyrest(bIn, fileOut);
        copyrest(cIn, fileOut);
        fileOut.close();
        bIn.close();
        cIn.close();
        fileIn = new DataInputStream(new FileInputStream(file));
        bOut = new PrintStream(new FileOutputStream(b));
        cOut = new PrintStream(new FileOutputStream(c)) ;
    }

    public static void copyrest (DataInputStream a, PrintStream b) throws Exception {
        int x;
        while (!eof(a)) {
            x = readInt(a);
            writeInt(b,x);
        }
    }

    public void mergerun (int k) throws Exception {
    /* mische je k>0 Elemente von b, c (mit length(b) >= length(c) >=1) nach file */
        int i, j;
        int x, y;
        x = readInt(bIn);
        y = readInt(cIn);
        i = k;
        j = k; // Anzahl der zu uebertragenden Elemente
        do {
            if (x <= y) {
                writeInt(fileOut, x);
                --i;
                if (i > 0 && !eof(bIn))
                    x = readInt(bIn);
                else break;
            }
            else{
                writeInt(fileOut, y);
                --j;
                if (j > 0 && !eof(cIn))
                    y = readInt(cIn);
                else break;
            }
        }
        while (i > 0 || j > 0); /* i+j > 0 */
            if (i == 0) {
                writeInt(fileOut, y);
                copyrun(j - 1, cIn, fileOut);
            } else {
                writeInt(fileOut, x);
                copyrun(i - 1, bIn, fileOut);
            }

    }

    public int inputsize () throws Exception{ /* k¨onnte effizienter mittels fileIn.available() realisiert werden */
         int k=0;
         while (!eof(fileIn)) {
             ++k;
            readInt(fileIn) ;
         }
         fileIn.close();
         fileIn = new DataInputStream(new FileInputStream(file));
         return k ;
    }

    public static boolean eof (InputStream i) throws Exception
    { return i.available() <= 0 ; }

    public static int readInt (DataInputStream i) throws Exception
    { return Integer.parseInt(i.readLine()) ; }

    public static void writeInt (PrintStream o, int x)
    { o.println(x) ;}

    public void finalize() throws Exception
    { fileIn.close() ; bOut.close() ; cOut.close() ;
    }
    public static void main(String args []) throws Exception
    { MergeSort m = new MergeSort(args[0], args[1], args[2]) ; m.directmergesort() ;
    }
}
