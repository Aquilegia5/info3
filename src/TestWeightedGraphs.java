import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

/**
 * Testprogramm für Aufgabe 3 unf 4 auf Blatt 10. <br />
 * Informatik III, Universität Augsburg <br />
 * Wintersemester 2014/15
 * @author Moritz Laudahn
 * @version 2015-01-14
 */
public class TestWeightedGraphs {
    public static void main(String args[]) {
        testRoadMapTo();
        testLeastLeakageFrom_1();
        // testLeastLeakageFrom_2();

        /** erwartete Ausgabe: **/

//#Zusammenhangskomponenten: 3
//Breitensuche:
//[2, 4, 7, 8, 14, 12, 15, 13]
//[3, 11, 16, 9, 6, 5, 10]
//Tiefensuche:
//[2, 8, 15, 12, 13, 14, 7, 4]
//[3, 11, 9, 6, 5, 10, 16]
//Kürzeste Wege:
//Zu Knoten 4:
//[0, 1, 7, 3, 4, 5, 6, 4, 2, 9, 10, 11, 13, 14, 7, 8, 16]
//Zu Knoten 5:
//[0, 1, 2, 11, 4, 5, 5, 7, 8, 6, 5, 9, 12, 13, 14, 15, 11]
//
//Größter Durchfluss:
//Ausgehend von Knoten 0:
//[0, 0, 8, 0, 2, 3, 5, 14, 15, 0, 6, 10, 8, 12, 6, 1, 11]
//Ausgehend von Knoten 16:
//[3, 0, 8, 5, 2, 6, 10, 14, 12, 12, 11, 16, 13, 16, 6, 8, 16]

    }

    public static void testRoadMapTo() {
        WeightedGraph graph = new WeightedGraph(17);
        graph.addEdge(0, 1, 1.0);
        graph.addEdge(2, 7, 0.1);
        graph.addEdge(2, 4, 3.3);
        graph.addEdge(7, 4, 1.2);
        graph.addEdge(7, 14, 0.1);

        graph.addEdge(2, 8, 8.0);
        graph.addEdge(8, 12, 2.5);
        graph.addEdge(12, 13, 7.0);
        graph.addEdge(13, 14, 0.2);
        graph.addEdge(8, 15, 0.5);

        graph.addEdge(3, 11, 0.5);
        graph.addEdge(5, 6, 0.5);
        graph.addEdge(6, 9, 0.5);
        graph.addEdge(9, 11, 0.5);
        graph.addEdge(5, 10, 0.5);

        graph.addEdge(11, 16, 0.5);
       /* int components = graph.connectedComponents();
        List<Integer> bfs1 = graph.breadthFirst(2);
        List<Integer> bfs2 = graph.breadthFirst(3);
        List<Integer> dfs1 = graph.depthFirst(2);
        List<Integer> dfs2 = graph.depthFirst(3);

        System.out.println("#Zusammenhangskomponenten: " + components);

        System.out.println("Breitensuche:");
        System.out.println(bfs1);
        System.out.println(bfs2);

        System.out.println("Tiefensuche:");
        System.out.println(dfs1);
        System.out.println(dfs2);
        */
        try {
            PrintWriter writer = new PrintWriter("roadMapTo.dot", "UTF-8");
            writer.println(graph.toString());
            writer.close();
        } catch(Exception e) {
            System.out.println("Erstellen der Datei roadMapTo.dot fehlgeschlagen.");
            e.printStackTrace();
        }

        System.out.println("Kürzeste Wege:");
        System.out.println("Zu Knoten 4:");
        int result[] = graph.roadMapTo(4);
        System.out.println(Arrays.toString(result));
        try {
            routeToDotFile(graph, result, false, "roadMapTo_4.dot", "weg_zu_4");
        } catch(Exception e) {
            System.out.println("Erstellen der Datei roadMapTo_4.dot fehlgeschlagen.");
            e.printStackTrace();
        }

        System.out.println("Zu Knoten 5:");
        result = graph.roadMapTo(5);
        System.out.println(Arrays.toString(result));
        try {
            routeToDotFile(graph, result, false, "roadMapTo_5.dot", "weg_zu_5");
        } catch(Exception e) {
            System.out.println("Erstellen der Datei roadMapTo_5.dot fehlgeschlagen.");
            e.printStackTrace();
        }

        System.out.println();
    }

