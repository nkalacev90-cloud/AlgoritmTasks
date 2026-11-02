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

    public static Map<String, Integer> dijkstraDistances(
            Map<String, Map<String, Integer>> graph, String start) {

        Map<String, Integer> dist = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());

        dist.put(start, 0);
        pq.add(Map.entry(start, 0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> current = pq.poll();
            String vertex = current.getKey();
            int currentDist = current.getValue();

            // устаревшая запись — пропускаем
            if (currentDist > dist.getOrDefault(vertex, Integer.MAX_VALUE)) continue;

            for (Map.Entry<String, Integer> edge :
                    graph.getOrDefault(vertex, Collections.emptyMap()).entrySet()) {
                String neighbor = edge.getKey();
                int weight = edge.getValue();
                int newDist = currentDist + weight;

                if (newDist < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, newDist);
                    pq.add(Map.entry(neighbor, newDist));
                }
            }
        }
        return dist;
    }


    public static void tsg(Map<String, List<String>> originalGraph) {
        // 1. Копия графа
        Map<String, List<String>> graph = new TreeMap<>();
        for (var e : originalGraph.entrySet()) {
            graph.put(e.getKey(), new ArrayList<>(e.getValue()));
        }

        // 2. Входящие степени
        Map<String, Integer> inDegree = new TreeMap<>();
        for (String v : graph.keySet()) inDegree.put(v, 0);
        for (List<String> neighbors : graph.values()) {
            for (String n : neighbors) {
                inDegree.merge(n, 1, Integer::sum);
            }
        }

        // 3. Очередь с приоритетом по алфавиту
        PriorityQueue<String> queue = new PriorityQueue<>();
        for (var e : inDegree.entrySet()) {
            if (e.getValue() == 0) queue.add(e.getKey());
        }

        List<String> result = new ArrayList<>();

        // 4. Основной цикл
        while (!queue.isEmpty()) {
            String v = queue.poll();
            result.add(v);
            for (String neighbor : graph.getOrDefault(v, List.of())) {
                int deg = inDegree.merge(neighbor, -1, Integer::sum);
                if (deg == 0) queue.add(neighbor);
            }
        }

        // ✅ Сравниваем с inDegree, а не с graph
        if (result.size() != inDegree.size()) {
            throw new IllegalStateException("Граф содержит цикл");
        }
        Collections.reverse(result);
        System.out.println("Подсортировка: " + result);
    }

    public static Map<String, Map<String, Integer>> PMST(
            Map<String, Map<String, Integer>> graph, String start) {

        Map<String, Map<String, Integer>> result = new HashMap<>();

        if (!graph.containsKey(start)) {
            throw new IllegalArgumentException("Стартовая вершина отсутствует в графе: " + start);
        }

        Set<String> visited = new HashSet<>();
        visited.add(start);

        Map<String, Map<String, Integer>> heap = new HashMap<>();
        heap.put(start, new HashMap<>(graph.getOrDefault(start, Map.of())));

        while (visited.size() < graph.size()) {
            String bestFrom = null;
            String bestTo = null;
            int bestWeight = Integer.MAX_VALUE;

            // линейно ищем минимальное ребро из фронтира
            for (Map.Entry<String, Map<String, Integer>> entry : heap.entrySet()) {
                String from = entry.getKey();
                for (Map.Entry<String, Integer> edge : entry.getValue().entrySet()) {
                    String to = edge.getKey();
                    int w = edge.getValue();

                    if (visited.contains(to)) continue;   // уже в MST
                    if (w < bestWeight) {
                        bestWeight = w;
                        bestFrom = from;
                        bestTo = to;
                    }
                }
            }

            // фронтир пуст, а вершины ещё остались → граф несвязный
            if (bestTo == null) {
                throw new IllegalStateException("Граф несвязный — MST не существует");
            }

            // добавляем ребро в результат
            result.computeIfAbsent(bestFrom, k -> new HashMap<>())
                    .put(bestTo, bestWeight);

            // помечаем вершину посещённой
            visited.add(bestTo);

            // добавляем её рёбра в фронтир
            heap.computeIfAbsent(bestTo, k -> new HashMap<>())
                    .putAll(graph.getOrDefault(bestTo, Map.of()));

            // чистим heap от рёбер в уже посещённые вершины (опционально, для чистоты)
            for (Map<String, Integer> edges : heap.values()) {
                edges.keySet().removeIf(visited::contains);
            }
        }

        return result;
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

        Map<String, List<String>> graph1 = Map.of(
                "A", List.of(),
                "B", List.of("A"),
                "C", List.of("D", "B"),
                "D", List.of(),
                "F", List.of("C", "D")
        );

        Map<String, Map<String, Integer>> graph2 = Map.of(
                "A", Map.of("B", 4, "C", 2),
                "B", Map.of("C", 5, "D", 10),
                "C", Map.of("E", 3),
                "D", Map.of("F", 11),
                "E", Map.of("D", 4)
        );

        Map<String, Map<String, Integer>> graph3 = Map.of(
                "A", Map.of("B", 4, "C", 2),
                "B", Map.of("C", 5, "D", 10),
                "C", Map.of("E", 3),
                "D", Map.of("F", 11),
                "E", Map.of("D", 4)
        );

        String start = "A";

        System.out.print("BFS от " + start + ": ");
        bfs(graph, start);
        System.out.println();

        System.out.print("DFS от " + start + ": ");
        dfsIterative(graph, start);
        System.out.println();

        Map<String, Integer> result = dijkstraDistances(graph2, "A");

        System.out.println("Кратчайшие расстояния от A:");
        result.forEach((v, d) -> System.out.println("  A -> " + v + " = " + d));


        System.out.println(PMST(graph3, "A"));


        tsg(graph1);
    }
}
