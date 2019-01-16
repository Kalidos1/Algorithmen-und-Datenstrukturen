package TelNetApplication;

public class TelVerbindung {
    private int c;
    private TelKnoten u;
    private TelKnoten v;

    public TelVerbindung(TelKnoten u,TelKnoten v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    public int compareTo(TelVerbindung o) {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
