import java.util.HashSet;
import java.util.Iterator;

public class Goban {
private int sizeGoban; // la taille du goban, d�finie par le joueur en d�but de partie
private int nbCases; // le nombre de case, d�finie par la taille du goban
private int nbStones; // le nombre de pierres qui ont �t� pos�es sur le goban � un instant t
private Case gobanTab [][]; // le corps du goban, un tableau d'objet Cases
private Player white; // le joueur blanc
private Player black; // le joueur noir
private boolean bool; // un bool�en pour l'�valuation, voir fonction isInGoban
private int nbGroup; // le nombre de groupes cr��s sur le goban depuis le d�but de la partie



public Goban(int psizeGoban){
    System.out.println("\nCr�ation du Goban !");      // cr�ation du goban en d�but de partie    
        nbStones = 0;
        sizeGoban = psizeGoban;
    
        gobanTab = new Case [sizeGoban][sizeGoban];
    
   for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
    	for (int j=0;j <sizeGoban;j++){
    		gobanTab[i][j] = new Case(i,j,0,'f');
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
   }

public void displayfreeGoban() {
	System.out.println("\nVoici l'�tat d'occupation du Goban :\n");
for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
	for (int j=0;j <sizeGoban;j++){
System.out.print(gobanTab[i][j].getOccupied());
}
	System.out.println(""); // saut � la ligne pour voir ligne par ligne
	
}
System.out.println("\n");}

public void displayNearCasesGoban() {
	System.out.println("\nVoici le nombre de voisines des cases du Goban :\n");
	for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
	for (int j=0;j <sizeGoban;j++){
System.out.print(gobanTab[i][j].getNbNearCases());
}
	System.out.println("");
	
}
System.out.println("\n");
}

public boolean isInTheGoban(int pline, int pcolumn) { // renvoit un bool�en qui �value si des coordonn�es sont dans le goban
bool = (pline<sizeGoban)&&(pcolumn<sizeGoban);
return bool;}

public void newStoneGoban(Player pplayer,int pline, int pcolumn) { // M�thode pour poser une nouvelle pierre sur le goban
		
	if (isInTheGoban(pline, pcolumn)==false) // test si dans le Goban
		System.out.println("D�sol�, cette case n'est pas dans le Goban !"); 
	
	else {
		Case newCase =gobanTab[pline][pcolumn];
		if (newCase.isNotOccupied()==false) { // test si la case est occup�e
		System.out.println("D�sol�, cette case est d�j� occup�e !");}
	
		else if (newCase.isNotSuicidal2(pplayer)==false) // test si le coup est suicidaire
			System.out.println("D�sol�, ce coup est suicidaire pour le joueur " +pplayer.color+"!");
		else { // Pose de la pierre
			
			newCase.setOccupied(pplayer.color); // pose effective = changement d'�tat d'occupation de la case
			nbStones++; // incr�ment du nombre de pierres pos�es
			System.out.println("\nCoup num�ro " +nbStones+ "\nLe joueur " +pplayer.color+ " vient de jouer en ligne " +pline+ " et colomne " +pcolumn+"." );
			
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
					masterCase.getGroup().FreedomsGroupUpdate();
					}
			}
		displayfreeGoban();
		
	}
	}











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
	return nbStones;
}


public void setNbStones(int nbStones) {
	this.nbStones = nbStones;
}


public Case[][] getGobanTab() {
	return gobanTab;
}


public void setGobanTab(Case[][] gobanTab) {
	this.gobanTab = gobanTab;
}


}
