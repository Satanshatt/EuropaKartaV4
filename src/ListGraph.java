import java.util.*;

public class ListGraph <T> implements Graph<T> {

    private final Map<T, Set<Edge<T>>> nodes = new HashMap<>();

    @Override //KLAR
    public void add(T node) {
        nodes.putIfAbsent(node, new HashSet<>());
    }

    @Override  //KLAR
    public void remove(T node){
        if(nodes.containsKey(node)){
            nodes.remove(node);
        } else {
            throw new NoSuchElementException("Node not in graph");
        }
    }

    @Override   //KLAR
    public boolean pathExists(T from, T to) {
        if(!nodes.containsKey(from) || !nodes.containsKey(to)){
            return false;
        }
        Set<T> visited = new HashSet<>(); //"Anteckningslistan" en mängd med alla vägar
        //rekursiv funktion. Besök alla som vi kan nå från from
        depthFirstVisitAll(from, visited);
        return visited.contains(to);
    }

    private void depthFirstVisitAll(T from, Set<T> visited) {
        //anteckna i anteckningslistan
        visited.add(from);
        for (Edge<T> e: nodes.get(from)) {
            if(!visited.contains(e.getDestination())){
                depthFirstVisitAll(e.getDestination(), visited);
            }
        } //för alla kanter som vi kan nå via from
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) { //lista för vi vill ha den i ordning
        Map<T, T> connectionPath = new HashMap<>(); //vi vill koppla en node till en annan node, därför en map

        depthFirstConnection(from, null, connectionPath);
        if (!connectionPath.containsKey(to)){
            return null;
        }
        return collectPath(from, to, connectionPath);
    }

    private void depthFirstConnection(T to, T from, Map<T, T> connectionPath){
        connectionPath.put(to, from);
        for (Edge<T> edge : nodes.get(to)) {
            if (!connectionPath.containsKey(edge.getDestination())){
                depthFirstConnection((T) edge.getDestination(), to, connectionPath);
            }
        }
    }

    private List<Edge<T>> collectPath(T from, T to, Map<T, T> connectionPath){

        LinkedList<Edge<T>> collectedPath = new LinkedList<>();
        T current = to;
        while(!current.equals(from)){ //så länge current inte är lika med to
            T next = connectionPath.get(current);
            Edge<T> edge = getEdgeBetween(next, current);//hämta kanten mellan next & current
            collectedPath.addFirst(edge); //lägg till kanten
            current = next; //current är nu next
        }
        return collectedPath;
    }

    @Override  //KLAR
    public void connect (T node1, T node2, String con, int weight){
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)){
            throw new NoSuchElementException("Does not exist");
        }
        if(weight < 0){
            throw new IllegalArgumentException("Negative weight not possible");
        }

        for (Edge<T> e : nodes.get(node1)){
            if(e.getDestination().equals(node2)){
                throw new IllegalStateException("Already connected");
            }
        }
        Set<Edge<T>>node1Edges = nodes.get(node1); //ett set för nod1's kanter
        Set<Edge<T>>node2Edges = nodes.get(node2); // set för nod2's kanter

        node1Edges.add(new Edge<T>(node2, con, weight)); //här 'kopplas' noderna
        node2Edges.add(new Edge<T>(node1, con, weight));

    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {

        if(weight<0){
            throw new IllegalArgumentException("Negative weight not possible");
        }

        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)){
            throw new NoSuchElementException("Nodes does not exist");
        }

        if(getEdgeBetween(node1, node2) == null){
            throw new NoSuchElementException("Edges does not exist");
        }


        Edge<T> edge = getEdgeBetween(node1, node2);
        edge.setWeight(weight);
        edge = getEdgeBetween(node2, node1);
        edge.setWeight(weight);


    }

    @Override  //KLAR
    public Set<T> getNodes() {
        return nodes.keySet(); //keyset är bara för nyckeln, entrySet() är för båda
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        if(!nodes.containsKey(node)){
            throw new NoSuchElementException("No such node exists");
        }

        int i = 0;

        Collection<Edge<T>> copy = new HashSet<>();

        for(T nodeIterator : nodes.keySet()) {
            Edge<T> edge = this.getEdgeBetween(node, nodeIterator);

            if(edge == null) {
                continue;
            }

            copy.add(edge);
        }

        return copy;
    }

    @Override  //KLAR
    public Edge<T> getEdgeBetween(T node1, T node2) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)){
            throw new NoSuchElementException("Node does not exist");
        }
        for (Edge<T> edge : nodes.get(node1)){
            if(edge.getDestination().equals(node2)){
                return edge;
            }
        }
        return null;
    }

    @Override  //KLAR
    public void disconnect(T node1, T node2) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)){
            throw new NoSuchElementException("Does not exist");
        }
        if(getEdgeBetween(node1, node2) == null){
            throw new IllegalStateException("Fel");
        }
        Set<Edge<T>>node1Edges = nodes.get(node1);
        node1Edges.remove(getEdgeBetween(node1,node2));
        Set<Edge<T>>node2Edges = nodes.get(node2);
        node2Edges.remove(getEdgeBetween(node2, node1));

    }

    @Override
    public String toString (){

        StringBuilder sb = new StringBuilder("");

        for( Map.Entry<T, Set<Edge<T>>> nodes : this.nodes.entrySet()) {
            sb.append(nodes.getKey().toString()).append("\n\t\t");
            for(Edge<T> edges : nodes.getValue()) {
                sb.append(edges.toString()).append("\n\t\t");
            }
            sb.append("\n\n");
        }

        return sb.toString();
    }

}