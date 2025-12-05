package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class MovableObject extends GameObject implements Pushable {

	public MovableObject(Room room) {
		super(room);
	}

	@Override
	public void updatePhysics() {
		applyGravity();
	}

	public void applyGravity() {
	// vai guardar a posição imediatamente abaixo
	Point2D destination = getPosition().plus(Direction.DOWN.asVector());
	
	// se o objeto no Tile imediatamente abaixo for apenas a água, o objeto move-se 
	//para baixo (cai uma posição)
	if (getRoom().isOnlyWaterAt(destination)) {
		setPosition(destination);
		}
	}

	// este método implementa a interface Pushable
	// o objetivo é implementar um empurrão em objectos móveis
	@Override
	public boolean push(Vector2D dir) {
		// vai começar por ver se há apenas água na posição para onde está a ser
		// empurrado
		Point2D newPos = getPosition().plus(dir);

		// se houver apenas água no destino, move-se para essa posição
		if (getRoom().isOnlyWaterAt(newPos)) {
			setPosition(newPos);
			// devolve verdadeiro caso seja empurrado
			return true;
		}

		// se houver um objecto na direção para onde está a ser empurrado
		// vai ver se esse objeto é móvel
		// se o objeto for móvel, vai tentar empurrá-lo
		if (getRoom().hasMovableAt(newPos)) {

			MovableObject nextObject = (MovableObject) getRoom().getElementAt(newPos);

			// se for um empurrão na vertical, só pode mover-se uma posição
			if (dir.getY() != 0) {

				// inspeciona se na direção do empurrão há um objeto adjacente
				if (getRoom().isOnlyWaterAt(nextObject.getPosition().plus(dir))) {
					// havendo empurra esse objeto para a posição seguinte
					nextObject.setPosition(nextObject.getPosition().plus(dir));
					// move-se para a posição deixada vazia pelo objeto empurrado
					setPosition(newPos);
					return true;
				}

				return false;

			}

			// se for um empurrão na horizontal e houver mais do que objeto na horizontal na
			// direção
			// do empurrão, empurra-os em cadeia

			boolean pushed = nextObject.push(dir);

			if (pushed) {
				setPosition(newPos);
				return true;
			}
			return false;
		}

		// devolve falso caso não seja empurrado
		return false;

	}

}
