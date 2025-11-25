package pt.iscte.poo.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Anchor;
import objects.BigFish;
import objects.Bomb;
import objects.Cup;
import objects.GameCharacter;
import objects.GameObject;
import objects.HoledWall;
import objects.MovableObject;
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
				
				//vai processar linha a linha enquanto houver linhas para ler

				String line = sc.nextLine();
				
				//dentro de cada linha processa caracter a caracter
				for (int x = 0; x < line.length(); x++) {
					char c = line.charAt(x);
					Point2D pos = new Point2D(x, y);
					
					//põe água em todas as posições da grelha
				    GameObject water = new Water(r);
	                water.setPosition(pos);
	                r.addObject(water);
					
					//preenche ainda a grelha em conformidade com o caracter presente no ficheiro
					switch (c) {

					case 'B': // Big fish
						BigFish bf = BigFish.getInstance();
						bf.setPosition(pos);
						bf.setRoom(r);
						r.addObject(bf);
						break;

					case 'S': // Small fish
						SmallFish sf = SmallFish.getInstance();
						sf.setPosition(pos);
						sf.setRoom(r);
						r.addObject(sf);
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
		// if (canMoveTo(newPos)) {
		// setPosition(newPos)
	}
	
	public GameObject getElementAt(Point2D position) {
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position)) {
                // ignora a agua
                if (obj instanceof Water) continue;
                
                return obj;
            }
        }
        return null; // da null se a posicao é agua
    }

	public void applyGravity() {
	    // percore a room de cima para baixo
	    for (int y = 9; y >= 0; y--) { 
	        for (int x = 0; x < 10; x++) {
	            
	            Point2D position = new Point2D(x, y);
	            GameObject obj = getElementAt(position);
	            
	            
	            if (obj instanceof MovableObject) { 
	                
	                Point2D positionBelow = new Point2D(x, y + 1);
	                
	                // ver se ta dentro do mapa e o de baixo é agua
	                if (positionBelow.getY() < 10 && getElementAt(positionBelow) == null) {
	                    obj.setPosition(positionBelow); 
	                }
	            }
	        }
	    }
	}
	
}