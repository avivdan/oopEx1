import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class WGraph_DS implements weighted_graph, java.io.Serializable{
    public static class NodeInfo implements node_info,java.io.Serializable{
        private static int idCounter = 0;
        private int Key = idCounter;
        private double tag = 0;
        private HashMap<Integer, Double> Edges;//node key, size of edge weight.
        private String info;

        public NodeInfo(NodeInfo n) {
            this.Key = n.Key;
            this.tag = n.tag;
            this.Edges = n.Edges;
            this.info = n.info;
        }

        public NodeInfo() {
            this.Key = this.idCounter;
            this.idCounter++;
            this.tag = 0;
            this.Edges = new HashMap<Integer, Double>();
            this.info = "The key : " + this.Key + "/n The tag : " + this.tag + "/n The Neighborhood size" + this.Edges.size();
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return
         */
        @Override
        public int getKey() {
            return this.Key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        //remove edge
        @Override
        public void removeEdge(int node){
            if(this.hasEdge(node))
                this.Edges.remove(node);
        }
        //create edge
        @Override
        public void connectEdge(int node, double weight){
            if(!this.hasEdge(node))
                this.Edges.put(node,weight);
        }
        //has edge?
        @Override
        public boolean hasEdge(int node){
            if(this.Edges.containsKey(node))
                return true;
            return false;
        }
        //get edge
        @Override
        public double getEdge(int node){
            return this.Edges.get(node);
        }
        //return neighbors keys
        @Override
        public Collection<Integer> getEdgesKeys(){
            HashSet<Integer> set = new HashSet<Integer>();
            for(int key : this.Edges.keySet()){
                set.add(key);
            }
            return set;
        }
    }
    private HashMap<Integer, node_info> G;
    private int Edges = 0;
    private HashMap<Integer,HashSet<Integer>> Neighbors;
    private int MC = 0;

    public WGraph_DS(WGraph_DS g) {
        this.G = new HashMap<Integer, node_info>();
        if(g.G.values().size()>0){
            for (node_info node : g.G.values()) {
                this.G.put(node.getKey(), node);
            }
        }
        //new
        this.Neighbors = new HashMap<Integer, HashSet<Integer>>();
        if(g.Neighbors.size()>0){
            for(int node: g.Neighbors.keySet()){
                this.Neighbors.put(node, g.Neighbors.get(node));
            }
        }
        //new
        this.Edges = g.Edges;
        this.MC = 0;
    }

    public WGraph_DS(){
        HashMap <Integer, node_info> g = new HashMap<Integer, node_info>();
        this.G = g;
        //new
        HashMap<Integer,HashSet<Integer>> n = new HashMap<Integer, HashSet<Integer>>();
        this.Neighbors = n;
        //new
        this.Edges = 0;
        this.MC = 0;
    }
    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return this.G.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2){
        if(this.G.get(node1)!=null&&this.G.get(node2)!=null)
            return this.Neighbors.get(node1).contains(node2);
        return false;
    }

    /**
     * return the weight if the edge (node1, node2). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {//change may be!
        if(this.G.get(node1).hasEdge(node2))
            return this.G.get(node1).getEdge(node2);
        else if(this.G.get(node2).hasEdge(node1))
            return this.G.get(node2).getEdge(node1);
        else
            return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        NodeInfo n = new NodeInfo();
        n.Key = key;
        if(!this.G.containsKey(key)){
            this.G.put(key,n);
            this.MC++;
        }
        if(!Neighbors.containsKey(key)){//2 lines above alike
            HashSet<Integer> f = new HashSet<Integer>();
            this.Neighbors.put(key,f);
            this.MC++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(!hasEdge(node1, node2) && w>=0 ){
            //checking if node1
            if(this.G.get(node1)!=null&&this.G.get(node2)!=null){
                if(node1!=node2){
                    this.G.get(node1).connectEdge(node2,w);
                    this.Edges++;
                    this.Neighbors.get(node1).add(node2);//syntax of adding neighbor to the hashset of the hashmap.
                    this.Neighbors.get(node2).add(node1);//same
                    this.MC++;
                }
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return this.G.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @param node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(this.G.get(node_id)==null)
            return null;
        HashSet<node_info> set = new HashSet<>();
        for(int key : this.Neighbors.get(node_id)){
            set.add(this.G.get(key));
        }
        return set;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if(this.G.containsKey(key)){
            for(node_info node : this.getV(key)) {
                //need to remove neighbor and edge, bothsides.
                if (hasEdge(key, node.getKey())){
                    this.removeEdge(key,node.getKey());
                    this.MC++;
                    //find me in removeEdge method
                }
            }
            this.MC++;
            return this.G.remove(key);
        }
        return null;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(this.hasEdge(node1,node2)){//
            if(this.G.get(node1).hasEdge(node2)){//two different functions
                this.G.get(node1).removeEdge(node2);
                this.Neighbors.get(node1).remove(node2);//remove from neighbors
                this.Neighbors.get(node2).remove(node1);//remove from neighbors
                this.Edges--;
                this.MC++;
            }else if(this.G.get(node2).hasEdge(node1)){
                this.G.get(node2).removeEdge(node1);
                this.Neighbors.get(node1).remove(node2);//remove from neighbors
                this.Neighbors.get(node2).remove(node1);//remove from neighbors
                this.Edges--;
                this.MC++;
            }
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.G.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return Edges;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }
}
