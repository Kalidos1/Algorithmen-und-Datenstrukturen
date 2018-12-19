// O. Bittel;
// 22.02.2017

import java.util.*;

/**
 * Klasse für Tiefensuche.
 *
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class DepthFirstOrder<V> {

    private final List<V> preOrder = new LinkedList<>();
    private final List<V> postOrder = new LinkedList<>();
    private final DirectedGraph<V> myGraph;
    private int numberOfDFTrees = 0;
	// ...

    /**
     * Führt eine Tiefensuche für g durch.
     *
     * @param g gerichteter Graph.
     */
    public DepthFirstOrder(DirectedGraph<V> g) {
        myGraph = g;
    }

    public void visitDF(V v) {
        Set<V> besucht = new TreeSet<>();
        visitDFRekursive(v,myGraph,besucht);
    }

    public void visitDFAllNodes() {
        Set<V> besucht = new TreeSet<>();

        for (V entry : myGraph.getVertexSet()) {
            if (!besucht.contains(entry)) {
                System.out.println("Tree: " + numberOfDFTrees);
                numberOfDFTrees++;
                visitDFRekursive(entry,myGraph,besucht);
            }
        }
    }

    private void visitDFRekursive(V v,DirectedGraph<V> g,Set<V> besucht) {
        besucht.add(v);
        preOrder.add(v);
        System.out.println(v);

        for (V entry1 : g.getSuccessorVertexSet(v)) {
            if (!besucht.contains(entry1)) {
                visitDFRekursive(entry1,g,besucht);
            }
        }
        postOrder.add(v);
    }

    public Set<V> visitDFTest(V v,DirectedGraph<V> g,Set<V> besucht) {
        besucht.add(v);
        preOrder.add(v);

        for (V entry1 : g.getSuccessorVertexSet(v)) {
            if (!besucht.contains(entry1)) {
                visitDFTest(entry1,g,besucht);
            }
        }
        postOrder.add(v);
        return besucht;
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Pre-Order-Reihenfolge zurück.
     *
     * @return Pre-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> preOrder() {
        return Collections.unmodifiableList(preOrder);
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Post-Order-Reihenfolge zurück.
     *
     * @return Post-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> postOrder() {
        return Collections.unmodifiableList(postOrder);
    }

    /**
     *
     * @return Anzahl der Bäume des Tiefensuchwalds.
     */
    public int numberOfDFTrees() {
        return numberOfDFTrees;
    }

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        //g.addEdge(7,3);
        g.addEdge(7, 4);

        DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(g);
        dfs.visitDFAllNodes();
        System.out.println("Number of Trees: " + dfs.numberOfDFTrees());	// 2
        System.out.println("PreOrder: " + dfs.preOrder());		// [1, 2, 5, 6, 3, 7, 4]
        System.out.println("PostOrder: " + dfs.postOrder());		// [5, 6, 2, 1, 4, 7, 3]
    }
}
