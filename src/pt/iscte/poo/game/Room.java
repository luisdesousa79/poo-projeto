package pt.iscte.poo.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Anchor;
import objects.BigFish;
import objects.Bomb;
import objects.Buoy;
import objects.Crab;
import objects.Cup;
import objects.Door;
import objects.GameCharacter;
import objects.GameObject;
import objects.HeavyObject;
import objects.HoledWall;
import objects.ImmovableObject;
import objects.MovableObject;
import objects.Sinkable;
import objects.SmallFish;
import objects.SteelHorizontal;
import objects.SteelVertical;
import objects.Stone;
import objects.Trap;
import objects.Trunk;
import objects.Wall;
import objects.Water;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

	private List<GameObject> objects;
	private String roomName;
	private GameEngine engine;
	private Point2D smallFishStartingPosition;
	private Point2D bigFishStartingPosition;
	private int activeFishIndex;

	private int levelNumber;

	public Room() {
		objects = new ArrayList<GameObject>();
	}

	private void setName(String name) {
		roomName = name;
	}

	public String getName() {
		return roomName;
	}

	private void setEngine(GameEngine engine) {
		this.engine = engine;
	}

	public void addObject(GameObject obj) {
		objects.add(obj);
		engine.updateGUI();
	}

	public void removeObject(GameObject obj) {
		objects.remove(obj);
		engine.updateGUI();
	}

	public List<GameObject> getObjects() {
		return objects;
	}

	public void setSmallFishStartingPosition(Point2D heroStartingPosition) {
		this.smallFishStartingPosition = heroStartingPosition;
	}

	public Point2D getSmallFishStartingPosition() {
		return smallFishStartingPosition;
	}

	public void setBigFishStartingPosition(Point2D heroStartingPosition) {
		this.bigFishStartingPosition = heroStartingPosition;
	}

	public Point2D getBigFishStartingPosition() {
		return bigFishStartingPosition;
	}

	public static Room readRoom(File f, GameEngine engine) {
		Room r = new Room();
		r.setEngine(engine);
		r.setName(f.getName());

		try {

			Scanner sc = new Scanner(f);

			int y = 0;

			while (sc.hasNextLine()) {

				// vai processar linha a linha enquanto houver linhas para ler

				String line = sc.nextLine();

				// dentro de cada linha processa caracter a caracter
				for (int x = 0; x < line.length(); x++) {
					char c = line.charAt(x);
					Point2D pos = new Point2D(x, y);

					// põe água em todas as posições da grelha
					GameObject water = new Water(r);
					water.setPosition(pos);
					r.addObject(water);

					// preenche ainda a grelha em conformidade com o caracter presente no ficheiro
					switch (c) {

					case 'B': // Big fish
						// BigFish bf = BigFish.getInstance();
						// bf.setPosition(pos);
						// bf.setRoom(r);
						r.setBigFishStartingPosition(pos);
						// r.addObject(bf);

						break;

					case 'S': // Small fish
						// SmallFish sf = SmallFish.getInstance();
						// sf.setPosition(pos);
						// sf.setRoom(r);
						r.setSmallFishStartingPosition(pos);
						// r.addObject(sf);
						break;

					case 'W': // Wall
						GameObject w = new Wall(r);
						w.setPosition(pos);
						r.addObject(w);
						break;

					case 'H':
						GameObject sh = new SteelHorizontal(r);
						sh.setPosition(pos);
						r.addObject(sh);
						break;

					case 'V':
						GameObject sv = new SteelVertical(r);
						sv.setPosition(pos);
						r.addObject(sv);
						break;

					case 'C':
						GameObject cup = new Cup(r);
						cup.setPosition(pos);
						r.addObject(cup);
						break;

					case 'R':
						GameObject stone = new Stone(r);
						stone.setPosition(pos);
						r.addObject(stone);
						break;

					case 'A':
						GameObject a = new Anchor(r);
						a.setPosition(pos);
						r.addObject(a);
						break;

					case 'b':
						GameObject b = new Bomb(r);
						b.setPosition(pos);
						r.addObject(b);
						break;

					case 'T':
						GameObject t = new Trap(r);
						t.setPosition(pos);
						r.addObject(t);
						break;

					case 'Y':
						GameObject trunk = new Trunk(r);
						trunk.setPosition(pos);
						r.addObject(trunk);
						break;

					case 'X':
						GameObject wh = new HoledWall(r);
						wh.setPosition(pos);
						r.addObject(wh);
						break;

					case 'E':
						GameObject door = new objects.Door(r);
						door.setPosition(pos);
						r.addObject(door);
						break;

					case 'U':
						GameObject buoy = new Buoy(r);
						buoy.setPosition(pos);
						r.addObject(buoy);
						break;

					case ' ':

					default:
						// ignora caracteres desconhecidos
						break;
					}

				}
				y++;

			}
			sc.close();

		} catch (Exception e) {
			System.err.println("Erro ao ler room " + f.getName());
		}

		return r;

	}

	public int getActiveFishIndex() {
		return activeFishIndex;
	}

	public GameCharacter getActiveFish() {
		if (activeFishIndex == 0)
			return SmallFish.getInstance();
		else
			return BigFish.getInstance();
	}

	public void nextFish() {
		if (activeFishIndex == 0)
			activeFishIndex = 1;
		else
			activeFishIndex = 0;
	}

	public Point2D getActiveFishPosition() {
		return getActiveFish().getPosition();
	}

	public void moveActiveFish(Direction d) {
		GameCharacter fish = getActiveFish();
		fish.move(d.asVector());
	}

	// este método permite listar os objectos que se encontram em determinada
	// posição
	public List<GameObject> getObjectsAt(Point2D position) {
		List<GameObject> objectsList = new ArrayList<>();
		for (GameObject gameObject : objects) {
			if (gameObject.getPosition().equals(position)) {
				objectsList.add(gameObject);
			}
		}

		return objectsList;

	}

	// função para verificar se uma determinada posição é ocupada apenas por água
	public boolean isOnlyWaterAt(Point2D p) {
		List<GameObject> objs = getObjectsAt(p);

		for (GameObject o : objs) {
			if (!(o instanceof Water) && !(o instanceof Door)) {
				return false;
			}
		}
		return true;
	}

	// função para verificar se um dado ponto contém um objeto imóvel
	public boolean hasImmovableAt(Point2D p) {
		List<GameObject> objs = getObjectsAt(p);

		for (GameObject o : objs) {
			if (o instanceof ImmovableObject) {
				return true;
			}
		}
		return false;
	}

	// função para verificar se um dado ponto contém um objeto móvel
	public boolean hasMovableAt(Point2D p) {
		List<GameObject> objs = getObjectsAt(p);

		for (GameObject o : objs) {
			if (o instanceof MovableObject) {
				return true;
			}
		}
		return false;
	}

	// este método diz que objeto está numa determinada posição, ignorando a
	// água
	public GameObject getElementAt(Point2D position) {
		for (GameObject obj : objects) {
			if (obj.getPosition().equals(position)) {
				// ignora a água
				if (obj instanceof Water)
					continue;

				return obj;
			}
		}
		return null; // dá null se a posicao é agua
	}

	// verifica se uma determinada posição contém uma instância de muro perfurado
	public boolean hasHoledWallAt(Point2D pos) {
		for (GameObject o : getObjectsAt(pos)) {
			if (o instanceof HoledWall)
				return true;
		}
		return false;
	}

	// verifica se uma determinada posição contém uma instância de taça
	public boolean hasCupAt(Point2D pos) {
		for (GameObject o : getObjectsAt(pos)) {
			if (o instanceof HoledWall)
				return true;
		}
		return false;
	}

	// este método aplica a gravidade (movimento para baixo de uma unidade) aos
	// objetos do Room
	public void applyGravity() {

		// cria uma cópia da lista de objectos do jogo
		List<GameObject> orderedObjects = new ArrayList<>(objects);

		// ordena a lista de objetos do jogo de tal forma que venham primeiro os que
		// estão em baixo
		orderedObjects.sort((a, b) -> (Integer.compare(b.getPosition().getY(), a.getPosition().getY())));

		// percorre a lista ordenada de objectos da Room
		for (GameObject object : orderedObjects) {

			// se o objecto for móvel, vai guardar a posição imediatamente abaixo
			if (object instanceof MovableObject && !(object instanceof Buoy)) {
				Point2D destination = object.getPosition().plus(Direction.DOWN.asVector());

				// se o objeto no Tile imediatamente abaixo for apenas a água, o objeto move-se
				// para baixo (cai uma posição)
				if (isOnlyWaterAt(destination)) {
					object.setPosition(destination);

					// se for a bomba a cair, o estado dela muda para falling
					if (object instanceof Bomb) {
						((Bomb) object).setFalling(true);
					}

				}

				// caso em que a bomba está a cair e encontra algo

				if (object instanceof Bomb) {
					Bomb b = (Bomb) object;

					if (b.isFalling()) {
						// salva o que está em baixo
						GameObject objetoAtingido = getElementAt(destination);

						// se a bomba colide com um peixe, o peixe suporta a bomba
						if (objetoAtingido instanceof GameCharacter) {
							// agora peixe está suportando a bomba
							b.setFalling(false);

						}

						// caso contrário, explode
						b.falls();
					}
				}

				// vamos verificar se, na queda, um objecto pesado cai em cima do tronco
				if (object instanceof HeavyObject) {

					Point2D below = destination.plus(Direction.DOWN.asVector());

					List<GameObject> objsBelow = getObjectsAt(below);

					for (GameObject o : objsBelow) {
						// se por baixo houver um tronco, remove-o
						if (o instanceof Trunk) {
							removeObject(o);
							break;
						}

					}
				}

			}

		}
	}

	public List<MovableObject> getMovableObjectsAbove(Point2D pos) {

		List<MovableObject> stack = new ArrayList<>();
		Point2D above = pos.plus(Direction.UP.asVector());

		while (getElementAt(above) instanceof MovableObject) {
			stack.add((MovableObject) getElementAt(above));
			above = above.plus(Direction.UP.asVector());
		}

		return stack;
	}

	// função que aplica o boiar aos objetos que boiam (neste caso, apenas à bóia)
	public void applyBuoyancy() {
			for (GameObject object : objects) {
				if (object instanceof Buoy) {
					//deve boiar se não tiver nenhum objeto móvel por cima
					Point2D above = object.getPosition().plus(Direction.UP.asVector());
					if (isOnlyWaterAt(above)) {
						object.setPosition(above);
					}
			}
		}
	}
	
	// função que aplica o afundar aos objetos Sinkable
	public void applySinking() {
	    for (GameObject object : objects) {
	        if (object instanceof Sinkable) {
	            ((Sinkable)object).sinks();
	        }
	    }
	}

	// faz com que os caranguejos se movam
	public void processEnemies() {
		
		List<GameObject> enemies = new ArrayList<>(objects);
	    
	    for (GameObject obj : enemies) {
	        // se tiver um caranguejo, move-o
	        if (obj instanceof Crab) {
	            ((Crab) obj).moveEnemy();
	        }
	    }
	}
	
}