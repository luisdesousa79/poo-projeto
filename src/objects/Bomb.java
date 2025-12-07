package objects;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends LightObject {

	// estado de queda
	private boolean isFalling = false;

	public Bomb(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "bomb";
	}

	@Override
	public int getLayer() {
		return 1; 
	}

	public void setFalling(boolean falling) {
		this.isFalling = falling;
	}

	public boolean isFalling() {
		return isFalling;
	}

	public void explodes() {
		// define a area da explosao
		List<Point2D> areaDeExplosao = new ArrayList<>();
		areaDeExplosao.add(getPosition());
		for (Direction d : Direction.values()) {
			areaDeExplosao.add(getPosition().plus(d.asVector()));
		}

		// pega todos os objetos e guarda
		List<GameObject> todosObjetos = new ArrayList<>(getRoom().getObjects());

		for (GameObject obj : todosObjetos) {
			if (areaDeExplosao.contains(obj.getPosition())) {

				if (obj instanceof Water) {
					continue;
				}

				// verifica se o objeto está na borda
				Point2D p = obj.getPosition();
				if (p.getX() == 0 || p.getX() == 9 || p.getY() == 0 || p.getY() == 9) {
					continue;
					// ignora pois esta na extremidade
				}

				if (obj instanceof GameCharacter) {
					// mata o peixe na área de explosão
					((GameCharacter) obj).dies();
				} else {
					// remove qualquer objeto na área de explosão
					getRoom().removeObject(obj);
				}
			}
		}
	}

	@Override
	public void applyMovement() {
		this.applyGravity();
	}

	@Override
	public void applyGravity() {
		Point2D destination = getPosition().plus(Direction.DOWN.asVector());

		// se o objeto no Tile imediatamente abaixo for apenas a água, a bomba move-se
		// para baixo (cai uma posição) e muda o estado para falling
		if (getRoom().isOnlyWaterAt(destination)) {
			setPosition(destination);
			setFalling(true);
			return;
		} else {
			// se estiver cair e já não houver água no tile inferior
			if (isFalling) {
				// salva os objetos em baixo
				List<GameObject> hitObjects = new ArrayList<>(getRoom().getObjectsAt(destination));

				for (GameObject object : hitObjects) {
					// se a bomba colide com um peixe, fica por cima do peixe
					if (object instanceof GameCharacter) {
						// deixa de estar a cair
						this.setFalling(false);
						return;
					}
				 
				// caso contrário, se não há nenhum peixe em baixo, mas outro objeto,
				// a bomba explode
				this.explodes();

				}
			}
		}
	}
}