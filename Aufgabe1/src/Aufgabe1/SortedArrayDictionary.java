package Aufgabe1;

import java.util.*;



public class SortedArrayDictionary<K extends Comparable<? super K>,V> implements Dictionary<K,V> {

    private static final int DEF_CAPACITY = 32;
    private int size;
    private Entry<K,V> [] data;

    @SuppressWarnings("unchecked")
    public SortedArrayDictionary() {
        size = 0;
        data = new Entry[DEF_CAPACITY];
    }


    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);

        if (i >= 0) {
            V r = data[i].getValue();
            data[i].setValue(value);
            return r;
        }
        if (data.length == size) {
            ensureCapacity(2*size);
        }
        int j = size-1;
        while (j >= 0 && key.compareTo(data[j].getKey()) < 0) {
            data[j+1] = data[j];
            j--;
        }
        data[j+1] = new Entry<K,V>(key,value);
        size++;
        return null;
    }
    public int searchKey(K key) {
        for (int i = 0;i < size; i++) {
            if (data[i].getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public V search(K key) {
        for (int i = 0;i < size; i++) {
            if (data[i].getKey().equals(key)) {
                return data[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int i = searchKey(key);
        if (i == -1) {
            return null;
        }
        V r = data[i].getValue();
        for (int j = i; j < size-1;j++){
            data[j] = data[j+1];
        }
        data[--size] = null;
        return r;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        List<Entry<K,V>> l = new LinkedList<>();
        for (int i = 0;i < size;i++) {
            l.add(data[i]);
        }
        return l.iterator();
    }

    @Override
    public String toString(){
        for (int i = 0; i < size; i++) {
            System.out.println(data[i].getKey() + ": " + data[i].getValue());
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int newCapacity) {
        if(newCapacity < size)
            return;
        Entry[] old = data;
        data =new Entry[newCapacity];
        System.arraycopy(old, 0, data, 0, size);
    }
}
