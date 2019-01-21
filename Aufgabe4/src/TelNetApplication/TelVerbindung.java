package TelNetApplication;

public class TelVerbindung implements Comparable<TelVerbindung>{
    int c;
    TelKnoten u;
    TelKnoten v;

    public TelVerbindung(TelKnoten u,TelKnoten v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    public int compareTo(TelVerbindung o) {
        if (o == null) throw new IllegalArgumentException();
        return this.c - o.c;
    }

    @Override
    public String toString() {
        return "TelVerbindung{u = " + u + ", v = " + v + ", c = " + c + " }";
    }
}
