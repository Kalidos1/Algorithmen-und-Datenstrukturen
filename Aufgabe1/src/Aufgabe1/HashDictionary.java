package Aufgabe1;

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K extends Comparable<? super K>,V> implements Dictionary<K,V> {

    private static final int DEF_CAPACITY = 31;
    private int size;
    private LinkedList<Entry<K,V>>[] data;

    @SuppressWarnings("unchecked")
    public HashDictionary() {
        size = 0;
        data = new LinkedList[DEF_CAPACITY];
        ensureLinkedList();
    }

    @SuppressWarnings("unchecked")
    public HashDictionary(int n) {
        size = 0;
        data = new LinkedList[n];
        ensureLinkedList();
    }


    @Override
    public V insert(K key, V value) {
        int i = getHashKey(key);
        int j = Math.abs(key.hashCode());
        int prim = 0;
        while ((prim = getPrimzahl(prim)) <= data.length) {
            for (Entry<K,V> x : data[j % prim]) {
                if (x.getKey().equals(key)) {
                    V r = x.getValue();
                    x.setValue(value);
                    return r;
                }
            }

        }
        if (data[i].size() == 2) {
            ensureCapacity(data.length);
            i = getHashKey(key);
        }
        data[i].add(new Entry<K,V>(key,value));
        size++;
        return null;
    }

    private void ensureLinkedList() {
        for (int i = 0; i < data.length;i++) {
            if (data[i] == null) data[i] = new LinkedList<>();
        }
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int newCapacity) {
        newCapacity *= 2;
        LinkedList<Entry<K, V>>[] old = data;
        while ((newCapacity % 2 == 0) || (newCapacity % 3 == 0) || (newCapacity % 5 == 0))
            newCapacity++;
        data = new LinkedList[newCapacity];
        ensureLinkedList();
        System.arraycopy(old, 0, data, 0, old.length);
    }

    public int getHashKey(K key) {
        int hashkey = Math.abs(key.hashCode());
        return hashkey % data.length;
    }

    @Override
    public V search(K key) {
        int prim = 0;
        int i = Math.abs(key.hashCode());
        while ((prim = getPrimzahl(prim)) <= data.length) {
            for (Entry<K,V> x : data[i % prim]) {
                if (x.getKey().equals(key)) return x.getValue();
            }
        }
        return null;
    }

    public int getPrimzahl(int x) {
        x *= 2;
        if (x == 0) return 3;
        while ((x % 2 == 0) || (x % 3 == 0) || (x % 5 == 0)) x++;
        return x;
    }

    @Override
    public V remove(K key) {
        int index;
        int prim = 0;
        int i = Math.abs(key.hashCode());
        while ((prim = getPrimzahl(prim)) <= data.length) {
            index = 0;
            for (Entry<K, V> x : data[i % prim]) {
                if (x.getKey().equals(key)) {
                    V r = x.getValue();
                    data[i % prim].remove(index);
                    size--;
                    return r;
                }
                index++;
                System.out.println(index);
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<Entry<K, V>> iterator() {
        LinkedList<Entry<K,V>> x = new LinkedList();
        for (int i = 0; i < data.length; i++) {
            x.addAll(data[i]);
        }
        return x.iterator();
    }


    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("SAD: ");
        for (int i = 0; i < data.length; i++) {
            s.append("Data[" + i + "]: Size = " + data[i].size() + " {");
            for (Entry<K,V> x : data[i]){
                s.append("Key: " + x.getKey() + " ");
                s.append("Value: " + x.getValue() + " | ");
            }
            s.append(" } \n");

        }
        s.append(size);
        return s.toString();
    }

}
