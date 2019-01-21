package TelNetApplication;

import princetonStdLib.StdDraw;

import java.util.*;

public class TelNet {

    int lbg;
    List<TelVerbindung> minSpanTree;
    HashMap<TelKnoten, Integer> telKnotenList;


    public TelNet(int lbg) {
        this.lbg = lbg;
        this.telKnotenList = new HashMap<>();
    }

    private boolean addTelKnoten(int x, int y) {
        TelKnoten telKnoten = new TelKnoten(x,y);
        Integer nodeNR = telKnotenList.size();

        if (telKnotenList.containsKey(telKnotenList)) {
            return false;
        } else {
            minSpanTree = null;
            telKnotenList.put(telKnoten, nodeNR);
            return true;
        }
    }

    private boolean computeOptTelNet() {
        UnionFind forest = new UnionFind(telKnotenList.size());
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>();
        minSpanTree = new LinkedList<>();


        for (TelKnoten x : telKnotenList.keySet()) {
            for (TelKnoten y : telKnotenList.keySet()) {
                if (x.equals(y)) continue;

                int c = Math.abs(x.x - y.x) + Math.abs(x.y - y.y);

                TelVerbindung t = new TelVerbindung(x,y,c);

                if (t.c <= this.lbg) edges.add(t);
            }
        }

        while(forest.size() != 1 && !edges.isEmpty()) {
            TelVerbindung help = edges.poll();

            int v = telKnotenList.get(help.u);
            int w = telKnotenList.get(help.v);

            int t1 = forest.find(v);
            int t2 = forest.find(w);

            if (t1 != t2) {
                forest.union(t1,t2);
                minSpanTree.add(help);
            }

        }

        if (edges.isEmpty() && forest.size() != 1) {
            minSpanTree = null;
            System.out.println("Es existiert kein aufspannender Baum!!");
            return false;
        } else {
            return true;
        }
    }

    private void drawOptTelNet(int xMax,int yMax) {
        StdDraw.clear();
       // StdDraw.setCanvasSize(xMax,yMax);

        StdDraw.setPenRadius(0.050); //0.010 fürs Große
        StdDraw.setPenColor(StdDraw.BLUE);
        for (TelKnoten x : telKnotenList.keySet()) {
            StdDraw.point(x.x / (double) xMax, x.y / (double) yMax);
        }

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (TelVerbindung x : minSpanTree) {
            StdDraw.line(x.u.x / (double) xMax, x.u.y / (double) yMax,
                    x.u.x / (double) xMax, x.v.y / (double) yMax);

            StdDraw.line(x.u.x / (double) xMax, x.v.y / (double) yMax,
                    x.v.x / (double) xMax, x.v.y / (double) yMax);
        }

        StdDraw.show(0);
    }

    private void generateRandomTelNet(int n,int xMax,int yMax) {
        if (n <= 0 || xMax <= 0 || yMax <= 0) {
            throw new IllegalArgumentException();
        }

        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            int x = rnd.nextInt(xMax);
            int y = rnd.nextInt(yMax);

            if (!addTelKnoten(x, y))
                i--;
        }
    }

    private List<TelVerbindung> getOptTelNet () {
        if (!computeOptTelNet()) throw new IllegalStateException();
        return minSpanTree;
    }

    private int getOptTelNetKosten() {
        int help = 0;
        for (TelVerbindung x : minSpanTree) {
            help += x.c;
        }
        return help;
    }

    private int size() {
        return telKnotenList.size();
    }

    @Override
    public String toString() {
        return "TelNet{lbg = " + lbg + " , telKnotenList = "
                + telKnotenList + " , minSpanTree = " + minSpanTree + "}";
    }

    public static void main(String[] args) {
        TelNet telNet = new TelNet(7);

        telNet.addTelKnoten(1, 1);
        telNet.addTelKnoten(3, 1);
        telNet.addTelKnoten(4, 2);
        telNet.addTelKnoten(3, 4);
        telNet.addTelKnoten(2, 6);
        telNet.addTelKnoten(4, 7);
        telNet.addTelKnoten(7, 5);
        telNet.computeOptTelNet();

        System.out.println("Kosten: " + telNet.getOptTelNetKosten());
        telNet.drawOptTelNet(7, 7);

        /*
        TelNet telNet2 = new TelNet(100);
        telNet2.generateRandomTelNet(1000, 1000, 1000);
        telNet2.computeOptTelNet();

        System.out.println("Kosten: " + telNet2.getOptTelNetKosten());
        telNet2.drawOptTelNet(1000, 1000);
*/
    }

}
