package com.routesearch.route;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import org.omg.CORBA.PUBLIC_MEMBER;

class Pair{
	public final LinkedList<Integer> K;
	public final LinkedList<DirectedEdge> V;
	public Pair(LinkedList<Integer> K ,LinkedList<DirectedEdge> V) {
		// TODO Auto-generated constructor stub
		this.K=K;
		this.V=V;
	}
}
public class StretchLineSearch {
	private final int N=600;


	

	private EdgeWeightedDigraph gh;

	private int s;
	private int t;
	private Set<Integer> Vs;
	private Set<Integer> VsRemian = new HashSet<Integer>();
	private LinkedList<Integer > VisitedVs = new LinkedList<Integer>();  // visited nodes of nodes that must pass 
	private LinkedList<DirectedEdge> resultPath = new LinkedList<DirectedEdge>();
	
	private LinkedList<DirectedEdge> midPath_2 = new LinkedList<DirectedEdge>();
	HashMap<Long, LinkedList<DirectedEdge>> midMap =new HashMap<Long, LinkedList<DirectedEdge>>();
	HashMap<Long, Pair> map =new HashMap<Long, Pair>();
	LinkedList<Long> listMidK = new LinkedList<Long>();
	LinkedList<Long> listK = new LinkedList<Long>();

	private Stack<Integer> nodePath = new Stack<Integer>();
	private DijkstraSp dijkstra = new DijkstraSp();
	private DijkstraSp dijkstra_2 = new DijkstraSp();
	private  Comparator<LinkedList<DirectedEdge>> comparator_1;
	private  Comparator<Pair> comparator_2;
	private PriorityQueue<Pair> oldMidPathVs;
	private PriorityQueue<Pair> oldMidPathVs_2;
	private PriorityQueue<Pair> oldMidPathVs_3;
	private PriorityQueue<Pair> oldMidPathVs_4;
	private PriorityQueue<Pair> newMidPathVs;
	
//	EdgeWeightedDigraph VsDigraph = new EdgeWeightedDigraph(graphContent);
//	VsDigraph.delNode(t);
	
	public StretchLineSearch(EdgeWeightedDigraph gh,int s, int t,Set<Integer> Vs) {
		this.gh =gh;
		

		this.s =s;
		this.t = t;
		this.Vs = Vs;
		comparator_1= new Comparator<LinkedList<DirectedEdge>>(){
			public int compare(LinkedList<DirectedEdge> o1, LinkedList<DirectedEdge> o2) {  
	            long distA=0;
	            long distB=0;
	            int midPathVsSizeA =0 ;  
	            int midPathVsSizeB =0; 
	    		if(o1==null) {
	    			midPathVsSizeA = 0;
	    			distA=Long.MAX_VALUE;
	    		}else{
		    		for(DirectedEdge v :o1){
		    			distA+=v.weight();
		    			if(VsRemian.contains(v.from())){
							midPathVsSizeA++;

						}
		    		}
	    		}
	    		if(o2==null) {
	    			midPathVsSizeB = 0;
	    			distB=Long.MAX_VALUE;
	    		}else{
		    		for(DirectedEdge v :o2){
		    			distB+=v.weight();
		    			if(VsRemian.contains(v.from())){
							midPathVsSizeB++;

						}
		    		}
	    		}
	            if(midPathVsSizeA < midPathVsSizeB)  {  
	                return 1;  
	            }else if(midPathVsSizeA > midPathVsSizeB){  
	                return -1;  
	            }  
	            else  
	            {  
	            	
	                if(distA > distB){
	                	return 1;
	                }else if(distA < distB){
	                	return -1;
	                }else {
						return 0;
					}
	            }  
	          
	        }  
		};
		comparator_2= new Comparator<Pair>(){
			public int compare(Pair pairA,Pair pairB) {  
				LinkedList<DirectedEdge> o1=pairA.V;
				LinkedList<DirectedEdge> o2=pairB.V;
	            long distA=0;
	            long distB=0;
	            int midPathVsSizeA =0;  
	            int midPathVsSizeB =0; 
	    		if(o1==null) {
	    			midPathVsSizeA = 0;
	    			distA=Long.MAX_VALUE;
	    		}else{
		    		for(DirectedEdge v :o1){
		    			distA+=v.weight();
		    			if(VsRemian.contains(v.from())){
							midPathVsSizeA++;

						}
		    		}
	    		}
	    		if(o2==null) {
	    			midPathVsSizeB = 0;
	    			distB=Long.MAX_VALUE;
	    		}else{
		    		for(DirectedEdge v :o1){
		    			distB+=v.weight();
		    			if(VsRemian.contains(v.from())){
							midPathVsSizeB++;

						}
		    		}
	    		}
	            if(midPathVsSizeA < midPathVsSizeB)  {  
	                return 1;  
	            }else if(midPathVsSizeA > midPathVsSizeB){  
	                return -1;  
	            }  
	            else  
	            {  
	            	
	                if(distA > distB){
	                	return 1;
	                }else if(distA < distB){
	                	return -1;
	                }else {
						return 0;
					}
	            }  
	          
	        }  
		};
		oldMidPathVs =  new PriorityQueue<Pair>(10000,comparator_2);
		oldMidPathVs_2 =  new PriorityQueue<Pair>(10000,comparator_2);
		oldMidPathVs_3 =  new PriorityQueue<Pair>(10000,comparator_2);
		oldMidPathVs_4=  new PriorityQueue<Pair>(10000,comparator_2);
		newMidPathVs =  new PriorityQueue<Pair>(10000,comparator_2);

		
		
//		for(int i :Vs){
//			EdgeWeightedDigraph vsDigraph = new EdgeWeightedDigraph();
//		    vsDigraph = gh_remove_t;
//			dijsk[i] =new DijkstraSp(vsDigraph, i);
//		}
//		

	}
	
