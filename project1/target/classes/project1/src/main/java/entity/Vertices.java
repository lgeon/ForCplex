package entity;

import lombok.Data;

@Data
public class Vertices implements Comparable<Vertices>{
    private int v;
    private double dist;
    public Vertices(int v, double dist){
        this.dist = dist;
        this.v = v;
    }

    @Override
    public int compareTo(Vertices other) {
        return Double.compare(this.dist, other.dist);
    }

//    @Override
//    public boolean equals(Object obj){
//        if(obj == null)
//            return false;
//        if (this == obj)
//            return true;
//        if(this.getClass() != obj.getClass())
//            return false;
//        Vertices other = (Vertices) obj;
//        return this.v == other.v;
//    }
}
