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
		return 1; // verificar
	}

	public void setFalling(boolean falling) {
		this.isFalling = falling;
	}

	public boolean isFalling() {
		return isFalling;
	}

	public void falls() {
		explode();
	}

	public void explode() {
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
}

