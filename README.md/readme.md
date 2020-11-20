<h1>Ex1 Project</h1>
<h2>
node_info
</h2>
<h5>We had the usual methods like:</h5>
<ul>
<li>getKey</li> 
<li>getInfo</li> 
<li>getTag</li> 
</ul>
And set to all of them.<br>
since the graph is undirected I used Edges (HashSet{int, double})
while Integer for the key and Double for the edge size it moves to.
<br>so for edges we have the functions:
<ul>
<li>Connect</li>
<li>remove</li>
<li>has</li>
<li>get</li>
</ul>



<h2>weighted_graph</h2>
<h5>DS is build in this structure:</h5>
<ul>
<li>G (HashSet{Integer, node_info}) - > Node and it's key</li>
<li>Neighbors (HashSet{Integer,HashSet{Integers}}) To know all neighbors of every node.</li>
<li>Edges (int) size of edges</li>
<li>node_info</li>
</ul>
<h5>This functions</h5>
<ul>
<li>hasEdge -> check in node neighbors</li>
<li>getEdge -> return double</li>
<li>removeEdge</li>
<li>addNode -> the node can be created after we create the graph itself.</li>
<li>getNode</li>
<li>removeNode</li>
<li>connect - > between 2 nodes</li>
<li>getV -> all nodes</li>
<li>getV(node) -> all Neighbors in o(k)</li>
<li>nodeSize -> getV size</li>
<li>edgeSize => this.Edges</li>
</ul>


<h2>weighted_graph_algorithms</h2>
<h5>DS is build in this structure:</h5>
weighted_graph WGA -> the graph That we manipulate
<h5>This functions</h5>
<ul>
<li>init</li> 
<li>clone</li> 
<li>getGraph</li> 
<li>isConnected - <a href="https://en.wikipedia.org/wiki/Breadth-first_search">BFS implementation</a></li> 
    <li>shortestPathDist -  
    <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">
         Dijkstra Algorithm implementation</a> 
</li> 
<li>shortestPath - also
<a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">
     Dijkstra Algorithm </a> 
implementation that return list</li> 
<li>save - save an object to file</li> 
<li>load - load object to class</li> 
</ul>







