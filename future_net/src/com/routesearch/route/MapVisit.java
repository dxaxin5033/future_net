package com.routesearch.route;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MapVisit {
	
	private String graphContent;
	String t;
	String s;
	Set<String> Vs;
	public MapVisit(String s, String t,String gc,Set<String> Vs) {
		// TODO Auto-generated constructor stub
		graphContent=gc;
		this.s=s;
		this.t=t;
		this.Vs=Vs;
	}
	public List<Road> init() {
		List<Road> list = new ArrayList<Road>();
		String regex = "\n";
		String[] graphContentCach =graphContent.split(regex);
		for(String tmp : graphContentCach){
			String[] edge = tmp.split(",");
			Road Edge = new Road(edge[0],edge[1],edge[2],edge[3]);
			list.add(Edge);
		}
		return list;
	}
	
	
	public boolean existBegin(String begin, List<Road> roads) {
		if (begin.equals(t)) {
			return true;
		}
		boolean result = false;
		for (Road r : roads) {
			if (r.getBegin().equals(begin)) {
				result = true;
				break;
			}
		}
		return result;
	}

	
	public boolean existEnd(String end, List<Road> roads) {
		if (end.equals(s)) {
			return true;
		}
		boolean result = false;
		for (Road r : roads) {
			if (r.getEnd().equals(end)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public Set<String> getAllNodes(List<Road> roads) {
		Set<String> nodes = new HashSet<String>();
		for(Road r : roads) {
			nodes.add(r.getBegin());
			nodes.add(r.getEnd());
		}
		return nodes;
	}

	
	public Set<Road> deleteRoad(Set<String> begins, Set<String> ends,
			List<Road> roads) {
		Set<Road> set = new HashSet<Road>();
		for (String str : begins) {
			for (Road r : roads) {
				if (r.getBegin().equals(str)) {
					set.add(r);
				}
				if (r.getEnd().equals(str)) {
					set.add(r);
				}
			}
		}
		return set;
	}

	
	public Set<String> filterInvalidNode(Set<String> all, List<Road> roads,
			Set<String> begins, Set<String> ends) {
		Set<String> result = new HashSet<String>();
		boolean isBegin = true;
		boolean isEnd = true;
		for (String str : all) {
			if (!existEnd(str, roads)) { 
				isBegin = false;
				begins.add(str);
				
			} else if (!existBegin(str, roads)) {
				isEnd = false;
				ends.add(str);
				// this.deleteEnd(str, roads);
			} else {
				result.add(str); 
			}
		}
		if (isBegin == true && isEnd == true) {
			return result;
		} else {
			return filterInvalidNode(result, roads, begins, ends);
		}
	}

	
	public String search() {
		List<Road> roads = init(); 
		Set<String> invalidBegins = new HashSet<String>(); 
		Set<String> invalidEnds = new HashSet<String>(); 
		Set<String> allNodes = getAllNodes(roads);
		filterInvalidNode(allNodes, roads, invalidBegins, invalidEnds); 
		Set<Road> invalidRoads = deleteRoad(invalidBegins, invalidEnds,
				roads); 
		roads.removeAll(invalidRoads); 
		Practise pra = new Practise(roads,Vs);
		String begin = s; 
		String end = t; 
		
		Road r0 = new Road("1000", "10001", "10002", "10003");
		pra.getAllRoad(begin,r0, end);
        LinkedList<Road> result = pra.getRoad();
		if(result ==null)
			return "NA";
		else{
			StringBuilder route = new StringBuilder();
			int dist=0;
			for(Road v :result){
					route.append(v.Index()+"|");
					dist += Integer.parseInt(v.weigth());
			}

			System.out.println("BestDist: "+dist);
            String ret = route.toString();
			return (String) ret.subSequence(0, ret.length()-1);
			
		}
		
		
		
		
	}

}

