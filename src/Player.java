import java.util.HashSet;
import java.util.Iterator;

public class Player {
	
	protected char color; // black B or white W
	protected int captureNb; // le nombre de capture du joueur
	protected double komi;// les points de retard de blanc
	protected int groupNb; // le nombre de groupe de joueur sur le goban
	protected HashSet listGroup; // la liste des groupes du joueur sur le goban

	// CONSTRUCTOR OF PLAYER CLASS
	public Player(char pcolor) { 
	color = pcolor;
	captureNb =0;
	groupNb =0;
		if (color =='B')
		{komi =0;
		}
		else 
		{komi =6.5;
		}
	}
}
