import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.List;
import java.util.Stack;

public class WGraph_Algo implements weighted_graph_algorithms, java.io.Serializable {
    private weighted_graph WGA;


    public WGraph_Algo(){
        this.WGA = new WGraph_DS();
    }

    public weighted_graph clone() {
        weighted_graph g = new WGraph_DS((WGraph_DS) this.WGA);
        return g;
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.WGA = g;
        for (node_info node : this.WGA.getV()) {
            node.setTag(-1);
        }
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.WGA;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public weighted_graph copy() {
        return this.clone();
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
            for (node_info nodeIn : this.WGA.getV()){
                return prettyIsConnected(nodeIn.getKey());
            }
        return true;
    }
    public boolean prettyIsConnected(int node1){
        HashSet<Integer> visited = new HashSet<Integer>();
        Queue<Integer> q = new LinkedList<Integer>();
        visited.add(node1);
        q.add(node1);
        while(!q.isEmpty()){
            int nodeQ = q.poll();
            for(node_info node : this.getGraph().getV(nodeQ)){
                if(!visited.contains(node.getKey())){
                    visited.add(node.getKey());
                    q.add(node.getKey());
                }
            }
        }
        return visited.size()==this.getGraph().nodeSize();
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        this.init(this.WGA);//set tags to -1
        Dijkstra_Algorithm_SizesOnly(src);
        return this.WGA.getNode(dest).getTag();
    }

    /**
     * This method work better for shortestPath function
     *
     * @param src
     */
    public void Dijkstra_Algorithm_SizesOnly(int src) {
        PriorityQueue<Integer> MinPQ = new PriorityQueue<>();//should extract minimum as always
        HashSet<Integer> visited = new HashSet<>();

        this.WGA.getNode(src).setTag(0.0);
        MinPQ.add(src);
        while (!MinPQ.isEmpty()) {
            int SmallestOne = MinPQ.poll();
            for (int node : this.WGA.getNode(SmallestOne).getEdgesKeys()) {//need to get the ones he pointing at
                if (!visited.contains(node)) {
                    double altPath = this.WGA.getNode(SmallestOne).getTag() + this.WGA.getNode(SmallestOne).getEdge(node);
                    if(this.getGraph().getNode(node).getTag() == -1|| altPath< this.getGraph().getNode(node).getTag()){
                        this.getGraph().getNode(node).setTag(altPath);
                        MinPQ.add(node);
                    }
                }
            }
        }
    }


    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        this.init(this.WGA);
        return reconstruct(Dijkstra_Algorithm(src), dest);
    }


    /**
     * Reconstruct path from dest to src
     * Starting from dest to src while we need return src to dest
     * Stack is light and simple method for this need.
     */
    public List<node_info> reconstruct(HashMap<Integer, Integer> PrevNodes, int dest) {
        Stack<node_info> St = new Stack<>();
        int node = dest;
        //start from the destination and follow back to source src.tag == 0
        while (this.WGA.getNode(node).getTag() > 0) {
            St.push(this.WGA.getNode(node));
            node = PrevNodes.get(node);
        }
        St.push(this.WGA.getNode(node));//not forgetting the src

        List<node_info> re = new LinkedList<>();//new list
        while (!St.isEmpty()) {
            re.add(St.pop());
        }
        return re;
    }


    /**
     * Dijkstra Algorithm which return a map
     * we can reconstruct from the map shortest path to a node.
     *
     * @param src
     * @return
     */
    public HashMap<Integer, Integer> Dijkstra_Algorithm(int src) {
        //init set all tags to -1
        HashMap<Integer, Integer> PrevNodes = new HashMap<>();
        PriorityQueue<Integer> MinPQ = new PriorityQueue<>();//should extract minimum as always
        HashSet<Integer> visited = new HashSet<>();
        this.WGA.getNode(src).setTag(0.0);
        MinPQ.add(src);
        while (!MinPQ.isEmpty()) {
            int SmallestOne = MinPQ.poll();
            for (int node : this.WGA.getNode(SmallestOne).getEdgesKeys()) {//need to get the ones he pointing at
                if (!visited.contains(node)) {
                    double altPath = this.WGA.getNode(SmallestOne).getTag() + this.WGA.getNode(SmallestOne).getEdge(node);
                    if(this.getGraph().getNode(node).getTag() == -1|| altPath< this.getGraph().getNode(node).getTag()){
                        this.getGraph().getNode(node).setTag(altPath);
                        PrevNodes.put(node, SmallestOne);
                        MinPQ.add(node);
                    }
                }
            }
        }
        return PrevNodes;
    }


    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(this);
            oos.close();
            fileOut.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            weighted_graph_algorithms g  = (weighted_graph_algorithms) ois.readObject();
            ois.close();
            fileIn.close();
            this.init(g.getGraph());
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
