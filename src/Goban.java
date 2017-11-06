import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Goban implements Cloneable{
private int sizeGoban; // la taille du goban, d�finie par le joueur en d�but de partie
private int nbCases; // le nombre de case, d�finie par la taille du goban
private int nbPlayedStones; // le nombre de pierres qui ont �t� pos�es sur le goban � un instant t
private Case gobanTab [][]; // le corps du goban, un tableau d'objet Cases
private boolean bool; // un bool�en pour l'�valuation, voir fonction isInGoban
private String prevMoves[]= {"Coup Pair", "Coup Impair"}; // clef format String pour l'�valuation de pr�sence d'un KO
private LinkedList story = new LinkedList();
//CONSTRUCTOR OF GOBAN CLASS


public Goban(int psizeGoban){
    System.out.println("\nCr�ation du Goban !");      // cr�ation du goban en d�but de partie    
        nbPlayedStones = 0;
        sizeGoban = psizeGoban;
    
        gobanTab = new Case [sizeGoban][sizeGoban];

        
   for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
    	for (int j=0;j <sizeGoban;j++){
    		gobanTab[i][j] = new Case(i,j,0);
    		if (i>0) {
    		   
    		   gobanTab[i][j].setTop(gobanTab[i-1][j]);
    		   gobanTab[i][j].setNbNearCases((gobanTab[i][j].getNbNearCases()+1));
    		   gobanTab[i-1][j].setDown(gobanTab[i][j]);
    		   gobanTab[i-1][j].setNbNearCases((gobanTab[i-1][j].getNbNearCases()+1));
    	      		}
    	   if (j>0) {
    		   gobanTab[i][j].setLeft(gobanTab[i][j-1]);
    		  gobanTab[i][j].setNbNearCases((gobanTab[i][j].getNbNearCases()+1));
    		   gobanTab[i][j-1].setRight(gobanTab[i][j]);
    		   gobanTab[i][j-1].setNbNearCases((gobanTab[i][j-1].getNbNearCases()+1));
    	   }
    	   }
    	}
   story.add(freeGobanStringKey());
   }


//METHODS OF GOBAN CLASS
	//BOOLEAN TEST FOR GOBAN CASE INPUT
public boolean isInTheGoban(int pline, int pcolumn) { // renvoit un bool�en qui �value si des coordonn�es sont dans le goban
bool = (pline<sizeGoban)&&(pcolumn<sizeGoban);
return bool;}

	// DISPLAY METHODS OF GOBAN STATUS
public void displayfreeGoban() { // permet d'afficher dans la console l'�tat du goban � chaque coup
	System.out.println("\nVoici l'�tat d'occupation du Goban :\n");
for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
	for (int j=0;j <sizeGoban;j++){
		if (gobanTab[i][j].getOccupied()==null)
		{System.out.print('-');}
		else
		{System.out.print(gobanTab[i][j].getOccupied());}
}
	System.out.println(""); // saut � la ligne pour voir ligne par ligne
	
}
System.out.println("\n");}
public void displayNearCasesGoban() { // permet d'afficher dans la console l'�tat des libert�s de chaque case
	System.out.println("\nVoici le nombre de voisines des cases du Goban :\n");
	for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
	for (int j=0;j <sizeGoban;j++){
System.out.print(gobanTab[i][j].getNbNearCases());
}
	System.out.println("");
	
}
System.out.println("\n");
}
public String freeGobanStringKey() { // renvoit une cha�ne de caract�re de chaque libert� de chaque case. Utile pour l'�valuation des KO
String string = new String();
for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
	for (int j=0;j <sizeGoban;j++){
		if (gobanTab[i][j].getOccupied()==null)
		{string = string + 'f';}
		else {
string = string + gobanTab[i][j].getOccupied() ;}
}
}
return string;}

	// GOBAN STONE PLAYING METHOD
