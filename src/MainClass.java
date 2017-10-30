import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;

public class MainClass{
	

public static void main(String[] args) {
	
	Scanner sc = new Scanner(System.in);// initialisation de l'objet scanner
	System.out.println("Bienvenue ! Sur quel goban souhaitez-vous jouer ? 9, 13 ou 19 ?"); 	
	int mainSizeGoban =sc.nextInt();
	System.out.println("\nVous avez choisi une partie "+mainSizeGoban+" x " +mainSizeGoban+". Bonne partie !");
	Goban goban = new Goban(mainSizeGoban); // le goban est créé
	Player white = new Player('W');
	Player black = new Player ('B');
	
goban.newStoneGoban(black,8, 7);
goban.newStoneGoban(black,7, 8);
goban.newStoneGoban(black,6, 8);
goban.newStoneGoban(white,8,6);
goban.newStoneGoban(white,7,7);
goban.newStoneGoban(white,6,7);
goban.newStoneGoban(white,5,8);
goban.newStoneGoban(white,8,8);

}




}



