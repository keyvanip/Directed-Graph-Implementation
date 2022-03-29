package edu.union.adt.graph.tests.Keyvanip;
import edu.union.adt.graph.Graph;
import edu.union.adt.graph.GraphFactory;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class KeyvanipTests {
    private Graph<String> g;
    private Graph<String> g2;
    private Graph<Object> objectGraph;


    @Before
    public void setUp()
    {
        g = GraphFactory.<String> createGraph();
        g2 = GraphFactory.<String> createGraph();
        objectGraph = GraphFactory.<Object> createGraph();

    }

    @Test
    public void RemoveVertex() {
        g.addVertex("A");
        g2.addVertex("B");
        g.removeVertex("A");
        g2.removeVertex("B");


        assertEquals("RemoveVertex test: Adding a vertex to two empty graphs and removing them produces an empty graph.",
                g, g2);
        assertEquals("RemoveVertex test: After removing the vertex, the number of vertices in a graph should decrease by 1.",
                0, g.numVertices());
        assertEquals("RemoveVertex test: After removing the vertex, the removed vertex should no longer be in the graph.",
                false, g2.contains("B"));
        g2.removeVertex("K");
        assertEquals("RemoveVertex test: Removing a vertex that is not in the graph should not remove or alter anything in the graph.",
                objectGraph, g2);

    }



    @Test
    public void adjacentTo() {
        g2.addVertex("B");
        ArrayList<Object> empty = new ArrayList<Object>();
        assertEquals("adjacentTo test: a vertex not in the graph is not adjacent to any vertices in the graph.",
                empty, g2.adjacentTo("B"));
        g.addEdge("A","B");
        g.addEdge("A","D");
        g.addEdge("B", "W");
        g.addEdge("D","S");
        g.addEdge("A", "S");


        ArrayList<Object> expected = new ArrayList<Object>(Arrays.asList("B","D","S"));
        assertEquals("adjacentTo test: a vertex with many edges.",
                expected, g.adjacentTo("A"));

        g.addEdge("S", "S");
        expected = new ArrayList<Object>(Arrays.asList("S"));
        assertEquals("adjacentTo test: a vertex with an edge to itself.",
                expected, g.adjacentTo("S"));

    }



    @Test
    public void RemoveEdge() {
        g.addEdge("A", "B");
        g.addEdge("A", "A");
        g.addEdge("B","C");
        g.removeEdge("A", "B");
        assertEquals("RemoveEdge test: Removing an edge from a graph should not have that edge in the graph anymore",
                false, g.hasEdge("A","B"));
        assertEquals("RemoveEdge test: After removing the edge, the number of edges in a graph should decrease by 1.",
                2, g.numEdges());
        g.removeEdge("A", "A");
        assertEquals("RemoveEdge test: Removing an edge to itself check",
                1, g.numEdges());
    }


    @Test
    public void hasPath() {
        objectGraph.addEdge("Koo","Bar");
        objectGraph.addEdge("Koo","Mal");
        objectGraph.addEdge("Bar","Mal");
        objectGraph.addEdge("Mal","Tar");

        assertEquals("hasPath test:Checking if there is a path from one vertex to another vertex (unidirectional)",
                true, objectGraph.hasPath("Koo","Tar"));
        g.addEdge("1", "Poo");
        g.addEdge("1", "Bar");
        g.addEdge("Loo", "Bar");
        g.addEdge("Bar", "Poo");
        g.addEdge("Poo", "1");
        g.addEdge("Poo","Poo");
        g.addEdge("Loo","Koo");
        assertEquals("hasPath test: Checking if there is a path from one vertex to another vertex (bidirectional)",
                true, g.hasPath("Loo","1"));
        assertEquals("hasPath test: Checking if there is a path from a vertex to itself with an edge between them.",
                true, g.hasPath("Poo","Poo"));
        assertEquals("hasPath test: Checking if there is a path from a vertex to itself even when no other vertices lead to itself.",
                true, g.hasPath("Loo","Loo"));
        assertEquals("hasPath test: Checking for no existing paths",
                false, g.hasPath("1","Koo"));
        assertEquals("hasPath test: from start vertex to destination vertex, there's path, but not the other way check.",
                false, g.hasPath("Koo","Loo"));
    }


    @Test
    public void PathLength() {
        g.addEdge("1", "Poo");
        g.addEdge("1", "Bar");
        g.addEdge("Loo", "Bar");
        g.addEdge("Bar", "Poo");
        g.addEdge("Poo", "1");
        g.addEdge("Poo","Poo");
        g.addEdge("Loo","Koo");


        assertEquals("PathLength test: Checking the length of path of a bidirectional graph",
                3, g.pathLength("Loo","1"));
        g.addEdge("Loo","Poo");
        assertEquals("PathLength test: Checking the length of the shortest path when many paths are present",
                2, g.pathLength("Loo","1"));
        assertEquals("PathLength test: Checking the length of a path of a unidirectional graph",
                1, g.pathLength("Bar","Poo"));
        assertEquals("PathLength test: Checking the length of path to itself when there's an edge to itself",
                0, g.pathLength("Poo","Poo"));
        assertEquals("PathLength test: Checking the length of path to itself when there's no edges to itself",
                0, g.pathLength("1","1"));
        assertEquals("PathLength test: Checking the length of path that does not exist.",
                Integer.MAX_VALUE, g.pathLength("1","Koo"));
        assertEquals("PathLength test: Checking the length of path with vertex not in the graph.",
                Integer.MAX_VALUE, g.pathLength("1","Che"));
    }


    @Test
    public void getPathToItself() {
        g2.addEdge("1", "Poo");
        g2.addEdge("1", "Bar");
        g2.addEdge("Loo", "Bar");
        g2.addEdge("Bar", "Poo");
        g2.addEdge("Poo", "1");
        g2.addEdge("Poo", "Poo");
        g2.addEdge("Loo", "Koo");

        ArrayList<Object> expected = new ArrayList<Object>(Arrays.asList("Koo"));
        assertEquals("getPath test:Checking path to itself.",
                expected, g2.getPath("Koo", "Koo"));

        expected = new ArrayList<Object>(Arrays.asList("Poo"));
        assertEquals("getPath test:Checking path to itself with an edge to itself.",
                expected, g2.getPath("Poo", "Poo"));

        assertEquals("getPath test:Checking path to itself when the vertex is not in the graph.",
                null, g2.getPath("Doo", "Doo"));
    }


    @Test
    public void getPathToAnotherVertex(){
        objectGraph.addEdge("1", "Poo");
        objectGraph.addEdge("1", "Bar");
        objectGraph.addEdge("Loo", "Bar");
        objectGraph.addEdge("Bar", "Poo");
        objectGraph.addEdge("Poo", "1");
        objectGraph.addEdge("Poo","Poo");
        objectGraph.addEdge("Loo","Koo");


        assertEquals("getPath test:Checking for no paths from a vertex to another vertex.",
                null, objectGraph.getPath("1","Koo"));

        assertEquals("getPath test:Checking for paths from a vertex to another vertex not in the graph.",
                null, objectGraph.getPath("1","Phoo"));

        objectGraph.addEdge("Bar","Loo");
        ArrayList<Object> expected = new ArrayList<Object>(Arrays. asList("1","Bar","Loo"));
        assertEquals("getPath test:Checking path from a vertex to another vertex when only one path is present.",
                expected, objectGraph.getPath("1","Loo"));

        objectGraph.addEdge("Bar","Koo");
        expected = new ArrayList<Object>(Arrays. asList("Bar","Koo"));
        assertEquals("getPath test:Checking for the shortest path from a vertex to another vertex when many paths of varying length are present.",
                expected, objectGraph.getPath("Bar","Koo"));

        objectGraph.addEdge("Poo","Loo");
        objectGraph.addEdge("Poo","Bar");
        ArrayList<Object> expected1 = new ArrayList<Object>(Arrays. asList("Poo","Loo","Koo"));
        // checking for when there are two shortest paths of same length, in which case both can be correct.
        ArrayList<Object> expected2 = new ArrayList<Object>(Arrays. asList("Poo","Bar","Koo"));
        assertTrue(objectGraph.getPath("Poo","Koo").equals(expected1) || objectGraph.getPath("Poo","Koo").equals(expected2));

    }


    @Test
    public void equalsTest(){
        assertEquals("equals test:Two empty graphs should still be equal.",g2,objectGraph);
        g2.addEdge("1","2");
        g2.addEdge("1","4");
        g2.addEdge("2","8");
        g2.addEdge("2","7");
        g2.addEdge("4","3");
        g2.addEdge("4","5");

        objectGraph.addEdge("1","4");
        objectGraph.addEdge("1","2");
        objectGraph.addEdge("4","5");
        objectGraph.addEdge("4","3");
        objectGraph.addEdge("2","7");
        objectGraph.addEdge("2","8");

        assertEquals("equals test:Two graphs with the same vertices and edges, but in different order should still be equal.",g2,objectGraph);
        objectGraph.addEdge("7","2");
        objectGraph.addEdge("8","10");
        assertNotEquals("equals test:Two graphs with different vertices and different edges should not be equal.",g2,objectGraph);
        objectGraph.removeEdge("8","10");
        objectGraph.removeVertex("10");
        objectGraph.addEdge("1","8");
        assertNotEquals("equals test:Two graphs with same vertices, but with one different edge should not be equal.",g2,objectGraph);
        g.addEdge("1","5");
        g.addEdge("1","3");
        g.addEdge("3","8");
        g.addEdge("5","7");
        g.addEdge("5","4");
        g.addEdge("5","2");

        assertNotEquals("equals test:Two graphs with same vertices, but with all different edges should not be equal.",g,g2);

        g.removeVertex("1");
        g.removeVertex("3");
        g.removeVertex("5");
        g.removeVertex("8");
        g.removeVertex("7");
        g.removeVertex("4");
        g.removeVertex("2");
        g.addEdge("20","10");
        g.addEdge("40","10");
        g.addEdge("10","20");
        g.addEdge("10","40");
        assertNotEquals("equals test:Two graphs with completely different vertices and edges, should not be equal.",g,g2);

    }


    @Test
    public void isEmpty(){
        assertEquals("isEmpty test:checking if an empty graph is really empty",true,g.isEmpty());
        g.addVertex("Rooz");
        assertEquals("isEmpty test:a graph with that contains vertex is not empty",false,g.isEmpty());
        g.addEdge("Rooz", "Kooz");
        assertEquals("isEmpty test:a graph that contains vertex and edge is not empty",false,g.isEmpty());
        g.removeVertex("Rooz");
        assertEquals("isEmpty test:After removing a vertex from the graph, the graph should have one less vertex.",1,g.numVertices());
        g.removeVertex("Kooz");
        assertEquals("isEmpty test:After removing the last vertex from the graph, the graph should contain no vertices.",0,g.numVertices());
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void degree(){
        g.addVertex("Rooz");
        assertEquals("degree test: The degree of a vertex with no edges is 0",0,g.degree("Rooz"));
        g.addEdge("Rooz", "Kooz");
        g.addEdge("Rooz", "Pooz");
        g.addEdge("Rooz", "Looz");
        g.addEdge("Rooz", "Mooz");
        g.addEdge("Kooz", "Mooz");
        g.addEdge("Rooz", "Shooz");
        assertEquals("degree test: a vertex containing 5 edges",5,g.degree("Rooz"));
        g.removeEdge("Rooz","Kooz");
        assertEquals("degree test: removing an edge should return one less degree value.",4,g.degree("Rooz"));
        exceptionRule.expect(RuntimeException.class);
        assertEquals("degree test: No vertex in the graph",exceptionRule,g.degree("Nooz"));
    }


}

