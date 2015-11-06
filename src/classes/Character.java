package classes;

import classes.enumerations.Image;
import classes.enumerations.Position;
import classes.graph.Graph;
import classes.graph.Vertex;
import classes.utils.ResourcesUtils;
import javafx.application.Platform;

import java.util.List;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Character extends MapElement implements Runnable {
    private int characterImageIndex;
    private Position position;
    private List<Vertex> path;
    private boolean actionDone;

    public Character(int x, int y, int shapeSize) {
        super(x, y, shapeSize);
        actionDone = false;
        characterImageIndex = ResourcesUtils.getInstance().getRandomBirdIndex();

        position = Position.LEFT;
        if (characterImageIndex % 2 == 0)
            position = Position.RIGHT;

        getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex, position));
    }

    public Character(int x, int y, int shapeSize, Image image) {
        super(x, y, shapeSize, image);
    }

    public void changePosition() {
        if (position.equals(Position.LEFT)) {
            position = Position.RIGHT;
            getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex, position));
        } else {
            position = Position.LEFT;
            getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex, position));
        }
    }

    public void initPath(Graph graph, Vertex start, Vertex destination) {
        if (path != null)
            path.clear();
        path = graph.dijkstra(start, destination);
    }

    public void setActionDone(boolean actionDone) {
        this.actionDone = actionDone;
    }

    public Boolean getActionDone() {
        return actionDone;
    }

    @Override
    public void run() {
        if (path != null) {
            actionDone = false;
            for (Vertex vertex : path) {
                if (!actionDone) {
                    if (vertex.getX() < x && position.equals(Position.RIGHT)) {
                        changePosition();
                    } else if (vertex.getX() > x && position.equals(Position.LEFT)) {
                        changePosition();
                    }

                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }

                    Platform.runLater(() -> {
                        setX(vertex.getX());
                        setY(vertex.getY());
                    });
                }
            }
            actionDone = true;
        }
    }
}
