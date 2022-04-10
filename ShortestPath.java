import java.util.ArrayList;

public class ShortestPath {

	// Adapted from my Assignment2 - CompetitionDijkstra.java
	// Source - https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html

	public double[] weights;
	public boolean[] visited;
	public int[] edgeTo;
	
	// keeps track of all the interim nodes between the source and destination
	public ArrayList<Node> visitedStops;
	
	// to obtain the weight of a trip!
	public double cost;
	
	// tracks whether a path between the stops is existent
	public boolean notFound = false;

	public ShortestPath(int sourceStop, int destinationStop, Graph graph) {
		
		// initialise the ArrayList to keep track of all interim nodes!
		visitedStops = new ArrayList<Node>();

		// verify that both stops are valid...
		if (graph.checkValid(sourceStop) != -1 && graph.checkValid(destinationStop) != -1) {
			
			// each node will track the associated weights (weights[]) as well 
        	// as whether the node has been visited, in visited[]
			weights = new double[graph.numNodes];
			visited = new boolean[graph.numNodes];
			
			// last edge visited on the shortest path of node @
			edgeTo = new int[graph.numNodes];
			edgeTo[graph.checkValid(sourceStop)] = -1;
			
			// setting up all the distances as infinity
			// they've also not been visited yet
			for (int i = 0; i < weights.length; i++) {
				weights[i] = Double.POSITIVE_INFINITY;
				visited[i] = false;
			}
			
			// sourceStop has a distance of 0 to itself!!
			weights[graph.checkValid(sourceStop)] = 0;
			
			// visiting all the nodes in the graph
			for (int i = 0; i < graph.numNodes - 1; i++) {
				// getting the unvisited node with the smallest weight
				int currNode = minWeight(weights, visited);
				
				// if it's been visited, we skip the next steps!
				if (currNode != -1) {
					
					//we set that node as visited
					visited[currNode] = true;
					
					// iterating through the edges
					// relaxing every single edge in the list...
					for (Edge e : graph.edgesList.get(currNode))
						relaxEdge(e, graph);
				}
			}
			
			// get the index of the destinationStop
			int destIndex = graph.checkValid(destinationStop);
			
			// find the shortest path to the destination
			// based on the updated distances (done through relaxing the edges)
			visitedStops = recordPath(edgeTo, destIndex, graph);
			
		} 
		else {
			// a path hasn't been found if the sources are invalid!
			notFound = true;
		}
	}

	/**
	 * Finds the next edge to explore in the Dijkstra algorithm: smallest weighted edge that
	 * hasn't been visited yet
	 *
	 * @param weights[]: an array that tracks the weight of the path from the source to dest node.
	 * @param visited[]: an array that tracks which nodes have been visited!
	 * @return int: returns the index of the node that a) hasn't been visited yet and b) has the 
	 * 				smallest weighted edge!
	 */
	public int minWeight(double[] weights, boolean[] visited) {
		double minWeight = 1000000000;

		int edgeIndex = -1;

		// runs through each node..
		for (int i = 0; i < visited.length; i++) {
			
			// if a node hasn't been visited yet,
			// and it has a smaller weight than the current found one
			if (!visited[i] && weights[i] <= minWeight) {
				
				// we update the minimum weight with the found one
				minWeight = weights[i];
				// and update its index
				edgeIndex = i;
			}
		}

		return edgeIndex;
	}
	
	/**
	 * this method 'relaxes' a specified edge, a.k.a it updates the paths for the
	 * nodes we visited previously if we've found a shortest path to reach it in 
	 * future passes!!
	 *
	 * @param edge: the edge to relax
	 * @param graph: graph containing surrounding nodes and edges!
	 */
	public void relaxEdge(Edge edge, Graph graph) {
		int fromNode = graph.checkValid(edge.fromStop);
		int toNode = graph.checkValid(edge.toStop);

		// if the current weight of the path to the destination node
		// is larger than the current weight to the sourceNode (the node we're checking)
		// and the weight of the edge
		if (weights[toNode] > weights[fromNode] + edge.weight) {
			
			// we update this distance with the shorter distance
			weights[toNode] = weights[fromNode] + edge.weight;
			
			// we update the last node visited
			edgeTo[toNode] = fromNode;
		}
	}

	/**
	 * This adds to the ArrayList of nodes, all the nodes visited on the way to the shortest path!
	 * It returns this ArrayList, and works recursiveley to do so!
	 *
	 * @param edgeTo: array containing the index of the last node visited as part of the shortest path
	 * @param destinationStop: .. self-explanatory, the stop of the destination Node!
	 * @param graph: the graph containing all the nodes and edges
	 * @return ArrayList<Node>: contains all the interim nodes from the source to the destinationStop
	 */ 
	public ArrayList<Node> recordPath(int[] edgeTo, int destinationStop, Graph graph) {
		
		// base case of the recursive method.. 
		if (edgeTo[destinationStop] == -1) {

			// adding the stop to the visitedStops list!!
			visitedStops.add(graph.stopsList.get(destinationStop));

			return visitedStops;
		}

		// recursive call!!!
		recordPath(edgeTo, edgeTo[destinationStop], graph);

		// add the stop to the visitedStops list!
		visitedStops.add(graph.stopsList.get(destinationStop));
		
		cost = weights[destinationStop];
		
		return visitedStops;
	}
}
