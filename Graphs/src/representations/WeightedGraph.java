package representations;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Weighted undirected graph that allows max of 2 edges (u, v) and (v, u) between any pair
 * of vertices, u and v.
 * Expects incoming vertices will be represented by integers 0 - V-1;
 * Edges can have weight as double values.
 * Store edges in an adjacency list.
 * Allows loops.
 */
public class WeightedGraph {

    private ENode[] adjList;  // list of vertices and an their edges
    private int V;
    private int E;

    // constructor that takes the number of vertices in the graph
    public WeightedGraph(int V) {
        this.V = V;
        adjList = new ENode[V];
    }

    // constructor that take Scanner input from file and create full graph
    public WeightedGraph(Scanner input) {
        // TODO complete
    }

    //**************** PRIMARY METHODS **********************//

    /**
     * Adds an edge if not already present.
     * If edge exists, updates the weight of the edge.
     * @returns true if the edge was added and false if it was updated.
     */
    public boolean addEdge(int from, int to, double weight) {
        // validate that the vertices exist in the graph
        isValid(from);
        isValid(to);

        // determine if the vertices don't have edges
        ENode current = adjList[from];

        if (current == null) {

            // add first ENode to the list
            adjList[from] = new ENode(from, to, weight, current);

            // make sure not a loop
            if (from != to) {
                adjList[to] = new ENode(to, from, weight, adjList[to]);
            }
            E++;
            return true;
        }

        boolean result = false;

        // add the 'from' edge to its list
        current = getEdge(from, to);

        // if edge doesn't exist, add it
        if (current == null) {
            // just add new edge to front of list
            adjList[from] = new ENode(from, to, weight, adjList[from]);
            result = true;
            E++;
        } else {  // update the existing edge
            current.weight = weight;
        }

        // add the 'to' edge to its list
        current = getEdge(to, from);

        if (current == null) {
            adjList[to] = new ENode(to, from, weight, adjList[to]);
        } else {
            current.weight = weight;
        }

        return result;
    }

    // helper method -- returns edge node, if it exists;
    // otherwise, returns null.s
    private ENode getEdge(int from, int to) {
        isValid(from);
        isValid(to);

        ENode current = adjList[from];

        while (current != null && current.to != to) {
            current = current.next;
        }

        return current;
    }

    /**
     * Indicates whether a specified edge exist in the graph.
     * @return  true if edge exists; false otherwise
     */
    public boolean hasEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }

        ENode current = adjList[from];

        while (current != null) {
            if (current.to == to) return true;
            current = current.next;
        }

        return false;
    }

    /**
     * Validates that a vertex is in the graph
     * @param vertex
     * @return true if in the graph
     * @throws IllegalArgumentException if vertex is not in graph
     */
    public boolean isValid(int vertex) {
        if (!hasVertex(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex + " is not between 0 and " + (V-1));
        }
        return true;
    }

    /**
     * Indicates if vertex is in range of graph
     */
    public boolean hasVertex(int vertex) {
        return vertex >= 0 && vertex < V;
    }

    public boolean hasEdges(int vertex) {
        isValid(vertex);

        return !(adjList[vertex] == null);
    }

    /**
     * Gets the weight associated with an edge
     * @param from
     * @param to
     * @return weight as a double of the edge if it exists
     * @throw NoSuchElementException if edge does not exist
     */
    public double getEdgeWeight(int from, int to) {
        ENode edge = getEdge(from, to);
        if (edge == null) {
            throw new NoSuchElementException("Edge " + from + "->" + to + " does not exist in graph.");
        }
        return edge.weight;
    }

    //********** COUNTS **************/

    // getters
    public int vertexSize() {
        return V;
    }

    public int edgeSize() {
        return E;
    }



    //************ VIEWING INTERNAL DATA *******************//

    public void printGraph() {
        for (int i = 0; i < adjList.length; i++) {
            printEdges(i);
        }
    }

    public void printEdges(int i) {
        String edges = "V" + i + ": {";
        ENode current = adjList[i];  // get first edge in list
        while (current != null) {
            edges += "(" + current.from + "->" + current.to + ", " + current.weight + "), ";
            current = current.next;
        }

        if (!edges.endsWith("{")) edges = edges.substring(0, edges.length() - 2);  // remove trailing comma and space
        edges += "}";
        System.out.println(edges);
    }


    //********************** INNER CLASS *******************//

    // represents an edge
    private class ENode {
        private int from;
        private int to;
        private double weight;
        private ENode next;

        public ENode(int from, int to, double weight, ENode next) {
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
    }
}