public void newStoneGoban(Player pplayer,int pline, int pcolumn) { // M�thode pour v�rivier les conditions de pose d'une nouvelle pierre sur le goban
		
	if (isInTheGoban(pline, pcolumn)==false) // test si dans le Goban = si OK, on passe � la suite, sinon on sort
		System.out.println("Coup "+(getNbStones()+1)+ " refus� : D�sol�, cette case n'est pas dans le Goban !"); 
	
	else {
		Case newCase =gobanTab[pline][pcolumn]; // on cr�e la case en question pou r�aliser des tests suppl�mentaires
		if (newCase.isFree()==false) { // test si la case est occup�e = Si OK, on passe � la suite, sinon on sort
		System.out.println("Coup "+(getNbStones()+1)+ " refus� : D�sol�, cette case est d�j� occup�e !");}
	
		else {
			if (newCase.isKilling(pplayer)==true) // test si on tue. Si OK = on tue le groupe et on place la pierre
			{
				if (newCase.isNotKO(pplayer, this)==false)
				{System.out.println("Coup "+(getNbStones()+1)+ " refus� : D�sol�, ce coup est interdit par la r�gle du KO"); 
				}
			
				else
				{HashSet listCasesToKill = newCase.getNearCasesToKill(pplayer);
				Iterator itlistCasesToKill = listCasesToKill.iterator();
				addNewStoneGoban(pplayer, newCase);// Pose de la pierre
				while (itlistCasesToKill.hasNext())
					{Case killedCase = ((Case)itlistCasesToKill.next());
					caseGroupKill(killedCase);
					}
				displayfreeGoban();	

				}
			}
			else {
				if (newCase.isNotSuicidal(pplayer)==false) // test si le coup est suicidaire
				System.out.println("Coup "+(getNbStones()+1)+ " refus� : D�sol�, ce coup est suicidaire pour le joueur " +pplayer.color+"!");
				else 
				{addNewStoneGoban(pplayer, newCase);// Pose de la pierre
				displayfreeGoban();
				}
				}

			}
		}
}	
protected void addNewStoneGoban(Player pplayer, Case newCase) { // M�thode pour poser une nouvelle pierre sur le goban
	newCase.setOccupied(pplayer.color); // pose effective = changement d'�tat d'occupation de la case
	nbPlayedStones++; // incr�ment du nombre de pierres pos�es
	System.out.println("\n___________________________________________________________\nCoup num�ro " +nbPlayedStones+ "\nLe joueur " +pplayer.color+ " vient de jouer en ligne " +newCase.getLine()+ " et colomne " +newCase.getColumn()+"." );
	
	HashSet friends = newCase.getNearFriendCases(); // liste des pierres alli�es dans les alentours
	Iterator itFriends = friends.iterator(); // iterateur de pierres alli�es
	Group group = new Group(pplayer,newCase); // cr�ation du groupe de la nouvelle pierre
	newCase.setGroup(group); // stockage du groupe dans la case de la nouvelle pierre
	HashSet masterList = new HashSet(); // cr�ation d'une masterlist pour les gouverner toutes
	masterList.add(newCase); // ajout de la nouvelle case dans la masterlist
		
		while (itFriends.hasNext())//boucle pour ajouter les groupes chaque pierre alli�e � la master list
			{Case friendCase = (Case)(itFriends.next()); // stockage temporaire de la case en cours
			masterList.addAll(friendCase.getGroup().listCases);// ajout de chaque �l�ments de la liste � la masterlist
			}
		Iterator itMasterList = masterList.iterator(); // cr�ation d'un it�rator dans la master list
		
		while (itMasterList.hasNext()) // boucle pour stocker la master list dans chaque �l�ment de celle-ci
			{
			Case masterCase = (Case)(itMasterList.next()); // stockage temporaire de la case en cours
			masterCase.getGroup().setListCases(masterList); // stockage de la masterlist dans la case
			masterCase.getGroup().setNbStones(masterList.size()); // mise � jour de la taille du groupe
			masterCase.getGroup().freedomsGroupUpdate(); // mise � jour des libert�s
			}
		story.add(freeGobanStringKey());
			}
public void caseGroupKill(Case firstCaseToKill) { // une m�thode pour tuer un groupe
	HashSet listCasesToKill = firstCaseToKill.getGroup().getListCases(); // stockage des cases du groupe � tuer
	Iterator itlistCasesToKill = listCasesToKill.iterator();
	while(itlistCasesToKill.hasNext())
	{Case caseToKill = (Case) itlistCasesToKill.next();
	caseToKill.setOccupied(null);
	caseToKill.setGroup(null);
	System.out.println("La case "+caseToKill.getLetter()+caseToKill.getLine()+" est libre !");
	}
	System.out.println("\nLe groupe est mort !");
}

	// GETTERS AND SETTERS OF GOBAN CLASS
public int getSizeGoban() {
	return sizeGoban;
}
public void setSizeGoban(int sizeGoban) {
	this.sizeGoban = sizeGoban;
}
public int getNbCases() {
	return nbCases;
}
public void setNbCases(int nbCases) {
	this.nbCases = nbCases;
}
public int getNbStones() {
	return nbPlayedStones;
}
public void setNbStones(int nbStones) {
	this.nbPlayedStones = nbStones;
}
public Case[][] getGobanTab() {
	return gobanTab;
}
public void setGobanTab(Case[][] gobanTab) {
	this.gobanTab = gobanTab;
}
public String[] getPrevMoves() {
	return prevMoves;
}

public void setPrevMoves(String[] prevMoves) {
	this.prevMoves = prevMoves;
}
public LinkedList getStory() {
	return story;
}

public void setStory(LinkedList story) {
	this.story = story;
}

public void printStory() {
	for(int i = 0; i < story.size(); i++)
		System.out.println("Au coup " + i + ", l'�tat du Goban �tait = " + story.get(i));
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
