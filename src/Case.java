import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Case implements Cloneable {

	private int line; // ligne, compris entre 1 et 19
	private int column; // colonne, compris entre A et T sans le I
	private Color occupied; // f pour free, B pour black, W pour white
	private int nbNearCases; // nombre de voisines, soit 2 (coin), 3 bords et 4 centre
	private char vectLetterColumn[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T' };
	private char letter;
	private Case left;
	private Case right;
	private Case top;
	private Case down;
	private Group group;
	private Goban goban;

	
// CONSTRUCTOR OF CASE CLASS
	public Case(int pline, int pcolumn, int pnbNearCases, Goban pgoban) { // builder case
		line = pline;
		column = pcolumn;
		nbNearCases = pnbNearCases;
		letter = vectLetterColumn[column];
		goban = pgoban;
	}

// METHODS OF CASE CLASS
	
	// BOOLEAN TESTS FOR CASE PLAYING
	public boolean isFree() { // renvoit un booléen qui informe si la case est libre pour jouer
		if (occupied == null) // évalue l'occupation de la case
		{
			return true;
		} else {
			return false;
		}

	}
	public boolean isNotSuicidal(Player pplayer) {
	boolean bool = true; // création du bouléen de retour
	HashSet possibleFreedoms = getFreedomCases(); // les libertés de la case potentiellement jouée
	HashSet possibleFriends = getNearPossibleFriendCases(pplayer); // les alliés de la case aux alentours
	HashSet possibleFriendsFreedoms = new HashSet(); // les libertés de ses possibles alliés
	HashSet possibleGroupFreedoms = possibleFreedoms; // les libertés du groupe potentiellement créé
	Iterator itpossibleFriends = possibleFriends.iterator(); // itérateur de la liste des alliés potentiels
	while (itpossibleFriends.hasNext()) // boucle de parcours
	{
		possibleFriendsFreedoms.addAll(((Case) itpossibleFriends.next()).getGroup().getListFreedoms()); // ajout des libertés de chaque alliés possible
	}
	possibleGroupFreedoms.addAll(possibleFriendsFreedoms); //ajout de la somme des libertés des alliés potentiels à la liste des libertés du groupe
	possibleGroupFreedoms.remove(this); // retrait de la case potentiellement jouée aux libertés du groupe
	
	if (possibleGroupFreedoms.isEmpty()==true) // test pour vérifier que le coup est suicidaire
	{bool = false;}
	return bool  ; // retour du booléen de réponse 
	}
	public boolean isKilling(Player pplayer) {
	boolean bool = false; // création du bouléen de retour
	HashSet possibleEnemies = getNearPossibleEnemyCases(pplayer); // stocke les potentielles pierres ennemies aux alentours	
	Iterator itpossibleEnemies = possibleEnemies.iterator(); // iterateur
	while(itpossibleEnemies.hasNext()) // boucle visant à parcourir les potentielles pierres enemies aux alentours
	{Case enemy=(Case) itpossibleEnemies.next(); // stockage de la pierre ennemie en cours de traitement
	enemy.getGroup().freedomsGroupUpdate(); // remise à jour des libertés du groupe stocké dans la case de la pierre en cours
	HashSet enemyFreedoms = enemy.getGroup().getListFreedoms(); // stockage de la liste des libertés du groupe
		if ((enemyFreedoms.size()==1) && (enemyFreedoms.contains(this))) //si la case potentiellement jouée est la seule et unique liberté du groupe ennemi
		{bool = true;
	// invocation killing
		}
	
	}
	
	return bool;}
	public boolean isNotKO (Player pplayer, Goban goban)
	{
	System.out.println("\n______________________________________________________\nPontentielle capture => Test de KO en cours pour le coup suivant :\n");
	Goban gobanClone = (Goban) goban.clone();
	gobanClone.getGobanTab()[this.line][this.column].killingProcess(pplayer,gobanClone,gobanClone.getGobanTab()[this.line][this.column]);
	String currentKey = goban.freeGobanStringKey(); // copie de la clef du goban
	String potentialNewKey = gobanClone.freeGobanStringKey(); // création de la chaîne du pontentiel goban
	System.out.println("Voici l'actuelle clef au coup "+goban.getNbStones()+":"+currentKey);
	System.out.println("Voici la potentielle nouvelle chaîne de caractère :"+potentialNewKey);
	System.out.println("Voici l'ancienne chaîne du coup précédent "+(goban.getNbStones()-1)+":"+goban.getStory().get(goban.getNbStones()-1));
	boolean bool = (potentialNewKey!=goban.getStory().get(goban.getNbStones()-1));
	System.out.println("isNotKo =" +bool);
	return bool;}
	


	// GETTERS OF SURROUNDING CASES LIST
	public HashSet getNearFriendCases() { // renvoit une liste des cases alliées aux alentours d'une case quelconque
		HashSet NearCasesFriend = new HashSet();
		if (top == null) {} // si la case n'existe pas (bords du goban) alors ne rien faire
		 else if (top.occupied == occupied)  // vérifie si le statut occupied de la case en cours est le même que celui de la case du test
		 {NearCasesFriend.add(top);
		 }
		if (down == null) {}
		 else if (down.occupied == occupied) 
		 {NearCasesFriend.add(down);
		 }
		if (left == null) {}
		 else if (left.occupied == occupied) 
		 {NearCasesFriend.add(left);
		 }
		if (right == null) {}
		 else if (right.occupied == occupied) 
		 {NearCasesFriend.add(right);
		 }
		return NearCasesFriend;
	}
	public HashSet getNearPossibleFriendCases(Player pplayer) { // renvoit une liste des cases alliées aux alentours d'une potentielle case à jouer par un joueur
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
	public HashSet getFreedomCases() { // renvoit une liste des cases libres aux alentours
		HashSet NearFreeCases = new HashSet();
		if (top == null) {} // si la case n'existe pas (bords du goban) alors ne rien faire
		 else if (top.occupied == null)  // vérifie si le statut occupied de la case en cours est le même que celui de la case du test
		 {NearFreeCases.add(top);
		 }
		if (down == null) {}
		 else if (down.occupied == null) 
		 {NearFreeCases.add(down);
		 }
		if (left == null) {}
		 else if (left.occupied == null) 
		 {NearFreeCases.add(left);
		 }
		if (right == null) {}
		 else if (right.occupied == null) 
		 {NearFreeCases.add(right);
		 }
		return NearFreeCases;
		}
	public HashSet getNearPossibleEnemyCases(Player pplayer) { // renvoit une liste des cases alliées aux alentours d'une potentielle case à jouer par un joueur
		HashSet NearCasesEnemy = new HashSet();
		if (top == null) { // si la case n'existe pas (bords du goban) alors ne rien faire
		} else if (top.occupied != pplayer.color && top.occupied != null) { // vérifie si le statut occupied de la case en cours est le même que celui de la case du test
			NearCasesEnemy.add(top);
		}
		if (down == null) {
		} else if (down.occupied != pplayer.color && down.occupied != null) {
			NearCasesEnemy.add(down);
		}
		if (left == null) {
		} else if (left.occupied != pplayer.color && left.occupied != null) {
			NearCasesEnemy.add(left);
		}
		if (right == null) {
		} else if (right.occupied != pplayer.color && right.occupied != null) {
			NearCasesEnemy.add(right);
		}
		return NearCasesEnemy;
	}
	public HashSet getNearCasesToKill (Player pplayer){ // renvoit une liste de cases à tuer
	HashSet casesToKill = new HashSet();
	HashSet possibleEnemies = getNearPossibleEnemyCases(pplayer); // stocke les potentielles pierres ennemies aux alentours	
	Iterator itpossibleEnemies = possibleEnemies.iterator(); // iterateur
	while(itpossibleEnemies.hasNext()) // boucle visant à parcourir les potentielles pierres enemies aux alentours
	{Case enemy=(Case) itpossibleEnemies.next(); // stockage de la pierre ennemie en cours de traitement
	enemy.getGroup().freedomsGroupUpdate(); // remise à jour des libertés du groupe stocké dans la case de la pierre en cours
	HashSet enemyFreedoms = enemy.getGroup().getListFreedoms(); // stockage de la liste des libertés du groupe
		if ((enemyFreedoms.size()==1) && (enemyFreedoms.contains(this))) //si la case potentiellement jouée est la seule et unique liberté du groupe ennemi}
		{casesToKill.add(enemy);}
	}
	return casesToKill;
}
	public void killingProcess(Player pplayer,Goban pprocessedGoban, Case processedCase)
	{
	HashSet listCasesToKill = processedCase.getNearCasesToKill(pplayer);
	Iterator itlistCasesToKill = listCasesToKill.iterator();
	
	while (itlistCasesToKill.hasNext())
		{Case killedCase = ((Case)itlistCasesToKill.next());
		pprocessedGoban.caseGroupKill(killedCase);
		}
	pprocessedGoban.addNewStoneGoban(pplayer, processedCase);// Pose de la pierre
	}
	// GETTERS AND SETTERS OF CASE CLASS
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
	public Color getOccupied() {
		return occupied;
	}
	public void setOccupied(Color poccupied) {
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
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public int getNbNearCases() {
		return nbNearCases;
	}
	public void setNbNearCases(int pnbNearCase) {
		this.nbNearCases = pnbNearCase;
	}
	public Goban getGoban() {
		return goban;
	}
	public void setGoban(Goban goban) {
		this.goban = goban;
	}
	//SURCHARGE toString
	@Override
	public String toString() {
		return " \t" + this.letter + "" + this.line + " contient une pierre " + this.occupied + ". Elle possède "
				+ this.nbNearCases + " voisines.\n";
	}
	// SURCHARGE clone
	public Object clone() {
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la 
			// méthode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		// on renvoie le clone
		return o;
	}

}
