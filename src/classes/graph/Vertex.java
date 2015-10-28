package classes.graph;

import classes.Location;

import java.util.ArrayList;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Vertex implements Comparable<Vertex> {
	private Location location;
	private Vertex previous;
	private double minDistance = Double.POSITIVE_INFINITY;
	private ArrayList<Edge> adjacencies;

	public Vertex(Location location) {
		this.location = location;
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

	public String getName() {
		return location.toString();
	}
}