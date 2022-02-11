import javax.crypto.spec.DESedeKeySpec;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {

    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     *
     * @param g       The graph to use.
     * @param stlon   The longitude of the start location.
     * @param stlat   The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        long start = g.closest(stlon, stlat);
        long end = g.closest(destlon, destlat);
        Map<Long, Boolean> marked = new HashMap<>();
        Map<Long, Double> bestDis = new HashMap<>();
        Map<Long, Long> edgeTo = new HashMap<>();
        PriorityQueue<Node> fringe = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                if (node.weight < t1.weight) {
                    return -1;
                } else if (node.weight == t1.weight) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        fringe.add(new Node(start, 0.0));
        bestDis.put(start, 0.0);
        while (!fringe.isEmpty()) {
            Node v = fringe.poll();
            if (marked.containsKey(v.id) && marked.get(v.id) == true) {
                continue;
            }
            if (v.id == end) {
                break;
            }
            marked.put(v.id, true);
            for (long w : g.adjacent(v.id)) {
                if (!bestDis.containsKey(w) || bestDis.get(v.id) + g.distance(v.id, w) < bestDis.get(w)) {
                    bestDis.put(w, bestDis.get(v.id) + g.distance(v.id, w));
                    edgeTo.put(w, v.id);
                    fringe.add(new Node(w, bestDis.get(w) + g.distance(w, end)));
                }
            }
        }
        Deque<Long> deque = new ArrayDeque<>();
        long temp = end;
        while (temp != start) {
            deque.addFirst(temp);
            temp = edgeTo.get(temp);
        }
        deque.addFirst(temp);
        List<Long> list = new ArrayList<>();
        for (Long v : deque) {
            list.add(v);
        }
        return list;
    }

    private static class Node {
        long id;
        double weight;

        public Node(long v, double weight) {
            this.id = v;
            this.weight = weight;
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     *
     * @param g     The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> list = new ArrayList<>();
//        "%s on %s and continue for %.3f miles.",
        Iterator<Long> routeIterator = route.iterator();
        long from = routeIterator.next();
        long to = routeIterator.next();

        double distance = g.distance(from, to);
        int direction = 0;
        boolean flag = false;
        String fromName = "";
        String toName = "";
//        Start on Shattuck Avenue for 0.5 miles.
        while (routeIterator.hasNext()) {
            if(g.getWayName(from) != null){
                fromName = g.getWayName(from);
            }
            if(g.getWayName(to) != null){
                toName = g.getWayName(to);
            }
            if (!fromName.equals(toName)) {
                if (flag) {
                    direction = getDirection(g, from, to);
                }else{
                    flag = true;
                }

                NavigationDirection navigationDirection = new NavigationDirection();
                navigationDirection.direction = direction;
                navigationDirection.distance = distance;
                navigationDirection.way = fromName;
                distance = 0;
                list.add(navigationDirection);
                System.out.println(navigationDirection);
            }
            from = to;
            to = routeIterator.next();
            distance += g.distance(from, to);
        }
        System.out.println(list);
        return list; // FIXME
    }

    public static int getDirection(GraphDB g, long v, long w) {
        double angel = g.bearing(v, w);
        if (Math.abs(angel) <= 15) {
            return 1;
        } else if (Math.abs(angel) <= 30) {
            if (angel < 0) {
                return 2;
            } else {
                return 3;
            }
        } else if (Math.abs(angel) <= 100) {
            if (angel < 0) {
                return 4;
            } else {
                return 5;
            }
        } else {
            if (angel < 0) {
                return 6;
            } else {
                return 7;
            }
        }
    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /**
         * Integer constants representing directions.
         */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /**
         * Number of directions supported.
         */
        public static final int NUM_DIRECTIONS = 8;

        /**
         * A mapping of integer values to directions.
         */
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /**
         * Default name for an unknown way.
         */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /**
         * The direction a given NavigationDirection represents.
         */
        int direction;
        /**
         * The name of the way I represent.
         */
        String way;
        /**
         * The distance along this way I represent.
         */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         *
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                        && way.equals(((NavigationDirection) o).way)
                        && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
