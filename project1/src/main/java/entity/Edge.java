package entity;

import lombok.Data;

import java.util.NoSuchElementException;

@Data
public class Edge implements Comparable<Edge>{
    private int v;
    private int w;
    private double weight;
    public Edge(int v, int w, Double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight(){
        return this.weight;
    }
    public int either(){
        return this.v;
    }
    public int other(int x){
        if(this.v == x)
            return w;
        else if (this.w == x)
            return v;
        else
            throw new NoSuchElementException("vertices not exist");
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString(){
        return v + "-" + w  + " " + weight;
    }
}