	public Set<Integer> getAllNodes(List<DirectedEdge> roads) {
		Set<Integer> nodes = new HashSet<Integer>();
		if(roads != null){
			for(DirectedEdge r : roads) {
				nodes.add(r.from());
				nodes.add(r.to());
			}
		}
		return nodes;
	}
	public void initial(){
		dijkstra = new DijkstraSp(gh, s);
		VisitedVs.add(s);
		VisitedVs .addAll(midNodes(dijkstra, t));
		VisitedVs.add(t);
		Stack<DirectedEdge> tmpMyStack = dijkstra.pathEdgesTO(t);
		while(tmpMyStack!=null&&!tmpMyStack.isEmpty())
			resultPath.add(tmpMyStack.pop());
		VsRemian.addAll(Vs);
		VsRemian.add(s);
		VsRemian.add(t);
		VsRemian.removeAll(VisitedVs);
		Set<Integer> nodes = getAllNodes(resultPath);
		for(Integer node :nodes)
			gh.delNode(node);
	}
	boolean isSeeked =true;
	LinkedList<DirectedEdge> recoverPath = new LinkedList<DirectedEdge>();
	public void addVsNode(){
		System.out.println(VisitedVs);
		System.out.println(VsRemian);
		Pair pair = seekBestNode();
		if (pair!=null) {
			Set<Integer> nodes = getAllNodes(pair.V);
			for(Integer node :nodes)
				gh.delNode(node);
			LinkedList<DirectedEdge> tmpResultPath = new LinkedList<DirectedEdge>();
			for(int i=0;i<IndexOfNode(resultPath, pair.K.get(0))+1;i++)
				tmpResultPath.add(resultPath.get(i));
			//recover nodes
			if(!isSeeked){
				for(int i=IndexOfNode(resultPath, pair.K.get(0))+2;i<IndexOfNode(resultPath, pair.K.get(1));i++)
					recoverPath.add(resultPath.get(i));
				Set<Integer> path = getAllNodes(recoverPath);
				for(Integer node :path){
					gh.recoverNode(node);
					if(Vs.contains(node))
						VsRemian.add(node);
				}
			}
			tmpResultPath.addAll(pair.V);
			for(int i=IndexOfNode(resultPath, pair.K.get(1))+1;i<resultPath.size();i++)
				tmpResultPath.add(resultPath.get(i));
			resultPath=tmpResultPath;
			//update VisitedVs
			LinkedList<Integer> tmpVisitedVs = new LinkedList<Integer>();
			for(int i=0;i<VisitedVs.indexOf(pair.K.get(0))+1;i++)
				tmpVisitedVs.add(VisitedVs.get(i));
			for(DirectedEdge e : pair.V){
				if(VsRemian.contains(e.from())){
					tmpVisitedVs.add(e.from());
					VsRemian.remove(e.from());

				}
			}
			for(int i=VisitedVs.indexOf(pair.K.get(1));i<VisitedVs.size();i++)
				tmpVisitedVs.add(VisitedVs.get(i));
			VisitedVs=tmpVisitedVs;
//			VsRemian.addAll(Vs);
//			VsRemian.add(s);
//			VsRemian.add(t);

		
//			
		}
	}
	public int IndexOfNode(LinkedList<DirectedEdge> edges, int node){
		for(int i=0;i<edges.size();i++){
			if(edges.get(i).to()==node)
				return i;
		}
		return -1;
	}
	private long sumEdgesDist(LinkedList<DirectedEdge> edges) {
		long dist=0;
		if(edges ==null) return Long.MAX_VALUE;
		for(DirectedEdge v :edges){
			dist+=v.weight();
		}
		return dist;
		
	}
	public Pair seekBestNode() {
		
		if (Vs.isEmpty()) {
			return null;
			
		}else{
//			map.clear();
//			listK.clear();
			newMidPathVs.clear();
			for(int i=0;i<VisitedVs.size()-1;i++){
//				listMidK.clear();
//				midMap.clear();
//				oldMidPathVs.clear();
				for(int k=IndexOfNode(resultPath, VisitedVs.get(i))+1;k<IndexOfNode(resultPath, VisitedVs.get(i+1))+1;k++)
					
					gh.recoverNode(resultPath.get(k).from());
				dijkstra = new DijkstraSp(gh, VisitedVs.get(i));
				for(Integer vs :VsRemian){
					if(dijkstra.hasPathTo(vs)){
//						long midDist = dijkstra.distTo(vs);
						LinkedList<DirectedEdge> midPath = new LinkedList<DirectedEdge>();
						midPath = dijkstra.pathEdges(vs);
						dijkstra_2 = new DijkstraSp( gh,vs,midPath);
//						midDist += dijkstra_2.distTo(VisitedVs.get(i+1));
						Stack<DirectedEdge> tmpMyStack = dijkstra_2.pathEdgesTO(VisitedVs.get(i+1));
						while(tmpMyStack!=null&&!tmpMyStack.isEmpty())
							midPath.add(tmpMyStack.pop());
						if(dijkstra_2.hasPathTo(VisitedVs.get(i+1))){
							LinkedList<Integer> K = new LinkedList<Integer>();
							K.add(VisitedVs.get(i));
							K.add(VisitedVs.get(i+1));
							newMidPathVs.offer( new Pair(K, midPath));
							System.out.println(midPath);
						}
//						if(midDist>0){
//							midMap.put(midDist, midPath);
//							System.out.println(midMap.toString());
//							listMidK.add(midDist);
//						}
					}
				}
				for(int k=IndexOfNode(resultPath, VisitedVs.get(i))+1;k<IndexOfNode(resultPath, VisitedVs.get(i+1))+1;k++)
					gh.delNode(resultPath.get(k).from());
//				System.out.println(listMidK);
//				if(!oldMidPathVs.isEmpty()){
//					long dist = Collections.min(listMidK);
//					LinkedList<DirectedEdge> midPath_2 = new LinkedList<DirectedEdge>(oldMidPathVs.poll());
//					newMidPathVs.offer( new Pair(i, midPath_2));
//					listK.add(dist);
	//				System.out.println(map.toString());
//				}
			}
			if(!newMidPathVs.isEmpty()){
//				System.out.println(listK);
//				long tmpDist = Collections.min(listK);
				isSeeked =true;
				Pair pair = newMidPathVs.poll();
				
//				oldMidPathVs_4=oldMidPathVs_3;
//				oldMidPathVs_3=oldMidPathVs_2;
//				oldMidPathVs_2=oldMidPathVs;
				oldMidPathVs_4.clear();
				while(! oldMidPathVs_3.isEmpty())
					oldMidPathVs_4.offer(oldMidPathVs_3.poll());
				while(! oldMidPathVs_2.isEmpty())
					oldMidPathVs_3.offer(oldMidPathVs_2.poll());
				while(! oldMidPathVs.isEmpty())
					oldMidPathVs_2.offer(oldMidPathVs.poll());
				while(! newMidPathVs.isEmpty())
					oldMidPathVs.offer(newMidPathVs.poll());
				System.out.println("listK");
				System.out.println(oldMidPathVs.isEmpty());
				return pair;
				}
			else if((!oldMidPathVs.isEmpty())){
				isSeeked =false;
				System.out.println("listK");
				return oldMidPathVs.poll();
			}else if((!oldMidPathVs_2.isEmpty())){
				isSeeked =false;
				return oldMidPathVs_2.poll();
			}else if((!oldMidPathVs_3.isEmpty())){
				isSeeked =false;
				return oldMidPathVs_3.poll();
			}else if((!oldMidPathVs_4.isEmpty())){
				isSeeked =false;
				
				return oldMidPathVs_4.poll();
				
			}
			
			return null;
		}
		
		
	}
	private LinkedList<Integer> midNodes(DijkstraSp dij, int v2){

		nodePath = dij.pathTO(v2);
		LinkedList<Integer> pathTmpList_2= new LinkedList<Integer>();
		if(nodePath == null) return null;
		while( !nodePath.isEmpty()){
			int tmp = nodePath.pop();
			if(Vs.contains(tmp))
				pathTmpList_2.add(tmp);
			}
//		System.out.println(pathTmpList);
		return pathTmpList_2;
		
	}
	
	
	public String searchRoute() {
//		System.out.println(oldMidPathVs.toString());
		for(int i=0;i<Vs.size()+20;i++){
			System.out.println("cycle: "+i);
			if(!VsRemian.isEmpty())
				addVsNode();
			else{
				StringBuilder route = new StringBuilder();
				long dist=0;
				for(DirectedEdge v :resultPath){
						route.append(v.index()+"|");
						dist+=v.weight();
				}

				System.out.println("BestDist: "+dist);
	            String ret = route.toString();
				return (String) ret.subSequence(0, ret.length()-1);
				
			}
			
			
		}
		return "NA";
		
//		
	}

}
