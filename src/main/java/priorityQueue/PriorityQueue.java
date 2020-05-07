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
        actualSize++;
        PriorityQueueElement elem = new PriorityQueueElement(nodeId, priority);
        minHeap[actualSize] = elem;

        int current = actualSize;
        while (minHeap[current].compareTo(minHeap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
        positions[nodeId] = current;

    }

    /**
     * Remove the element with the minimum priority
     * from the min heap and return its nodeId.
     * @return nodeId of the element with the smallest priority
     */
    public int removeMin() {
        // FILL IN CODE

        swap(1, actualSize);
        actualSize--;
        if (actualSize != 0)
            pushDown(1);

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


    }


    private int parent(int pos) {
        return pos / 2;
    }


    private void swap(int pos1, int pos2) {
        PriorityQueueElement tmp;
        tmp = minHeap[pos1];
        minHeap[pos1] = minHeap[pos2];
        minHeap[pos2] = tmp;
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
            if (minHeap[position].compareTo(minHeap[smallestchild]) <= 0)
                return;
            swap(position, smallestchild);
            position = smallestchild;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[5];
        Arrays.fill(arr, -1);
        for (int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }

    }

}

