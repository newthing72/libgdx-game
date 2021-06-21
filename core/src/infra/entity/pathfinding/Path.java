package infra.entity.pathfinding;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.*;

public class Path {

  Vertex source;
  Vertex target;

  List<Vertex> visted = new LinkedList<>();

  Set<PathNode> unvisitedPathNodeSet = new HashSet<>();
  Set<PathNode> visitedPathNodeSet = new HashSet<>();

  PathNode finalPathNode = null;

  @Inject Graph graph;

  @Inject
  public Path(@Assisted("source") Vertex source, @Assisted("target") Vertex target) {
    this.source = source;
    this.target = target;
    this.unvisitedPathNodeSet.add(
        new PathNode(new Edge(this.source, this.source, null), this.target));
  }

  public void search() {
    while (unvisitedPathNodeSet.size() > 0) {
      PathNode current =
          unvisitedPathNodeSet.stream()
              .min(Comparator.comparingDouble(PathNode::getHeuristic))
              .get();
      if(current.edge.from.position.getYReal()>=1.5){
        System.out.println(current.edge.actionKey+ " "+current.edge.from.position+ " "+current.edge.to.position);

      }
      unvisitedPathNodeSet.remove(current);
      visitedPathNodeSet.add(current);
      if (!current.edge.to.isExplored()) {
        current.edge.to.exploreEdges();
      }
      for (Edge edge : this.graph.getEdges(current.edge.to)) {
//        System.out.println("unvisted "+unvisitedPathNodeSet.size());
//        System.out.println("visted " + visitedPathNodeSet.size());
        PathNode discoveredPathNode = new PathNode(edge, this.target);

        discoveredPathNode.setPrevious(current);

        if (discoveredPathNode.getHeuristic() < 0.5) {
          System.out.println("found");
          finalPathNode = discoveredPathNode;
          return;
        }

        if (visitedPathNodeSet.contains(discoveredPathNode)
            || unvisitedPathNodeSet.contains(discoveredPathNode)) continue;
        unvisitedPathNodeSet.add(discoveredPathNode);
      }
    }
  }

  public List<Edge> getPathEdgeList() {
    List<Edge> edgeList = new LinkedList<>();
    PathNode current = finalPathNode;
    while (current.getPrevious() != null) {
      current = current.getPrevious();
      edgeList.add(current.edge);
    }
    return edgeList;
  }
}
