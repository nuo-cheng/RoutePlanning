package graph;

/** Class Dijkstra. Implementation of Dijkstra's algorithm for finding the shortest path
 * between the source vertex and other vertices in the graph.
 *  Fill in code. You may add additional helper methods or classes.
 */

import java.awt.*;
import java.util.List;
//import java.util.PriorityQueue;
import priorityQueue.PriorityQueue;

public class Dijkstra {
    private Graph graph; // stores the graph of CityNode-s and edges connecting them
    private List<Integer> shortestPath = null; // nodes that are part of the shortest path

    /** Constructor
     *
     * @param filename name of the file that contains info about nodes and edges
     * @param graph graph
     */
    public Dijkstra(String filename, Graph graph) {
        this.graph = graph;
        graph.loadGraph(filename);
    }

    /**
     * Returns the shortest path between the origin vertex and the destination vertex.
     * The result is stored in shortestPathEdges.
     * This function is called from GUIApp, when the user clicks on two cities.
     * @param origin source node
     * @param destination destination node
     * @return the ArrayList of nodeIds (of nodes on the shortest path)
     */
    public List<Integer> computeShortestPath(CityNode origin, CityNode destination) {

        // FILL IN CODE

        // Create and initialize Dijkstra's table
        // Create and initialize a Priority Queue

        // Run Dijkstra

        // Compute the nodes on the shortest path by "backtracking" using the table

        // The result should be in an instance variable called "shortestPath" and
        // should also be returned by the method

        int cityNum = graph.numNodes();
        int[][] dijkstraTable = new int[cityNum][2];
        for (int i = 0; i < dijkstraTable.length; i++){
            for (int j = 0; j < 2; j++){
                dijkstraTable[i][0] = Integer.MAX_VALUE;
                dijkstraTable[i][1] = -1;
            }
        }
        PriorityQueue pQueue = new PriorityQueue(cityNum);
        Edge[] adjacencyList = graph.getAdjacencyList();
        int originCityId = graph.getId(origin);
        int destinationCityId = graph.getId(destination);
        dijkstraTable[originCityId][0] = 0;
        pQueue.insert(originCityId, 0);

        while (pQueue != null){
            int currentCityId = pQueue.removeMin();
            Edge currentEdge = adjacencyList[currentCityId];
            while (currentEdge != null){
                int neighbor = currentEdge.getNeighbor();
                int cost = currentEdge.getCost();
                dijkstraTable[neighbor][1] = currentCityId;
                dijkstraTable[neighbor][0] = cost + dijkstraTable[currentCityId][0];

                pQueue.insert(neighbor, cost);

                currentEdge = currentEdge.getNext();
            }
        }






        return shortestPath; // don't forget to change it
    }

    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
    public Point[][] getPath() {
        if (shortestPath == null)
            return null;
        return graph.getPath(shortestPath); // delegating this task to the Graph class
    }

    /** Set the shortestPath to null.
     *  Called when the user presses Reset button.
     */
    public void resetPath() {
        shortestPath = null;
    }

}