import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SearchTrip {
	
	// Constants - used for error-checking
	private static final int HRS_LOWER = 0;
	private static final int HRS_UPPER = 23;
	private static final int MINS_LOWER = 0;
	private static final int MINS_UPPER = 59;
	private static final int SECS_LOWER = 0;
	private static final int SECS_UPPER = 59;
	
	private static int stopNum = 0;

	/**
    *
    * @param input: the time to be checked, as a string
    * @return boolean: true if the time is valid, false if it isn't!
    */
	public static boolean checkTime(String input) {
		
		String[] separateVal = input.split(":");
		
		int userHrs;
		int userMins;
		int userSecs;
		
		// Source - https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
		// Checking for Input Validity: are the inputs integers?
		try {
			userHrs = Integer.parseInt(separateVal[0]);
			userMins = Integer.parseInt(separateVal[1]);
			userSecs = Integer.parseInt(separateVal[2]);
		}
		catch (NumberFormatException ex) {
			System.out.println("Please enter a valid time format, with colons and no spaces (e.g. 11:47:58");
			return false;
		}
		
		// Checking for Input Validity: are the integers within bounds?
		if ((userHrs >= HRS_LOWER && userHrs <= HRS_UPPER) && 
			(userMins >= MINS_LOWER && userMins <= MINS_UPPER) &&
			(userSecs >= SECS_LOWER && userSecs <= SECS_UPPER)) {
			
			return true;
		}
		else {
			System.out.println("Your inputs are out of bounds! Please enter a time within 00:00:00 and 23:59:59");
			return false;
		}
		
	}
	
	/**
    * As outlined in the Project Specification, some of the times in the file may be invalid. This method returns
    * all of the lines containing the valid times in stop_times.txt.  
    *
    * @param input: the time to be checked, as a string
    * @return ArrayList<String>: an ArrayList of Strings containing the lines which have valid times.
    */
	public static ArrayList<String> cleanTimes(File filename) throws IOException {
		
		BufferedReader buffRead = new BufferedReader(new FileReader(filename));
		
		String stopLine = "";
		
		ArrayList<String> cleanTimes = new ArrayList<String>();
		
		while ((stopLine = buffRead.readLine()) != null) {
			
			String[] line = stopLine.split(",");
			buffRead.close();
			
			String stopTime = line[1];
			String leaveTime = line[2];
			
			// if either of the stopTime or leaveTime is invalid, the whole line is deemed invalid
			if (checkTime(stopTime) && checkTime(leaveTime)) {
				cleanTimes.add(Arrays.toString(line));
			}
		}
		
		buffRead.close();
		
		return cleanTimes;
		
	}

	// Find all stops matching a given arrival time
	public static ArrayList<String> matchTimes(String input, ArrayList<String> cleanTimes) {
		
		ArrayList<String> matchTimes = new ArrayList<String>();
		
		return matchTimes;
	}
	
	// Sort the stopID in ascending order
	public static void sortStops(ArrayList<String> matchTimes) {
		
		// gonna use bubble sort....
		
	}
	
	// Mainline
	public static void main(String[] args) throws IOException {
		
		File inputFile = new File("stop_times.txt");
		ArrayList<String> cleanTimes = cleanTimes(inputFile);
		
		// Handle the user input
		System.out.print("Please enter a valid time (e.g. 21:48:29): ");
		String userInput = "";
		
		sortStops(matchTimes(userInput, cleanTimes));
		
		// Output the sorted, matching times.
		
	}

}
