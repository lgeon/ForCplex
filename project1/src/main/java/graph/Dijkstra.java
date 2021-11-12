package graph;

import entity.Edge;
import entity.Vertices;
import entity.Heap;
import entity.WeightedGraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Dijkstra {
    private double[] distTo;
    private Edge[] pathTo;
    private Heap<Vertices> pq;
    private Set<Stack<Edge>> paths;

    public void OnePath(WeightedGraph graph, int source){
        distTo = new double[graph.getV()];
        for(int i = 0; i < graph.getV(); i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        distTo[source] = 0;
        pathTo = new Edge[graph.getV()];
        pq = new Heap<>(graph.getV());
        pq.addElement(new Vertices(source, 0));
        while(pq.getSize() != 0){
            relax(graph, pq.removeElement().getV());
        }
    }

    public Set<Stack<Edge>> KPath(int k, Stack<Edge> onePath, WeightedGraph graph, int source, int destination){
        paths = new HashSet<>();
        paths.add(onePath);
        Set<Stack<Edge>> pathSet = new HashSet<>();
        DeviatePath(onePath, graph, pathSet, source, destination, k);
        return paths;
    }

    public void DeviatePath(Stack<Edge> onePath, WeightedGraph graph, Set<Stack<Edge>> pathSet, int source, int destination, int k){
        if(paths.size() >= k )
            return;
        Stack<Edge> minPath = new Stack<>();
        Stack<Edge> tempPath = (Stack<Edge>) onePath.clone();
        Stack<Edge> prePath = new Stack<>();
        double minWeight = Double.POSITIVE_INFINITY;

        int v = source;
        while (!tempPath.isEmpty()) {
            Edge e = tempPath.pop();
            double weight = e.getWeight();
            e.setWeight(Double.POSITIVE_INFINITY);
            OnePath(graph, v);
            e.setWeight(weight);
            Stack<Edge> secPath = pathTo(destination);
            Stack<Edge> tempPre = (Stack<Edge>) prePath.clone();
            for(int i = 0; i < prePath.size(); i++){
                secPath.push(tempPre.pop());
            }
            v = e.other(v);
            pathSet.add(secPath);
            prePath.push(e);
        }

        do {
            for (Stack<Edge> path : pathSet) {
                double pathWeight = path.stream().mapToDouble(Edge::getWeight).sum();
                if (pathWeight < minWeight) {
                    minWeight = pathWeight;
                    minPath = path;
                }
            }
            pathSet.remove(minPath);
            minWeight = Double.POSITIVE_INFINITY;
        }while (! paths.add(minPath));
        DeviatePath(minPath, graph, pathSet, source, destination, k);
    }

    private void relax(WeightedGraph graph, int v){
        for(Edge e : graph.getEdge(v)){
             int w = e.other(v);
            if(distTo[w] > distTo[v] + e.getWeight()){
                distTo[w] = distTo[v] + e.getWeight();
                pathTo[w] = e;
                if(pq.contains(w))
                    pq.changeDist(w, distTo[w]);
                else
                    pq.addElement(new Vertices(w, distTo[w]));
            }
        }
    }

    public Stack<Edge> pathTo(int v){
        Stack<Edge> path = new Stack<>();
        Edge e = pathTo[v];
        int from = e.other(v);
        while(true){
            path.push(e);
            e = pathTo[from];
            if(e == null)
                break;
            from = e.other(from);
        }
        return path;
    }
}
