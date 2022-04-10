// this class exists to represent the nodes (stops) in the graph!
// a node has two things:
//  - a stopNumber
//  - information associated with it (the line associated with the stop number)

public class Node {

	int stopNumber; // ... the stop number
	String info; // line associated with the stop
	
	// constructor!
	public Node(int stopNumber, String info) {
		this.stopNumber = stopNumber;
		this.info = info;
	}
}