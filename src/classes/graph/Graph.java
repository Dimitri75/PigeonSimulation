package classes.graph;

import enumerations.MovementSpeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Dimitri on 21/10/2015.
 */
public class Graph {
	private int width;
	private int height;
	private ArrayList<Vertex> listVertex;
	private ArrayList<Edge> listEdges;

	public Graph(int width, int height) {
		this.width = width;
		this.height = height;

		listVertex = new ArrayList<>();
		listEdges = new ArrayList<>();

		init();
	}

	public ArrayList<Vertex> getListVertex() {
		return listVertex;
	}

	public ArrayList<Edge> getListEdges() {
		return listEdges;
	}

	public Edge addEdge(Vertex source, Vertex target, MovementSpeed movementSpeed) {
		Edge edge = new Edge(source, target, movementSpeed);
		listEdges.add(edge);
		return edge;
	}

	public Vertex addVertex(int x, int y) {
		Vertex vertex = new Vertex(x, y);
		listVertex.add(vertex);
		return vertex;
	}

	public Vertex getVertexByLocation(int x, int y){
		for (Vertex vertex : listVertex)
			if (vertex.getX() == x && vertex.getY() == y)
				return vertex;

		return null;
	}

	public void init(){
		for (int y = 0; y <= height; y++){

			Vertex leftVertex = null;
			for (int x = 0; x <= width; x++){
				Vertex tmpVertex = addVertex(x, y);

				if (leftVertex != null)
					addEdge(tmpVertex, leftVertex, MovementSpeed.NORMAL);
				leftVertex = tmpVertex;

				if (y != 0){
					Vertex upVertex = getVertexByLocation(x, y - 1);
					addEdge(tmpVertex, upVertex, MovementSpeed.NORMAL);
				}
			}
		}
	}

	public List<Vertex> dijkstra(Vertex start, Vertex destination) {
		// ReinitVertex
		for (Vertex vertex : listVertex) {
			vertex.setMinDistance(Double.POSITIVE_INFINITY);
			vertex.setPrevious(null);
		}

		// ComputePaths
		start.setMinDistance(0.);
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(start);

		while (!vertexQueue.isEmpty()) {
			Vertex current = vertexQueue.poll();

			for (Edge e : current.getAdjacencies()) {
				Vertex targetVertex = e.getTarget();
				double weight = e.getWeight();
				double distanceThroughCurrent = current.getMinDistance()
						+ weight;
				if (distanceThroughCurrent < targetVertex.getMinDistance()) {
					vertexQueue.remove(targetVertex);
					targetVertex.setMinDistance(distanceThroughCurrent);
					targetVertex.setPrevious(current);
					vertexQueue.add(targetVertex);
				}
			}
		}
		vertexQueue.clear();

		// GetShortestPath
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = destination; vertex != null; vertex = vertex
				.getPrevious())
			path.add(vertex);

		Collections.reverse(path);

		return path;
	}
}