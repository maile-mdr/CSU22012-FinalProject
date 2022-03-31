import java.util.*;

import com.sun.jdi.Value;

public class SearchStop {
	
	// source: https://algs4.cs.princeton.edu/52trie/TST.java.html
	
	private int size;
	private Node<Value> root;
	
	private static class Node<Value> {
		private char c;
		private Node<Value> left, mid, right;
		private Value val;
	}
	
	// initialises an empty table
	public SearchStop() {
		
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean contains(String key) {
		if (key == null) {
			throw new IllegalArgumentException("contains() argument is null :(");
		}
		return get(key) != null;
	}
	
	public Value get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("get() argument is null :("); 
		}
		if (key.length() == 0) throw new IllegalArgumentException("key length must be >= 1! :(");
	    Node<Value> x = get(root, key, 0);
	    if (x == null) return null;
	    return x.val;
	}
	
	private Node<Value> get(Node<Value> x, String key, int d) {
		if (x == null) return null;
		if (key.length() == 0) throw new IllegalArgumentException("key length must be >= 1! :(");
		char c = key.charAt(d);
		if (c < x.c) {
			return get(x.left, key, d);
		}
		else if (c > x.c) {
			return get(x.right, key, d);
		}
		else if (d < key.length() - 1) {
			return get(x.mid, key, d+1);
		}
		else {
			return x;
		}
		
	}

}
