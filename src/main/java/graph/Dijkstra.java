package graph;

/** Class Dijkstra. Implementation of Dijkstra's algorithm for finding the shortest path
 * between the source vertex and other vertices in the graph.
 *  Fill in code. You may add additional helper methods or classes.
 */

import java.awt.*;
import java.util.ArrayList;
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
        shortestPath = new ArrayList<>();
        int cityNum = graph.numNodes();
        //initialize Dijkstra Table
        int[][] dijkstraTable = new int[cityNum][2];
        for (int i = 0; i < dijkstraTable.length; i++){
            dijkstraTable[i][0] = Integer.MAX_VALUE;
            dijkstraTable[i][1] = -1;
        }

        //create priority queue
        PriorityQueue pQueue = new PriorityQueue(cityNum);

        Edge[] adjacencyList = graph.getAdjacencyList();
        int originCityId = graph.getId(origin);
        int destinationCityId = graph.getId(destination);

        //set up starting point
        dijkstraTable[originCityId][0] = 0;
        pQueue.insert(originCityId, 0);

        while (pQueue.getActualSize() != 0){
            //get next starting point from priority queue
            int fromCityId = pQueue.removeMin();
            //loop through starting point's edges
            Edge currentEdge = adjacencyList[fromCityId];
            while (currentEdge != null){
                //get neighbor & cost
                int neighbor = currentEdge.getNeighbor();
                int cost = currentEdge.getCost();
                int previousCost = dijkstraTable[fromCityId][0];
                //if shorter then update
                if (cost + previousCost < dijkstraTable[neighbor][0]){
                    dijkstraTable[neighbor][0] = cost + dijkstraTable[fromCityId][0];
                    dijkstraTable[neighbor][1] = fromCityId;
                    // if already add in the queue, reduce the priority
                    if (pQueue.positions[neighbor] != -1 && pQueue.positions[neighbor] != 0){
                        pQueue.reduceKey(neighbor, dijkstraTable[neighbor][0]);
                    }
                }

                pQueue.insert(neighbor, dijkstraTable[neighbor][0]);

                currentEdge = currentEdge.getNext();
            }
        }

        //add path result from back to front
        int pathToAdd = destinationCityId;
        int cost = dijkstraTable[destinationCityId][0];
        System.out.println(cost);
        shortestPath.add(pathToAdd);
        while (pathToAdd != originCityId){
            pathToAdd = dijkstraTable[pathToAdd][1];
            shortestPath.add(0, pathToAdd);
        }
//        shortestPath.add(0, originCityId);

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

    public static void main(String[] args) {
        Graph graph = new Graph();
        Dijkstra dijkstra = new Dijkstra("USA.txt", graph);
        CityNode[] cityNodes = graph.getNodesArray();
        CityNode from = cityNodes[13];
        CityNode to = cityNodes[9];
        graph.printCityNodes();
        graph.printAdjacencyList();
        List<Integer> shortestPath = dijkstra.computeShortestPath(from, to);
        System.out.println(shortestPath);
    }

}