// O. Bittel;
// 18.10.2011

package shortestPath;

import sim.SYSimulation;
import directedGraph.*;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 27.01.2015
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	
	SYSimulation sim = null;
	
	Map<V,Double> dist = new HashMap<>(); // Distanz für jeden Knoten
	Map<V,V> pred = new HashMap<>(); // Vorgänger für jeden Knoten
	DirectedGraph<V> myGraph = new AdjacencyListDirectedGraph<>();
	private Heuristic<V> heuristic;
	private V v;

	/**
	 * Berechnet im Graph g kürzeste Wege nach dem A*-Verfahren.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
		this.myGraph = g;
		this.heuristic = h;
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <p><blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public boolean searchShortestPath(V s, V g) {
		if (heuristic == null) {
			doDijkstra(s);
			v = g;
			if (getShortestPath().get(0) == s) {
				return true;
			} else {
				return false;
			}
		} else {
			return doAStern(s,g);
		}
	}

	public void doDijkstra(V s) {
		v = s;
		List<V> kl = new LinkedList();
		for (V x : myGraph.getVertexSet()) {
			dist.put(x, Double.POSITIVE_INFINITY);
			pred.put(x,null);
		}
		dist.put(s,0.0);
		kl.add(s);

		while(!kl.isEmpty()) {
			double z = Double.MAX_VALUE;
			for (V x : kl) {
				if (dist.get(x) < z) {
					v = x;
					z = dist.get(x);
				}
			}
			kl.remove(v);
			if (sim != null)
				sim.visitStation((Integer) v, Color.blue);
			System.out.println("Besuche Knoten " + v + " mit d = " + dist.get(v));
			for (V w : myGraph.getSuccessorVertexSet(v)) {
				if (dist.get(w) == Double.POSITIVE_INFINITY) kl.add(w);
				if (dist.get(v) + myGraph.getWeight(v, w) < dist.get(w)) {
					pred.put(w, v);
					dist.put(w, dist.get(v) + myGraph.getWeight(v, w));
				}
			}
		}
	}

	public boolean doAStern(V s,V z) {
		v = s;
		List<V> kl = new LinkedList();
		for (V x : myGraph.getVertexSet()) {
			dist.put(x, Double.POSITIVE_INFINITY);
			pred.put(x,null);
		}
		dist.put(s,0.0);
		kl.add(s);

		while(!kl.isEmpty()) {
			double u = Double.MAX_VALUE;
			for (V x : kl) {

				double dh = dist.get(x) + heuristic.estimatedCost(x,z);
				if (dh < u) {
					v = x;
					u = dh;
				}
			}
			kl.remove(v);
			if (sim != null)
				sim.visitStation((Integer) v, Color.blue);
			System.out.println("Besuche Knoten " + v + " mit d = " + dist.get(v));
			if (v.equals(z)) {
				return true;
			}
			for (V w : myGraph.getSuccessorVertexSet(v)) {
				if (dist.get(w) == Double.POSITIVE_INFINITY) kl.add(w);
				if (dist.get(v) + myGraph.getWeight(v, w) < dist.get(w)) {
					pred.put(w, v);
					dist.put(w, dist.get(v) + myGraph.getWeight(v, w));
				}
			}
		}
		return false;
	}

	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {
		List<V> help = new LinkedList<>();
		V u = v;
		help.add(v);
		while (pred.get(u) != null) {
			u = pred.get(u);
			help.add(0,u);
		}
		return help;
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		double help = 0;
		V last = null;
		List<V> help2 = getShortestPath();
		for(V x : help2) {
			if(last != null) {
				help += myGraph.getWeight(last, x);
			}
			last = x;
		}
		return help;
	}
}
