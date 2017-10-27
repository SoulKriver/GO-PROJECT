import java.util.HashSet;
import java.util.Iterator;

public class Group {
private Player player; // le joueur propriétaire du groupe
HashSet listCases = new HashSet(); // la liste des cases comprises dans le groupe
private int nbCasesGroup = listCases.size(); // le nombre de pierres dans le groupe
HashSet listFreedoms = new HashSet(); // la lise des cases de libertés du groupe
private int nbFreedoms; // le nombre de liberté qu'il reste au groupe

// CONSTRUCTOR OF GROUP CLASS
public Group (Player pplayer, Case pcase) { // création d'un nouveau groupe lors de la pose d'une pierre
	
	player = pplayer;
	player.groupNb++; // incrémentation du nombre de groupe
	listCases.add(pcase); // ajout de la case initiale du premier groupe
	nbCasesGroup = listCases.size(); // le nombre de cases constituant le groupe
	listFreedoms = pcase.getFreedomCases(); // la liste des cases de libertés du groupe (une seule case à la création)
	nbFreedoms = listFreedoms.size(); // le nombre de cases de libertés du groupe
}

//METHODS OF CASE CLASS
public void FreedomsGroupUpdate () { // une méthode pour mettre à jour les libertés d'un groupe
	listFreedoms.removeAll(listFreedoms);
	Iterator itListCases = listCases.iterator();
	while (itListCases.hasNext())
		{HashSet freedomCase = ((Case)itListCases.next()).getFreedomCases();
		listFreedoms.addAll(freedomCase);	
		}
	nbFreedoms = listFreedoms.size();
	}

	// GETTERS AND SETTERS OF GROUP CLASS
public void setListFreedoms(HashSet listFreedoms) {
	this.listFreedoms = listFreedoms;
}
public HashSet getListCases() {
	return listCases;
}
public void setListCases(HashSet listCases) {
	this.listCases = listCases;}
public HashSet getListCases(HashSet listCases) {
	return listCases;}
public int getNbStones() {
	return nbCasesGroup;
}
public void setNbStones(int nbStones) {
	this.nbCasesGroup = nbStones;
}
public HashSet getListFreedoms() {
	return listFreedoms;
}

//SURCHARGE toString

@Override
public String toString() {
	// TODO Auto-generated method stub
	return " \t Ce groupe appartient au joueur "+ player.color+" et contient "+nbCasesGroup+" pierre(s)." ;
}
}