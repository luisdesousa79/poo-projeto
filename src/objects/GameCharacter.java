package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameCharacter extends GameObject {
	
	public GameCharacter(Room room) {
		super(room);
	}
	
	
	public void move(Vector2D dir) {
		Point2D destination = getPosition().plus(dir); 
		
		GameObject obj = getRoom().getElementAt(destination);
		
		if (obj == null) {
            // se for agua movese
            setPosition(destination);
        }
		else if (obj instanceof Interactable) {
            // trata o obj como interctable
            Interactable interactableObj = (Interactable) obj;
            
            // se interagiu, move-se para o destino
            if (interactableObj.interact(this, dir)) {
                setPosition(destination);
            }
        }
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
}