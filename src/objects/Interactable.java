package objects;
import pt.iscte.poo.utils.Vector2D;

public interface Interactable {
		// devolve um boolean que diz se há interação ou não
	    boolean interact(GameCharacter actor, Vector2D dir);
}
