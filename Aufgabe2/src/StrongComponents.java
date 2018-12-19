// O. Bittel;
// 05-09-2018

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

	private final Map<Integer,Set<V>> comp = new TreeMap<>();
	private final DirectedGraph<V> myGraph;
	private final DepthFirstOrder<V> DF;

	
	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(DirectedGraph<V> g) {
		myGraph = g;
		DF = new DepthFirstOrder<>(g);
		KosarajuSharirAlgorithmus();
	}

	public void KosarajuSharirAlgorithmus() {
		DF.visitDFAllNodes();
		List<V> p = DF.postOrder();
		System.out.println("p : " + p);

		List<V> pi = new LinkedList<>();
		for (int i = p.size()-1; i >= 0; i--) {
			pi.add(p.get(i));
		}
		System.out.println("pi : " + pi);

		DirectedGraph<V> gi = myGraph.invert();
		System.out.println("gi : \n" + gi);



		DepthFirstOrder test = new DepthFirstOrder<>(gi);

		Set<V> besucht = new TreeSet<>();
		Set<V> testSet = new TreeSet<>();
		Set<V> testSet2 = new TreeSet<>();

		int tmp = 0;
		for (V x : pi) {
			besucht = test.visitDFTest(x,gi,besucht);
			for (V y : besucht) {
				if (!testSet2.contains(y)) {
					testSet.add(y);
				}
			}
			for (V z : besucht) {
				testSet2.add(z);
			}
			if (testSet.size() != 0) {
				comp.put(tmp,testSet);
				tmp++;
			}
			testSet = new TreeSet<>();
		}

		System.out.println(comp);



	}


	
	/**
	 * 
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return comp.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Set<V>> entry : comp.entrySet()) {
			sb.append(entry);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Liest einen gerichteten Graphen von einer Datei ein. 
	 * @param fn Dateiname.
	 * @return gerichteter Graph.
	 * @throws FileNotFoundException
	 */
	public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		Scanner sc = new Scanner(fn);
		sc.nextInt();
		sc.nextInt();
		while (sc.hasNextInt()) {
			int v = sc.nextInt();
			int w = sc.nextInt();
			g.addEdge(v, w);
		}
		return g;
	}
	
	private static void test1() {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);
		
		g.addEdge(1,4);
		g.addEdge(5,4);
		
		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);
		
		g.addEdge(7,8);
		g.addEdge(8,2);
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		
		System.out.println(sc.numberOfComp());  // 4
		
		System.out.println(sc);
			// Component 0: 5, 6, 7,
        	// Component 1: 8,
            // Component 2: 1, 2, 3, 
            // Component 3: 4, 
	}
	
	private static void test2() throws FileNotFoundException {
		DirectedGraph<Integer> g = readDirectedGraph(new File("mediumDG.txt"));
		System.out.println("Knoten: " + g.getNumberOfVertexes());
		System.out.println("Kanten: " + g.getNumberOfEdges());
		System.out.println(g);
		
		System.out.println("");
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		System.out.println(sc.numberOfComp());  // 10
		System.out.println(sc);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//test1();
		test2();
	}
}
