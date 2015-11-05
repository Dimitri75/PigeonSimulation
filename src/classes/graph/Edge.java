package classes.graph;

import classes.enumerations.MovementSpeed;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Edge {
	private Vertex source;
	private Vertex target;
	private double weight;

	public Edge(Vertex source, Vertex target, MovementSpeed movementSpeed) {
		this.source = source;
		this.target = target;

		switch (movementSpeed) {
		case NORMAL:
			this.weight = 1;
			break;
		case SLOW:
			this.weight = 2;
			break;
		default:
			this.weight = 1;
			break;
		}

		source.getAdjacencies().add(this);
	}
	
	public Vertex getSource(){
		return source;
	}
	
	public Vertex getTarget(){
		return target;
	}
	
	public double getWeight(){
		return weight;
	}
}