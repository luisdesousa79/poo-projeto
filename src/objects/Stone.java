package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Stone extends MovableObject implements Interactable {
	
	public Stone(Room room) {
		super(room);
	}

	@Override
    public boolean interact(GameCharacter actor, Vector2D dir) {
        
        if (actor instanceof SmallFish) {
            return false;
        }

        // futura posicao da pedra
        Point2D futurePosition = getPosition().plus(dir);
        
        GameObject objBehind = getRoom().getElementAt(futurePosition);
        
        if (objBehind == null) {
            setPosition(futurePosition);
            // diz ao peixe que interagiu
            return true; 
        }

        return false; // caminho bloqueado 
    }
	
	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public int getLayer() {
		return 1; //verificar se está correcto
	}
	
}
