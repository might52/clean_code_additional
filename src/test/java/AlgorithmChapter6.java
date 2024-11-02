import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmChapter6 {

    @Test
    public void graphTest() {
        HashMap<String, List<String>> graph = new HashMap<String, List<String>>();
        List<String> level = new ArrayList<>();
        level.add("alice");
        level.add("bob");
        level.add("claire");
        graph.put("you", level);
        level = new ArrayList<>();
        level.add("anuj");
        level.add("peggy");
        graph.put("bob", level);
        level = new ArrayList<>();
        level.add("peggy");
        graph.put("alice", level);
        level = new ArrayList<>();
        level.add("thom");
        level.add("jonny");
        graph.put("claire", level);
        graph.put("anuj", new ArrayList<>());
        graph.put("peggy", new ArrayList<>());
        graph.put("thom", new ArrayList<>());
        graph.put("jonny", new ArrayList<>());
        findSellerPerson(graph);
    }

    private boolean findSellerPerson(Map<String, List<String>> graph) {
        Deque<String> queue = new ArrayDeque<>();
        queue.addAll(graph.get("you"));
        Map<String, String> searched = new HashMap<>();
        while (!queue.isEmpty()) {
            String person = queue.poll();
            if (searched.containsKey(person)) {
                continue;
            }
            System.out.println(person);
            if (personIsSeller(person)) {
                System.out.println("This person is seller: " + person);
                return true;
            } else {
                queue.addAll(graph.get(person));
                searched.put(person, person);
            }
        }
        System.out.println("We didn't find the seller person");
        return false;
    }

    private boolean personIsSeller(String person) {
        return person.endsWith("m");
    }

    @Test
    public void deikstraTest() {
        Map<String, Map<String, Integer>> graph = new HashMap<>();
        Map<String, Integer> level = new HashMap<>();
        level.put("a", 6);
        level.put("b", 2);
        graph.put("start", level);
        level = new HashMap<>();
        level.put("fin", 1);
        graph.put("a", level);
        level = new HashMap<>();
        level.put("a", 3);
        level.put("fin", 5);
        graph.put("b", level);
        level = new HashMap<>();
        graph.put("fin", level);
        Map<String, Integer> costs = new HashMap<>();
        costs.put("a", 6);
        costs.put("b", 2);
        costs.put("fin", 1000);
        Map<String, String> parents = new HashMap<>();
        parents.put("a", "start");
        parents.put("b", "start");
        parents.put("fin", "None");
        System.out.println("Lowest cost to find proper way: " + findLowestCost(graph, costs, parents));
    }

    private String findLowestCost(Map<String, Map<String, Integer>> graph, Map<String, Integer> costs, Map<String, String> parents) {
        List<String> processed = new ArrayList<>();
        String node = findLowestCostNode(costs, processed);
        while (node != null) {
            Integer cost = costs.get(node);
            Map<String, Integer> neighbors = graph.get(node);
            for (String neighbor : neighbors.keySet()) {
                Integer newCost = cost + neighbors.get(neighbor);
                if (costs.get(neighbor) > newCost) {
                    costs.put(neighbor, newCost);
                    parents.put(neighbor, node);
                }
            }
            processed.add(node);
            node = findLowestCostNode(costs, processed);
        }
        return parents.get("fin");
    }

    private String findLowestCostNode(Map<String, Integer> costs, List<String> processed) {
        Integer lowestCost = 1000;
        String lowestCostNode = null;
        for (String node : costs.keySet()) {
            Integer cost = costs.get(node);
            if (cost < lowestCost && !processed.contains(node)) {
                lowestCost = cost;
                lowestCostNode = node;
            }
        }
        return lowestCostNode;
    }
}
