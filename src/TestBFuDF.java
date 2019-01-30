import java.util.List;

/**
 * Test der Breitensuche, Tiefensuche und Z�hlung der
 * Zusammenhangskomponenten von <code>Graph</code> <br />
 * Teil von Aufgabe 1 - Blatt 10. <br />
 * Informatik III, Universit�t Augsburg <br />
 * Wintersemester 2018/19
 * @author Moritz Laudahn
 * @version 2014-01-02
 */
public class TestBFuDF {
    public static void main(String args[]) {
        // Beispielgraph mit 17 Knoten und drei Zusammenhangskomponenten:
        //
        // 15   10-5-6-9-11-3
        // |              |
        // 8-12-13        16
        // |    |
        // 2-7-14
        //  \|        0-1
        //   4
        //
        Graph graph = new Graph(17);
        graph.addEdge(0, 1);

        graph.addEdge(2, 7);
        graph.addEdge(2, 4);
        graph.addEdge(7, 4);
        graph.addEdge(7, 14);
        graph.addEdge(2, 8);
        graph.addEdge(8, 12);
        graph.addEdge(12, 13);
        graph.addEdge(13, 14);
        graph.addEdge(8, 15);

        graph.addEdge(3, 11);
        graph.addEdge(5, 6);
        graph.addEdge(6, 9);
        graph.addEdge(9, 11);
        graph.addEdge(5, 10);
        graph.addEdge(11, 16);
        int components = graph.connectedComponents();
        List<Integer> bfs1 = graph.breadthFirst(2);
        List<Integer> bfs2 = graph.breadthFirst(3);
        List<Integer> dfs1 = graph.depthFirst(2);
        List<Integer> dfs2 = graph.depthFirst(3);

        System.out.println("#Zusammenhangskomponenten: " + components + " (erwartet: 3)");

        System.out.println("Breitensuche:");
        System.out.println(bfs1 + " (erwartet: [2, 4, 7, 8, 14, 12, 15, 13])");
        System.out.println(bfs2 + " (erwartet: [3, 11, 9, 16, 6, 5, 10])");

        System.out.println("Tiefensuche:");
        System.out.println(dfs1 + " (erwartet: [2, 8, 15, 12, 13, 14, 7, 4])");
        System.out.println(dfs2 + " (erwartet: [3, 11, 16, 9, 6, 5, 10])");
    }
}
