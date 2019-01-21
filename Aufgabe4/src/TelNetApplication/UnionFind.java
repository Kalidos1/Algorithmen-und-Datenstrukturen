package TelNetApplication;

import java.util.*;

public class UnionFind {

    private int[] p;
    private int[] e;

    public UnionFind(int n) {
        e = new int[n];
        p = new int[n];

        for (int i = 0; i < n; i++) {
            e[i] = i;
            p[i] = -1;
        }
    }

    public int find(int e) {
        while (p[e] >= 0) {
            e = p[e];
        }
        return e;
    }

    public int size() {
        int size = 0;

        for (int e : p) {
            if (e <= -1) size ++;
        }
        return size;
    }

    public void union(int s1, int s2) {
        if (p[s1] >= 0 || p[s2] >= 0)
            return;


        if (s1 == s2)
            return;

        if ( -p[s1] < -p[s2] ) // Höhe von s1 < Höhe von s2
            p[s1] = s2;
        else {
            if ( -p[s1] == -p[s2] )
                    p[s1]--; // Höhe von s1 erhöht sich um 1
            p[s2] = s1;
        }
    }

    public String toString() {
        return "<UnionFind\ne " + Arrays.toString(e) + "\np[e] " + Arrays.toString(p) + "\n>";
    }


    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);
        System.out.println(uf.toString());

        uf.union(1,2);
        System.out.println("union 1 2");
        System.out.println(uf);

        uf.union(6,3);
        System.out.println("union 6 3");
        System.out.println(uf);

        uf.union(8,9);
        System.out.println("union 8 9");
        System.out.println(uf);

        uf.union(6,9);
        System.out.println("union 3 9");
        System.out.println(uf);

        uf.union(4,7);
        System.out.println("union 6 7");
        System.out.println(uf);

        uf.union(9,4);
        System.out.println("union 3 4");
        System.out.println(uf);

        uf.union(2,0);
        System.out.println("union 1 0");
        System.out.println(uf);

        uf.union(4,5);
        System.out.println("union 1 3");
        System.out.println(uf);

        System.out.println(uf.find(4));
        System.out.println(uf.find(9));
    }



}
