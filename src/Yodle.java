import java.io.File;
import java.util.*;
import java.util.Collections;

public class Yodle {
	// sets up manipulated values
	static int rowCounter = 0;

	// main
	public static void main(String[] args) {
		// generates a 2D arraylist
		ArrayList<ArrayList<Integer>> row = new ArrayList<ArrayList<Integer>>();
		// starts a try catch block for file reading
		try {
			// reads the triangle file
			Scanner input = new Scanner(new File("Triangle.txt"));

			// array of strings after they are split.
			String[] tempHolder;
			//temp to hold the current line
			String line;
			//goes over input file
			while (input.hasNextLine()) {
				//generate new arraylist to populate
				ArrayList<Integer> column = new ArrayList<Integer>();
				//gets the next line
				line = input.nextLine();
				//splits the line into numbers
				tempHolder = line.split(" ");
				//for each number, convert to int and add to column
				for (int i = 0; i < tempHolder.length; i++) {
					column.add(Integer.parseInt(tempHolder[i]));
				}
				//adds the column to the 2D array
				row.add(column);
				//increments counter
				rowCounter++;
			}
		} catch (Exception ie) {
			
		}

		//topDown
		//goes over all the rows
		for (int i = 0; i < rowCounter-1; i++) {
			//generates new arraylist to replace the next line
			ArrayList<Integer> reduced = new ArrayList<Integer>();
			//Catch for the first number since there is no comparison to make
			if (i == 0) {
				reduced.clear();
				reduced.add(row.get(0).get(0) + row.get(1).get(0));
				reduced.add(row.get(0).get(0) + row.get(1).get(1));
			}
			//moves onto algorithm to add top row to bottom row
			else {
			reduced = topDown(row.get(i), row.get(i+1));
			}
			//sets the next line equal to the new line we created after additions
			row.set(i+1, reduced);

		}
		//moves the final row to be sorted
		ArrayList<Integer> finalRow = row.get(rowCounter-1);
		//sorts the array
		Collections.sort(finalRow);
		//prints out the final highest number
		System.out.println(finalRow.get(finalRow.size()-1));

	}

	//topDown addition
	public static ArrayList<Integer> topDown(ArrayList<Integer> smaller, ArrayList<Integer> bigger) {
		//new array to return 
		ArrayList<Integer> merged = new ArrayList<Integer>();
		//initialize the large number to add to the arraylist
		int large;
		//for each number other then the last number in the row, go through the algorithm
		for (int i = 0; i < bigger.size()-1; i++) {
			//since there is no comparison for the first number, we can ignore the case
			//and simply add
			if (i == 0) {
				large = smaller.get(0) + bigger.get(0);
			}
			//Otherwise, we add the next line number and the bigger number of the two
			//on the line we are currently on directly above our number
			else {
				large = bigger.get(i) + larger(smaller.get(i-1), smaller.get(i));
			}
			//adds the number to the return arraylist
			merged.add(large);
		}
		//because the last number also has no comparison, we can simply do an
		//addition
		merged.add(bigger.get(bigger.size()-1) + smaller.get(smaller.size()-1));
		//returns the arraylist
		return merged;
	}
	
	//Comparison of the two values
	public static int larger(int a, int b) {
		if (a > b) {
			return a;
		} else if (b > a) {
			return b;
		} else {
			return a;
		}
	}


}
