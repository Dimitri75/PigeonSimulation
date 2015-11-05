package classes;

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
    private List<Vertex> path;


    public Character(int x, int y) {
        super(x, y, 70);
        characterImageIndex = ResourcesUtils.getInstance().getRandomBirdIndex();
        getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex));
    }

    public Character(int x, int y, Image image) {
        super(x, y, 70, image);
    }

    public void runPath(Graph graph, Vertex start, Vertex destination){
        if (path != null)
            path.clear();
        path = graph.dijkstra(start, destination);
        run();
    }

    public void run() {
        if (path != null){
            for (Vertex v : path){
                setX(v.getX());
                setY(v.getY());
            }
        }
    }
}
