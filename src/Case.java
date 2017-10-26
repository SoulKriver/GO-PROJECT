import java.util.HashSet;
import java.util.Iterator;

public class Case {

	private int line; // ligne, compris entre 1 et 19
	private int column; // colonne, compris entre A et T sans le I
	private char occupied; // f pour free, B pour black, W pour white
	private int nbNearCases; // nombre de voisines, soit 2 (coin), 3 bords et 4 centre
	private char vectLetterColumn[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T' };
	private char letter;
	private Case left;
	private Case right;
	private Case top;
	private Case down;
	private Group group;

	

	public Case(int pline, int pcolumn, int pnbNearCases, char poccupied) { // builder case
		line = pline;
		column = pcolumn;
		occupied = poccupied;
		nbNearCases = pnbNearCases;
		letter = vectLetterColumn[column];
	}

	public boolean isNotOccupied() { // renvoit un booléen qui informe si la case est libre pour jouer
		if (occupied == 'f') // évalue l'occupation de la case
		{
			return true;
		} else {
			return false;
		}

	}

	public boolean isNotSuicidal(Player pplayer) {//renvoit un booléen qui évalue si la case n'est pas suicidaire pour un joueur
		char[] nearCases = getNearCasesOccupied();
		boolean bool = false;
		for (int i = 0; i<4; i++) {
			if ((nearCases[i] == 'f')||(nearCases[i] ==pplayer.color)){
				bool=true;
		}}
		return bool;
	}

	public boolean isNotSuicidal2(Player pplayer) {
	HashSet possibleFreedoms = getFreedomCases(); // les libertés de la case potentiellement jouée
	HashSet possibleFriends = getNearPossibleFriendCases(pplayer); // les alliés de la case aux alentours
	HashSet possibleFriendsFreedoms = new HashSet(); // les libertés de ses possibles alliés
	HashSet possibleGroupFreedoms = possibleFreedoms; // les libertés du groupe potentiellement créé
	Iterator itpossibleFriends = possibleFriends.iterator();
	while (itpossibleFriends.hasNext()) 
	{
		possibleFriendsFreedoms.addAll(((Case) itpossibleFriends.next()).getGroup().getListFreedoms());
		System.out.println(possibleFriendsFreedoms);
	}
	possibleGroupFreedoms.addAll(possibleFriendsFreedoms);
	possibleGroupFreedoms.remove(this);
	boolean bool = true;
	if (possibleGroupFreedoms.isEmpty()==true)
	{bool = false;}
	return bool  ;
	}
	
	
	public char[] getNearCasesOccupied() { // renvoit un tableau des occupations des cases latérales
		char nearCasesOccupied[] = new char[4];
		if (top == null) {
			nearCasesOccupied[0] = 'O';
		} else
			nearCasesOccupied[0] = top.occupied;
		if (down == null) {
			nearCasesOccupied[1] = 'O';
		} else
			nearCasesOccupied[1] = down.occupied;
		if (left == null) {
			nearCasesOccupied[2] = 'O';
		} else
			nearCasesOccupied[2] = left.occupied;
		if (right == null) {
			nearCasesOccupied[3] = 'O';
		} else
			nearCasesOccupied[3] = right.occupied;
		return nearCasesOccupied;
	}

	public HashSet getNearFriendCases() { // renvoit une liste des cases alliées aux alentaours
		HashSet NearCasesFriend = new HashSet();
		if (top == null) { // si la case n'existe pas (bords du goban) alors ne rien faire
		} else if (top.occupied == occupied) { // vérifie si le statut occupied de la case en cours est le même que celui de la case du test
			NearCasesFriend.add(top);
		}
		if (down == null) {
		} else if (down.occupied == occupied) {
			NearCasesFriend.add(down);
		}
		if (left == null) {
		} else if (left.occupied == occupied) {
			NearCasesFriend.add(left);
		}
		if (right == null) {
		} else if (right.occupied == occupied) {
			NearCasesFriend.add(right);
		}
		return NearCasesFriend;
	}
	
	public HashSet getNearPossibleFriendCases(Player pplayer) { // renvoit une liste des cases alliées aux alentours
		HashSet NearCasesFriend = new HashSet();
		if (top == null) { // si la case n'existe pas (bords du goban) alors ne rien faire
		} else if (top.occupied == pplayer.color) { // vérifie si le statut occupied de la case en cours est le même que celui de la case du test
			NearCasesFriend.add(top);
		}
		if (down == null) {
		} else if (down.occupied == pplayer.color) {
			NearCasesFriend.add(down);
		}
		if (left == null) {
		} else if (left.occupied == pplayer.color) {
			NearCasesFriend.add(left);
		}
		if (right == null) {
		} else if (right.occupied == pplayer.color) {
			NearCasesFriend.add(right);
		}
		return NearCasesFriend;
	}

	public HashSet getFreedomCases() { // renvoit une liste des cases alliées aux alentaours
		HashSet NearFreeCases = new HashSet();
		if (top == null) { // si la case n'existe pas (bords du goban) alors ne rien faire
		} else if (top.occupied == 'f') { // vérifie si le statut occupied de la case en cours est le même que celui de la case du test
			NearFreeCases.add(top);
		}
		if (down == null) {
		} else if (down.occupied == 'f') {
			NearFreeCases.add(down);
		}
		if (left == null) {
		} else if (left.occupied == 'f') {
			NearFreeCases.add(left);
		}
		if (right == null) {
		} else if (right.occupied == 'f') {
			NearFreeCases.add(right);
		}
		return NearFreeCases;
	}
	
	public int getNbNearCases() {
		return nbNearCases;
	}

	public void setNbNearCases(int pnbNearCase) {
		this.nbNearCases = pnbNearCase;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int pline) {
		this.line = pline;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int pcolumn) {
		this.column = pcolumn;
	}

	public char getOccupied() {
		return occupied;
	}

	public void setOccupied(char poccupied) {
		this.occupied = poccupied;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char pletter) {
		this.letter = pletter;
	}

	public Case getLeft() {
		return left;
	}

	public void setLeft(Case pleft) {
		left = pleft;
	}

	public Case getRight() {
		return right;
	}

	public void setRight(Case pright) {
		right = pright;
	}

	public Case getTop() {
		return top;
	}

	public void setTop(Case ptop) {
		top = ptop;
	}

	public Case getDown() {
		return down;
	}

	public void setDown(Case pdown) {
		down = pdown;
	}

	@Override
	public String toString() {
		return " \t" + this.letter + "" + this.line + " contient une pierre " + this.occupied + ". Elle possède "
				+ this.nbNearCases + " voisines.\n";
	}
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