    public static void testLeastLeakageFrom_1() {
        WeightedGraph graph = new WeightedGraph(17);

        graph.addEdge(0, 1, 0.9);
        graph.addEdge(2, 7, 0.1);
        graph.addEdge(2, 4, 0.3);
        graph.addEdge(7, 4, 0.2);
        graph.addEdge(7, 14, 0.1);

        graph.addEdge(2, 8, 0.08);
        graph.addEdge(8, 12, 0.5);
        graph.addEdge(12, 13, 0.7);
        graph.addEdge(13, 14, 0.2);
        graph.addEdge(8, 15, 0.5);

        graph.addEdge(1, 15, 0.7);
        graph.addEdge(0, 3, 0.7);
        graph.addEdge(3, 5, 0.7);
        graph.addEdge(5, 6, 0.7);
        graph.addEdge(6, 14, 0.7);

        graph.addEdge(0, 9, 0.1);
        graph.addEdge(9, 12, 0.1);
        graph.addEdge(6, 10, 0.7);
        graph.addEdge(10, 11, 0.7);
        graph.addEdge(11, 16, 0.7);

        graph.addEdge(16, 13, 0.7);

        try {
            PrintWriter writer = new PrintWriter("leastLeakageFrom.dot", "UTF-8");
            writer.println(graph.toString());
            writer.close();
        } catch(Exception e) {
            System.out.println("Erstellen der Datei leastLeakageFrom.dot fehlgeschlagen.");
            e.printStackTrace();
        }

        System.out.println("Größter Durchfluss:");
        System.out.println("Ausgehend von Knoten 0:");
        int result[] = graph.leastLeakageFrom(0);
        System.out.println(Arrays.toString(result));
        try {
            routeToDotFile(graph, result, true, "leastLeakageFrom_0.dot", "fluss_von_0");
        } catch(Exception e) {
            System.out.println("Erstellen der Datei leastLeakageFrom_0.dot fehlgeschlagen.");
            e.printStackTrace();
        }

        System.out.println("Ausgehend von Knoten 16:");
        result = graph.leastLeakageFrom(16);
        System.out.println(Arrays.toString(result));
        try {
            routeToDotFile(graph, result, true, "leastLeakageFrom_16.dot", "fluss_von_16");
        } catch(Exception e) {
            System.out.println("Erstellen der Datei leastLeakageFrom_16.dot fehlgeschlagen.");
            e.printStackTrace();
        }

        System.out.println();
    }

    public static void testLeastLeakageFrom_2() {
        WeightedGraph graph = new WeightedGraph(3);
        graph.addEdge(0, 1, 0.9);
        graph.addEdge(1, 2, 0.9);
        graph.addEdge(0, 2, 0.5);

        System.out.println(Arrays.toString(graph.leastLeakageFrom(0)));
        System.out.println();
    }

    /**
     * Erstellt eine dot-Datei des Graphen. <br />
     * Einzelne Knoten werden ignoriert. <br />
     * Der in <code>route</code> angegebene Spannbaum
     * innerhalb des Graphen wird farbig hervorgehoben. <br />
     * <br />
     * Siehe
     * https://de.wikipedia.org/wiki/DOT_%28GraphViz%29
     * und
     * https://en.wikipedia.org/wiki/DOT_%28graph_description_language%29
     * für weitere Informationen.
     */
    public static void routeToDotFile(WeightedGraph graph, int route[], boolean inv, String filename, String graphName)
            throws FileNotFoundException, UnsupportedEncodingException {
        ArrayList<HashMap<Integer, Double>> edges = graph.copyWeightedEdges();
        ArrayList<HashMap<Integer, Double>> specialEdges = new ArrayList<HashMap<Integer, Double>>();
        for(int i = 0; i < graph.numNodes(); ++i) {
            specialEdges.add(new HashMap<Integer, Double>());
        }

        for(int i = 0; i < route.length; ++i) {
            if(i != route[i]) {
                edges.get(i).remove(route[i]);
                edges.get(route[i]).remove(i);
                specialEdges.get(i).put(route[i], graph.getWeight(i, route[i]));
            }
        }

        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.print("digraph ");
        writer.print(graphName);
        writer.println(" {");
        writer.println("\tedge [dir=none];");
        for(int i = 0; i < edges.size(); ++i) {
            HashMap<Integer, Double> adjList = edges.get(i);
            for(int j : adjList.keySet()) {
                if(i < j) {
                    writer.print("\t");
                    writer.print(i);
                    writer.print(" -> ");
                    writer.print(j);
                    writer.print(" [label=\"");
                    writer.print(adjList.get(j));
                    writer.println("\"];");
                }
            }
        }

        writer.println("\tedge [color=red];");
        if(inv) {
            writer.println("\tedge [dir=backward];");
        } else {
            writer.println("\tedge [dir=forward];");
        }

        for(int i = 0; i < specialEdges.size(); ++i) {
            HashMap<Integer, Double> adjList = specialEdges.get(i);
            for(int j : adjList.keySet()) {
                writer.print("\t");
                writer.print(i);
                writer.print(" -> ");
                writer.print(j);
                writer.print(" [label=\"");
                writer.print(adjList.get(j));
                writer.println("\"];");
            }
        }

        writer.println("}");
        writer.close();
    }
}
