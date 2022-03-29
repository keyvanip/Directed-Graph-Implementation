package edu.union.adt.graph;

/**
 * A graph that establishes connections (edges) between objects of
 * (parameterized) type V (vertices).  The edges are directed.  An
 * undirected edge between u and v can be simulated by two edges: (u,
 * v) and (v, u).
 *
 * The API is based on one from
 *     http://introcs.cs.princeton.edu/java/home/
 *
 * Some method names have been changed, and the Graph type is
 * parameterized with a vertex type V instead of assuming String
 * vertices.
 *
 * @author Aaron G. Cass, Parsa Keyvani
 * @version 1
 */
public interface Graph<V> {
    int numVertices();

    int numEdges();

    int degree(V vertex);

    void addEdge(V from, V to);

    void addVertex(V vertex);

    Iterable<V> getVertices();

    Iterable<V> adjacentTo(V from);

    boolean contains(V vertex);

    boolean hasEdge (V from, V to);

    String toString();

    @Override
    boolean equals(Object g2);

    boolean isEmpty();

    void removeVertex(V toRemove);

    void removeEdge(V from, V to);


    boolean hasPath(V from, V to);

    int pathLength(V from, V to);

    Iterable<V> getPath(V from, V to);



}