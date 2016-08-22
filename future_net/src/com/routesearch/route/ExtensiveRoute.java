package com.routesearch.route;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import com.routesearch.route.EdgeWeightedDigraph;
import com.routesearch.route.DijkstraSp;

public class ExtensiveRoute {
	private final int N=600;
	private  Comparator<LinkedList<Integer>> comparator;
	private PriorityQueue<LinkedList<Integer>> oldMidPathVs;
	private PriorityQueue<LinkedList<Integer>> newMidPathVs;
	private Map<LinkedList<Integer>, LinkedList<DirectedEdge>> midPath= new LinkedHashMap<LinkedList<Integer>, LinkedList<DirectedEdge>>();
	private Map<LinkedList<Integer>, Long> midDist= new LinkedHashMap<LinkedList<Integer>, Long>();
	private EdgeWeightedDigraph gh;
	private EdgeWeightedDigraph gh_remove_t ;
	private DijkstraSp dijsk;
	private String graphContent;
	private int s;
	private int t;
	private Set<Integer> Vs;
	private Set<LinkedList<Integer>> result = new HashSet<LinkedList<Integer>>();
	private LinkedList<LinkedList<Integer>> result_2 = new LinkedList<LinkedList<Integer>>();
	private Stack<Integer> tmpStack = new Stack<Integer>();
	private Stack<Integer> nodePath = new Stack<Integer>();
	private LinkedList<Integer> tmp_List = new LinkedList<Integer>();
	private LinkedList<Integer> tmp_List_2 = new LinkedList<Integer>();
	private LinkedList<Integer> tmp_List_3 = new LinkedList<Integer>();
	private LinkedList<Integer> pathTmpList_3= new LinkedList<Integer>();
	private LinkedList<Integer> pathNodes =new LinkedList<Integer>();
	private DijkstraSp dijkstra = new DijkstraSp();
	private DijkstraSp dijkstra_2 = new DijkstraSp();
	
//	EdgeWeightedDigraph VsDigraph = new EdgeWeightedDigraph(graphContent);
//	VsDigraph.delNode(t);
	public ExtensiveRoute(EdgeWeightedDigraph gh,int s, int t,Set<Integer> Vs,String graphContent) {
		comparator= new Comparator<LinkedList<Integer>>(){
			public int compare(LinkedList<Integer> o1, LinkedList<Integer> o2) {  
	      
	            int midPathVsSizeA = o1.size();  
	            int midPathVsSizeB= o2.size();  
	            if(midPathVsSizeA < midPathVsSizeB)  {  
	                return 1;  
	            }else if(midPathVsSizeA > midPathVsSizeB){  
	                return -1;  
	            }  
	            else  
	            {  
	            	
	                if(midDist.get(o1) > midDist.get(o2)){
	                	return 1;
	                }else if(midDist.get(o1) < midDist.get(o2)){
	                	return -1;
	                }else {
						return 0;
					}
	            }  
	          
	        }  
		};
		oldMidPathVs =  new PriorityQueue<LinkedList<Integer>>(10000,comparator);
		newMidPathVs =  new PriorityQueue<LinkedList<Integer>>(10000,comparator);
		this.gh =gh;
		
		this.graphContent = graphContent;
		this.s =s;
		this.t = t;
		this.Vs = Vs;
//		dijsk = new DijkstraSp();
		gh_remove_t=new EdgeWeightedDigraph(graphContent);
		gh_remove_t.delNode(t);
        EdgeWeightedDigraph sDigraph = new EdgeWeightedDigraph();
        sDigraph = gh_remove_t;
        System.out.println(gh_remove_t.adj(0));
		dijsk = new DijkstraSp(sDigraph, s);
		
//		for(int i :Vs){
//			EdgeWeightedDigraph vsDigraph = new EdgeWeightedDigraph();
//		    vsDigraph = gh_remove_t;
//			dijsk[i] =new DijkstraSp(vsDigraph, i);
//		}
//		
//			
			


	}
	
	
	public void initial(){
		for(int i : Vs){		
			tmp_List= midNodes(dijsk, i);
//			System.out.println(tmp_List);
			if(tmp_List != null){
				LinkedList<DirectedEdge> pathTmpEdges= new LinkedList<DirectedEdge>();
//				LinkedList<Integer> pathEdges= new LinkedList<Integer>();
//				pathEdges=dijsk[s].pathIndexTO(i);
				Stack<DirectedEdge> pathTmpstack = dijsk.pathEdgesTO(i);
				while(!pathTmpstack.isEmpty()){
					pathTmpEdges.add(pathTmpstack.pop());
				}


				midPath.put(tmp_List, pathTmpEdges);
				midDist.put(tmp_List, dijsk.distTo(i));
				System.out.println(tmp_List);
				oldMidPathVs.add(tmp_List);
			}
			
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
	
	private Set<LinkedList<Integer>> testLoopAndUpdate(LinkedList<Integer> pathVs){
//		System.out.println(pathVs);
//		System.out.println(midPath.get(pathVs));
		LinkedList<DirectedEdge> path =  midPath.get(pathVs);
		
	    
	    
//	    gh_remove_t.putEdgeDeath(path);
		result.clear();
		int vStart = pathVs.getLast();
		Set<Integer> VsRemain = new HashSet<Integer>();
		VsRemain.addAll(Vs);
		VsRemain.removeAll(pathVs);
		
//		pathNodes.clear();
//		for(DirectedEdge e: path) 
//			pathNodes.add(e.from());

		long distLong = new Long(midDist.get(pathVs));

		if(VsRemain.size() !=0){
			dijkstra = new DijkstraSp(gh_remove_t,vStart,path);
			for( Integer vs:VsRemain){
				pathTmpList_3.clear();
//				tmpStack = dijkstra.pathNodesTO(vs);
//				if(tmpStack != null ){
//					while(!tmpStack.isEmpty()){
//						pathTmpList_3.add(tmpStack.pop());
//					}
//					pathTmpList_3.add(vs);
//					if(Collections.disjoint(pathNodes,pathTmpList_3)){
				Stack<DirectedEdge> tmpMyStack = dijkstra.pathEdgesTO(vs);
				if(tmpMyStack != null){
						LinkedList<Integer> listKey =new LinkedList<Integer>(pathVs);
						for(Integer j:midNodes(dijkstra, vs))
							listKey.add(j);
                        if(!midPath.containsKey(listKey)){
						LinkedList<DirectedEdge> listValue =new LinkedList<DirectedEdge>(path);
						
						while(tmpMyStack!=null&&!tmpMyStack.isEmpty())
							listValue.add(tmpMyStack.pop());

						midPath.remove(pathVs);
						midPath.put(listKey, listValue);
						midDist.put(listKey,distLong+dijkstra.distTo(vs));
						midDist.remove(pathVs);
						result.add(listKey);
//						System.out.println(listKey);
//						System.out.println(listValue);
                        }
//					}
				}
			}
		}else {
			result.add(pathVs);
		}

		return result;
	}
	private LinkedList<Integer> finalTestLoopAndUpdate(LinkedList<Integer> pathVs){
		result_2.clear();
		int vStart = pathVs.getLast();

		LinkedList<DirectedEdge> path = midPath.get(pathVs);
	    dijkstra_2 = new DijkstraSp(gh,vStart,path);
//		pathNodes.clear();
//		for(DirectedEdge e: path) 
//			pathNodes.add(e.from());
//
//        if (pathVs.size()==Vs.size()) {
//        	LinkedList<Integer> pathTmp=new LinkedList<Integer>();
//        	Stack<Integer> pathTmpstack = dijsk[vStart].pathNodesTO(t);
//        	if (pathTmpstack != null) {
//				while(!pathTmpstack.isEmpty()){
//					pathTmp.add(pathTmpstack.pop());
//				}
//				pathTmp.add(t);
//				if(Collections.disjoint(pathNodes,pathTmp)){
	            Stack<DirectedEdge> tmpMyStack = dijkstra_2.pathEdgesTO(t);
	    		if(tmpMyStack != null){

					LinkedList<Integer> listKey =new LinkedList<Integer>(pathVs);
					listKey.add(t);	
					LinkedList<DirectedEdge> listValue =new LinkedList<DirectedEdge>(path);
					
					while(!tmpMyStack.isEmpty())
						listValue.add(tmpMyStack.pop());

					midPath.put(listKey, listValue);
					midDist.put(listKey, midDist.get(pathVs)+dijkstra_2.distTo(t));
					result.add(listKey);
//					System.out.println("path:"+midPath.get(listKey));
//					System.out.println("dist:"+midDist.get(listKey));

					return listKey;
				}else {
					return null;
				}
//        	}
//        }

//		return null;
	}
	public String searchRoute() {
//		System.out.println(oldMidPathVs.toString());
		int count =Vs.size();
		int factor;
		if(!(Vs.size()>10))
			factor=Vs.size();
		else {
			factor=10;
		}
		for(int i=1;i<Vs.size();i++){
			
			System.out.println("cycle: "+i);

			while(!oldMidPathVs.isEmpty()){
				for(LinkedList<Integer> list : testLoopAndUpdate(oldMidPathVs.poll())){
//	                if(list.size() ==Vs.size()){
//	                	tmp_List_3=finalTestLoopAndUpdate(list);
//	                	if( tmp_List_3 != null){
//	                		StringBuilder route_2 = new StringBuilder();
//	                		for(DirectedEdge v :midPath.get(tmp_List_3))
//	        					route_2.append(v.index()+"|");
//	                		System.out.println("BestDist: "+midDist.get(tmp_List_3));
//	                		String ret = route_2.toString();
//	            			return (String) ret.subSequence(0, ret.length()-1);
//	                		
//	                		
//	                	}
//	                }
					if( !(list.size()<i+1)){
						newMidPathVs.offer(list);
					
					}
				}
				

	
			}
//			System.out.println(newMidPathVs.toString());
			int j=0;
			while(  !newMidPathVs.isEmpty()){
				tmp_List_2 = newMidPathVs.poll();
				if(j<count){
					
//					if(!(tmp_List_2.size()<i+1)){
						oldMidPathVs.offer(tmp_List_2);
						j++;
//					}
				}else {
					midDist.remove(tmp_List_2);
					midPath.remove(tmp_List_2);
					j++;
				}
				
			
			}

			if(!(Vs.size()>10)){
				count =count*factor;
			    factor--;
			}
			else {
				if(i<15) count +=40;
				else count +=40;
			}
//			
			newMidPathVs.clear();
//			System.out.println(oldMidPathVs.toString());
		}
			

		while(!oldMidPathVs.isEmpty()){
			tmp_List_3 = finalTestLoopAndUpdate(oldMidPathVs.poll());
			if(tmp_List_3 != null)
				newMidPathVs.offer(tmp_List_3);
		}
		if(newMidPathVs.isEmpty())
			return "NA";
		else{
			StringBuilder route = new StringBuilder();
			LinkedList<Integer> resultTmp = newMidPathVs.poll();
			for(DirectedEdge v :midPath.get(resultTmp))
					route.append(v.index()+"|");

			System.out.println("BestDist: "+midDist.get(resultTmp));
            String ret = route.toString();
			return (String) ret.subSequence(0, ret.length()-1);
			
		}

//		
	}

		
}
