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
	public boolean canPush(Pushable object, Vector2D dir) {
		Point2D objectPosition = ((GameObject) object).getPosition();

		// se o empurrão for na vertical, o peixe grande só consegue empurrar um objecto
		// leve ou pesado
		if (dir.getY() != 0) {
			// se houver dois objetos seguidos na vertical, o peixe grande não consegue
			// empurrar

			Point2D nextToObject = objectPosition.plus(dir);

			if (getRoom().isOnlyWaterAt(nextToObject)) {
				return true;
			}

			return false;
		}

		// se o empurrão for na horizontal, o peixe grande consegue empurrar vários
		// objetos leves ou pesados
			
	       if (dir.getX() != 0)
	           return true;
		
		return false;
	}

}
