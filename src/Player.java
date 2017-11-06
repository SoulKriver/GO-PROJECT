import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Player {
	
	protected Color color; // black B or white W
	protected int captureNb; // le nombre de capture du joueur
	protected double komi;// les points de retard de blanc
	protected int groupNb; // le nombre de groupe de joueur sur le goban
	protected HashSet listGroup; // la liste des groupes du joueur sur le goban

	// CONSTRUCTOR OF PLAYER CLASS
	public Player(Color pcolor) { 
	color = pcolor;
	captureNb =0;
	groupNb =0;
		if (color ==Color.B)
		{komi =0;
		}
		else 
		{komi =6.5;
		}
	}
}
