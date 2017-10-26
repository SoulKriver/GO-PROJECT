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
	

//HashSet list = new HashSet();
//list.add(10);
//System.out.println(list.size());
//list.add(10);
//System.out.println(list.size());
	
for (int i = 1; i<4;i=i+2) {
	for (int j =1; j<6; j++)
	{goban.newStoneGoban(black, i,j);
}
	
}
goban.newStoneGoban(black, 2,1);
goban.newStoneGoban(black, 2,3);
goban.newStoneGoban(white, 2,2);
//goban.newStoneGoban(white, 2,4);
//goban.newStoneGoban(white, 2,3);
//System.out.println(goban.getGobanTab()[i][i].getGroup().toString());
//System.out.println(goban.getGobanTab()[j][i].getGroup().toString());
//System.out.println(goban.getGobanTab()[k][i].getGroup().toString());
//System.out.println(goban.getGobanTab()[j][i].getGroup().getListCases());
//System.out.println(goban.getGobanTab()[k][i].getGroup().getListCases());
//System.out.println(goban.getGobanTab()[i][i].getGroup().getListCases());


}




}



