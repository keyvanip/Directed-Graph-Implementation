package edu.union.adt.graph;

public class GraphFactory {
    static public <V> Graph<V> createGraph() {
        return new PKHashGraph<V>();
    }
}