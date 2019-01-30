import java.util.*;

/**
 * Klasse zur Verwaltung ungerichteter Graphen <br />
 * Kanten werden über Adjazenzlisten realisiert. <br />
 * Informatik III, Universität Augsburg <br />
 * Wintersemester 2014/15
 * @author Moritz Laudahn
 * @version 2014-11-18
 */
public class Graph {
    int num_nodes;
    Object nodeData[];
    ArrayList<TreeSet<Integer>> edges; //Adjazenzliste

    public Graph(int num_nodes) {
        this.num_nodes = num_nodes;
        this.nodeData = new Object[num_nodes];
        this.edges = new ArrayList<TreeSet<Integer>>();
        for(int i = 0; i < num_nodes; ++i) {
            this.edges.add(new TreeSet<Integer>());
        }
    }

    public void addEdge(int node1, int node2) {
        this.edges.get(node1).add(node2);
        this.edges.get(node2).add(node1);
    }

    public Object getNodeData(int node) {
        return this.nodeData[node];
    }

    public boolean hasEdge(int node1, int node2) {
        return this.edges.get(node1).contains(node2) && this.edges.get(node2).contains(node1);
    }

    public void removeEdge(int node1, int node2) {
        this.edges.get(node1).remove(node2);
        this.edges.get(node2).remove(node1);
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
     * Zu erzielende Laufzeit: O(n + m) <br />
     * Erreichte Laufzeit: Jenseits von Gut und Böse
     */
    public List<Integer> myBreadthFirst(int startNode) {
        boolean[] W = new boolean[this.num_nodes];
        ArrayList<Integer> resultW = new ArrayList<Integer>();
        LinkedList<Integer> l = new LinkedList<Integer>();
        ArrayList<TreeSet<Integer>> A = new ArrayList<TreeSet<Integer>>();

        W[startNode] = true;
        l.add(startNode);
        resultW.add(startNode);
        for(int v=0;v<this.num_nodes;v++) {
            TreeSet<Integer> temp = this.edges.get(v);  //uebernehme Adjazenzliste
            A.add(v, temp);
        }

        while(!(l.isEmpty())) {
            int v = l.getFirst();
            TreeSet<Integer> currentNeighbors = A.get(v);
            if(!(currentNeighbors.isEmpty())){
                int w = currentNeighbors.first();
                if(W[w] == false) {
                    W[w] = true;
                    resultW.add(w);
                    l.addLast(w);
                }
                A.get(v).remove(w);
            } else {
                l.removeFirst();    //weiter in der Schlange
            }
        }
        return resultW;
    }

    public List<Integer> breadthFirst(int startNode) {
        boolean inQueue[] = new boolean[this.num_nodes];		//  O(n) da initialisiert
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(startNode);									// O(1)
        inQueue[startNode] = true;								// O(1)
        ArrayList<Integer> result = new ArrayList<Integer>();
        while(queue.size() > 0) {									// O(1) (O(n) mal aufgerufen)
            int node = queue.remove();								// O(1)
            TreeSet<Integer> currentEdges = this.edges.get(node);	// O(1)
            for(Integer neighbor : currentEdges) {					// O(Kantenanzahl von node)
                // => über alle Durchläufe der äußeren Schleife:O(m)
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
     * Gibt die Anzahl der Zusammenhangskomponenten zurück. <br />
     * Zu erzielende Laufzeit: O(n + m) <br />
     * Erreichte Laufzeit: ToDo
     */
    public int connectedComponents() {
        boolean[] isVisited = new boolean[this.num_nodes];
        int components = 0;

        for(int i=0;i<this.num_nodes;i++) {
            if(!isVisited[i]){
                components++;

                List<Integer> temp = depthFirst(i);
                for(Integer comp : temp) {
                    isVisited[comp] = true;
                }
            }
        }
        return components;
    }

    /**
     * Tiefensuchdurchlauf <br />
     * Liefert Liste der erreichbaren Knoten zurück. Die
     * Reihenfolge der Knoten in der Liste entspricht einem
     * möglichen Durchlauf des Graphen mittels einer
     * Tiefensuche, angefangen am Knoten
     * <code>startNode</code>. <br />
     * Zu erzielende Laufzeit: O(n + m) <br />
     * Erreichte Laufzeit: ToDo
     */
    public List<Integer> depthFirst(int startNode) {
        boolean[] inStack = new boolean[this.num_nodes];
        Stack<Integer> W = new Stack<Integer>();
        ArrayList<Integer> result = new ArrayList<Integer>();

        inStack[startNode] = true;
        W.push(startNode);

        while(!(W.isEmpty())) {
            int v = W.pop();
            TreeSet<Integer> currentNeighbors = this.edges.get(v);
            for(Integer neighbor : currentNeighbors) {
                if(!inStack[neighbor]) {
                    W.push(neighbor);
                    inStack[neighbor] = true;
                }
            }
            result.add(v);
        }
        return result;
    }
}
