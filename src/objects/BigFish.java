package objects;

import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class BigFish extends GameCharacter implements Supporter {

	private static BigFish bf = new BigFish(null);

	private BigFish(Room room) {
		super(room);
	}

	public static BigFish getInstance() {
		return bf;
	}

	@Override
	public String getName() {
		return "bigFishLeft";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	// implementa a morte do peixe grande
	@Override
	public void dies() {

	}

	@Override
	public boolean canSupport(List<MovableObject> objects) {
		if (objects.size() > 2)
			return false;
		if (objects.size() == 2) {
			/*
			 * for(MovableObject object : objects) { if (object.isHeavyObject()) return
			 * false;
			 */
		}
		return false;
	}
	
	@Override
	public void move(Vector2D dir) {
		// chama a função move(Vector2D dir) de GameCharacter
		super.move(dir);
		
		Point2D destination = getPosition().plus(dir);

		GameObject obj = getRoom().getElementAt(destination);
		
		if(getElementAt(destination).equals("trap")) {
			 dies();
		 }
		
		GameObject obj = getRoom().getElementAt(destination);
		
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
