package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class HeavyObject extends MovableObject implements Interactable {

	public HeavyObject(Room room) {
		super(room);
	}
	
	@Override
    public boolean interact(GameCharacter actor, Vector2D dir) {
        
        if (actor instanceof SmallFish) {
            return false;
        }

        // futura posicao do objecto pesado
        Point2D futurePosition = getPosition().plus(dir);
        
        GameObject objBehind = getRoom().getElementAt(futurePosition);
        
        if (objBehind == null) {
            setPosition(futurePosition);
            // diz ao peixe que interagiu
            return true; 
        }

        return false; // caminho bloqueado 
    }
	

}
