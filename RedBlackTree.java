// Easy Red-Black Tree code (perfect for lab exam)
class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // Node structure
    class Node {
        int data;
        Node left, right, parent;
        boolean color;

        Node(int data) {
            this.data = data;
            this.color = RED; // new node is always red
        }
    }

    private Node root;

    // Left rotation
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    // Right rotation
    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == null)
            root = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    // Insert like a BST
    public void insert(int data) {
        Node node = new Node(data);
        Node parent = null;
        Node current = root;

        while (current != null) {
            parent = current;
            if (data < current.data)
                current = current.left;
            else
                current = current.right;
        }

        node.parent = parent;
        if (parent == null)
            root = node;
        else if (data < parent.data)
            parent.left = node;
        else
            parent.right = node;

        fixInsert(node);
    }

    // Fix color and balance after insertion
    private void fixInsert(Node node) {
        while (node != root && node.parent.color == RED) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    // Simple search
    public boolean search(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.data)
                return true;
            else if (key < current.data)
                current = current.left;
            else
                current = current.right;
        }
        return false;
    }

    // 🔻 Minimal delete (just like BST delete, no rebalance)
    public void delete(int key) {
        root = deleteNode(root, key);
    }

    private Node deleteNode(Node root, int key) {
        if (root == null)
            return root;

        if (key < root.data)
            root.left = deleteNode(root.left, key);
        else if (key > root.data)
            root.right = deleteNode(root.right, key);
        else {
            // Found the node to delete
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            Node temp = findMin(root.right);
            root.data = temp.data;
            root.right = deleteNode(root.right, temp.data);
        }
        return root;
    }

    private Node findMin(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Display in sorted order with color
    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + (node.color ? "(R) " : "(B) "));
            inorder(node.right);
        }
    }

    public void display() {
        inorder(root);
        System.out.println();
    }

    // Main function for demo
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);

        System.out.println("Tree after insertion:");
        tree.display();

        System.out.println("Search 15: " + tree.search(15));
        System.out.println("Search 40: " + tree.search(40));

        tree.delete(20);
        System.out.println("Tree after deleting 20:");
        tree.display();
    }
}
