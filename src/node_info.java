
import java.util.Collection;

public interface node_info {
    /**
     * Return the key (id) associated with this node.
     * Note: each node_data should have a unique key.
     * @return
     */
    public int getKey();

    /**
     * return the remark (meta data) associated with this node.
     * @return
     */
    public String getInfo();
    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s
     */
    public void setInfo(String s);
    /**
     * Temporal data (aka distance, color, or state)
     * which can be used be algorithms
     * @return
     */
    public double getTag();
    /**
     * Allow setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    public void setTag(double t);

    /**
     * from now it my toppings
     */

    /**
     * Connect an one directional edge to given node
     * and set weight to the edge.
     * @param node
     * @param weight
     */
    public void connectEdge(int node, double weight);

    /**
     * remove one direction edge
     * @param node
     */
    public void removeEdge(int node);

    /**
     * Checks if there is an edge between this and te node we are given
     * @param node
     * @return
     */
    public boolean hasEdge(int node);

    /**
     * return the weight of the edge between this -> node
     * @param node
     * @return
     */
    public double getEdge(int node);

    /**
     * return all the neighbors in collection
     * @return Collection<Integer>
     */
    public Collection<Integer> getEdgesKeys();

}
