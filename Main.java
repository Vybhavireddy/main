// ✅ Simple B-Tree program for lab exam
import java.util.*;

class BTreeNode {
    int t; // Minimum degree
    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<BTreeNode> child = new ArrayList<>();
    boolean leaf;

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
    }

    // ---------- SEARCH ----------
    BTreeNode search(int k) {
        int i = 0;
        while (i < keys.size() && k > keys.get(i)) i++;
        if (i < keys.size() && keys.get(i) == k)
            return this;
        if (leaf) return null;
        return child.get(i).search(k);
    }

    // ---------- INSERT ----------
    void insertNonFull(int k) {
        int i = keys.size() - 1;
        if (leaf) {
            keys.add(0); // dummy to shift
            while (i >= 0 && k < keys.get(i)) {
                keys.set(i + 1, keys.get(i));
                i--;
            }
            keys.set(i + 1, k);
        } else {
            while (i >= 0 && k < keys.get(i)) i--;
            i++;
            if (child.get(i).keys.size() == 2 * t - 1) {
                splitChild(i);
                if (k > keys.get(i)) i++;
            }
            child.get(i).insertNonFull(k);
        }
    }

    // ---------- SPLIT ----------
    void splitChild(int i) {
        BTreeNode y = child.get(i);
        BTreeNode z = new BTreeNode(y.t, y.leaf);

        for (int j = 0; j < t - 1; j++)
            z.keys.add(y.keys.remove(t)); // move keys
        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.child.add(y.child.remove(t)); // move children
        }

        child.add(i + 1, z);
        keys.add(i, y.keys.remove(t - 1));
    }

    // ---------- DELETE ----------
    void delete(int k) {
        // simplified delete (no rebalance)
        if (keys.contains(k)) {
            keys.remove(Integer.valueOf(k));
        } else if (!leaf) {
            for (BTreeNode c : child)
                c.delete(k);
        }
    }

    // ---------- PRINT ----------
    void print(String space) {
        System.out.println(space + keys);
        for (BTreeNode c : child)
            c.print(space + "   ");
    }
}

class BTree {
    BTreeNode root;
    int t;

    BTree(int t) {
        this.t = t;
        root = new BTreeNode(t, true);
    }

    void insert(int k) {
        if (root.keys.size() == 2 * t - 1) {
            BTreeNode s = new BTreeNode(t, false);
            s.child.add(root);
            s.splitChild(0);
            int i = (k > s.keys.get(0)) ? 1 : 0;
            s.child.get(i).insertNonFull(k);
            root = s;
        } else {
            root.insertNonFull(k);
        }
    }

    void search(int k) {
        BTreeNode r = root.search(k);
        if (r != null)
            System.out.println("Key " + k + " found");
        else
            System.out.println("Key " + k + " not found");
    }

    void delete(int k) {
        root.delete(k);
    }

    void print() {
        root.print("");
    }
}

public class Main {
    public static void main(String[] args) {
        BTree t = new BTree(3); // t = minimum degree

        // Insert elements
        t.insert(10);
        t.insert(20);
        t.insert(5);
        t.insert(6);
        t.insert(12);
        t.insert(30);
        t.insert(7);
        t.insert(17);

        System.out.println("B-Tree structure:");
        t.print();

        // Search keys
        t.search(6);
        t.search(15);

        // Delete a key
        t.delete(6);
        System.out.println("\nAfter deleting 6:");
        t.print();
    }
}
