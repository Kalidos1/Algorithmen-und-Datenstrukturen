package Aufgabe1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BinaryTreeDictionary<K extends Comparable<? super K>,V> implements Dictionary<K,V> {


    private Node<K, V> root = null;
    private int size = 0;
    private V oldValue;

    static private class Node<K, V> {
        int height;
        private Node<K, V> parent;
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        private Node(K k, V v) {
            height = 0;
            key = k;
            value = v;
            left = null;
            right = null;
            parent = null;
        }
    }

    private int getHeight(Node<K,V> p) {
        if (p == null)
            return -1;
        else
            return p.height;
    }
    private int getBalance(Node<K,V> p) {
        if (p == null)
            return 0;
        else
            return getHeight(p.right) - getHeight(p.left);
    }

    private static class MinEntry<K, V> {
        private K key;
        private V value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V insert(K key, V value) {
        root = insertR(key, value, root);
        if (root != null) {
            root.parent = null;
        }
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    private Node<K,V> insertR(K key, V value, Node<K,V> p) {
        if (p == null) {
            p = new Node(key, value);
            oldValue = null;
        }
        else if (key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);
            if (p.left != null)
                p.left.parent = p;
        } else if (key.compareTo(p.key) > 0) {
            p.right = insertR(key, value, p.right);
            if (p.right != null)
                p.right.parent = p;
        } else {
            oldValue = p.value;
            p.value = value;
        }
        p = balance(p);
        return p;
    }

    private Node<K, V> balance(Node<K, V> p) {
        if (p == null)
            return null;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        if (getBalance(p) == -2) {
            if (getBalance(p.left) <= 0) {
                p = rotateRight(p);
                if (p.left != null) {
                    p.left.parent = p;
                }
            } else {
                p = rotateLeftRight(p);
                if (p.left != null) {
                    p.left.parent = p;
                }
            }
        }
        else if (getBalance(p) == +2) {
            if (getBalance(p.right) >= 0) {
                p = rotateLeft(p);
                if (p.right != null) {
                    p.right.parent = p;
                }
            }
            else {
                p = rotateRightLeft(p);
                if (p.right != null) {
                    p.right.parent = p;
                }
            }
        }
        return p;
    }
    private Node<K,V> rotateRight(Node<K,V> p) {
        assert p.left != null;
        Node<K, V> q = p.left;
        p.left = q.right;
        q.right = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K,V> rotateLeft(Node<K,V> p) {
        assert p.right != null;
        Node<K, V> q = p.right;
        p.right = q.left;
        q.left = p;
        p.height = Math.max(getHeight(p.right), getHeight(p.left)) + 1;
        q.height = Math.max(getHeight(q.right), getHeight(q.left)) + 1;
        return q;
    }

    private Node<K,V> rotateLeftRight(Node<K,V> p) {
        assert p.left != null;
        p.left = rotateLeft(p.left);
        if (p.left != null) {
            p.left.parent = p;
        }
        return rotateRight(p);
    }

    private Node<K,V> rotateRightLeft(Node<K,V> p) {
        assert p.right != null;
        p.right = rotateRight(p.right);
        if (p.right != null) {
            p.right.parent = p;
        }
        return rotateLeft(p);
    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }

    private V searchR(K key, Node<K,V> p) {
        if (p == null)
            return null;
        else if (key.compareTo(p.key) < 0)
            return searchR(key, p.left);
        else if (key.compareTo(p.key) > 0)
            return searchR(key, p.right);
        else
            return p.value;
    }

    @Override
    public V remove(K key) {
        if (search(key) == null) {
            return null;
        }
        root = removeR(key, root);
        if (root != null) {
            root.parent = null;
        }
        return oldValue;
    }

    private Node<K,V> removeR(K key, Node<K,V> p) {
        if (p == null) {
            oldValue = null;
        } else if (key.compareTo(p.key) < 0) {
            p.left = removeR(key, p.left);
            if (p.left != null) {
                p.left.parent = p;
            }
        } else if (key.compareTo(p.key) > 0) {
            p.right = removeR(key, p.right);
            if (p.right != null) {
                p.right.parent = p;
            }
        } else if (p.left == null || p.right == null) {
                oldValue = p.value;
                p = (p.left != null) ? p.left : p.right;
        } else {
            // p muss gelöscht werden und hat zwei Kinder:
            MinEntry<K,V> min = new MinEntry<K,V>();
            p.right = getRemMinR(p.right, min);
            if (p.right != null) {
                p.right.parent = p;
            }
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        p = balance(p);
        return p;
    }

    private Node<K,V> getRemMinR(Node<K,V> p, MinEntry<K,V> min) {
        assert p != null;
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else {
            p.left = getRemMinR(p.left, min);
            if (p.left != null) {
                p.left.parent = p;
            }
        }
        return p;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        LinkedList<Entry<K,V>> l = new LinkedList<>();
        einfuegen(l,root);
        return l.iterator();
    }

    private void einfuegen(List<Entry<K,V>> l, Node<K,V> p) {
        if (p != null) {
            einfuegen(l,p.left);
            l.add(new Entry<>(p.key,p.value));
            einfuegen(l,p.right);
        }
    }

    public void prettyPrint() {
        System.out.println(root.key +" "+root.value);
        prettyPrint(root,0);
    }

    private void prettyPrint(Node<K,V> node, int tiefe) {
        if (node == null) {
            return;
        }
        for (int i = 1; i < tiefe;i++) {
            System.out.print("   ");
        }
        if (tiefe != 0) {
            System.out.println("|__" + node.key + " " + node.value);
        }

        tiefe++;
        prettyPrint(node.left,tiefe);
        if ((node.left == null) && (node.right != null)) {
            for (int i = 1; i < tiefe;i++) {
                System.out.print("   ");
            }
            System.out.println("|__#");
        }
        prettyPrint(node.right,tiefe);
        if ((node.left != null) && (node.right == null)) {
            for (int i = 1; i < tiefe;i++) {
                System.out.print("   ");
            }
            System.out.println("|__#");
        }
    }
}

