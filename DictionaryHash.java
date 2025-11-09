import java.util.*;

class DictionaryHash {
    private HashMap<String, String> dict = new HashMap<>();

    // Insert a key-value pair
    void insert(String key, String value) {
        dict.put(key, value);
        System.out.println("Inserted (" + key + ", " + value + ")");
    }

    // Search for a key
    void search(String key) {
        if (dict.containsKey(key))
            System.out.println("Found: " + key + " -> " + dict.get(key));
        else
            System.out.println(key + " not found");
    }

    // Delete a key
    void delete(String key) {
        if (dict.remove(key) != null)
            System.out.println(key + " deleted");
        else
            System.out.println(key + " not found");
    }

    // Display all key-value pairs
    void display() {
        System.out.println("Dictionary contents:");
        for (Map.Entry<String, String> e : dict.entrySet())
            System.out.println(e.getKey() + " -> " + e.getValue());
    }

    public static void main(String[] args) {
        DictionaryHash d = new DictionaryHash();
        d.insert("apple", "a fruit");
        d.insert("car", "a vehicle");
        d.insert("book", "a set of pages");

        d.display();

        d.search("apple");
        d.delete("car");
        d.display();
    }
}
