package AlgoritmTask.Graph;

import java.util.ArrayList;

public class Stack {
    private final ArrayList<String> stack = new ArrayList<>();

    public void push(String item) {
        stack.add(item);
    }

    public String pop() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.removeLast();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}