import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
public class WGraph_Algo_Test {
    private static Random _rnd = null;
    private static int _errors = 0, _tests = 0,_number_of_exception=0;
    private static String _log = "";


    @Test
    public void iniTest(){
        WGraph_Algo ga= new WGraph_Algo();
        WGraph_DS g = new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        ga.init(g);
        assertEquals(g.getNode(2).getTag(),-1);
    }
    @Test
    public void cloneTest(){
        WGraph_Algo ga= new WGraph_Algo();
        ga.getGraph().addNode(1);
        ga.getGraph().addNode(2);
        ga.init(ga.getGraph());
        weighted_graph d = ga.clone();
        assertEquals(d.getNode(2).getTag(),-1);
    }
    @Test
    public void isConnected(){
        WGraph_Algo ga= new WGraph_Algo();
        ga.getGraph().addNode(1);
        ga.getGraph().addNode(2);
        ga.getGraph().addNode(3);
        ga.getGraph().addNode(4);
        ga.getGraph().connect(1,2,5);
        ga.getGraph().connect(2,3,6);
        ga.getGraph().connect(3,4,7);
        ga.getGraph().connect(4,1,8);
        //round graph is easy checking for isConnected method.
        assertTrue(ga.isConnected());
    }
    @Test
    public void DijkstraSizeOnlyTest(){
        WGraph_Algo ga= new WGraph_Algo();
        ga.getGraph().addNode(1);
        ga.getGraph().addNode(2);
        ga.getGraph().addNode(3);
        ga.getGraph().addNode(4);
        ga.getGraph().connect(1,2,5);
        ga.getGraph().connect(2,3,6);
        ga.getGraph().connect(3,4,7);
        ga.getGraph().connect(2,4,6);
        assertEquals(ga.shortestPathDist(1,4),11);
    }
    @Test
    public void DijkstraSizeTest(){
        WGraph_Algo ga= new WGraph_Algo();
        ga.getGraph().addNode(1);
        ga.getGraph().addNode(2);
        ga.getGraph().addNode(3);
        ga.getGraph().addNode(4);
        ga.getGraph().connect(1,2,5);
        ga.getGraph().connect(2,3,6);
        ga.getGraph().connect(3,4,7);
//        ga.getGraph().connect(2,4,6);
        for(node_info node : ga.shortestPath(1,4)){
            System.out.print("-> "+node.getKey());
        }
        boolean b = ga.shortestPath(1, 4) instanceof List;
        assertTrue(b);
    }

}

