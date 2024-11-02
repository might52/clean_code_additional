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
}
