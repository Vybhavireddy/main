// Simple Leftist Tree implementation in Java
// Includes: Insertion, Deletion, and Search

class LeftistTree {
    // Node class for Leftist Tree
    static class Node {
        int key;
        Node left, right;
        int npl; // Null Path Length

        Node(int key) {
            this.key = key;
            left = right = null;
            npl = 0;
        }
    }

    private Node root;

    // Function to get Null Path Length safely
    private int npl(Node node) {
        return (node == null) ? -1 : node.npl;
    }

    // Function to merge two leftist trees
    private Node merge(Node h1, Node h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        // Keep smaller root as main root
        if (h1.key > h2.key) {
            Node temp = h1;
            h1 = h2;
            h2 = temp;
        }

        // Merge h2 into right subtree of h1
        h1.right = merge(h1.right, h2);

        // Maintain leftist property
        if (npl(h1.left) < npl(h1.right)) {
            Node temp = h1.left;
            h1.left = h1.right;
            h1.right = temp;
        }

        // Update null path length
        h1.npl = npl(h1.right) + 1;
        return h1;
    }

    // -------------------------------
    // 1️⃣ INSERTION
    // -------------------------------
    public void insert(int key) {
        Node newNode = new Node(key);
        root = merge(root, newNode);
    }

    // -------------------------------
    // 2️⃣ DELETION (Delete Min)
    // -------------------------------
    public void deleteMin() {
        if (root == null) {
            System.out.println("Tree is empty!");
            return;
        }
        System.out.println("Deleted minimum element: " + root.key);
        root = merge(root.left, root.right);
    }

    // -------------------------------
    // 3️⃣ SEARCH for a Key
    // -------------------------------
    public boolean search(Node node, int key) {
        if (node == null) return false;
        if (node.key == key) return true;
        // Since it's not a BST, search both sides
        return search(node.left, key) || search(node.right, key);
    }

    public boolean search(int key) {
        return search(root, key);
    }

    // Display the tree (inorder)
    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public void display() {
        System.out.print("Inorder: ");
        inorder(root);
        System.out.println();
    }

    // -------------------------------
    // MAIN METHOD (Testing)
    // -------------------------------
    public static void main(String[] args) {
        LeftistTree tree = new LeftistTree();

        // Insert elements
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(15);
        tree.insert(30);

        System.out.println("After insertion:");
        tree.display();

        // Search for elements
        int key = 15;
        System.out.println("Searching for " + key + ": " + (tree.search(key) ? "Found" : "Not Found"));

        key = 25;
        System.out.println("Searching for " + key + ": " + (tree.search(key) ? "Found" : "Not Found"));

        // Delete minimum
        tree.deleteMin();

        System.out.println("After deleting minimum:");
        tree.display();
    }
}
