package directedGraph;// O. Bittel;
// 19.03.2018

import java.lang.reflect.Array;
import java.util.*;

/**
 * Implementierung von DirectedGraph mit einer doppelten TreeMap 
 * für die Nachfolgerknoten und einer einer doppelten TreeMap 
 * für die Vorgängerknoten. 
 * <p>
 * Beachte: V muss vom Typ Comparable&lt;V&gt; sein.
 * <p>
 * Entspicht einer Adjazenzlisten-Implementierung 
 * mit schnellem Zugriff auf die Knoten.
 * @author Oliver Bittel
 * @since 19.03.2018
 * @param <V> Knotentyp.
 */
public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {
    // doppelte Map für die Nachfolgerknoten:
    private final Map<V, Map<V, Double>> succ = new TreeMap<>(); 
    
    // doppelte Map für die Vorgängerknoten:
    private final Map<V, Map<V, Double>> pred = new TreeMap<>();

    private int numberEdge = 0;

	@Override
	public boolean addVertex(V v) {
		if (succ.containsKey(v)) return false;
		succ.put(v,new TreeMap<>());
		pred.put(v,new TreeMap<>());
		return true;
    }

    @Override
    public boolean addEdge(V v, V w, double weight) {
		addVertex(v);
		addVertex(w);
		if (containsEdge(v,w)) succ.get(v).put(w,weight);
		succ.get(v).put(w,weight);
		pred.get(w).put(v,weight);
		numberEdge++;
		return true;
	}

    @Override
    public boolean addEdge(V v, V w) {
		addVertex(v);
		addVertex(w);
		if (containsEdge(v,w)) succ.get(v).put(w,1.0);
		succ.get(v).put(w,1.0);
		pred.get(w).put(v,1.0);
		numberEdge++;
		return true;
	}

    @Override
    public boolean containsVertex(V v) {
		if (succ.containsKey(v)) return true;
		return false;
	}

    @Override
    public boolean containsEdge(V v, V w) {
		if (succ.containsKey(v)) {
			return succ.get(v).containsKey(w);
		}
		return false;
	}

    @Override
    public double getWeight(V v, V w) {
		if (succ.containsKey(v)) {
			if(succ.get(v).containsKey(w)) {
				return succ.get(v).get(w);
			}
		}
		return -1;
	}

	
    @Override
    public int getInDegree(V v) {
		if (pred.containsKey(v)) {
			return pred.get(v).size();
		}
		return 0;
	}

    @Override
    public int getOutDegree(V v) {
		if (succ.containsKey(v)) {
			return succ.get(v).size();
		}
		return 0;
	}
	
	@Override
    public Set<V> getVertexSet() {
		return Collections.unmodifiableSet(succ.keySet()); // nicht modifizierbare Sicht
    }

    @Override
    public Set<V> getPredecessorVertexSet(V v) {
		return Collections.unmodifiableSet(new TreeSet<>(pred.get(v).keySet()));
	}

    @Override
    public Set<V> getSuccessorVertexSet(V v) {
		return Collections.unmodifiableSet(new TreeSet<>(succ.get(v).keySet()));
	}

    @Override
    public int getNumberOfVertexes() {
		return succ.size();
	}

    @Override
    public int getNumberOfEdges() {
		return numberEdge;
	}
	
	@Override
    public DirectedGraph<V> invert() {
		DirectedGraph<V> dg = new AdjacencyListDirectedGraph();
		for (Map.Entry<V, Map<V,Double>> entry : succ.entrySet()) {
			for (Map.Entry<V, Double> entry1 : entry.getValue().entrySet()) {
				dg.addVertex(entry1.getKey());
				dg.addEdge(entry1.getKey(),entry.getKey(),entry1.getValue());
			}
		}
		return dg;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<V, Map<V,Double>> entry : succ.entrySet()) {
			for (Map.Entry<V,Double> entry1 : entry.getValue().entrySet()) {
				 sb.append(entry.getKey() + " --> " + entry1.getKey()
						 + " weight = " + entry1.getValue() + "\n");
			}
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(2,5);
		g.addEdge(5,1);
		g.addEdge(2,6);
		g.addEdge(3,7);
		g.addEdge(4,3);
		g.addEdge(4,6);
		g.addEdge(7,4);
		
		
		System.out.println(g.getNumberOfVertexes());	// 7
		System.out.println(g.getNumberOfEdges());		// 8
		System.out.println(g.getVertexSet());	// 1, 2, ..., 7
		System.out.println(g);
			// 1 --> 2 weight = 1.0 
			// 2 --> 5 weight = 1.0
			// 2 --> 6 weight = 1.0
			// 3 --> 7 weight = 1.0
			// ...
		
		System.out.println("");
		System.out.println(g.getOutDegree(2));				// 2
		System.out.println(g.getSuccessorVertexSet(2));	// 5, 6
		System.out.println(g.getInDegree(6));				// 2
		System.out.println(g.getPredecessorVertexSet(6));	// 2, 4
		
		System.out.println("");
		System.out.println(g.containsEdge(1,2));	// true
		System.out.println(g.containsEdge(2,1));	// false
		System.out.println(g.getWeight(1,2));	// 1.0	
		g.addEdge(1, 2, 5.0);
		System.out.println(g.getWeight(1,2));	// 5.0	
		
		System.out.println("");
		System.out.println(g.invert());
			// 1 --> 5 weight = 1.0
			// 2 --> 1 weight = 1.0
			// 3 --> 4 weight = 1.0 
			// 4 --> 7 weight = 1.0
			// ...
			
		Set<Integer> s = g.getSuccessorVertexSet(2);
		System.out.println(s);
		//s.remove(5);	// Laufzeitfehler! Warum? -> UnmodifiableCollection

	}
}
