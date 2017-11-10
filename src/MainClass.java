import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MainClass{
	

public static void main(String[] args) {
	
//	Scanner sc = new Scanner(System.in);// initialisation de l'objet scanner
//	System.out.println("Bienvenue ! Sur quel goban souhaitez-vous jouer ? 9, 13 ou 19 ?"); 	
//	int mainSizeGoban =sc.nextInt();
	int mainSizeGoban = 9;
	System.out.println("\nVous avez choisi une partie "+mainSizeGoban+" x " +mainSizeGoban+". Bonne partie !");
	Goban goban = new Goban(mainSizeGoban,"Original"); // le goban est créé
	Player white = new Player(Color.W);
	Player black = new Player (Color.B);
	
	


goban.newStoneGoban(black, 0, 1);
goban.newStoneGoban(white, 0, 2);
goban.newStoneGoban(black, 1, 2);
goban.newStoneGoban(white, 1, 3);
goban.newStoneGoban(black, 0, 3);
goban.newStoneGoban(white, 0, 4);
goban.newStoneGoban(black, 3, 3);
goban.newStoneGoban(white, 0, 2);
goban.newStoneGoban(black, 0, 3);


}




}



