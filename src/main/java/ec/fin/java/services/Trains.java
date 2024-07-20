package ec.fin.java.services;

import io.quarkus.logging.Log;
import org.jboss.logging.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Trains {
    private static final Logger LOG = Logger.getLogger(Trains.class);

    private Map<Character, Map<Character, Integer>> graph = new HashMap<>();

    public void addRoute(String route) {
        char start = route.charAt(0);
        char end = route.charAt(1);
        int distance = Integer.parseInt(route.substring(2));
        graph.putIfAbsent(start, new HashMap<>());
        graph.get(start).put(end, distance);
    }

    public int getRouteDistance(String route) {
        int totalDistance = 0;
        for (int i = 0; i < route.length() - 1; i++) {
            char start = route.charAt(i);
            char end = route.charAt(i + 1);
            Map<Character, Integer> neighbors = graph.get(start);
            if (neighbors == null || !neighbors.containsKey(end)) {
                return -1;  // NO SUCH ROUTE
            }
            totalDistance += neighbors.get(end);
        }
        return totalDistance;
    }

    public int countRoutesWithMaxStops(char start, char end, int maxStops) {
        return countRoutesWithMaxStopsHelper(start, end, maxStops, 0);
    }

    private int countRoutesWithMaxStopsHelper(char current, char end, int maxStops, int stops) {
        if (stops > maxStops) return 0;
        int count = (current == end && stops > 0) ? 1 : 0;
        if (graph.containsKey(current)) {
            for (char neighbor : graph.get(current).keySet()) {
                count += countRoutesWithMaxStopsHelper(neighbor, end, maxStops, stops + 1);
            }
        }
        return count;
    }

    public int countRoutesWithExactStops(char start, char end, int exactStops) {
        return countRoutesWithExactStopsHelper(start, end, exactStops, 0);
    }

    private int countRoutesWithExactStopsHelper(char current, char end, int exactStops, int stops) {
        if (stops > exactStops) return 0;
        int count = (current == end && stops == exactStops) ? 1 : 0;
        if (graph.containsKey(current)) {
            for (char neighbor : graph.get(current).keySet()) {
                count += countRoutesWithExactStopsHelper(neighbor, end, exactStops, stops + 1);
            }
        }
        return count;
    }

    public int shortestRouteDistance(char start, char end) {
        PriorityQueue<RouteNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        queue.add(new RouteNode(start, 0));

        Map<Character, Integer> shortestDistances = new HashMap<>();
        shortestDistances.put(start, 0);

        while (!queue.isEmpty()) {
            RouteNode currentNode = queue.poll();

            if (currentNode.node == end) {
                return currentNode.distance;
            }

            if (!graph.containsKey(currentNode.node)) continue;

            for (Map.Entry<Character, Integer> neighbor : graph.get(currentNode.node).entrySet()) {
                int newDist = currentNode.distance + neighbor.getValue();
                if (newDist < shortestDistances.getOrDefault(neighbor.getKey(), Integer.MAX_VALUE)) {
                    shortestDistances.put(neighbor.getKey(), newDist);
                    queue.add(new RouteNode(neighbor.getKey(), newDist));
                }
            }
        }
        return -1;  // NO SUCH ROUTE
    }


    public int countRoutesWithMaxDistance(char start, char end, int maxDistance) {
        return countRoutesWithMaxDistanceHelper(start, end, maxDistance, 0);
    }

    private int countRoutesWithMaxDistanceHelper(char current, char end, int maxDistance, int currentDistance) {
        if (currentDistance >= maxDistance) return 0;
        int count = (current == end && currentDistance > 0) ? 1 : 0;
        if (graph.containsKey(current)) {
            for (Map.Entry<Character, Integer> neighbor : graph.get(current).entrySet()) {
                count += countRoutesWithMaxDistanceHelper(neighbor.getKey(), end, maxDistance, currentDistance + neighbor.getValue());
            }
        }
        return count;
    }

    private static class RouteNode {
        char node;
        int distance;

        RouteNode(char node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }


}
