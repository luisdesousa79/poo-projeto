package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameCharacter extends GameObject {

	public GameCharacter(Room room) {
		super(room);
	}

	/*
	 * // inicialmente esta função fazia apenas movimentos aleatórios e agora //
	 * implementa o movimento dos peixes controlado pelo jogador
	 * 
	 * public void move(Vector2D dir) { Point2D destination =
	 * getPosition().plus(dir);
	 * 
	 * // inspecionamos a posição de destino para ver se está vazia (é só água) ou
	 * ocupada //no caso de estar ocupada, se está ocupada com um objecto móvel ou
	 * imóvel
	 * 
	 * // se é só água, o peixe pode mover-se para lá if
	 * (getRoom().isOnlyWaterAt(destination)) { setPosition(destination); return; }
	 * 
	 * // se é um objecto imóvel (Wall, SteelPipe), não acontece nada if
	 * (getRoom().hasImmovableAt(destination)) { return; }
	 * 
	 * // se for um objeto móvel, vai tentar empurrá-lo if
	 * (getRoom().hasMovableAt(destination)) { // obtém a lista de objetos na
	 * posição de destino List<GameObject> objectsAtDestination =
	 * getRoom().getObjectsAt(destination);
	 * 
	 * 
	 * Pushable pushableObject = null;
	 * 
	 * //percorre a lista de objetos na posção de destino for (GameObject object :
	 * objectsAtDestination) { if (object instanceof Pushable) { // se encontrar um
	 * objeto que possa ser empurrado, guarda-o numa variável pushableObject =
	 * (Pushable) object; break; } }
	 * 
	 * // chama a função push do objecto empurrado boolean pushed =
	 * pushableObject.push(dir);
	 * 
	 * // caso o objeto tenha sido empurrado, move-se para a posição anterior dele
	 * if (pushed) { setPosition(destination); }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * // se é um objecto móvel, pode ser empurrado (chamar o push desse objecto?)
	 * // aqui tenho de verificar se é um empurrão horizontal ou vertical // uma
	 * hipótese aqui é tratar o empurrar como uma espécie de interacção // se é uma
	 * bomba ou uma armadilha, os peixes têm de interagir com a bomba e com // a
	 * armadilha ou com outros objectos // esse objecto cama a sua interacção com o
	 * peixe. para que serve o dir aqui? // obj.interact(fish, dir)
	 * 
	 * 
	 * }
	 * 
	 */

	public void move(Vector2D dir) {
		Point2D destination = getPosition().plus(dir);

		GameObject obj = getRoom().getElementAt(destination);

		// se o destino for apenas água, move-se para lá
		if (obj == null) {
			setPosition(destination);
		}
	}

	// função que implementa a morte dos peixes
	public void dies() {
		getRoom().removeObject(this);
		// volta ao início do nível
	}

	@Override
	public int getLayer() {
		return 2;
	}

}