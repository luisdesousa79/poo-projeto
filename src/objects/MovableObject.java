package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class MovableObject extends GameObject implements Pushable {

	public MovableObject(Room room) {
		super(room);
	}

	// este método implementa a interface Pushable
	// o obetivo é implementar um empurrão em objectos móveis
	@Override
	public boolean push(Vector2D dir) {
		// vai começar por ver se há apenas água na posição para onde está a ser
		// empurrado
		Point2D newPos = getPosition().plus(dir);

		// se houver apenas água, move-se para essa posição
		if (getRoom().isOnlyWaterAt(newPos)) {
			setPosition(newPos);
			// devolve verdadeiro caso seja empurrado
			return true;
		}

		// devolve falso caso não seja empurrado
		return false;
	}
	
	
}
