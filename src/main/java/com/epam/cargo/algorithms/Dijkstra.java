package com.epam.cargo.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Special class of calculating smaller rout in the graph between two vertex, using Dijkstra's algorithm.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class Dijkstra {

    private double[][] graph;

    private int source;

    private double[] path;
    private boolean[] used;
    private int[] parents;

    private boolean done = false;

    /**
     * Sets graph object for further work.<br>
     * Checks data validity.
     * @param graph adjacency matrix
     * @param source vertex from which we are about to calculate
     * */
    public Dijkstra(double[][] graph, int source){
        requireValidMatrix(graph);
        requirePresenceInGraph(graph, source);

        this.graph = graph;
        this.source = source;

        initializePath();
        initializeUsed();
        initializeParents();
    }

    /**
     * Calculate a minimum distance between source node all other nodes.
     * @return calculated smallest distance between source and given destination,
     * if no way exists returns Double.POSITIVE_INFINITIVE
     * @throws IllegalArgumentException when destination node doesn't belong to the graph
     * or it's the same with source node
     * */
    public double calculateMinDistance(int destination){
        requirePresenceInGraph(graph, destination);
        requireAbsenceSelfLoop(destination);
        if (!done) {

            for (int i = 0; i < graph.length; i++) {
                int v = -1;
                //finding the vertex to which the lightest edge now leads
                for (int j = 0; j < path.length; j++) {
                    if (!used[j] && (v == -1 || path[j] < path[v])) {
                        v = j;
                    }
                }
                //relaxation
                for (int j = 0; j < graph[v].length; j++) {
                    double weight = graph[v][j];    //шлях від ребра v до ребра j

                    //if edge is present
                    if (weight != Double.POSITIVE_INFINITY) {
                        weight += path[v];
                        if (weight < path[j]) {
                            path[j] = weight;
                            parents[j] = v;
                        }
                    }
                }

                used[v] = true;
            }
        }
        done = true;
        return path[destination] != Double.POSITIVE_INFINITY ? path[destination] : Double.POSITIVE_INFINITY;
    }

    /**
     * Build smallerRoute from source node to destination,
     * if calculateMinDistance hasn't been called, it will be called
     * @return an List with integer ordinary nodes
     * like a shortest route to go between source and destination
     * */
    public List<Integer> buildShortestRoute(int destination){
        requirePresenceInGraph(graph, destination);
        requireAbsenceSelfLoop(destination);
        if(!done){
            calculateMinDistance(destination);
        }
        return buildRoute(source, destination, parents);
    }

    private void requireAbsenceSelfLoop(int destination) {
        if (destination == source){
            throw new IllegalArgumentException("Destination node cannot be the same with source");
        }
    }

    private static List<Integer> buildRoute(int from, int to, int[] parents){
        int v = to;
        List<Integer> route = new ArrayList<>();
        for (int i = 0; v != from && i < parents.length; i++) {
            route.add(v);
            v = parents[v];
        }
        if (v != from){
            throw new IllegalStateException("Couldn't build any route between edges!");
        }
        route.add(v);
        Collections.reverse(route);
        return route;
    }


    private void initializeParents() {
        this.parents = new int[totalNodes()];
    }

    private void initializeUsed() {
        this.used = new boolean[totalNodes()];
        Arrays.fill(used, false);
    }

    private void initializePath() {
        this.path = new double[totalNodes()];
        Arrays.fill(path, Double.POSITIVE_INFINITY);
        this.path[source] = 0.0;
    }


    private static void requirePresenceInGraph(double[][] graph, int node) {
        if (node >= graph.length){
            throw new IllegalArgumentException("Given node doesn't belong to the graph");
        }
    }

    private static void requireValidMatrix(double[][] matrix){
        requireSquareMatrix(matrix);
        requireOnlyPositiveValues(matrix);
    }

    private static void requireSquareMatrix(double[][] matrix){
        for (double[] row : matrix) {
            if (row.length != matrix.length) {
                throw new IllegalArgumentException("Given matrix must be only square");
            }
        }
    }

    private static void requireOnlyPositiveValues(double[][] matrix){
        for (double[] row : matrix) {
            for (double cell : row) {
                if (cell < 0.0){
                    throw new IllegalArgumentException("Dijkstra algorithm works only with positive values");
                }
            }
        }
    }

    private int totalNodes(){
        return graph.length;
    }
}
