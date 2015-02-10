import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class JuggleFest {
	// sets up manipulated values
	static int x = 0;
	static int count = 0;
	static int stringCount = 0;
	static int rowCounter = 0;
	static String[] numbers = new String[101];
	public static ArrayList<Circuit> circuitList = new ArrayList<Circuit>();

	// main
	public static void main(String[] args) {
		// starts a try catch block for file reading
		try {
			// reads the triangle file
			Scanner input = new Scanner(new File("Juggler.txt"));

			int jugglerCount = 0;
			int circuitCount = 0;
			double maxJC = 0;

			// grabbing count to determine how many jugglers each circuit can have
			String line;
			while (input.hasNextLine()) {
				line = input.nextLine();
				if (line.isEmpty()) {
				} else if (line.charAt(0) == 'J')
					jugglerCount++;
				else if (line.charAt(0) == 'C')
					circuitCount++;
			}
			//determining the amount of jugglers each circuit can have
			maxJC = jugglerCount / circuitCount;
			String[] tempHolder;
			
			// sorting inputs, inputs are parsed and then sorted respectfully
			input = new Scanner(new File("Juggler.txt"));
			line = "";
			while (input.hasNextLine()) {
				line = input.nextLine();
				if (line.isEmpty()) {
					//jugglers = 'J'
				} else if (line.charAt(0) == 'J') {
					tempHolder = purgeJuggler(line);
					Map<Integer, Integer> tempMap = generateMap(tempHolder);
					//making a map of circuits they like then creating a temp juggler
					Juggler tempJugglers = new Juggler(
							Integer.parseInt(tempHolder[2]),
							Integer.parseInt(tempHolder[3]),
							Integer.parseInt(tempHolder[4]), tempMap,
							Integer.parseInt(tempHolder[1]));
					//moving tempJuggler to be sorted
					sortJuggler(tempJugglers);
					//circuits = 'C'
				} else if (line.charAt(0) == 'C') {
					tempHolder = line.split(" ");
					tempHolder = purgeCircuit(tempHolder);
					//creating new circuit
					Circuit tempCircuit = new Circuit(
							Integer.parseInt(tempHolder[2]),
							Integer.parseInt(tempHolder[3]),
							Integer.parseInt(tempHolder[4]), 0,
							Integer.parseInt(tempHolder[1]), (int) maxJC);
					//adding the circuit to the list of circuits
					circuitList.add(tempCircuit);
				}
			}

		} catch (Exception ie) {

		}
		//printing out total to be emailed
		int total = 0;
//		for (int i = 0; i < circuitList.get(1970).jugglers.size(); i++) {
//			total += circuitList.get(1970).jugglers.get(i).getIdentifier();
//		}
		System.out.println(total);
		
		//writing to text file
		//This involves readding the letters i removed when they were being inputted
		FileWriter outputStream = null;
		try {
			//open filewriter
			 outputStream = new FileWriter("outputfile.txt");
			 //for each circuit in the list, start at teh end and move towards the beginning
			 for (int c = circuitList.size()-1; c >= 0; c--) {
					outputStream.write("C" + circuitList.get(c).getIdentifier() + " ");
					//for each juggler in each circuit, print them all
					for (int d = 0; d < circuitList.get(c).jugglers.size(); d++) {
						Juggler temporJuggler = circuitList.get(c).getJuggler(d);
						String tempString = "";
						//for each juggler being printed, calculate the dot product for all of their preferences
						for (int p = 0; p < temporJuggler.preferences.size(); p++) {
							tempString = tempString + " C" + circuitList.get(temporJuggler.preferences.get(p)).getIdentifier() + ":" + dotProduct(temporJuggler, circuitList.get(temporJuggler.preferences.get(p)));
						}
						//if they are not the last juggler to be printed, add a comma
						if ( d != circuitList.get(c).jugglers.size()-1 ) {
							outputStream.write("J" + temporJuggler.getIdentifier() + tempString + ", ");
						}
						//else, dont print a comma (formatting per website's instruction)
						else 
						outputStream.write("J" + temporJuggler.getIdentifier() + tempString);

					}
					//at the end of each circuit, print a new line
					outputStream.write("\n");
				}
			 
			 //close the stream
			 if (outputStream != null) {
			        outputStream.close();
			    }
		} catch (Exception ie) {
			System.err.print(ie);
		}
	}

	//sorting jugglers
	public static void sortJuggler(Juggler jugglerInput) {
		//grab the input
		Juggler toSortJuggler = jugglerInput;
		//begin while loop
		boolean sorted = false;
		//int to increment preference number if a juggler has to be sorted multiple times
	    int x = 0;
	    //while sorted
		while (!sorted) {
			//get the preference number
			int preferenceNumber = toSortJuggler.preferences.get(x);
			//get the temporary circuit according to preference.
			Circuit tempSortCircuit = circuitList.get(preferenceNumber);
			//calculate the current dot product
			toSortJuggler.setCurrentDot(dotProduct(toSortJuggler,
					tempSortCircuit));
			//if the circuit can have more jugglers, add the juggler and end the loop
			if (tempSortCircuit.isNotFull()) {
				tempSortCircuit.addJuggler(toSortJuggler);
				circuitList.set(preferenceNumber, tempSortCircuit);
				sorted = true;
			} 
			else {
				//if the circuit is full, sort it, this establishes a minimum value
				tempSortCircuit.sort();
				//if the value is greater then the minimum
				if (toSortJuggler.getCurrentDot() > tempSortCircuit.minimum) {
					//get the first juggler after sort
					Juggler tempJuggler = tempSortCircuit.getJuggler(0);
					//remove him
					tempSortCircuit.removeJuggler(0);
					//add the new juggler and set the circuit list
					tempSortCircuit.addJuggler(toSortJuggler);
					circuitList.set(preferenceNumber, tempSortCircuit);
					//continue the loop with the new juggler
					toSortJuggler = tempJuggler;
					x++;
				} else {
					x++;
				}
			}
		}
	}

	//calculates the dot product
	public static int dotProduct(Juggler tempJuggler, Circuit tempCircuit) {
		int returnValue = 0;
		returnValue = tempJuggler.getE() * tempCircuit.getE()
				+ tempJuggler.getH() * tempCircuit.getH() + tempJuggler.getP()
				* tempCircuit.getP();
		return returnValue;
	}

	//for the input of jugglers, grab each circuit and make a map of their preferences
	public static Map<Integer, Integer> generateMap(String[] tempString) {
		String[] tempHolder = tempString;
		Map<Integer, Integer> newMap = new HashMap<Integer, Integer>();
		int x = 0;
		for (int i = 5; i < tempHolder.length; i++) {
			newMap.put(x, Integer.parseInt(tempHolder[i]));
			x++;
		}
		return newMap;
	}

	//purges the text of jugglers to just the needed information
	public static String[] purgeJuggler(String tempString) {
		String[] tempHolder = { "" };
		tempString = tempString.replaceAll(",", " ");
		tempHolder = tempString.split(" ");

		// J J0 H:3 E:9 P:2 C2 C0 C1
		for (int i = 1; i < tempHolder.length; i++) {
			if (i == 2 || i == 3 || i == 4) {
				StringBuilder sb = new StringBuilder(tempHolder[i]);
				sb.deleteCharAt(0);
				sb.deleteCharAt(0);
				tempHolder[i] = sb.toString();
			} else {
				StringBuilder sb = new StringBuilder(tempHolder[i]);
				sb.deleteCharAt(0);
				tempHolder[i] = sb.toString();
			}
		}
		return tempHolder;
	}

	//purges the text of circuits to just the needed information
	public static String[] purgeCircuit(String[] tempString) {
		String[] tempHolder = tempString;
		for (int i = 1; i < tempHolder.length; i++) {
			if (i == 1) {
				StringBuilder sb = new StringBuilder(tempHolder[i]);
				sb.deleteCharAt(0);
				tempHolder[i] = sb.toString();
			} else {
				StringBuilder sb = new StringBuilder(tempHolder[i]);
				sb.deleteCharAt(0);
				sb.deleteCharAt(0);
				tempHolder[i] = sb.toString();
			}
		}
		return tempHolder;
	}
	

}
