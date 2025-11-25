package objects;
import pt.iscte.poo.utils.Vector2D;

public interface Interactable {
		// diz se pôde interagir
	    boolean interact(GameCharacter actor, Vector2D dir);
}
