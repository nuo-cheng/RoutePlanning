package graph;

/** Edge class represents a link in the linked list of edges for a vertex.
 *  Each Edge stores the id of the "neighbor" (the vertex where this edge is going =
 *  "destination" vertex), the cost and the reference to the next Edge.
 */
class Edge {
    private int neighbor; // id of the neighbor ("destination" vertex of this edge)
    private int cost; // cost of this edge
    private Edge next; // reference to the next "edge" in the linked list

    // FILL IN CODE: constructor, getters, setters
    public Edge(int neighbor, int cost){
        this.neighbor = neighbor;
        this.cost = cost;
    }

    public int getNeighbor() {
        return neighbor;
    }

    public int getCost() {
        return cost;
    }

    public Edge getNext() {
        return next;
    }

    public void setNeighbor(int neighbor) {
        this.neighbor = neighbor;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNext(Edge next) {
        this.next = next;
    }

    public String toString(){
        return neighbor + "(" + cost + ")";
    }

}