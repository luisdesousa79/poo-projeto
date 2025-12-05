package objects;

import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class HeavyObject extends MovableObject {

	public HeavyObject(Room room) {
		super(room);
	}

	@Override
	public void applyGravity() {

		super.applyGravity();

		// vai guardar a posição imediatamente abaixo
		Point2D destination = getPosition().plus(Direction.DOWN.asVector());

		Point2D below = destination.plus(Direction.DOWN.asVector());

		List<GameObject> objsBelow = getRoom().getObjectsAt(below);

		for (GameObject o : objsBelow) {
			// se por baixo houver um tronco, remove-o
			if (o instanceof Trunk) {
				getRoom().removeObject(o);
				break;
			}
		}
	}
}
