import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException; 

public class Graph {
	
	// Source - https://algs4.cs.princeton.edu/42digraph/Digraph.java.html
	// Source - https://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html

	final static String TRANSFERS = "transfers.txt";
	public int numNodes;
	
	public ArrayList<Node> stopsList = new ArrayList<>();
	
	// to represent the edges, we're doing an ArrayList of ArrayList of edges!
	public ArrayList<ArrayList<Edge>> edgesList;
	//public ArrayList<Trip> trips;
	
	// Constructor...
	public Graph(ArrayList<String> stopsArray, ArrayList<String> stopTimes) {
		
		// set up all the stops, based on the pre-existing stopsArray ArrayList
		setUpNodes(stopsArray);
		
		// update the number of Nodes!
		this.numNodes = stopsList.size();
		
		// initiate the list of allllll existing edges in the graph,
		// based on the calculated number of nodes
		// this takes the shape of an ArrayList of ArrayList<Edge>
		edgesList = new ArrayList<>(numNodes);
		
		// initiating all the inner ArrayList in the edgesList
		for (int i = 0; i < stopsList.size(); i++) {
			edgesList.add(new ArrayList<>());
			
		}
		
		// setting up all the stop_times.txt edges, based on the validated stop_times (done in Part 3)
		setUpEdges(stopTimes);
		
		// setting up all the transfers.txt edges!
		setUpTransfers();
	}
	
	/**
	 * As explained in the method name, this method sets up the Node objects (stops)
	 * in the graph, based on the stopsList ArrayList<String> that was previously
	 * initialised in part 2 of the program (see setUpTST() in UserInterface.java)
	 * This design decision was made to avoid re-reading a file again!
	 *
	 * @param stopsArray: an ArrayList<String> defined in a previous section of the program,
	 * 					  it contains the lines of stops.txt as an ArrayList
	 */
	private void setUpNodes(ArrayList<String> stopsArray) {
		
		// runs through every stop
		for (int i = 0; i < stopsArray.size(); i++) {
			String line = stopsArray.get(i);
			
			// Source - https://www.geeksforgeeks.org/split-string-java-examples/
			String[] split = line.split(",");
			
			// accesses the first data of the line (the stopID)
			int stopID = Integer.parseInt(split[0]);
			
			// see Node.java for more documentation
			stopsList.add(new Node(stopID, line));
		}
	}
	
	/**
	 * this method serves a dual purpose - it checks whether a stopID is a valid Node
	 * in the graph, and if so, it returns its index in the ArrayList data structure.
	 * If the stopID isn't valid, the method returns -1
	 *
	 * @param stopID: 
	 * @return int: the index of the validated stopID :)
	 */
	public int checkValid(int stopID) {
		for (int i = 0; i < stopsList.size(); i++) {
			if (stopsList.get(i).stopNumber == stopID) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * This method sets up the edges in the graph from the stop_times.txt. 
	 * It takes in an ArrayList, stopTimes, representing a previously used
	 * ArrayList in Part 3. The reason why a parameter is used instead of 
	 * re-reading the file in directly is for efficiency reasons, as the stopTimes
	 * has already removed any invalid times (hence invalid edges) that appear
	 * in the graph
	 *
	 * @param stopTimes: the time to search for matches for, as a string
	 */
	private void setUpEdges(ArrayList<String> stopTimes) {
		
		// loops through each line of the stop_times.txt
		for (int i = 0; i < stopTimes.size() - 1; i++) {
			
			// getting two adjacent lines
			String line1 = stopTimes.get(i);
			String line2 = stopTimes.get(i+1);
			
			// Source - https://www.geeksforgeeks.org/split-string-java-examples/
			// splitting them up into an array to access the data points individually
			String[] splitStop1 = line1.split(",");
			String[] splitStop2 = line2.split(",");
			
			// accessing the trip ID
			int tripID1 = Integer.parseInt(splitStop1[0]);
			int tripID2 = Integer.parseInt(splitStop2[0]);
			
			// if the tripID is the same, then we can add an edge
			if (tripID1 == tripID2) {
				
				// get the stops
				int stopID1 = Integer.parseInt(splitStop1[3]);
				int stopID2 = Integer.parseInt(splitStop2[3]);
				
				// add an edge between those stops of weight 1
				edgesList.get(checkValid(stopID1)).add(new Edge(stopID1, stopID2, 1));
			}
			
		}
	}
	
	/**
	 * This method processes the transfers.txt file, adding the edges as the lines
	 * are read and determining their weights.
	 */
	private void setUpTransfers() {
		try {
			
			// reading in the transfers.txt file 
			BufferedReader buffRead = new BufferedReader(new FileReader(TRANSFERS));
			
			String line;
			// skipping first line
			line = buffRead.readLine();
			while ((line = buffRead.readLine()) != null) {
				
				// Source - https://www.geeksforgeeks.org/split-string-java-examples/
				// splitting up the line to access individual data points
				String[] splitStop = line.split(",");
				
				// getting the stopIDs and transfer type
				int stopID1 = Integer.parseInt(splitStop[0]);
				int stopID2 = Integer.parseInt(splitStop[1]);
				int transferType = Integer.parseInt(splitStop[2]);
				
				// transfer type = 0, edge of weight 2 as per the assignment instructions
				if (transferType == 0) {				
					edgesList.get(checkValid(stopID1)).add(new Edge(stopID1, stopID2, 2));
				}
				// transfer type = 2, calculate the weight of the edge!
				else if (transferType == 2) {
					
					// set the transferTime from the minimum time
					double transferTime = Integer.parseInt(splitStop[3]);
					
					// get the weight!
					transferTime /= 100;
					
					// adding the edge based on the calculated weight
					edgesList.get(checkValid(stopID1)).add(new Edge(stopID1, stopID2, transferTime));
				}
			}
			
			buffRead.close();
			
		} catch (FileNotFoundException e) {
			stopsList = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}