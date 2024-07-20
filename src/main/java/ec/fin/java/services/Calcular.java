package ec.fin.java.services;

import jakarta.enterprise.context.ApplicationScoped;
import netscape.javascript.JSObject;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Calcular {
    private static final Logger LOG = Logger.getLogger(Calcular.class);


    public  JSONObject execute(JSONObject data) {
        String entradas = data.getString("entradas");
        Trains trains = new Trains();
        String[] routes = entradas.split(",\\s*");
        for (String route : routes) {
            trains.addRoute(route);
        }

        JSONArray tests = data.getJSONArray("tests");
        List<String> responses = new ArrayList<>();
        // Process each test
        for (int i = 0; i < tests.length(); i++) {
            JSONObject test = tests.getJSONObject(i);
            String type = test.getString("type");

            switch (type) {
                case "distance":
                    String route = test.getString("route");
                    int distance = trains.getRouteDistance(route);
                    System.out.println("Output for route " + route + ": " + (distance == -1 ? "NO SUCH ROUTE" : distance));
                    responses.add("Output for route " + route + ": " + (distance == -1 ? "NO SUCH ROUTE" : distance));
                    break;

                case "maxStops":
                    char startMaxStops = test.getString("start").charAt(0);
                    char endMaxStops = test.getString("end").charAt(0);
                    int maxStops = test.getInt("maxStops");
                    int routesWithMaxStops = trains.countRoutesWithMaxStops(startMaxStops, endMaxStops, maxStops);
                    System.out.println("Output for max stops " + startMaxStops + " to " + endMaxStops + " with max " + maxStops + " stops: " + routesWithMaxStops);
                    responses.add("Output for max stops " + startMaxStops + " to " + endMaxStops + " with max " + maxStops + " stops: " + routesWithMaxStops);
                    break;

                case "exactStops":
                    char startExactStops = test.getString("start").charAt(0);
                    char endExactStops = test.getString("end").charAt(0);
                    int exactStops = test.getInt("exactStops");
                    int routesWithExactStops = trains.countRoutesWithExactStops(startExactStops, endExactStops, exactStops);
                    System.out.println("Output for exact stops " + startExactStops + " to " + endExactStops + " with " + exactStops + " stops: " + routesWithExactStops);
                    responses.add("Output for exact stops " + startExactStops + " to " + endExactStops + " with " + exactStops + " stops: " + routesWithExactStops);
                    break;

                case "shortestRoute":
                    char startShortest = test.getString("start").charAt(0);
                    char endShortest = test.getString("end").charAt(0);
                    int shortestDistance = trains.shortestRouteDistance(startShortest, endShortest);
                    System.out.println("Output for shortest route " + startShortest + " to " + endShortest + ": " + (shortestDistance == -1 ? "NO SUCH ROUTE" : shortestDistance));
                    responses.add("Output for shortest route " + startShortest + " to " + endShortest + ": " + (shortestDistance == -1 ? "NO SUCH ROUTE" : shortestDistance));
                    break;

                case "maxDistance":
                    char startMaxDist = test.getString("start").charAt(0);
                    char endMaxDist = test.getString("end").charAt(0);
                    int maxDistance = test.getInt("maxDistance");
                    int routesWithMaxDistance = trains.countRoutesWithMaxDistance(startMaxDist, endMaxDist, maxDistance);
                    System.out.println("Output for routes from " + startMaxDist + " to " + endMaxDist + " with max distance " + maxDistance + ": " + routesWithMaxDistance);
                    responses.add("Output for routes from " + startMaxDist + " to " + endMaxDist + " with max distance " + maxDistance + ": " + routesWithMaxDistance);
                    break;

                default:
                    System.out.println("Unknown test type: " + type);
                    responses.add("Unknown test type: " + type);
                    break;
            }
        }
        JSONObject result = new JSONObject();
        result.put("responses", new JSONArray(responses));
        return result;
    }

}
