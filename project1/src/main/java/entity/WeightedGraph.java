package entity;

import graph.Dijkstra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WeightedGraph {
    private int v;
    private int e;
    private LinkedList<Edge>[] adj;

    public WeightedGraph(int v){
        this.v = v;
        this.e = 0;
        adj = new LinkedList[v];
        for(int i = 0; i < v; i++)
            adj[i] = new LinkedList<Edge>();
    }

    public WeightedGraph(Scanner scanner){
        this(scanner.nextInt());
        int e = scanner.nextInt();
        for(int i = 0; i < e; i++){
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            Double weight = scanner.nextDouble();
            Edge edge = new Edge(v, w, weight);
            addEdge(edge);
        }
    }

    public int getV(){
        return this.v;
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        this.e++;
    }

    public List<Edge> getEdge(int v){
        return adj[v];
    }
    public List<Edge> getEdges(){
        List<Edge> edges = new LinkedList<>();
        for(LinkedList<Edge> adjs : adj){
            adjs.forEach((Edge e)->{
                edges.add(e);
            });
        }
        edges.sort(Comparator.comparing(Edge::getWeight));
        return edges;
    }


    public String toString(){
        String str = "vertices:" + this.v + "edges:" + this.e + "\n";
        String temp = "";
        for(int i = 0; i < v; i++){
            temp += i + ":";
            for(Edge e : adj[i]){
                temp += e.toString() + ", ";
            }
            temp += "\n";
        }
        return str + temp;
    }
}
