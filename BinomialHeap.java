class BinomialHeap {
    static class Node {
        int key, degree;
        Node parent, child, sibling;

        Node(int key) {
            this.key = key;
            this.degree = 0;
            this.parent = this.child = this.sibling = null;
        }
    }

    private Node head;

    // Merge two binomial trees of same degree
    private Node mergeTrees(Node b1, Node b2) {
        if (b1.key > b2.key) {
            Node temp = b1;
            b1 = b2;
            b2 = temp;
        }
        b2.parent = b1;
        b2.sibling = b1.child;
        b1.child = b2;
        b1.degree++;
        return b1;
    }

    // Union two heaps
    private Node unionHeaps(Node h1, Node h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        Node head;
        Node tail;
        Node a = h1, b = h2;

        if (a.degree <= b.degree) {
            head = a;
            a = a.sibling;
        } else {
            head = b;
            b = b.sibling;
        }
        tail = head;

        while (a != null && b != null) {
            if (a.degree <= b.degree) {
                tail.sibling = a;
                a = a.sibling;
            } else {
                tail.sibling = b;
                b = b.sibling;
            }
            tail = tail.sibling;
        }

        tail.sibling = (a != null) ? a : b;
        return head;
    }

    // Adjust heap after union
    private Node adjust(Node head) {
        if (head == null || head.sibling == null)
            return head;

        Node prev = null, curr = head, next = curr.sibling;

        while (next != null) {
            if (curr.degree != next.degree ||
                (next.sibling != null && next.sibling.degree == curr.degree)) {
                prev = curr;
                curr = next;
            } else {
                if (curr.key <= next.key) {
                    curr.sibling = next.sibling;
                    curr = mergeTrees(curr, next);
                } else {
                    if (prev == null) head = next;
                    else prev.sibling = next;
                    next = mergeTrees(next, curr);
                    curr = next;
                }
            }
            next = curr.sibling;
        }
        return head;
    }

    // Insert a key
    void insert(int key) {
        Node newNode = new Node(key);
        head = unionHeaps(head, newNode);
        head = adjust(head);
    }

    // Search a key
    boolean search(Node h, int key) {
        if (h == null) return false;
        if (h.key == key) return true;
        return search(h.child, key) || search(h.sibling, key);
    }

    boolean search(int key) {
        return search(head, key);
    }

    // Get min node
    private Node getMinNode(Node h) {
        Node y = null, x = h;
        int min = Integer.MAX_VALUE;
        while (x != null) {
            if (x.key < min) {
                min = x.key;
                y = x;
            }
            x = x.sibling;
        }
        return y;
    }

    // Delete min node (simplified deletion)
    void deleteMin() {
        if (head == null) return;

        Node minNode = getMinNode(head);
        Node prev = null, curr = head;

        while (curr != null && curr != minNode) {
            prev = curr;
            curr = curr.sibling;
        }

        if (prev == null) head = curr.sibling;
        else prev.sibling = curr.sibling;

        Node child = minNode.child;
        Node revChild = null;
        while (child != null) {
            Node next = child.sibling;
            child.sibling = revChild;
            child.parent = null;
            revChild = child;
            child = next;
        }

        head = unionHeaps(head, revChild);
        head = adjust(head);
    }

    // Display heap
    void display(Node h) {
        while (h != null) {
            System.out.print(h.key + " ");
            display(h.child);
            h = h.sibling;
        }
    }

    void display() {
        display(head);
        System.out.println();
    }

    // Main
    public static void main(String[] args) {
        BinomialHeap bh = new BinomialHeap();

        bh.insert(10);
        bh.insert(20);
        bh.insert(5);
        bh.insert(15);

        System.out.print("Heap elements: ");
        bh.display();

        System.out.println("Search 15: " + bh.search(15));
        System.out.println("Search 100: " + bh.search(100));

        bh.deleteMin();
        System.out.print("After deleting min: ");
        bh.display();
    }
}
