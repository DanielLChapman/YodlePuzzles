import java.util.*;

public class Circuit {
	//initialize information
	public int h = 0;
	public int e = 0;
	public int p = 0;
	public int minimum = 0;
	public int identifier = 0;
	public int size = 0;
	public ArrayList<Juggler> jugglers = new ArrayList<Juggler>();

	//intiialize a new circuit
	public Circuit(int h, int e, int p, int minimum, int identifier, int size) {
		this.h = h;
		this.e = e;
		this.p = p;
		this.minimum = minimum;
		this.identifier = identifier;
		this.size = size;
	}

	//selection sort based on dot product information of stored jugglers
	public void sort() {
		try {
		if (jugglers.size() != 1) {
			for (int i = 0; i < jugglers.size() - 1; i++) {
				int index = i;
				for (int j = i + 1; j < jugglers.size(); j++)
					if (jugglers.get(j).currentDot <= jugglers.get(index).currentDot)
						index = j;

				Juggler smallerJ = jugglers.get(index);
				jugglers.set(index, jugglers.get(i));
				jugglers.set(i, smallerJ);
			}
		}
		minimum = jugglers.get(0).getCurrentDot();
		}
		catch (Exception ie) {
			System.out.println("here");
		}
	}

	//get juggler at positions in array list if required
	public Juggler getJuggler(int position) {
		Juggler tempJuggler;
		tempJuggler = jugglers.get(position);
		return tempJuggler;
	}

	//removes a juggler at position in arraylist
	public void removeJuggler(int position) {
		jugglers.remove(position);
	}

	//adds a juggler to arraylist
	public void addJuggler(Juggler tempJuggler) {
		jugglers.add(tempJuggler);
	}

	//checks to see if the circuit is full
	public boolean isNotFull() {
		if (jugglers.size() == size) {
			return false;
		} else
			return true;
	}

	//set and get information
	public void setH(int h) {
		this.h = h;
	}

	public void setE(int e) {
		this.e = e;
	}

	public void setP(int p) {
		this.p = p;
	}

	public void setMinimum(int h) {
		minimum = h;
	}

	public void setIdentifier(int e) {
		identifier = e;
	}

	public void setSize(int p) {
		size = p;
	}

	public int getH() {
		return h;
	}

	public int getE() {
		return e;
	}

	public int getP() {
		return p;
	}

	public int getMinimum() {
		return minimum;
	}

	public int getIdentifier() {
		return identifier;
	}

	public int getSize() {
		return size;
	}
}
