package AlgoritmTask.Graph;

import java.util.ArrayList;

public class Queue {
    private final ArrayList<String> queue = new ArrayList<>();

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void put(String item) {
        queue.add(item);
    }

    public String poll() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.removeFirst();
    }
}