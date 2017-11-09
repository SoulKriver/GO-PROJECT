import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Group implements Cloneable{
private Player player; // le joueur propri�taire du groupe
HashSet listCases = new HashSet(); // la liste des cases comprises dans le groupe
private int nbCasesGroup = listCases.size(); // le nombre de pierres dans le groupe
HashSet listFreedoms = new HashSet(); // la lise des cases de libert�s du groupe
private int nbFreedoms; // le nombre de libert� qu'il reste au groupe

// CONSTRUCTOR OF GROUP CLASS
public Group (Player pplayer) { // cr�ation d'un nouveau groupe
	
	player = pplayer;
	player.groupNb++; // incr�mentation du nombre de groupe
	}

public Group (Player pplayer, Case pcase) { // cr�ation d'un nouveau groupe lors de la pose d'une pierre
	
	player = pplayer;
	player.groupNb++; // incr�mentation du nombre de groupe
	listCases.add(pcase); // ajout de la case initiale du premier groupe
	nbCasesGroup = listCases.size(); // le nombre de cases constituant le groupe
	listFreedoms = pcase.getFreedomCases(); // la liste des cases de libert�s du groupe (une seule case � la cr�ation)
	nbFreedoms = listFreedoms.size(); // le nombre de cases de libert�s du groupe
}

//METHODS OF GROUP CLASS
public void freedomsGroupUpdate () { // une m�thode pour mettre � jour les libert�s d'un groupe
	listFreedoms.removeAll(listFreedoms);
	Iterator itListCases = listCases.iterator();
	while (itListCases.hasNext())
		{HashSet freedomCase = ((Case)itListCases.next()).getFreedomCases();
		listFreedoms.addAll(freedomCase);	
		}
	nbFreedoms = listFreedoms.size();
	}
public Group groupTransfert (Goban pprocessedGoban)
{Group copyGroup = new Group (this.player);

Iterator itGroup = this.listCases.iterator();
	while(itGroup.hasNext())
	{Case copiedCase = (Case) itGroup.next();
	copyGroup.listCases.add(pprocessedGoban.getGobanTab()[copiedCase.getLine()][copiedCase.getColumn()]);
	}

	
return copyGroup;
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
//SURCHARGE Clone
public Object clone() {
	Object o = null;
	try {
		// On r�cup�re l'instance � renvoyer par l'appel de la 
		// m�thode super.clone()
		o = super.clone();
	} catch(CloneNotSupportedException cnse) {
		// Ne devrait jamais arriver car nous impl�mentons 
		// l'interface Cloneable
		cnse.printStackTrace(System.err);
	}
	// on renvoie le clone
	return o;
}
}