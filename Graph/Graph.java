package AlgoritmTask.Graph;

import java.util.*;

public class Graph {

    public static void bfs(Map<String, List<String>> graph, String start) {
        Set<String> visited = new HashSet<>();
        Queue queue = new Queue();

        visited.add(start);
        queue.put(start);

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            System.out.print(vertex + " ");

            for (String neighbor : graph.getOrDefault(vertex, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.put(neighbor);
                }
            }
        }
    }

    public static void dfsIterative(Map<String, List<String>> graph, String start) {
        Set<String> visited = new HashSet<>();
        Stack stack = new Stack();

        stack.push(start);

        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                System.out.print(vertex + " ");

                List<String> neighbors = graph.getOrDefault(vertex, Collections.emptyList());
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    String neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }

    public static Map<String, Integer> bfsDistances(Map<String, List<String>> graph, String start) {
        Map<String, Integer> dist = new HashMap<>();
        Queue queue = new Queue();

        dist.put(start, 0);
        queue.put(start);

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            int currentDist = dist.get(vertex);

            for (String neighbor : graph.getOrDefault(vertex, Collections.emptyList())) {
                if (!dist.containsKey(neighbor)) {
                    dist.put(neighbor, currentDist + 1);
                    queue.put(neighbor);
                }
            }
        }
        return dist;
    }

    static void main() {
        // Входные данные — ваш граф
        Map<String, List<String>> graph = Map.of(
                "A", List.of("B", "D", "E"),
                "B", List.of("A", "C", "D", "E"),
                "C", List.of("B", "F"),
                "D", List.of("A", "B"),
                "E", List.of("A", "B", "F"),
                "F", List.of("C", "E", "G", "H"),
                "G", List.of("F", "H"),
                "H", List.of("F", "G")
        );

        String start = "A";

        System.out.print("BFS от " + start + ": ");
        bfs(graph, start);
        System.out.println();

        System.out.print("DFS от " + start + ": ");
        dfsIterative(graph, start);
        System.out.println();

        System.out.println("Расстояния от " + start + ":");
        Map<String, Integer> dist = bfsDistances(graph, start);
        for (Map.Entry<String, Integer> e : dist.entrySet()) {
            System.out.println("  До " + e.getKey() + ": " + e.getValue());
        }
    }
}