
import java.util.*;

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
     * Breitensuchdurchlauf <br />
     * Liefert Liste der erreichbaren Knoten zurück. Die
     * Reihenfolge der Knoten in der Liste entspricht einem
     * möglichen Durchlauf des Graphen mittels einer
     * Breitensuche, angefangen am Knoten
     * <code>startNode</code>. <br />
     * Zu erzielende Laufzeit: O(n' + m') <br />
     * Erreichte Laufzeit: O(n' + m')
     */
    public List<Integer> breadthFirst(int startNode) {
        boolean inQueue[] = new boolean[this.num_nodes];		//  O(1) (Beweis siehe zukünftiges Übungsblatt)
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(startNode);									// O(1)
        inQueue[startNode] = true;								// O(1)
        ArrayList<Integer> result = new ArrayList<Integer>();
        while(queue.size() > 0) {									// n' mal aufgerufen
            int node = queue.remove();								// O(1)
            Set<Integer> currentEdges = this.weightedEdges.get(node).keySet();	// O(1)
            for(Integer neighbor : currentEdges) {					// O(Kantenanzahl von node)
                // => über alle Durchläufe der äußeren Schleife: 2 * m'
                if(!inQueue[neighbor]) {							// O(1)
                    queue.add(neighbor);							// O(1)
                    inQueue[neighbor] = true;						// O(1)
                }
            }

            result.add(node);										// O(1)
        }

        return result;
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
        int nodesToCheck = this.breadthFirst(destination).size();
        boolean[] OK = new boolean[this.num_nodes];
        double dd[] = new double[this.num_nodes];
        int pre[] = new int[this.num_nodes];

        for(int i=0;i<this.num_nodes;i++) {
            OK[i] = false;
            dd[i] = Double.MAX_VALUE;
            pre[i] = i;
        }
        OK[destination] = true;
        dd[destination] = 0.0;

        int lastInserted = destination;
        int nodesInOK = 1;
        while(nodesInOK < nodesToCheck) {
            double distanceOfLast = dd[lastInserted];                               // distance (zur destination)
            HashMap<Integer, Double> lastInsertedAdj = this.weightedEdges.get(lastInserted);

            for(Integer neighbor : lastInsertedAdj.keySet()) {
                if(!OK[neighbor]) {
                    double distanceFromLast = lastInsertedAdj.get(neighbor);        // =d(w,v)
                    if(distanceOfLast + distanceFromLast < dd[neighbor]) {          // dd[w] + d(w,v) < dd[v]
                        dd[neighbor] = distanceOfLast + distanceFromLast;
                        pre[neighbor] = lastInserted;
                    }
                }
            }

            double minD = Double.MAX_VALUE;                                         // Suche nächstes w mit minimaler dist
            int minI = -1;
            for(int i=0;i<this.num_nodes;i++) {
                if(!OK[i] && (dd[i] < minD)) {
                    minD = dd[i];
                    minI = i;
                }
            }

            OK[minI] = true;
            lastInserted = minI;
            nodesInOK++;
        }
        return pre;
    }

    /**
     * Blatt 10 Aufgabe 4 b)
     */
    public int[] leastLeakageFrom(int source) {
        int n = this.num_nodes;
        WeightedGraph temp = new WeightedGraph(n);

        for(int i=0;i<n;i++) {
            HashMap<Integer, Double> adj = this.weightedEdges.get(i);
            for(Integer neighbor : adj.keySet()) {
                if(neighbor>i) {
                    double weight = adj.get(neighbor);
                    weight = -Math.log(weight);
                    temp.addEdge(i, neighbor , weight);
                }
            }

        }
        return temp.roadMapTo(source);
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
