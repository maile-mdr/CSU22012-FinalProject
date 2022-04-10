import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.*;

public class UserInterface {
	
	/* LAYOUT
	 * 1. Instance Variables
	 * 2. Functions
	 * 	 a. Part 1 
	 * 	 b. Part 2
	 *   c. Part 3
	 * 3. Homepage
	 * 4. Bibliography
	 */
	
	// --------------------------- INSTANCE VARIABLES -------------------------
	
	final static String STOPS = "stops.txt";
	final static String STOP_TIMES = "stop_times.txt";
	
	static Scanner scanner = new Scanner(System.in);
	
	// Part 1 - Variables
	public static Graph stopsGraph;
	
	// Part 2 - Variables
	public static TST<Integer> stopsTree = new TST<Integer>();
	static ArrayList<String> stopsList = new ArrayList<String>();
	
	// Part 3 - Variables
	private static ArrayList<String> times = new ArrayList<String>();
	static ArrayList<String> stopTimes = new ArrayList<String>();
	private static ArrayList<String> matchLines = new ArrayList<String>();
	
	// Constants - used for error-checking
	private static final int HRS_UPPER = 23;
	private static final int MINS_UPPER = 59;
	private static final int SECS_UPPER = 59;
	
	// -------------------------------- FUNCTIONS -------------------------------

	// --------------------------------   PART 1  ------------------------------- 
	
	/**
	 * this method finds the shortest path between two stops, and displays all the
	 * stops on the way! It makes use of the ShortestPath.java class, and accesses
	 * its instance variable visitedStops to display all the visited stops en route!
	 *
	 * @param stop1: the source stopID
	 * @param stop2: the destination stopID
	 */
	private static void printShortestPath(int stop1, int stop2) {
		
		// creating a new shortestPath object... based on the user-entered inputs
		// and the pre-existing Graph with the pre-validated stops :)
        ShortestPath sp = new ShortestPath(stop1, stop2, stopsGraph);
        
        // if the stops are the same...
        if (stop1 == stop2) {
        	
        	// outputs the information of that single stop for efficiency!!...
        	int i = stopsGraph.checkValid(stop1);
        	System.out.println(stopsGraph.stopsList.get(i).info);
        	
        	// misc. outputs! want to display the cost of a trip
        	System.out.println("Cost of trip: " + sp.cost);
        }
        // if the stops are DIFFERENT and there is an existing path...
        else if (stop1 != stop2 && !sp.notFound) {
        	// we copy the trace of all the intermediate stops from stop1 to stop2 (including those two)
        	ArrayList<Node> route = sp.visitedStops;
        	
            // and loop through each element of the ArrayList to display the intermediate stops
            for (int i = 0; i < route.size(); i++) {
                Node currentStop = route.get(i);
                System.out.println(currentStop.info);
            }
            
            System.out.println("Cost of trip: " + sp.cost);
        } 
        // stops are different.. but there may not be an existing path
        else {
            System.out.println("No path found. Are your inputs correct?");
        }
    }
	
