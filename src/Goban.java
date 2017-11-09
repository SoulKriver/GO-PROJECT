import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Goban implements Cloneable{
private String status;
private int sizeGoban; // la taille du goban, définie par le joueur en début de partie
private int nbCases; // le nombre de case, définie par la taille du goban
private int nbPlayedStones; // le nombre de pierres qui ont été posées sur le goban à un instant t
private Case gobanTab [][]; // le corps du goban, un tableau d'objet Cases
private boolean bool; // un booléen pour l'évaluation, voir fonction isInGoban
private LinkedList story = new LinkedList();
//CONSTRUCTOR OF GOBAN CLASS


public Goban(int psizeGoban){
    System.out.println("\nCréation du Goban !\n_________________________________________\n");      // création du goban en début de partie    
        nbPlayedStones = 0;
        sizeGoban = psizeGoban;
        status = "Original";
        gobanTab = new Case [sizeGoban][sizeGoban];

        
   for (int i =0;i<sizeGoban ;i++)  // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
    	{
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
public boolean isInTheGoban(int pline, int pcolumn) { // renvoit un booléen qui évalue si des coordonnées sont dans le goban
bool = (pline<sizeGoban)&&(pcolumn<sizeGoban);
return bool;}

	// DISPLAY METHODS OF GOBAN STATUS
public void displayfreeGoban() { // permet d'afficher dans la console l'état du goban à chaque coup
	System.out.println("\nVoici l'état d'occupation du Goban "+this.status+"  :\n");
for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
	for (int j=0;j <sizeGoban;j++){
		if (gobanTab[i][j].getOccupied()==null)
		{System.out.print('-');}
		else
		{System.out.print(gobanTab[i][j].getOccupied());}
}
	System.out.println(""); // saut à la ligne pour voir ligne par ligne
	
}
System.out.println("\n");}
public void displayNearCasesGoban() { // permet d'afficher dans la console l'état des libertés de chaque case
	System.out.println("\nVoici le nombre de voisines des cases du Goban :\n");
	for (int i =0;i<sizeGoban ;i++) { // boucle pour parcourir les cases, initialiser des cases, et remplir le tableau avec)
	for (int j=0;j <sizeGoban;j++){
System.out.print(gobanTab[i][j].getNbNearCases());
}
	System.out.println("");
	
}
System.out.println("\n");
}
public String freeGobanStringKey() { // renvoit une chaîne de caractère de chaque liberté de chaque case. Utile pour l'évaluation des KO
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
public void newStoneGoban(Player pplayer,int pline, int pcolumn) { // Méthode pour vérifier les conditions de pose d'une nouvelle pierre sur le goban
	System.out.println("\n___________________________________________________________\nCoup numéro " +(nbPlayedStones+1)+ "\nLe joueur " +pplayer.color+ " tente de jouer en ligne " +pline+ " et colomne " +pcolumn+"." );		
	if (isInTheGoban(pline, pcolumn)==false) // test si dans le Goban = si OK, on passe à la suite, sinon on sort
		System.out.println("Coup "+(getNbStones()+1)+ " refusé : Désolé, cette case n'est pas dans le Goban !"); 
	
	else {System.out.println("isInTheGoban = OK");
		Case newCase =gobanTab[pline][pcolumn]; // on crée la case en question pou réaliser des tests supplémentaires
		if (newCase.isFree()==false) { // test si la case est occupée = Si OK, on passe à la suite, sinon on sort
		System.out.println("Coup "+(getNbStones()+1)+ " refusé : Désolé, cette case est déjà occupée !");}
	
		else {System.out.println("isFree = OK");
			if (newCase.isKilling(pplayer)==true) // test si on tue. Si OK = on tue le groupe et on place la pierre
			{System.out.println("isKilling = true");
				if (newCase.isNotKO(pplayer, this)==false) // test pour vérifier si on joue un KO interdit
				{System.out.println("Coup "+(getNbStones()+1)+ " refusé : Désolé, ce coup est interdit par la règle du KO"); 
				}
			
				else
				{System.out.println("KillingProcess = Engaged");
				newCase.killingProcess(pplayer,this); // kill de groupes adjacents par le joueur en jouant en case newCase sur le goban this
				System.out.println("KillingProcessFinished");
				displayfreeGoban();	

				}
			}
			else {System.out.println("KillingProcess = NotEngaged");
				if (newCase.isNotSuicidal(pplayer)==false) // test si le coup est suicidaire
				System.out.println("Coup "+(getNbStones()+1)+ " refusé : Désolé, ce coup est suicidaire pour le joueur " +pplayer.color+"!");
				else 
				{System.out.println("addNewStoneGoban = Engaged");
					addNewStoneGoban(pplayer, newCase);// Pose de la pierre
					System.out.println("addNewStoneGoban = Finished");
				displayfreeGoban();
				}
				}

			}
		}
}	
protected void addNewStoneGoban(Player pplayer, Case newCase) { // Méthode pour poser une nouvelle pierre sur le goban
	newCase.setOccupied(pplayer.color); // pose effective = changement d'état d'occupation de la case
	this.nbPlayedStones++; // incrément du nombre de pierres posées
	System.out.println("\n___________________________________________________________\nCoup numéro " +nbPlayedStones+ "\nLe joueur " +pplayer.color+ " vient de jouer en ligne " +newCase.getLine()+ " et colomne " +newCase.getColumn()+" sur le Goban "+this.status+"." );
	
	HashSet friends = newCase.getNearFriendCases(); // liste des pierres alliées dans les alentours
	Iterator itFriends = friends.iterator(); // iterateur de pierres alliées
	Group group = new Group(pplayer,newCase); // création du groupe de la nouvelle pierre
	newCase.setGroup(group); // stockage du groupe dans la case de la nouvelle pierre
	HashSet masterList = new HashSet(); // création d'une masterlist pour les gouverner toutes
	masterList.add(newCase); // ajout de la nouvelle case dans la masterlist
		
		while (itFriends.hasNext())//boucle pour ajouter les groupes chaque pierre alliée à la master list
			{Case friendCase = (Case)(itFriends.next()); // stockage temporaire de la case en cours
			masterList.addAll(friendCase.getGroup().listCases);// ajout de chaque éléments de la liste à la masterlist
			}
		Iterator itMasterList = masterList.iterator(); // création d'un itérator dans la master list
		
		while (itMasterList.hasNext()) // boucle pour stocker la master list dans chaque élément de celle-ci
			{
			Case masterCase = (Case)(itMasterList.next()); // stockage temporaire de la case en cours
			masterCase.getGroup().setListCases(masterList); // stockage de la masterlist dans la case
			masterCase.getGroup().setNbStones(masterList.size()); // mise à jour de la taille du groupe
			masterCase.getGroup().freedomsGroupUpdate(); // mise à jour des libertés
			}
		story.add(freeGobanStringKey());
			}
public void caseGroupKill(Case firstCaseToKill) { // une méthode pour tuer un groupe
	HashSet listCasesToKill = firstCaseToKill.getGroup().getListCases(); // stockage des cases du groupe à tuer
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
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
	}
public LinkedList getStory() {
	return story;
}
public void setStory(LinkedList story) {
	this.story = story;
}
public void printStory() {
	for(int i = 0; i < story.size(); i++)
		System.out.println("Au coup " + i + ", l'état du Goban était = " + story.get(i));
		}
//SURCHARGE Clone
public Object clone() {
	Goban gobanclone = null;
	try {
		
		gobanclone = (Goban)super.clone();
		gobanclone.gobanTab = new Case[sizeGoban][sizeGoban];
		for (int i =0;i<sizeGoban;i++)
			for (int j=0;j<sizeGoban;j++)
		{gobanclone.getGobanTab()[i][j]=((Case) this.getGobanTab()[i][j].clone());
		System.out.println(gobanclone.getGobanTab()[i][j].getGroup());
		gobanclone.getGobanTab()[i][j].setGroup((Group)this.getGobanTab()[i][j].getGroup().clone());
		gobanclone.getGobanTab()[i][j].setGroup(this.getGobanTab()[i][j].getGroup().groupTransfert(gobanclone));
		 	if (i>0) 
		 	{  gobanclone.gobanTab[i][j].setTop(gobanclone.gobanTab[i-1][j]);
		 	gobanclone.gobanTab[i-1][j].setDown(gobanclone.gobanTab[i][j]);
    		}
    	   if (j>0) 
    	   {
    		   gobanclone.gobanTab[i][j].setLeft(gobanclone.gobanTab[i][j-1]);
    		   gobanclone.gobanTab[i][j-1].setRight(gobanclone.gobanTab[i][j]);
    	
    	   }
    	}
		
		
		
		}
		catch(CloneNotSupportedException cnse) {
		// Ne devrait jamais arriver car nous implémentons 
		// l'interface Cloneable
		cnse.printStackTrace(System.err);
	}
	
	for (int i = 0; i<sizeGoban;i++)
		for (int j = 0; j<sizeGoban; j++)
			gobanclone.gobanTab[i][j] = (Case)gobanTab[i][j].clone();
	// on renvoie le clone
	return gobanclone;
}



}


