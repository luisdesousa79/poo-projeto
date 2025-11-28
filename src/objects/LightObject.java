package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class LightObject extends MovableObject {
	
	public LightObject(Room room) {
		super(room);
	}

	@Override
    public boolean interact(GameCharacter actor, Vector2D dir) {
        
        // posição futura do objecto
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
