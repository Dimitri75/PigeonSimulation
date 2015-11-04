package classes.graph;

import java.util.ArrayList;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Vertex extends Location implements Comparable<Vertex> {
	private Vertex previous;
	private double minDistance = Double.POSITIVE_INFINITY;
	private ArrayList<Edge> adjacencies;

	public Vertex(){

	}

	public Vertex(int x, int y) {
		super(x, y);
		adjacencies = new ArrayList<Edge>();
	}

	public ArrayList<Edge> getAdjacencies(){
		return adjacencies;
	}

	public void setMinDistance(double minDistance) {
		this.minDistance = minDistance;
	}

	public double getMinDistance() {
		return minDistance;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public Vertex getPrevious() {
		return previous;
	}

	@Override
	public int compareTo(Vertex vertex) {
		return Double.compare(minDistance, vertex.minDistance);
	}
}