import java.util.*;

class HashNode {
    String key;
    String value;
    HashNode next;

    public HashNode(String key, String value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

class Dictionary {
    private HashNode[] table;
    private int size;

    public Dictionary(int capacity) {
        table = new HashNode[capacity];
        size = capacity;
    }

    // Hash function
    private int getHash(String key) {
        return Math.abs(key.hashCode() % size);
    }

    // Insert a key-value pair
    public void insert(String key, String value) {
        int index = getHash(key);
        HashNode newNode = new HashNode(key, value);
        HashNode head = table[index];

        // If key already exists, update value
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                System.out.println("Updated existing key: " + key);
                return;
            }
            head = head.next;
        }

        // Insert new node at the beginning
        newNode.next = table[index];
        table[index] = newNode;
        System.out.println("Inserted: (" + key + ", " + value + ")");
    }

    // Search for a key
    public String search(String key) {
        int index = getHash(key);
        HashNode head = table[index];

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    // Delete a key
    public void delete(String key) {
        int index = getHash(key);
        HashNode head = table[index];
        HashNode prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev != null) {
                    prev.next = head.next;
                } else {
                    table[index] = head.next;
                }
                System.out.println("Deleted: " + key);
                return;
            }
            prev = head;
            head = head.next;
        }
        System.out.println("Key not found: " + key);
    }

    // Display dictionary contents
    public void display() {
        System.out.println("\n--- Dictionary Contents ---");
        for (int i = 0; i < size; i++) {
            System.out.print("Bucket " + i + ": ");
            HashNode head = table[i];
            while (head != null) {
                System.out.print("(" + head.key + ", " + head.value + ") -> ");
                head = head.next;
            }
            System.out.println("null");
        }
    }
}

public class hash {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary(5);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Insert\n2. Search\n3. Delete\n4. Display\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter key: ");
                    String key = sc.nextLine();
                    System.out.print("Enter value: ");
                    String value = sc.nextLine();
                    dict.insert(key, value);
                    break;
                case 2:
                    System.out.print("Enter key to search: ");
                    key = sc.nextLine();
                    String result = dict.search(key);
                    if (result != null)
                        System.out.println("Value found: " + result);
                    else
                        System.out.println("Key not found!");
                    break;
                case 3:
                    System.out.print("Enter key to delete: ");
                    key = sc.nextLine();
                    dict.delete(key);
                    break;
                case 4:
                    dict.display();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
