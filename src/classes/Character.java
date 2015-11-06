package classes;

import classes.enumerations.Position;
import classes.graph.Graph;
import classes.graph.Vertex;

import classes.enumerations.Image;

import classes.utils.ResourcesUtils;

import java.util.List;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Character extends MapElement {
    private int characterImageIndex;
    private Position position;
    private List<Vertex> path;


    public Character(int x, int y) {
        super(x, y, 70);
        characterImageIndex = ResourcesUtils.getInstance().getRandomBirdIndex();

        position = Position.LEFT;
        if (characterImageIndex % 2 == 0)
            position = Position.RIGHT;

        getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex, position));
    }

    public Character(int x, int y, Image image) {
        super(x, y, 70, image);
    }

    public void changePosition(){
        if (position.equals(Position.LEFT)){
            position = Position.RIGHT;
            getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex, position));
        }
        else {
            position = Position.LEFT;
            getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex, position));
        }
    }

    public void runPath(Graph graph, Vertex start, Vertex destination){
        if (path != null)
            path.clear();
        path = graph.dijkstra(start, destination);
        start();
    }

    @Override
    public void run() {
        if (path != null){
            for (Vertex v : path){
                if (v.getX() < x && position.equals(Position.RIGHT)) {
                    changePosition();
                }
                else if (v.getX() > x && position.equals(Position.LEFT)){
                    changePosition();
                }

                setX(v.getX());
                setY(v.getY());
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
