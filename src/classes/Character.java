package classes;

import classes.enumerations.Image;
import classes.graph.Graph;
import classes.graph.Vertex;
import classes.utils.ResourcesUtils;
import javafx.application.Platform;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Character extends MapElement implements Runnable {
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

    public void runPath(Graph graph, Vertex start, Vertex destination) {
        if (path != null)
            path.clear();
        path = graph.dijkstra(start, destination);
    }

    @Override
    public void run() {
        try {
            if (path != null) {
                Iterator<Vertex> vertexIterator = path.iterator();
                while (vertexIterator.hasNext()) {
                    Vertex v = vertexIterator.next();

                    Platform.runLater(() -> {
                            setX(v.getX());
                            setY(v.getY());
                    });

                    Thread.sleep(70);
                }
            }
        } catch (Exception e) {

        }
    }

}
