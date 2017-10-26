import java.util.HashSet;
import java.util.Iterator;

public class Player {
	
	protected char color; // black B or white W
	protected int captureNb;
	protected double komi;
	protected int groupNb;
	protected HashSet listGroup;
	
	public Player(char pcolor) { // builder Player
		color = pcolor;
	captureNb =0;
		groupNb =0;
		if (color =='B')
			komi =0;
		else komi =6.5;
		
	}
	
}
