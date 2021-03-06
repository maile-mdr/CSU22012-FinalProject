# CSU22012 - Final Project
My Final Project for Algorithms &amp; Data Structures II

A Bus Management System for the city of Vancouver!

What's part of the project:
```
UserInterface.java
TST.java
ShortestPath.java
Graph.java
Node.java
Edge.java
+ the input files! (stops.txt, stop_times.txt, and transfers.txt)
```
**That's it! Ignore the other files, they were ~~useless~~ part of previous versions!**

## Overview
To understand the necessary requirements of the separate parts of my program. I initially designed and implemented Parts 1-3 separately. This allowed me to spot what data structures and resources the various Parts had in common. Based on these commonalities, I streamlined and tweaked the sections of my code to avoid unnecessary duplication, notably in scanning and processing the files. My program imports the commonly used java.util, as I relied heavily on ArrayLists throughout the program due to their ease of use and the ability to increase their size. To handle user input, I relied on a Java Scanner object, and to process the files, I imported the BufferedReader. 

I relied on heavy documentation and commenting to accompany my code, and to explain my thought processes at a higher-language where needed. All methods are properly documented, and the sections of the project that contain external code are all cited. Personally, the most difficult part of this project was the file and input handling, though with the available resources online, I was able to overcome this with ease.

## Part 1: Finding the shortest path
In order to find the shortest path between two stops, I implemented Dijkstra’s algorithm.  Dijkstra’s algorithm is a single-source shortest path algorithm, which was optimal for this situation, since a) we were dealing with a source and a destination, and b) we weren’t provided with heuristics to accompany our search, this algorithm was hence justified. Technically, I also had familiarity with the algorithm from a previous assignment. I thus based my implementation off of my previous work, making numerous tweaks to fit the algorithm within the context of the variables I was dealing with. Accompanying the shortest path algorithm is a Graph, the data structure I chose to represent the stops (represented as Nodes) and their connections (Edges). I turned towards Sedgewick and Wayne’s Edge-Weighted Directional Graph for guidance on how to structure this, removing redundant methods and making adjustments as necessary. I added various functions to complete this section, such as outputting a list of interim stops from a source to a destination, and a method to process user input handling (i.e. entering a valid stop).

## Part 2: Searching for a stop
The project specification mentioned that a Ternary Search Trie was to be used for this section of the project. I hence relied on Sedgewick and Wayne’s TST implementation to achieve this. I removed any methods which were redundant to my program, and made modifications to match the variable context of the algorithm. The original program relies on a user-made Iterable Queue class to represent symbols and results, though I replaced this with an ArrayList for ease and cohesion with the rest of the program’s data structures. Input handling for the user was also very simple to implement, as the strings contained a variety of characters: alpha, numeric and also special ones. I also created a method to ‘clean’ the string if it started with a negligible keyword (e.g. “flagstop”), I familiarised myself with string manipulation methods in java to achieve this.

## Part 3: Searching for a trip
In order to search for a trip based on its arrival time, I had to focus heavily on input-handling and error-checking. I created a method to read and validate the times directly from the stop_times.txt file, and a separate, more simple one to validate a user-entered time, using the pre-existing matches() method. In terms of sorting the searches, since the data type to be sorted was an ArrayList of Strings containing the lines matching the user-entered time, I used Collections.sort(), which implements merge sort. Merge sort was optimal as some inputs can yield up to 400 results, and a non-primitive data type was being sorted.

## Part 4: User Interface
I opted for a command line interface to give the user the ability to interact with the program, because this was easier to implement and yielded reliable results. The bulk of the User Interface is featured in the UserInterface.java class, this section of my project also handles the initialisation of the data structures (the Graph and TST). I paid close attention to input handling, and displayed any error messages for the three sections as needed. I provided a clear way for the user to quit the program. The termination of the program also allows the user to view any sources credited in my code.
