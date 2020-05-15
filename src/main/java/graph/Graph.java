package graph;


/** A class that represents a graph where nodes are cities (of type CityNode).
 * The cost of each edge connecting two cities is the distance between the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a PriorityQueue from scratch to get full credit.
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    public final int EPS_DIST = 5;

    private int numNodes;     // total number of nodes
    private int numEdges; // total number of edges
    private CityNode[] nodes; // array of nodes of the graph
    private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
    private Map<String, Integer> labelsToIndices; // a HashMap that maps each city to the corresponding node id
    private int cityNodeIndexToAdd = 0; //an int to remember the index when adding nodes to CityNode[] nodes

    /**
     * Read graph info from the given file, and create nodes and edges of
     * the graph.
     *
     * @param filename name of the file that has nodes and edges
     */
    public void loadGraph(String filename) {
        // FILL IN CODE
        String line = "";
        String splitBy = " ";
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            br.readLine();
            numNodes = Integer.parseInt(br.readLine());

            //load city names
            while (!(line = br.readLine()).equals( "ARCS")){
//                System.out.println(line);
                String[] cityNodesInfo = line.split(splitBy);
//                System.out.println(cityNodesInfo[0]);
//                System.out.println(cityNodesInfo[1]);
//                System.out.println(cityNodesInfo[2]);
                String cityName = cityNodesInfo[0];
                double x = Double.parseDouble(cityNodesInfo[1]);
                double y = Double.parseDouble(cityNodesInfo[2]);
                CityNode newNode = new CityNode(cityName, x, y);
                addNode(newNode);

            }

            //load edges
            while((line = br.readLine()) != null){
                String[] edgesInfo = line.split(splitBy);
                String cityNameFrom = edgesInfo[0];
                String cityNameTo = edgesInfo[1];
                int cost = Integer.parseInt(edgesInfo[2]);
                int cityFromId = labelsToIndices.get(cityNameFrom);
                int cityToId = labelsToIndices.get(cityNameTo);

                //add to edge and back edge
                Edge ToEdge = new Edge(cityToId, cost);
                addEdge(cityFromId, ToEdge);
                Edge backEdge = new Edge(cityFromId, cost);
                addEdge(cityToId, backEdge);

            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Add a node to the array of nodes.
     * Increment numNodes variable.
     * Called from loadGraph.
     *
     * @param node a CityNode to add to the graph
     */
    public void addNode(CityNode node) {
        // FILL IN CODE
        if (nodes == null){
            nodes = new CityNode[numNodes];
        }
        nodes[cityNodeIndexToAdd] = node;
        if (labelsToIndices == null){
            labelsToIndices = new HashMap<>();
        }
        labelsToIndices.put(node.getCity(), cityNodeIndexToAdd);
        cityNodeIndexToAdd += 1;
    }

    /**
     * Return the number of nodes in the graph
     * @return number of nodes
     */
    public int numNodes() {
        return numNodes;
    }

    /**
     * Adds the edge to the linked list for the given nodeId
     * Called from loadGraph.
     *
     * @param nodeId id of the node
     * @param edge edge to add
     */
    public void addEdge(int nodeId, Edge edge) {
        // FILL IN CODE
        if (adjacencyList == null){
            adjacencyList = new Edge[numNodes];
        }
        if (adjacencyList[nodeId] == null){
            adjacencyList[nodeId] = edge;
        } else {
            Edge current = adjacencyList[nodeId];
            while (current != null && current.getNext() != null){
                if (current.getNeighbor() == edge.getNeighbor() && current.getCost() == edge.getCost()){
                    return;
                }
                current = current.getNext();
            }
            //double check not repeating, because the check in the while loop will skip the last node
            if (current.getNeighbor() == edge.getNeighbor() && current.getCost() == edge.getCost()){
                return;
            }
            current.setNext(edge);
        }
        numEdges += 1;
    }

    /**
     * Returns an integer id of the given city node
     * @param city node of the graph
     * @return its integer id
     */
    public int getId(CityNode city) {
        return labelsToIndices.get(city.getCity());
    }

    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     * This info can be obtained from the adjacency list
     */
    public Point[][] getEdges() {
        int i = 0;
        Point[][] edges2D = new Point[numEdges][2];
//        System.out.println(numEdges);
        // FILL IN CODE
        //loop each index through adjacency list
        for (int j = 0; j < numNodes; j++){
            Edge current = adjacencyList[j];
            //loop through each edge
            while (current != null){
                //get source
                int sourceVertexId = j;
                Point sourceVertex = nodes[sourceVertexId].getLocation();
                //get target
                int destnVertexId = current.getNeighbor();
                Point destnVertex = nodes[destnVertexId].getLocation();
                //set point value
                edges2D[i][0] = sourceVertex;
                edges2D[i][1] = destnVertex;

                current = current.getNext();
                i += 1;
            }
        }

        return edges2D;
    }

    /**
     * Get the nodes of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getNodes() {
        if (this.nodes == null) {
            // System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
        Point[] pnodes = new Point[this.nodes.length];
        // FILL IN CODE
        for (int i = 0; i < nodes.length; i++){
            Point p = nodes[i].getLocation();
            pnodes[i] = p;
        }

        return pnodes;
    }

    /**
     * Used in GUIApp to display the names of the airports.
     * @return the list that contains the names of cities (that correspond
     * to the nodes of the graph)
     */
    public String[] getCities() {
        if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
        String[] labels = new String[nodes.length];
        // FILL IN CODE
        for (int i = 0; i < nodes.length; i++){
            String cityName = nodes[i].getCity();
            labels[i] = cityName;
        }

        return labels;

    }

    /** Take a list of node ids on the path and return an array where each
     * element contains two points (an edge between two consecutive nodes)
     * @param pathOfNodes A list of node ids on the path
     * @return array where each element is an array of 2 points
     */
    public Point[][] getPath(List<Integer> pathOfNodes) {
        int i = 0;
        Point[][] edges2D = new Point[pathOfNodes.size()-1][2];
        // Each "edge" is an array of size two (one Point is origin, one Point is destination)
        // FILL IN CODE
        for (int j = 0; j < pathOfNodes.size() - 1; j++){
            //get id -> get node -> get position
            int fromNodeId = pathOfNodes.get(i);
            CityNode fromNode = nodes[fromNodeId];
            Point fromP = fromNode.getLocation();
            //next id -> node -> position
            int toNodeId = pathOfNodes.get(i + 1);
            CityNode toNode = nodes[toNodeId];
            Point toP = toNode.getLocation();

            //set result 2d array
            edges2D[i][0] = fromP;
            edges2D[i][1] = toP;
            i += 1;
        }

        return edges2D;
    }

    /**
     * Return the CityNode for the given nodeId
     * @param nodeId id of the node
     * @return CityNode
     */
    public CityNode getNode(int nodeId) {
        return nodes[nodeId];
    }

    /**
     * Take the location of the mouse click as a parameter, and return the node
     * of the graph at this location. Needed in GUIApp class. No need to modify.
     * @param loc the location of the mouse click
     * @return reference to the corresponding CityNode
     */
    public CityNode getNode(Point loc) {
        if (nodes == null) {
            System.out.println("No node at this location. ");
            return null;
        }
        for (CityNode v : nodes) {
            Point p = v.getLocation();
            if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
                return v;
        }
        return null;
    }

    public void printCityNodes(){
        for (int i = 0; i < numNodes; i++){
            CityNode current = nodes[i];
            System.out.println(i + ": " + current.getCity());
        }
    }

    public void printAdjacencyList(){
        String result = "";
        for (int i = 0; i < adjacencyList.length; i++){
            result += i + ": ";
            Edge current = adjacencyList[i];
            while (current != null){
                result += current.toString() + "  ";
                current = current.getNext();
            }
            result += '\n';

        }
        System.out.println(result);
    }

    public Edge[] getAdjacencyList() {
        return adjacencyList;
    }

    public CityNode[] getNodesArray(){
        return nodes;
    }



    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.loadGraph("USA.txt");
        graph.printCityNodes();
        graph.printAdjacencyList();
    }
}