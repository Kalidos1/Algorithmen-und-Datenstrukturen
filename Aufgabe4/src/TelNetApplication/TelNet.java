package TelNetApplication;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TelNet {

    List<TelKnoten> telKnotenList;
    List<TelVerbindung> minTelNet;
    UnionFind verbindungen;
    PriorityQueue<TelVerbindung> edges;



    public TelNet(int lbg) {
        telKnotenList = new LinkedList<>();
        minTelNet = new LinkedList<>();
        verbindungen = new UnionFind(lbg);
    }

    private boolean addTelKnoten(int x, int y) {
        TelKnoten telKnoten = new TelKnoten(x,y);
        if (telKnotenList.contains(telKnoten)) {
            return false;
        }
        telKnotenList.add(telKnoten);
        return true;
    }

    private boolean computeOptTelNet() {

        while(verbindungen.size() != 1 && !edges.isEmpty()) {


        }





        return true;
    }

    private void drawOptTelNet(int xMax,int yMax) {

    }

    private void generateRandomTelNet(int n,int xMax,int yMax) {

    }

    private List<TelVerbindung> getOptTelNet () {

    }

    private int getOptTelNetKosten() {

    }

    private int size() {
        return telKnotenList.size();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {

    }

}
