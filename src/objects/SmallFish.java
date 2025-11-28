package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class SmallFish extends GameCharacter {

	private static SmallFish sf = new SmallFish(null);
	
	private SmallFish(Room room) {
		super(room);
	}

	public static SmallFish getInstance() {
		return sf;
	}
	
	@Override
	public String getName() {
		return "smallFishLeft";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public void move(Vector2D dir) {
		//chama a função move da classe GameCharacter
		super.move(dir);
		
		Point2D destination = getPosition().plus(dir);
		
		
		
		// se o destino for a parede com buracos, move-se para lá
		if (obj instanceof HoledWall) {
			setPosition(destination);
		}
		
		// se no destino estiver um objeto pesado, não faz nada
		if (obj instanceof HeavyObject) 
			return;
		
		else if (obj instanceof Interactable) {
            // trata o obj como interctable
            Interactable interactableObj = (Interactable) obj;
            
            // se interagiu, move-se para o destino
            if (interactableObj.interact(this, dir)) {
                setPosition(destination);
            }
        }
	}

}
