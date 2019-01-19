import java.io.*;

/**
 * Lösungsvorschlag für Implementierung von natürlichem Mischen <br />
 * Informatik III, Universität Augsburg <br />
 * Wintersemester 2009/10
 *
 * @author ?
 * @version 2018-12-03
 */

public class NaturalMergeSort {

    //Strings für die Pfade zum zu sortierenden File (_file)
    //bzw. den Files zum Verteilen (_b und _c)
    private String _file, _b, _c;

    public NaturalMergeSort(String file, String b, String c) {
        _file = file;
        _b = b;
        _c = c;
    }


    public void sort() throws Exception {
        //so lange teilen/mischen, bis nur noch ein Lauf gefunden wird
        while (distribute() != 1) {
            printSequence(_b);
            printSequence(_c);
            printSequence(_file);
            merge();
        }
        printSequence(_file);
    }

    public int distribute() throws Exception {
        //zum Merken der gefundenen Läufe
        int run_number = 1;
        //Streams zum Lesen/Schreiben erzeugen
        //Streams zum Lesen/Schreiben der einzelnen Files
        DataInputStream fileIn = new DataInputStream(new FileInputStream(_file));
        DataOutputStream bOut = new DataOutputStream(new FileOutputStream(_b));
        DataOutputStream cOut = new DataOutputStream(new FileOutputStream(_c));
        //x ist das letzte geschriebene Element
        //y das zu schreibende
        int x, y;
        //Flags für das File, in das geschrieben werden soll
        boolean b_active = true; // b_active == false => c aktiv
        //versuchen, das erste Element zu lesen
        try {
            x = fileIn.readInt();
        } catch (EOFException e) {
            //es gibt nichts zu schreiben
            fileIn.close();
            bOut.close();
            cOut.close();
            return 1;
        }
        //versuchen, das zweite Element zu lesen
        try {
            y = fileIn.readInt();
        } catch (EOFException e) {
            //nur x muss geschrieben werden
            bOut.writeInt(x);
            fileIn.close();
            bOut.close();
            cOut.close();
            return 1;
        }
        //x in _b schreiben
        bOut.writeInt(x);
        try {
            while (true) {
                //der Lauf geht weiter
                if (x <= y) {
                    //falls b_active, dann in _b schreiben, sonst in _c
                    if (b_active) {
                        bOut.writeInt(y);
                    } else {
                        cOut.writeInt(y);
                    }
                }
                //der Lauf ist unterbrochen -> vor dem Schreiben das aktive File wechseln
                //und die run_number erhöhen
                else {
                    if (b_active) {
                        cOut.writeInt(y);
                        run_number++;
                        b_active = false;
                    } else {
                        bOut.writeInt(y);
                        run_number++;
                        b_active = true;
                    }
                }
                //und in _file eine Stelle weiterrücken
                x = y;
                y = fileIn.readInt();
            }
        } catch (EOFException e) {
            //am Ende alle Streams schließen
            fileIn.close();
            bOut.close();
            cOut.close();
        }
        //Anzahl der gefundenen Läufe zurückgeben
        return run_number;
    }

    public void merge() throws Exception {
        //Streams zum Lesen/Schreiben erzeugen
        DataInputStream bIn = new DataInputStream(new FileInputStream(_b));
        DataInputStream cIn = new DataInputStream(new FileInputStream(_c));
        DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(_file));
        //anstehende Elemente aus _b bzw. _c
        int xb, xc;
        //das letzte in _file geschriebene Element
        int last_written = 0;
        //versuchen, aus _b zu lesen
        try {
            xb = bIn.readInt();
        } catch (EOFException e) {
            bIn.close();
            //_b ist leer, also nur _c nach _file übertragen
            write(cIn, fileOut);
            return;
        }
        //versuchen aus _c zu lesen
        try {
            xc = cIn.readInt();
        } catch (EOFException e) {
            cIn.close();
            //_c ist leer, also _b einschließlich xb nach _file übertragen
            fileOut.writeInt(xb);
            write(bIn, fileOut);
            return;
        }
        //Untersuchen ob xb oder xc als erster nach _file geschrieben werden muss
        if (xb < xc) {
            fileOut.writeInt(xb);
            last_written = xb;
            try {
                xb = bIn.readInt();
            } catch (EOFException e) {
                write(cIn, fileOut);
                return;
            }
        } else {
            fileOut.writeInt(xc);
            last_written = xc;
            try {
                xc = cIn.readInt();
            } catch (EOFException e) {
                write(bIn, fileOut);
                return;
            }
        }
        //durch die Files _b und _c laufen
        //while-Schleife wird durch returns in catch-Blöcken verlassen
        while (true) {
            //testen, ob xb als Nächstes an der Reihe ist
            if ((last_written <= xb && xb <= xc) || (last_written <= xb && xc < last_written) ||
                    (xb <= xc && xc <= last_written)) {
                //xb schreiben
                fileOut.writeInt(xb);
                //jetzt ist xb das letzte geschriebene Element
                last_written = xb;
                //versuchen, das nächste Element aus _b zu holen
                try {
                    xb = bIn.readInt();
                } catch (EOFException e) {
                    //_b ist leer, also xc und den Rest von _c nach _file übertragen
                    fileOut.writeInt(xc);
                    write(cIn, fileOut);
                    return;
                }
            }
            //xc muss geschrieben werden
            //analog zu oben
            else {
                fileOut.writeInt(xc);
                last_written = xc;
                try {
                    xc = cIn.readInt();
                } catch (EOFException e) {
                    fileOut.writeInt(xb);
                    write(bIn, fileOut);
                    return;
                }
            }
        }
    }

    public void write(DataInputStream in, DataOutputStream out) throws IOException {
        while (true) {
            try {
                out.writeInt(in.readInt());
            } catch (EOFException e) {
                out.close();
                in.close();
                return;
            }
        }
    }

    private void printSequence(String file) throws IOException {
        InputStream is = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(is);

        // available stream to be read
        while(dis.available()>0) {

            // read four bytes from data input, return int
            int k = dis.readInt();

            // print int
            System.out.print(k+" ");
        }
        System.out.print("\n");
    }

    public static void main(String[] args)
    {
        try {
            FileOutputStream fos = new FileOutputStream("testfile.txt");
            DataOutputStream dos = new DataOutputStream(fos);


            int[] ints = {2, 8, 3, 23, 10, 12, 27, 16, 5, 9};
            //int[] ints = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            for (int i = 0; i < ints.length; ++i)
                dos.writeInt(ints[i]);

            dos.flush();

            String inFile = "testfile.txt";
            String a = "a.txt";
            String b = "b.txt";

            NaturalMergeSort nm = new NaturalMergeSort(inFile, a, b);
            try {
                nm.sort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
