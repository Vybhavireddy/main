import java.util.ArrayList;

public class MinMaxHeap {
    ArrayList<Integer> heap = new ArrayList<>();

    // ---------- INSERT ----------
    void insert(int val) {
        heap.add(val);
        int i = heap.size() - 1;
        bubbleUp(i);
    }

    private void bubbleUp(int i) {
        if (i == 0) return;
        int parent = (i - 1) / 2;
        if (isMinLevel(i)) {
            if (heap.get(i) > heap.get(parent)) {
                swap(i, parent);
                bubbleUpMax(parent);
            } else {
                bubbleUpMin(i);
            }
        } else {
            if (heap.get(i) < heap.get(parent)) {
                swap(i, parent);
                bubbleUpMin(parent);
            } else {
                bubbleUpMax(i);
            }
        }
    }

    private void bubbleUpMin(int i) {
        if (i >= 3) {
            int grand = (i - 3) / 4;
            if (heap.get(i) < heap.get(grand)) {
                swap(i, grand);
                bubbleUpMin(grand);
            }
        }
    }

    private void bubbleUpMax(int i) {
        if (i >= 3) {
            int grand = (i - 3) / 4;
            if (heap.get(i) > heap.get(grand)) {
                swap(i, grand);
                bubbleUpMax(grand);
            }
        }
    }

    // ---------- DELETE MIN ----------
    void deleteMin() {
        if (heap.isEmpty()) return;
        int last = heap.remove(heap.size() - 1);
        if (heap.isEmpty()) return;
        heap.set(0, last);
        trickleDown(0);
    }

    // ---------- DELETE MAX ----------
    void deleteMax() {
        if (heap.size() <= 1) {
            heap.clear();
            return;
        }

        int maxIndex = (heap.size() == 2) ? 1 :
                       (heap.get(1) > heap.get(2) ? 1 : 2);

        int last = heap.remove(heap.size() - 1);
        if (maxIndex < heap.size()) {
            heap.set(maxIndex, last);
            trickleDown(maxIndex);
        }
    }

    // ---------- SEARCH ----------
    boolean search(int key) {
        for (int val : heap) {
            if (val == key) return true;
        }
        return false;
    }

    // ---------- GETTERS ----------
    int getMin() {
        return heap.isEmpty() ? -1 : heap.get(0);
    }

    int getMax() {
        if (heap.isEmpty()) return -1;
        if (heap.size() == 1) return heap.get(0);
        if (heap.size() == 2) return heap.get(1);
        return Math.max(heap.get(1), heap.get(2));
    }

    // ---------- UTILITIES ----------
    private void trickleDown(int i) {
        int size = heap.size();
        int left = 2 * i + 1, right = 2 * i + 2;
        if (left >= size) return;

        int minChild = left;
        if (right < size && heap.get(right) < heap.get(left)) minChild = right;

        if (isMinLevel(i)) {
            if (heap.get(minChild) < heap.get(i)) {
                swap(i, minChild);
                trickleDown(minChild);
            }
        } else {
            int maxChild = left;
            if (right < size && heap.get(right) > heap.get(left)) maxChild = right;

            if (heap.get(maxChild) > heap.get(i)) {
                swap(i, maxChild);
                trickleDown(maxChild);
            }
        }
    }

    private boolean isMinLevel(int i) {
        int level = (int) (Math.log(i + 1) / Math.log(2));
        return level % 2 == 0; 
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    void printHeap() {
        System.out.println(heap);
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        MinMaxHeap h = new MinMaxHeap();
        h.insert(10);
        h.insert(30);
        h.insert(5);
        h.insert(20);
        h.insert(40);
        h.insert(3);

        System.out.println("Heap: ");
        h.printHeap();

        System.out.println("Min: " + h.getMin());
        System.out.println("Max: " + h.getMax());

        System.out.println("Searching 20: " + h.search(20));
        System.out.println("Searching 100: " + h.search(100));

        h.deleteMin();
        System.out.println("After deleting Min:");
        h.printHeap();

        h.deleteMax();
        System.out.println("After deleting Max:");
        h.printHeap();
    }
}
