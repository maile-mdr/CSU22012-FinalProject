import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.*;

public class RunningMain {
	
	static Graph stopsGraph;
	static TNT stopsTree;
	
	// Processing the Files
	private static ArrayList<String> setupFile() {
		ArrayList<String> stopsList = new ArrayList<String>();
		
		return stopsList;
		
	}
	
	// Remove all invalid inputs!
	private static void removeInvalid(ArrayList<String> stopsList) {
		
	}
	
	// Cleaning up strings (removing the prefix to make them ~~ meaningful)
	private static void cleanUpStrings(ArrayList<String> stopsList) {
		
	}
	
	// Construct graph
	private static void initGraph() {
		// Set up all the nodes (from stops.txt)
		
		// Set up all the edges from stop_times first, and then transfers.txt
		
		// Weight of stop_times: 1
		// Weight of transfers: 2 if transfer type is 0, min_time / 100 if transfer type is 2!
		
	}
	
	// HomePage
	public static boolean homePage(Graph stopsGraph /*TNT stopsTree*/) {
		// put the bulk of this code in a separate class ofc!
		// 1. Shortest Path
		/*
		 * Stops are listed in stops.txt and connections (edges) between them come from stop_times.txt and
		 * transfers.txt files. All lines in transfers.txt are edges (directed), while in stop_times.txt an edge
         * should be added only between 2 consecutive stops with the same trip_id.
		 */
		
		
		
		// 2. Search for a Stop
		/*
		 * In order for this to provide meaningful search functionality please move keywords flagstop, wb, nb,
		 * sb, eb from start of the names to the end of the names of the stops when reading the file into a TST
         * (eg “WB HASTINGS ST FS HOLDOM AVE” becomes “HASTINGS ST FS HOLDOM AVE WB”) 
		 */
		
		// clean up the strings
		// set up the TST (min. 3 characters)
		
		
		
		// 3. Search for a Trip
		// let user input time
		// validate time
		// find all the valid times
		// display them in ascending order
		
		return false;
	}
	
	// Main - run the HomePage until user enters 'quit'
	public static void main(String[] args) {
		System.out.println("Loading program...");
		
		// set everything up
		System.out.println("Processing Files...");
		ArrayList<String> stopsList = setupFile();
		
		System.out.println("Removing invalid inputs...");
		removeInvalid(stopsList);
		
		System.out.println("Cleaning up the strings...");
		cleanUpStrings(stopsList);
		System.out.println("Initialising the graph...");
		
		
		System.out.println("Successfully loaded!");
		
		// start off (do-while loop?)
	}
}
