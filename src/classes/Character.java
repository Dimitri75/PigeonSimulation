package classes;

import classes.enumerations.Image;
import classes.enumerations.Position;
import classes.graph.Edge;
import classes.graph.Graph;
import classes.graph.Vertex;
import classes.utils.ResourcesUtils;
import javafx.application.Platform;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Character extends MapElement implements Runnable {

    private static List<Character> pigeons = new ArrayList<>();
    public static Graph graph;

    private int characterImageIndex;
    private Position position;
    private List<Vertex> path;

    public static boolean ACTION_DONE;
    public static Food FOOD_TO_EAT;


    public Character(int x, int y, int shapeSize) {
        super(x, y, shapeSize);
        characterImageIndex = ResourcesUtils.getInstance().getRandomBirdIndex();

        position = Position.LEFT;
        if (characterImageIndex % 2 == 0)
            position = Position.RIGHT;

        getShape().setFill(ResourcesUtils.getInstance().getBird(characterImageIndex, position));

        pigeons.add(this);
    }

    public Character(int x, int y, int shapeSize, Image image) {
        super(x, y, shapeSize, image);

        pigeons.add(this);
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
        this.graph = graph;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                if (path != null) {
                    ACTION_DONE = false;
                    for (Vertex vertex : path) {
                        if (!ACTION_DONE) {
                            if (vertex.getX() < x && position.equals(Position.RIGHT)) {
                                changePosition();
                            } else if (vertex.getX() > x && position.equals(Position.LEFT)) {
                                changePosition();
                            }

                            Thread.sleep(75);

                            Platform.runLater(() -> {
                                setX(vertex.getX());
                                setY(vertex.getY());
                            });
                        }
                    }
                    ACTION_DONE = true;
                    FOOD_TO_EAT.getShape().setOpacity(0.0);

                    this.notifyAll();
                    scatter();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void scatter() throws InterruptedException {
        while (inPigeonScope()) {
            Iterator i = pigeons.iterator();
            while (i.hasNext()) {
                Character p = (Character) i.next();
                synchronized (pigeons) {
                    boolean scatteringFinished = false;
                    Random randomGenerator = new Random();
                    while (!scatteringFinished) {
                        ifToBreak:
                        if (getX() - p.getX() <= graph.getPace() && getX() - p.getX() >= -graph.getPace()) {
                            if (getY() - p.getY() <= graph.getPace() && getY() - p.getY() >= -graph.getPace()) {
                                if (p != this) {
                                    Thread.sleep(200);

                                    Vertex v = graph.getVertexByLocation(getX(), getY());
                                    Object[] edges = v.getAdjacencies().toArray();

                                    boolean isPigeonInEdge = true;
                                    while (isPigeonInEdge) {
                                        long range = (long) edges.length - (long) 1 + 1;
                                        long fraction = (long) (range * randomGenerator.nextDouble());
                                        int randomNumber = (int) (fraction);

                                        Edge destination = (Edge) edges[randomNumber];

                                        if (destination.getTarget().getX() != p.getX() || destination.getTarget().getY() != p.getY()) {
                                            isPigeonInEdge = false;
                                            Platform.runLater(() -> {
                                                setX(destination.getTarget().getX());
                                                setY(destination.getTarget().getY());
                                            });
                                            pigeons.notifyAll();
                                        }
                                    }
                                    pigeons.wait();
                                    if (getX() == p.getX() && getY() == p.getY()) {

                                    } else {
                                        if (getX() - p.getX() > graph.getPace() || getX() - p.getX() < -(graph.getPace()) || getY() - p.getY() > graph.getPace() || getY() - p.getY() < -(graph.getPace())) {
                                            scatteringFinished = true;
                                        }
                                    }
                                } else {
                                    scatteringFinished = true;
                                }
                            } else {
                                scatteringFinished = true;
                            }
                        } else {
                            scatteringFinished = true;
                        }
                    }
                }
            }
        }
        return;
    }

    public synchronized boolean inPigeonScope() throws InterruptedException {
        synchronized (pigeons) {
            Iterator i = pigeons.iterator();
            while (i.hasNext()) {
                Character p = (Character) i.next();
                if (getX() - p.getX() <= graph.getPace() && getX() - p.getX() >= -(graph.getPace())) {
                    if (getY() - p.getY() <= graph.getPace() && getY() - p.getY() >= -(graph.getPace())) {
                        if (p != this) {
                            return true;
                        }
                    }
                }
            }
            ACTION_DONE = true;
            return false;
        }
    }
}
