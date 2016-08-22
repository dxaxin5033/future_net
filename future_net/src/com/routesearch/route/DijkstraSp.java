package com.routesearch.route;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import com.routesearch.route.DirectedEdge;
import com.routesearch.route.IndexMinPQ;
import com.routesearch.route.EdgeWeightedDigraph;


public class DijkstraSp {
	private final int V = 600;
	private DirectedEdge[] edgeTo;
	private long[] distTo;
	private IndexMinPQ<Long> pq;
	private int w;
	private Stack<Integer> path_De = new Stack<Integer>();
	private Stack<Integer> path = new Stack<Integer>();
	private Stack<DirectedEdge> indexPath = new Stack<DirectedEdge>();
	private DirectedEdge e;
	private HashSet<Integer> setpath = new HashSet<Integer>();
	Stack<DirectedEdge> tmpMyStack;
	
	public DijkstraSp() {
		// TODO Auto-generated constructor stub
	}
	public DijkstraSp( EdgeWeightedDigraph G, int s,LinkedList<DirectedEdge> path){
		edgeTo = new DirectedEdge[V];
		distTo = new long[V];
		pq = new IndexMinPQ<Long>(V);
		if(path != null)
			for(DirectedEdge e:path)
				setpath.add(e.from());
		for(int v = 0; v<V; v++){
			distTo[v] = Long.MAX_VALUE;
		}
		distTo[s] = 0;
		
		pq.insert(s, (long)0);
		while(!pq.isEmpty())
			relax(G, pq.delMin());
	}
	public DijkstraSp( EdgeWeightedDigraph G, int s){
		edgeTo = new DirectedEdge[V];
		distTo = new long[V];
		pq = new IndexMinPQ<Long>(V);
		
		for(int v = 0; v<V; v++){
			distTo[v] = Long.MAX_VALUE;
		}
		distTo[s] = 0;
		
		pq.insert(s, (long)0);
		while(!pq.isEmpty())
			relax(G, pq.delMin());
	}
	private void relax( EdgeWeightedDigraph G, int v){
		for(DirectedEdge e : G.adj(v)){
			if(!e.isDeath()){
				w = e.to(); 
				if(!setpath.contains(w)){
					
					if(distTo[w] >distTo[v]+e.weight()) {
						distTo[w] = distTo[v] +e.weight();
						edgeTo[w] =e;
						if(pq.contains(w)) pq.change(w, distTo[w]);
						else   pq.insert(w, distTo[w]);
					}
				}
			}
		}
	}
	public long distTo( int v){ return distTo[v];}
	public boolean hasPathTo(int v){
		return distTo[v] < Long.MAX_VALUE;
	}
	public Stack<Integer> pathTO(int v){
		if(!hasPathTo(v) )    return null;
		for( e = edgeTo[v]; e != null; e=edgeTo[e.from()])
			path_De.push(e.to());
		return path_De;
	}
	public Stack<Integer> pathNodesTO(int v){
		if(!hasPathTo(v) )    return null;
		path.clear();
		for( e = edgeTo[v]; e != null ; e=edgeTo[e.from()])
			path.push(e.from());
		return path;
	}
	public Stack<DirectedEdge> pathEdgesTO(int v){
		if(!hasPathTo(v) )    return null;
		for( e = edgeTo[v]; e != null; e=edgeTo[e.from()])
			indexPath.push(e);
		return indexPath ;
	}
	public LinkedList<DirectedEdge> pathEdges(int v){
		if(!hasPathTo(v) )    return null;
		tmpMyStack = pathEdgesTO(v);
		LinkedList<DirectedEdge> resultPath = new LinkedList<DirectedEdge>();
		while(tmpMyStack!=null&&!tmpMyStack.isEmpty())
			resultPath.add(tmpMyStack.pop());
		return resultPath;
	}
	
	
}