	// --------------------------------   PART 2  ------------------------------- 
	/**
	 * this method reads the stops.txt file line by line, isolates the name of each stop
	 * and adds it to the TST. It also adds each line to the stopsList ArrayList instance variable!
	 * 
	 * the method calls the stringCleanup method, as specified in the project document, in
	 * order to make the stop names more meaningful (by moving the eb, sb, nb, wb and flagstop
	 * to the end of the string)
	 *
	 * @return void...
	 */
	public static void setUpTST() {
		try {
			
			// Source - https://www.java67.com/2016/07/how-to-read-text-file-into-arraylist-in-java.html#:~:text=All%20you%20need%20to%20do,readLine()%3B%20while%20(line%20!
			// Initialising the BufferedReader to read in the file
			BufferedReader buffRead = new BufferedReader(new FileReader(STOPS));
			
			String line;
			
			// skipping over the first line...
			line = buffRead.readLine();
			
			// this variable allows us to track the current key of the TST
			// the last char of each name is accompanied with a value, to 
			// indicate it's the end of a string in the TST :)
			int value = 0;
			
			while ((line = buffRead.readLine()) != null) {
				
				// Source - https://stackoverflow.com/questions/2858121/is-there-a-built-in-way-to-convert-a-comma-separated-string-to-an-array
				// Source - https://www.geeksforgeeks.org/split-string-java-examples/
				// the line is split up into different strings, separated
				// by a ',', this separated line is represented as an String array
				String[] splitStop = line.split(",");
				
				// cleaning up the name of the stop (making it meaningful!!)
				// see stringCleanup method for more information...
				String cleanString = stringCleanup(splitStop[2]);
				
				// adding the whole line to the stopsList ArrayList
				stopsList.add(line);
				
				// adding the cleaned up string to the TST
				stopsTree.put(cleanString, value);
				
				// incrementing the key
				value++;
			}
			
			buffRead.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * As outlined in the Project Specification, some of the strings in the stops.txt need to
	 * be made more 'meaningful', i.e. remove the inital wb, eb, sb, nb, or flagstop that may
	 * feature at the start. This method moves this substring, if existent, to the end of the 
	 * orginal string.
	 *
	 * @param toClean: the string to be potentially cleaned
	 * @return String: the meaningful string, without any negligible keywords at the start
	 */
	public static String stringCleanup(String toClean) {
    	
		// Source - https://stackoverflow.com/questions/47332461/java-moving-first-letter-of-string-to-the-end-and-determining-if-word-is-the
		// Source - https://www.geeksforgeeks.org/substring-in-java/
		// takes a substring of the input, with the length of the word flagstop
    	String checkFlagstop = toClean.substring(0, 8);
    	
    	// if this substring represents the word flagstop
    	if (checkFlagstop.equalsIgnoreCase("FLAGSTOP")) {
    		
    		// we prepare to move it to the back of the string (with correct spaces)
    		String addToBack = " " + toClean.substring(0, 8);
    		
    		// replace the original string, removing flagstop
    		toClean = toClean.substring(9);
    		
    		// add flagstop back to the string!
    		toClean = toClean + addToBack;		
        }
    	
    	// takes a substring of the input, with the length of the word nb/eb/sb/wb
    	String checkNb = toClean.substring(0, 2);
    	
    	// if this substring represents any of those prefixes
    	if (checkNb.equalsIgnoreCase("NB") ||
    		checkNb.equalsIgnoreCase("EB") ||
    		checkNb.equalsIgnoreCase("SB") ||
    		checkNb.equalsIgnoreCase("WB")) {
    		
    		// we prepare to move it to the back of the string (with correct spaces)
    		String addToBack = " " + toClean.substring(0, 2);
    		
    		// replace the original string, removing flagstop
    		toClean = toClean.substring(3);
    		
    		// add flagstop back to the string!
    		toClean = toClean + addToBack;	
    	}
    	
    	return toClean;
    }

	
	// --------------------------------   PART 3  ------------------------------- 
	/**
    * this checks whether a time in the stop_times.txt file is valid!
    * 
    * @param input: the time to be checked, as a string
    * @return boolean: true if the time is valid, false if it isn't!
    */
	public static boolean checkTime(String input) {
		
		try {
			
			// Source - https://www.geeksforgeeks.org/split-string-java-examples/
			// splitting up the time into different strings, separated by a ':'
			String[] separateVal = input.split(":");
		
			int userHrs = 0;
			int userMins = 0;
			int userSecs = 0;
		
		// Source - https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
		// Checking for Input Validity: are the inputs integers?
			userHrs = Integer.parseInt(separateVal[0]);
			userMins = Integer.parseInt(separateVal[1]);
			userSecs = Integer.parseInt(separateVal[2]);
				
			// Checking for Input Validity: are the integers within bounds?
			if (userHrs <= HRS_UPPER && 
				userMins <= MINS_UPPER &&
				userSecs <= SECS_UPPER) {
				
				return true;
			}
			else {
				return false;
			}
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}
	
	/**
	 * This method has the same functionality as the one directly above it, with the main exception being
	 * that I used a different approach, using the matches() method to validate the user-entered time. I
	 * wanted to avoid the method throwing errors at the user interface...
	 * 
	 * @param input: the user-entered string to be checked!
	 * @return boolean: true if the input is a valid time, false if not
	 */
	public static boolean checkUserTime(String input) {
		
		// Source - https://stackoverflow.com/questions/63340716/how-to-check-if-a-users-input-follows-the-correct-format
		String regex = "[0-1][0-9]:[0-5][0-9]:[0-5][0-9]";
		String regex2 = "[2][0-3]:[0-5][0-9]:[0-5][0-9]";

		// Source - https://www.geeksforgeeks.org/string-matches-method-in-java-with-examples/
		if(input.matches(regex) || input.matches(regex2)) {
		    return true;
		} else {
		    return false;
		}
	}
	
	/**
	 * As outlined in the Project Specification, some of the times in the file may be invalid. This method 
	 * processes all the times in the stop_times.txt file, verifies them, and adds a) the valid times in the
	 * times ArrayList, and all the lines corresponding to those valid times in the stopTimes ArrayList!
	 */
	public static void cleanTimes() throws IOException {
		
		try {
			
			// turning the file into array list
			BufferedReader buffRead = new BufferedReader(new FileReader(STOP_TIMES));
			
			String line;
			// skipping over the first line...
			line = buffRead.readLine();
			while ((line = buffRead.readLine()) != null) {
				
				// Source - https://www.geeksforgeeks.org/split-string-java-examples/
				// separating all the 'categories' based on the ','
				// the strings are represented in an array now!
				String[] splitStop = line.split(",");
				
				// accessing the first time
				String stopTime = splitStop[1];
				
				// removing any spaces
				stopTime = stopTime.replaceAll(" ", "");
				
				// accessing the second time
				String leaveTime = splitStop[2];
				
				// removing any spaces
				leaveTime = leaveTime.replaceAll(" ", "");
				
				// checking that both times are valid!
				if (checkTime(stopTime) && checkTime(leaveTime)) {
					
					// if yes.. we add it to the times ArrayList
					// and add the whole line to the stopTimes
					times.add(stopTime);
					stopTimes.add(line);
				}
			}
			
			buffRead.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
	}
	
	/**
	 * This method finds all stops matching a given arrival time, and updates the matchLines ArrayList! :)
	 * It also sorts the matchLines arrayList... using the Collections.sort method (merge sort)
	 *
	 * @param input: the time to search for matches for, as a string
	 */
	public static void matchTimes(String input) {
		
		// reset the ArrayLists :)
		matchLines.clear();
		
		// going through the pre-defined set of valid times
		for (int i = 0; i < times.size(); i++) {
			
			// verifying whether the input matches any of the obtained times
			if (input.equals(times.get(i))) {
				
				// if yes, adding the line representing the match time to the
				// matchLines ArrayList!
				matchLines.add(stopTimes.get(i));
			}
		}
		
		// Source - https://www.quora.com/What-sorting-algorithm-is-used-by-Javas-Collections-sort-and-why
		Collections.sort(matchLines);
	}
	
	// --------------------------------  HOMEPAGE  ------------------------------- 
	 
	/**
	 * This is the bulk of my project's user interface. It operates off of a command line (out of simplicity)
	 * This method handles all of the user input handling for all 3 parts, with the help of input verifying / 
	 * validating methods in this class and in other classes!
	 *
	 * @return boolean: this method will always return true UNLESS the user enters 'quit'...
	 */
	public static boolean homePage() {
		
		// Display all the options available to the user
		System.out.println("Select one of the options or type in 'quit' to end the program.\n"
				+ "  To find the quickest route (aka shortest path) between two stops, enter '1'\n"
				+ "  To search for a stop, enter '2'\n"
				+ "  To find all trips arriving at a specific time, enter '3'\n");
		
		// Ask for user input
		System.out.print("Enter your input here: ");
		
		// Take in the user input
		String userInput = scanner.next();
		
		// Checks whether the user input is an integer
		boolean inputIsInt = false;
		int option = 0;
		try {
			option = Integer.parseInt(userInput);
			inputIsInt = true;
		} catch (NumberFormatException e) {
			
		}
		
		// Source - https://stackoverflow.com/questions/35444535/getting-numbers-from-user-input-until-user-types-quit
		if (userInput.equalsIgnoreCase("quit")) {
			// signals the main that it's time for the program to end!
			return false;
		} 
		
		else if (inputIsInt) {
			// checks that the user entered a valid integer
			if (option >= 1 && option <= 3) {
				// 1. Shortest Path
				/*
				 * Stops are listed in stops.txt and connections (edges) between them come from stop_times.txt and
				 * transfers.txt files. All lines in transfers.txt are edges (directed), while in stop_times.txt an edge
		         * should be added only between 2 consecutive stops with the same trip_id.
				 */
				if (option == 1) {
					System.out.println(" -- Searching for the Shortest Path -- ");
					
					int stop1 = 0;
					int stop2 = 0;
					
					// runs while the user doesn't enter a valid stopID
					boolean validInputs = false;
					do {
						try {
							System.out.print("Please enter the source stopID: ");
							stop1 = Integer.parseInt(scanner.next());
							
							// if the integer entered is a valid stop, we move on to the next input!
							if (stopsGraph.checkValid(stop1) != -1) {
								validInputs = true;
							}
							else {
								System.out.println("Please enter a valid stopID.\n");
							}
						}
						catch (NumberFormatException e) {
							System.out.println("Please enter a valid input (e.g. '238')\n");
						}
						
					} while (!validInputs);
					
					validInputs = false;
					do {
						try {
							System.out.print("Please enter the destination stopID: ");
							stop2 = Integer.parseInt(scanner.next());
							
							// if integer entered is a valid stop.. we can calculate the shortest path!
							if (stopsGraph.checkValid(stop2) != -1) {
								validInputs = true;
							}
							else {
								System.out.println("Please enter a valid stopID.\n");
							}
						}
						catch (NumberFormatException e) {
							System.out.println("Please enter a valid input (e.g. '238')\n");
						}
					} while (!validInputs);
					
					System.out.println("\nFinding the shortest path from stop " + stop1 + " to stop " + stop2);
					
					// moved the bulk of the code to another method!
					// see printShortestPath() desc.
					printShortestPath(stop1, stop2);
					
				}
				// 2. Search for a Stop
				/*
				 * In order for this to provide meaningful search functionality please move keywords flagstop, wb, nb,
				 * sb, eb from start of the names to the end of the names of the stops when reading the file into a TST
		         * (eg “WB HASTINGS ST FS HOLDOM AVE” becomes “HASTINGS ST FS HOLDOM AVE WB”) 
				 */
				else if (option == 2) {
					System.out.println(" -- Searching for a Stop -- ");
					// ask for user input
					System.out.print("Enter the stop to search: ");
					
				    // no specific input checking methods as the strings have alpha-numerical characters
					String toSearch = scanner.next();
					
					// find matching lines
					ArrayList<String> matchingStrings = stopsTree.keysWithPrefix(toSearch.toUpperCase());
					
					// displaying a bit of miscellaneous info
					// i.e. how many matches were found!
					int numMatches = matchingStrings.size();
					System.out.println("There " + (numMatches != 1? "are " : "is ")
									+ matchingStrings.size() 
									+ (numMatches != 1? " stops" : " stop") 
									+ " corresponding to your search!");
					
					// display lines
					for (int i = 0; i < matchingStrings.size(); i++) {
						System.out.println(stopsList.get(stopsTree.get(matchingStrings.get(i))));
					}
				}
				// 3. Search for a Trip
				else if (option == 3) {		
					System.out.println(" -- Searching for a Trip -- ");
					
					String userTime;
					
					boolean validTime = false;
					
					// asking for user to enter an input
					// runs until this input is validated
					do {
						System.out.print("Please enter a time in military time in the hh:mm:ss format, e.g. '07:45:22': ");
						
						// let user input time
						userTime = scanner.next();
						
						// validate time
						validTime = checkUserTime(userTime);
						
						if (!validTime) {
							System.out.println("Is your input in the right format (i.e. hh:mm:ss)?");
						}
					} while (!validTime);
					
					// find all the valid times
					matchTimes(userTime);
					
					// displaying a bit of miscellaneous info
					// i.e. how many matches were found!
					int numMatches = matchLines.size();
					System.out.println("There " + (numMatches != 1? "are " : "is ")
									+ matchLines.size() 
									+ (numMatches != 1? " buses" : " bus") 
									+ " arriving at that time!");
					
					// display the matches in ascending order
					for (int i = 0; i < matchLines.size(); i++) {
						System.out.println(matchLines.get(i));
					}
					
				}
			}
			// user hasn't entered a valid integer
			else {
				System.out.println("Enter a valid integer: 1, 2, or 3!");
			}
		}
		// user hasn't entered an integer OR 'quit'
		else {
			System.out.println("Watch your inputs: enter 1, 2, or 3, or 'quit'.");
		}
		
		// Taking a little 'break' so the user can view results before moving in
		System.out.println("\nPress any key to continue");
		
		// Waits for any input to be entered in the command line
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// returns true for another round, as 'quit' hasn't been entered!
		return true;
	}
	
	/**
	 * The main! Here, the program initialises the TST and the Graph (showing its progress as it goes)
	 * It then runs the main user interface (the homepage) until the user enters false!
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Setting up the program");
		
		System.out.println("  1/3 Initialising the TST");
		setUpTST();
		
		System.out.println("  2/3 Removing invalid times");
		cleanTimes();
		
		System.out.println("  3/3 Setting up the graph");
		stopsGraph = new Graph(stopsList, stopTimes);
		
		System.out.println("\nWelcome to the Vancouver Translink visualiser."
				+ "\n  To see the cited works, see the SOURCES section of UserInterface.java,"
				+ "\n  or quit the program by entering 'quit'.\n");
		
		// runs homePage until homePage returns false (i.e. user has entered 'quit')
		boolean programRunning = true;
		do {
			programRunning = homePage();
		} while (programRunning);
		
		System.out.println("Program successfully terminated. See you soon! ;)");
		citeSources();
	}
	
	// -------------------------------- SOURCES -------------------------------
	
	public static void citeSources() {
		System.out.println("\nSources used: ");
		System.out.println(" - https://www.java67.com/2016/07/how-to-read-text-file-into-arraylist-in-java.html#:~:text=All%20you%20need%20to%20do,readLine()%3B%20while%20(line%20!");
		System.out.println(" - https://stackoverflow.com/questions/2858121/is-there-a-built-in-way-to-convert-a-comma-separated-string-to-an-array");
		System.out.println(" - https://www.geeksforgeeks.org/split-string-java-examples/");
		System.out.println(" - https://stackoverflow.com/questions/47332461/java-moving-first-letter-of-string-to-the-end-and-determining-if-word-is-the");
		System.out.println(" - https://www.geeksforgeeks.org/substring-in-java/");
		System.out.println(" - https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/");
		System.out.println(" - https://stackoverflow.com/questions/63340716/how-to-check-if-a-users-input-follows-the-correct-format");
		System.out.println(" - https://www.geeksforgeeks.org/string-matches-method-in-java-with-examples/");
		System.out.println(" - https://www.quora.com/What-sorting-algorithm-is-used-by-Javas-Collections-sort-and-why");
		System.out.println(" - https://stackoverflow.com/questions/35444535/getting-numbers-from-user-input-until-user-types-quit");
		System.out.println(" - https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html");
		System.out.println(" - https://algs4.cs.princeton.edu/42digraph/Digraph.java.html");
		System.out.println(" - https://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html");
		System.out.println(" - https://algs4.cs.princeton.edu/52trie/TST.java.html");
		
	}
	
}
