package priorityQueue;

import java.util.Arrays;

/** A priority queue implemented using a min heap.
 *  You may not use any Java built-in classes, you should implement
 *  PriorityQueue yourself to get full credit for this part of the project.
 *  You may use/modify the MinHeap code posted by the instructor under codeExamples, as long as you understand it. */
public class PriorityQueue {
    private PriorityQueueElement[] minHeap;
    private int maxSize;
    private int actualSize;
    public int[] positions;

    public PriorityQueue(int maxSize){
        this.maxSize = maxSize;
        minHeap = new PriorityQueueElement[maxSize];
        actualSize = 0;
        positions = new int[maxSize];
        Arrays.fill(positions, -1);

    }
    /** Insert a new element (nodeId, priority) into the heap.
     *  For this project, the priority is the current "distance"
     *  for this nodeId in Dikstra's algorithm. */
    public void insert(int nodeId, int priority) {
        // FILL IN CODE
        //insert if value is -1, means that we are not visited yet
        if (positions[nodeId] == -1 && positions[nodeId] != 0){
            //add new element to index [actualSize]
            actualSize++;
            PriorityQueueElement elem = new PriorityQueueElement(nodeId, priority);
            minHeap[actualSize] = elem;

            //swap to the right place
            int current = actualSize;
            while (minHeap[parent(current)] != null && minHeap[current].compareTo(minHeap[parent(current)]) < 0) {
                swap(current, parent(current));
                current = parent(current);
            }
            //update position
            positions[nodeId] = current;
        }
    }

    /**
     * Remove the element with the minimum priority
     * from the min heap and return its nodeId.
     * @return nodeId of the element with the smallest priority
     */
    public int removeMin() {
        // FILL IN CODE
        //swap min with the last element
        swap(1, actualSize);
        actualSize--;
        //pushdown to the right position
        if (actualSize != 0)
            pushDown(1);

        //set remove element's position to 0, mean that already dequeue
        positions[minHeap[actualSize + 1].getId()] = 0;

        return minHeap[actualSize + 1].getId(); // don't forget to change it
    }

    /**
     * Reduce the priority of the element with the given nodeId to newPriority.
     * You may assume newPriority is less or equal to the current priority for this node.
     * @param nodeId id of the node
     * @param newPriority new value of priority
     */
    public void reduceKey(int nodeId, int newPriority) {
        int oldPosition = positions[nodeId];
        //if still in the queue
        if (oldPosition > 0){
            //get element
            PriorityQueueElement target = minHeap[oldPosition];
            //set new value
            target.setPriority(newPriority);
            //pop up to the right position
            int newPosition = popUp(oldPosition);
            //update position
//            positions[nodeId] = newPosition;
        }
    }

    public int popUp(int position){
        while (minHeap[parent(position)] != null && minHeap[position].compareTo(minHeap[parent(position)]) < 0){
            swap(position, parent(position));
            position = parent(position);
        }
        return position;
    }


    private int parent(int pos) {
        return pos / 2;
    }


    private void swap(int pos1, int pos2) {
        PriorityQueueElement tmp;
        tmp = minHeap[pos1];


        int pos1Id = minHeap[pos1].getId();
        int pos2Id = minHeap[pos2].getId();

        minHeap[pos1] = minHeap[pos2];
        minHeap[pos2] = tmp;

        positions[pos1Id] = pos2;
        positions[pos2Id] = pos1;

    }


    private boolean isLeaf(int pos) {
        return ((pos > actualSize / 2) && (pos <= actualSize));
    }


    private int leftChild(int pos) {
        return 2 * pos;
    }

    private int rightChild(int pos) {
        return 2 * pos + 1;
    }


    private void pushDown(int position) {
        int smallestchild;
        while (!isLeaf(position)) {
            smallestchild = leftChild(position);
            if ((smallestchild < actualSize) && (minHeap[smallestchild].compareTo(minHeap[smallestchild + 1]) > 0))
                smallestchild = smallestchild + 1;

            // the value of the smallest child is less than value of current,
            // the heap is already valid
//            System.out.println("position: " + minHeap[position].getId());
//            System.out.println("smallest child: " + minHeap[smallestchild].getId());
            if (minHeap[position].compareTo(minHeap[smallestchild]) <= 0){
                return;
            }

            swap(position, smallestchild);
            position = smallestchild;
        }
    }

    public int getActualSize() {
        return actualSize;
    }

    public static void main(String[] args) {
        int[] arr = new int[5];
        Arrays.fill(arr, -1);
        for (int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }

    }

}

