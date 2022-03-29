package edu.union.adt.graph;
import java.util.*;

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
public class PKHashGraph<V> implements Graph<V> {
    private Map<V, List<V>> graph;
    private int shortestPathLength;
    private Object predecessorVertices[];
    private ArrayList shortestPath=null;


    /**
     * Create an empty graph.
     */
    public PKHashGraph(){
        this.graph = new HashMap<>();
    }

    /**
     * @return the number of vertices in the graph.
     */
    @Override
    public int numVertices(){
        int num = graph.keySet().size();
        return num;
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
    public int numEdges(){
        int totalEdges =0;
        for (V vertex: graph.keySet()){
            totalEdges+= degree(vertex);
        }
        return totalEdges;
    }

    /**
     * Gets the number of vertices connected by edges from a given
     * vertex.  If the given vertex is not in the graph, throws a
     * RuntimeException.
     *
     * @param vertex the vertex whose degree we want.
     * @return the degree of vertex 'vertex'
     */
    @Override
    public int degree(V vertex){
        if (!contains(vertex))
            throw new RuntimeException();
        int degrees= graph.get(vertex).size();
        return degrees;
    }

    /**
     * Adds a directed edge between two vertices.  If there is already an edge
     * between the given vertices, does nothing.  If either (or both)
     * of the given vertices does not exist, it is added to the
     * graph before the edge is created between them.
     *
     * @param from the source vertex for the added edge
     * @param to the destination vertex for the added edge
     */
    @Override
    public void addEdge(V from, V to) {
        if (!contains(from))
            addVertex(from);
        if (!contains(to))
            addVertex(to);
        if (!hasEdge(from,to))
            graph.get(from).add(to);
    }

    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    @Override
    public void addVertex(V vertex) {
        if (!contains(vertex)) {
            graph.put(vertex, new LinkedList<V>());
        }
    }

    /**
     * @return an iterable collection for the set of vertices of
     * the graph.
     */
    @Override
    public Iterable<V> getVertices() {
        return new ArrayList<V>(graph.keySet());
    }

    /**
     * Gets the vertices adjacent to a given vertex.  A vertex y is
     * "adjacent to" vertex x if there is an edge (x, y) in the graph.
     * Because edges are directed, if (x, y) is an edge but (y, x) is
     * not an edge, we would say that y is adjacent to x but that x is
     * NOT adjacent to y.
     *
     * @param from the source vertex
     * @return an iterable collection for the set of vertices that are
     * the destinations of edges for which 'from' is the source
     * vertex.  If 'from' is not a vertex in the graph, returns an
     * empty iterator.
     */
    @Override
    public Iterable<V> adjacentTo(V from){
        ArrayList<V> a;
        if (!contains(from)){
            a= new ArrayList<>();
        }
        else{
            a= new ArrayList<V>(graph.get(from));
        }
        return a;


    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
    @Override
    public boolean contains(V vertex){
        if (graph.containsKey(vertex))
            return true;
        else
            return false;
    }

    /**
     * Tells whether an edge exists in the graph.
     *
     * @param from the source vertex
     * @param to the destination vertex
     *
     * @return true iff there is an edge from the source vertex to the
     * destination vertex in the graph.  If either of the given
     * vertices are not vertices in the graph, then there is no edge
     * between them.
     */
    @Override
    public boolean hasEdge(V from, V to){
        if (graph.get(from).contains(to))
            return true;
        else
            return false;
    }

    /**
     * Gives a string representation of the graph.  The representation
     * is a series of lines, one for each vertex in the graph.  On
     * each line, the vertex is shown followed by ": " and then
     * followed by a list of the vertices adjacent to that vertex.  In
     * this list of vertices, the vertices are separated by ", ".  For
     * example, for a graph with String vertices "A", "B", and "C", we
     * might have the following string representation:
     *
     * <PRE>
     * A: A, B
     * B:
     * C: A, B
     * </PRE>
     *
     * This representation would indicate that the following edges are
     * in the graph: (A, A), (A, B), (C, A), (C, B) and that B has no
     * adjacent vertices.
     *
     * Note: there are no extraneous spaces in the output.  So, if we
     * replace each space with '*', the above representation would be:
     *
     * <PRE>
     * A:*A,*B
     * B:
     * C:*A,*B
     * </PRE>
     *
     * @return the string representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        for (V vertex: graph.keySet()){
            build.append(vertex.toString() + ": ");
            for (V adj: graph.get(vertex))
                build.append(adj.toString() + ", ");
            build.append("\n");
        }
        return build.toString();
    }


    private <V> ArrayList<V> makeCollection(Iterable<V> iter) {
        ArrayList<V> list = new ArrayList<V>();
        for (V item : iter) {
            list.add(item);
        }
        return list;
    }



    /**
     * Helper method that Compares two graphs with different graph types and checks if they are equal.
     * @param otherGraph of type Object to be compared with the graph of type <V>.
     * @return true iff the two graphs have the same elements and are equal no matter their graph type.
     */
    private boolean equalityCheckHelper(Object otherGraph) {
        PKHashGraph<V> g2 = (PKHashGraph<V>) otherGraph;
        ArrayList<V> allVerticesList1 = (ArrayList<V>) getVertices();
        ArrayList<V> AllVerticesList2 = (ArrayList<V>) g2.getVertices();
        if (allVerticesList1.size()!= AllVerticesList2.size())
            return false;
        for (V x : allVerticesList1) {
            for (V y: AllVerticesList2) {
                if (x.equals(y)) {
                    if (degree((V) x) != g2.degree((V) y))
                        return false;
                    ArrayList adjList1 = (ArrayList) adjacentTo((V) x);
                    ArrayList adjList2 = (ArrayList) g2.adjacentTo((V) y);
                    HashSet<V> AdjHashSet1 = new HashSet<V>();
                    HashSet<V> AdjHashSet2 = new HashSet<V>();
                    AdjHashSet1.addAll( adjList1);
                    AdjHashSet2.addAll(adjList2);
                    if (AdjHashSet1.equals(AdjHashSet2)){
                        break;}
                    else
                        return false;
                }
            }
        }
        return true;
    }





    /**
     * Tells whether a graph is equal to another graph.
     * @param otherGraph second graph to be compared.
     * @return true iff both graphs are equal. Otherwise, return false.
     */
    @Override
    public boolean equals(Object otherGraph) {
        if (otherGraph == null)
            return false;
        if (otherGraph == this)
            return true;
        if (!(otherGraph.getClass().equals(this.getClass())))
            return false;
        if (equalityCheckHelper(otherGraph))
            return true;
        else
            return false;
    }


    /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    @Override
    public boolean isEmpty(){
        if (this.numEdges()==0 && this.numVertices()==0)
            return true;
        else
            return false;
    }


    /**
     * Removes a vertex from the graph.  Also removes any edges
     * connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    @Override
    public void removeVertex(V toRemove){
        if (contains(toRemove))
            graph.remove(toRemove);
    }


    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    @Override
    public void removeEdge(V from, V to){
        if (contains(from) && contains(to))
            graph.get(from).remove(to);
    }



    /**
     * Tells whether there is a path connecting two given vertices.  A
     * path exists from vertex A to vertex B iff A and B are in the
     * graph and there exists a sequence x_1, x_2, ..., x_n where:
     *
     * <ul>
     * <li>x_1 = A
     * <li>x_n = B
     * <li>for all i from 1 to n-1, (x_i, x_{i+1}) is an edge in the graph.
     * </ul>
     *
     * It therefore follows that, if vertex A is in the graph, there
     * is a path from A to A.
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true iff there is a path from 'from' to 'to' in the graph.
     */
    @Override
    public boolean hasPath(V from, V to) {
        if (contains(from) && contains(to)) {
            if (breadthFirstSearch(from, to, numVertices()))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    /**
     * private method to get the shortest path and set the length of the shortest path.
     * @param from starting vertex
     * @param to destination vertex
     * @param numV number of vertices in the graph
     * @return true iff there is a path in the graph.
     */
    private boolean breadthFirstSearch(V from, V to, int numV) {
        if (from ==to){
            shortestPathLength=0;
            return true;}
        LinkedList<V> queue = new LinkedList<V>();
        boolean visited[] = new boolean[numV];
        predecessorVertices = new Object[numV];
        int distance[] = new int[numV];
        Set<V> keys = graph.keySet();
        List<V> listKeys = new ArrayList<V>(keys);
        visited[listKeys.indexOf(from)] = true;
        distance[listKeys.indexOf(from)] = 0;
        queue.add(from);

        while (!queue.isEmpty()) {
            V current = queue.pop();
            for (V neighbor : adjacentTo(current)) {
                if (visited[listKeys.indexOf(neighbor)] == false) {
                    visited[listKeys.indexOf(neighbor)] = true;
                    distance[listKeys.indexOf(neighbor)] = distance[listKeys.indexOf(current)] + 1;
                    predecessorVertices[listKeys.indexOf(neighbor)] = current;
                    queue.add(neighbor);

                    if (neighbor == to) {
                        shortestPathLength = distance[listKeys.indexOf(to)];
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Gets the length of the shortest path connecting two given
     * vertices.  The length of a path is the number of edges in the
     * path.
     *
     * <ol>
     * <li>If from = to, shortest path has length 0
     * <li>Otherwise, shortest path length is length of the shortest
     * possible path connecting from to to.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the length of the shortest path from 'from' to 'to' in
     * the graph.  If there is no path, returns Integer.MAX_VALUE
     */
    @Override
    public int pathLength(V from, V to){
        if (hasPath(from, to))
            return shortestPathLength;
        else
            shortestPathLength=Integer.MAX_VALUE;
        return shortestPathLength;
    }



    /**
     * Returns the vertices along the shortest path connecting two
     * given vertices.  The vertices should be given in the order x_1,
     * x_2, x_3, ..., x_n, where:
     *
     * <ol>
     * <li>x_1 = from
     * <li>x_n = to
     * <li>for all i from 1 to n-1: (x_i, x_{i+1}) is an edge in the graph.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return an Iterable collection of vertices along the shortest
     * path from 'from' to 'to'.  The Iterable should include the
     * source and destination vertices.
     */
    @Override
    public Iterable<V> getPath(V from, V to) {
        if (from == to){
            if (!contains(from)){
                shortestPath=null;
                return shortestPath;
            }
        }
        if (!hasPath(from, to)){
            shortestPath=null;
            return shortestPath;}
        ArrayList<V> path = new ArrayList<>();
        path.add(from);
        ArrayList<ArrayList> storedPaths= new ArrayList<>();
        boolean[] isVisited = new boolean[numVertices()];
        AllPaths(from, to, isVisited, path,storedPaths);
        if (storedPaths.size()==1)
            shortestPath = storedPaths.get(0);
        if (storedPaths.isEmpty())
            return shortestPath;
        for (int i=0; i<storedPaths.size(); i++){
            for (int j=i+1; j<storedPaths.size(); j++){
                if (storedPaths.get(i).size() > storedPaths.get(j).size()) {
                    shortestPath = storedPaths.get(j);
                }
                else
                    shortestPath = storedPaths.get(i);
            }
        }
        return shortestPath;
    }


    /**
     * private method to get ALL the paths from starting and destination vertex.
     * @param from starting vertex
     * @param to destination vertex
     * @param isVisited list of boolean values indicating whether a vertex is visited or not.
     * @param PathList an Arraylist that stores the current path recursively.
     * @param storedPaths list of all the possible paths.
     */
    private void AllPaths(V from, V to, boolean[] isVisited, ArrayList<V> PathList, ArrayList<ArrayList> storedPaths) {
        if (from.equals(to)) {
            ArrayList currentPath= (ArrayList) PathList.clone();
            storedPaths.add(currentPath);
        }
        else {
            Set<V> keys = graph.keySet();
            List<V> listKeys = new ArrayList<V>(keys);
            isVisited[listKeys.indexOf(from)] = true;

            for (V i : adjacentTo(from)) {
                if (!isVisited[listKeys.indexOf(i)]) {
                    PathList.add(i);
                    AllPaths(i, to, isVisited, PathList,storedPaths);
                    PathList.remove(i);
                }
            }
            isVisited[listKeys.indexOf(from)] = false;
        }
    }


}