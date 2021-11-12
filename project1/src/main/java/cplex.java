
import graph.Dijkstra;
import entity.Edge;
import entity.WeightedGraph;
import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class cplex {
    @Test
    public void LinkPath() {
        WeightedGraph graph = null;
        try(Scanner scanner = new Scanner(new FileInputStream("C:\\Users\\Vega\\IdeaProjects\\project1\\weight.txt"), StandardCharsets.UTF_8)) {
            graph = new WeightedGraph(scanner);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        Dijkstra dijkstra = new Dijkstra();
        assert graph != null;

        int K = 5;
        int demandNum = 3;
        dijkstra.OnePath(graph, 5);
        Stack<Edge> path1 = dijkstra.pathTo(10);
        Set<Stack<Edge>> KPath1 =  dijkstra.KPath(K, path1, graph, 5, 10);
        dijkstra.OnePath(graph, 5);
        Stack<Edge> path2 = dijkstra.pathTo(15);
        Set<Stack<Edge>> KPath2  = dijkstra.KPath(K, path2, graph, 5, 15);
        dijkstra.OnePath(graph, 5);
        Stack<Edge> path3 = dijkstra.pathTo(20);
        Set<Stack<Edge>> KPath3  = dijkstra.KPath(K, path3, graph, 5, 20);

        Set<Edge> edges = new HashSet<>();
        KPath1.forEach(edges::addAll);
        KPath2.forEach(edges::addAll);
        KPath3.forEach(edges::addAll);

        List<Stack<Edge>> AllPath = new ArrayList<>();
        AllPath.addAll(KPath1);
        AllPath.addAll(KPath2);
        AllPath.addAll(KPath3);

        try {
            double demand1 = 4;
            double demand2 = 6;
            double demand3 = 8;

            double[] lb = new double[demandNum*K];  //条件约束
            double[] ub = new double[demandNum*K];
            for(int i = 0; i < lb.length; i++){
                lb[i] = 0;
                ub[i] = Double.MAX_VALUE;
            }

            IloCplex cplex = new IloCplex(); // creat a model
            IloNumVar[] x = cplex.numVarArray(K*demandNum, lb, ub);


            double[] objvals = new double[demandNum*K]; //优化目标
            for(int i = 0; i < AllPath.size(); i++){
                objvals[i] = AllPath.get(i).size();
            }
            cplex.addMinimize(cplex.scalProd(x, objvals));

            double[] coeff1 = {1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
            double[] coeff2 = {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
            double[] coeff3 = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0};

            cplex.addEq(cplex.scalProd(x, coeff1), demand1);  //需求约束
            cplex.addEq(cplex.scalProd(x, coeff2), demand2);
            cplex.addEq(cplex.scalProd(x, coeff3), demand3);

            for(Edge edge : edges){     //容量约束
                double[] coeff = new double[K*demandNum];
                for(int i = 0; i < AllPath.size(); i++){
                    if(AllPath.get(i).contains(edge))
                        coeff[i] = 1;
                }
                cplex.addLe(cplex.scalProd(x, coeff), edge.getWeight());
            }

            if (cplex.solve()) {
                cplex.output().println("Solution status = " + cplex.getStatus());
                cplex.output().println("Solution value = " + cplex.getObjValue());
                double[] val = cplex.getValues(x);
                for (int k = 0; k < val.length; k++)
                    cplex.output().println("x" + (k+1) + "  = " + val[k]);
            }
            cplex.end();

        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }

    @Test
    public void NodeLink(){} //#TODO
}
