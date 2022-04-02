import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SearchTrip {
	
	private static ArrayList<String> fullLine = new ArrayList<String>();
	private static ArrayList<String> times = new ArrayList<String>();
	
	private static ArrayList<String> matchTimes = new ArrayList<String>();
	private static ArrayList<String> matchLines = new ArrayList<String>();
	
	// Constants - used for error-checking
	private static final int HRS_LOWER = 0;
	private static final int HRS_UPPER = 23;
	private static final int MINS_LOWER = 0;
	private static final int MINS_UPPER = 59;
	private static final int SECS_LOWER = 0;
	private static final int SECS_UPPER = 59;

	/**
    *
    * @param input: the time to be checked, as a string
    * @return boolean: true if the time is valid, false if it isn't!
    */
	public static boolean checkTime(String input) {
		
		String[] separateVal = input.split(":");
		
		int userHrs = 0;
		int userMins = 0;
		int userSecs = 0;
		
		// Source - https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
		// Checking for Input Validity: are the inputs integers?
		try {
				userHrs = Integer.parseInt(separateVal[0]);
				userMins = Integer.parseInt(separateVal[1]);
				userSecs = Integer.parseInt(separateVal[2]);
		}
		catch (NumberFormatException ex) {
			return false;
		}
		
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
	
	/**
    * As outlined in the Project Specification, some of the times in the file may be invalid. This method returns
    * all of the lines containing the valid times in stop_times.txt.  
    *
    * @param input: the time to be checked, as a string
    * @return ArrayList<String>: an ArrayList of Strings containing the lines which have valid times.
    */
	public static void cleanTimes() throws IOException {
		
		try {
			
			// turning file into array list
			BufferedReader buffRead = new BufferedReader(new FileReader("stop_times.txt"));
			
			String line;
			line = buffRead.readLine();
			while ((line = buffRead.readLine()) != null) {
				String[] splitStop = line.split(",");
				
				String stopTime = splitStop[1];
				stopTime = stopTime.replaceAll(" ", "");
				
				String leaveTime = splitStop[2];
				leaveTime = leaveTime.replaceAll(" ", "");
				
				if (checkTime(stopTime) && checkTime(leaveTime)) {
					times.add(stopTime);
					fullLine.add(line);
				}
			}
			
			buffRead.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
	}

	// Find all stops matching a given arrival time
	public static void matchTimes(String input) {
		
		for (int i = 0; i < times.size(); i++) {
			if (input.equals(times.get(i))) {
				matchTimes.add(times.get(i));
				matchLines.add(fullLine.get(i));
			}
			
		}
	}
	
	// Sort the stopID in ascending order
	public static void sortStops() {
		
		Collections.sort(matchLines);
	}
	
	// Mainline
	public static void main(String[] args) throws IOException {
		
		// Handle the user input
		String userInput = "15:00:00";

		cleanTimes();
		matchTimes(userInput);
		sortStops();
		
		System.out.println(matchTimes.size());
		
		// Output the sorted, matching times.
		for (int i = 0; i < matchTimes.size(); i++) {
			System.out.println(matchLines.get(i));
		}
		
	}

}
