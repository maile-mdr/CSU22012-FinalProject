// this class exists to represent the edges (connections)
// between the nodes (stops)
// it has 3 identifying features:
//   - a source stop
//   - a destination stop
//   - a weight!

public class Edge {

	int fromStop; // source stop
	int toStop; // destination stop
	double weight; // weight
	
	// constructor...
	public Edge(int fromStop, int toStop, double weight) {
		this.fromStop = fromStop;
		this.toStop = toStop;
		this.weight = weight;
	}
}