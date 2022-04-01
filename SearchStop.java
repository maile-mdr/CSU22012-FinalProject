import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.jdi.Value;

public class SearchStop<Value> {
	
	// source: https://algs4.cs.princeton.edu/52trie/TST.java.html
	//         https://www.java67.com/2016/07/how-to-read-text-file-into-arraylist-in-java.html#:~:text=All%20you%20need%20to%20do,readLine()%3B%20while%20(line%20!
	//         https://stackoverflow.com/questions/2858121/is-there-a-built-in-way-to-convert-a-comma-separated-string-to-an-array
	
	// TNT structure
	private int size;
	private Node<Value> root;
	
	private static class Node<Value> {
		private char c; // character in the node
		private Node<Value> left, mid, right; // sub-trees :)
		private Value val; // key! is it the end of a string?
	}
	
	// Default constructor
	public SearchStop() {
		
	}
	
	/**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node<Value> x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    // return subtrie corresponding to given key
    private Node<Value> get(Node<Value> x, String key, int d) {
        if (x == null) return null;
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key)) size++;
        else if(val == null) size--;       // delete existing key
        root = put(root, key, val, 0);
    }

    private Node<Value> put(Node<Value> x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node<Value>();
            x.c = c;
        }
        if      (c < x.c)               x.left  = put(x.left,  key, val, d);
        else if (c > x.c)               x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1);
        else                            x.val   = val;
        return x;
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     * @throws IllegalArgumentException if {@code prefix} is {@code null}
     */
    public ArrayList<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        ArrayList<String> queue = new ArrayList<String>();
        Node<Value> x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.add(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node<Value> x, StringBuilder prefix, ArrayList<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix, queue);
        if (x.val != null) queue.add(prefix.toString() + x.c);
        collect(x.mid,   prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }
    
    public static String stringCleanup(String toClean) {
    	
    	String checkFlagstop = toClean.substring(0, 8);
    	if (checkFlagstop.equalsIgnoreCase("FLAGSTOP")) {
    		
    		String addToBack = " " + toClean.substring(0, 8);
    		
    		toClean = toClean.substring(9);
    		
    		toClean = toClean + addToBack;		
        }
    	
    	
    	String checkNb = toClean.substring(0, 2);
    	if (checkNb.equalsIgnoreCase("NB") ||
    		checkNb.equalsIgnoreCase("EB") ||
    		checkNb.equalsIgnoreCase("SB") ||
    		checkNb.equalsIgnoreCase("WB")) {
    		
    		String addToBack = " " + toClean.substring(0, 2);
    		
    		toClean = toClean.substring(3);
    		
    		toClean = toClean + addToBack;
    			
    	}
    	
    	return toClean;
    }

	public static void main(String[] args) throws IOException {
		
		ArrayList<String> stopsLine = new ArrayList<>();
		ArrayList<String> stopsName = new ArrayList<>();
		SearchStop<Integer> stopsTNT = new SearchStop<Integer>();
		
		try {
			
			// turning file into array list
			BufferedReader buffRead = new BufferedReader(new FileReader("stops.txt"));
			
			String line;
			line = buffRead.readLine();
			while ((line = buffRead.readLine()) != null) {
				String[] splitStop = line.split(",");
				
				String cleanString = stringCleanup(splitStop[2]);
				
				stopsLine.add(line);
				stopsName.add(cleanString);
			}
			
			buffRead.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
		for (int i = 0; i < stopsLine.size(); i++) {
			stopsTNT.put(stopsName.get(i), i);
		}
		
		Scanner userInput = new Scanner(System.in);
		System.out.print("Enter string to search please: ");
		String toSearch = userInput.nextLine();
		
		ArrayList<String> matchingStrings = stopsTNT.keysWithPrefix(toSearch);
		
		for (int i = 0; i < matchingStrings.size(); i++) {
			System.out.println(stopsLine.get(stopsTNT.get(matchingStrings.get(i))));
		}
	}
}
