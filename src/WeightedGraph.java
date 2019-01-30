
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Klasse zur Verwaltung ungerichteter Graphen mit gewichteten Kanten. <br />
 * Gewichtete Kanten werden über Adjazenzlisten realisiert. <br />
 * Informatik III, Universität Augsburg <br />
 * Wintersemester 2014/15
 * @author Moritz Laudahn
 * @version 2014-11-25
 */
public class WeightedGraph {
    int num_nodes;
    Object nodeData[];
    ArrayList<HashMap<Integer, Double>> weightedEdges;

    public WeightedGraph(int num_nodes) {
        this.num_nodes = num_nodes;
        this.nodeData = new Object[num_nodes];
        this.weightedEdges = new ArrayList<HashMap<Integer, Double>>();
        for(int i = 0; i < num_nodes; ++i) {
            this.weightedEdges.add(new HashMap<Integer, Double>());
        }
    }

    public void addEdge(int node1, int node2, double weight) {
        this.weightedEdges.get(node1).put(node2, weight);
        this.weightedEdges.get(node2).put(node1, weight);
    }

    public ArrayList<ArrayList<Integer>> copyEdges() {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < this.weightedEdges.size(); ++i) {
            result.add(new ArrayList<Integer>(this.weightedEdges.get(i).keySet()));
        }

        return result;
    }

    public ArrayList<HashMap<Integer, Double>> copyWeightedEdges() {
        ArrayList<HashMap<Integer, Double>> result = new ArrayList<HashMap<Integer, Double>>();
        for(int i = 0; i < this.weightedEdges.size(); ++i) {
            result.add(new HashMap<Integer, Double>(this.weightedEdges.get(i)));
        }

        return result;
    }

    public Object getNodeData(int node) {
        return this.nodeData[node];
    }

    public double getWeight(int node1, int node2) {
        return this.weightedEdges.get(node1).get(node2);
    }

    public boolean hasEdge(int node1, int node2) {
        return this.weightedEdges.get(node1).containsKey(node2) && this.weightedEdges.get(node2).containsKey(node1);
    }

    public int numNodes() {
        return this.num_nodes;
    }

    public void removeEdge(int node1, int node2) {
        this.weightedEdges.get(node1).remove(node2);
        this.weightedEdges.get(node2).remove(node1);
    }

    public void setNodeData(int node, Object data) {
        this.nodeData[node] = data;
    }



    /**
     * Diese Methode fast Kantengewichte als Weglängen
     * zwischen zwei Knoten auf. Sie können davon ausgehen,
     * dass alle Kantengewichte im Intervall
     * [0.0; Double.MAX_VALUE] liegen. <br />
     * Sei w,x,...,destination ein kürzetester Weg von einem
     * Knoten w nach destination:
     * <code>roadMapTo(destination)[w] == x</code>, falls x
     * in der gleichen Zusammenhangskomponente wie destination
     * liegt <br />
     * <code>roadMapTo(destination)[w] == w</code>, andernfalls <br />
     * Erreichte Laufzeit: ToDo
     */
    public int[] roadMapTo(int destination) {
        // ToDo
        return null;
    }

    /**
     * Blatt 10 Aufgabe 4 b)
     */
    public int[] leastLeakageFrom(int source) {
        // ToDo
        return null;
    }

    /**
     * Erstellt einen <code>String</code>, welcher eine
     * dot-Repräsenation des Graphen darstellt. <br />
     * <br />
     * Siehe
     * https://de.wikipedia.org/wiki/DOT_%28GraphViz%29
     * und
     * https://en.wikipedia.org/wiki/DOT_%28graph_description_language%29
     * für weitere Informationen.
     */
    @Override
    public String toString() {
        // Betriebssystemspezifischer Zeilentrenner:
        String nl = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        sb.append("graph WeightedGraph {").append(nl);
        for (int i = 0; i < this.num_nodes; ++i) {
            sb.append("\t").append(i).append(";").append(nl);
        }

        for (int i = 0; i < this.weightedEdges.size(); ++i) {
            HashMap<Integer, Double> adjList = this.weightedEdges.get(i);
            for (int j : adjList.keySet()) {
                if (i < j) {
                    sb.append("\t").append(i).append(" -- ").append(j);
                    sb.append(" [label=\"").append(adjList.get(j)).append("\"];").append(nl);
                }
            }
        }

        sb.append("}").append(nl);
        return sb.toString();
    }
}
