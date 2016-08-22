//EdgeWeightedDigraph.java
/**
 * 实现代码文件
 * 
 * @author zhangxin
 * @since 2016-3-9
 * @version V1.0
 */
package com.routesearch.route;
import java.util.*;

import com.routesearch.route.DirectedEdge;


public class EdgeWeightedDigraph{
	private final int V=600;
	private int E=0;
	private LinkedList<List<DirectedEdge>> adj ;
	public EdgeWeightedDigraph(){}
	public EdgeWeightedDigraph(EdgeWeightedDigraph gh){
		E=gh.E;
		adj =gh.adj;
		
		
	}
	public EdgeWeightedDigraph(String graphContent){
		String regex = "\n";
		String[] graphContentCach =graphContent.split(regex);
		adj = new LinkedList<List<DirectedEdge>>();
		for(int v =0;v<V;v++)
			adj.add( new LinkedList<DirectedEdge>());
		for(String tmp : graphContentCach){
			String[] edge = tmp.split(",");
			DirectedEdge Edge = new DirectedEdge(Integer.parseInt(edge[0]),Integer.parseInt(edge[1]),Integer.parseInt(edge[2]),Integer.parseInt(edge[3]));
			addEdge(Edge);
			
		}
		
	}
	
	public int E() { return E;}
	public void addEdge( DirectedEdge e){
		adj.get(e.from()).add(e);
		E++;
	}
	public Iterable<DirectedEdge> adj(int v){
		return adj.get(v);
	}
	public void delNode(int t){
		for(DirectedEdge edge : adj.get(t))
			edge.setDeath();
//		for(List<DirectedEdge> edges:adj){
//			for(DirectedEdge e:edges)
//				if(e.to()==t){
//					edges.remove(e);
//					break;
//				}
//		}
	}
	public void recoverNode(int t){
		for(DirectedEdge edge : adj.get(t))
			edge.setAlive();
//		for(List<DirectedEdge> edges:adj){
//			for(DirectedEdge e:edges)
//				if(e.to()==t){
//					edges.remove(e);
//					break;
//				}
//		}
	}
	public void deledgeNode(DirectedEdge e){
		delNode(e.from());
	}
	public List<DirectedEdge> edges(){
		List<DirectedEdge> edges = new ArrayList<DirectedEdge>();
		for(int v =0; v<600; v++)
			for(DirectedEdge e : adj.get(v))
				edges.add(e);
		return edges;
	}
	public void putEdgeDeath(LinkedList<DirectedEdge> e){
		HashSet<DirectedEdge> setEdges =new HashSet<DirectedEdge>(e);
		for(int v =0; v<600; v++)
			for(DirectedEdge edge : adj.get(v)){
				if(setEdges.contains(edge)){
					edge.setDeath();
					return;
				}
			}
	}
	public void putEdgeAlive(LinkedList<DirectedEdge> e){
		HashSet<DirectedEdge> setEdges =new HashSet<DirectedEdge>(e);
		for(int v =0; v<600; v++)
			for(DirectedEdge edge : adj.get(v)){
				if(setEdges.contains(edge)){
					edge.setAlive();
					return;
				}
			}
	}

}