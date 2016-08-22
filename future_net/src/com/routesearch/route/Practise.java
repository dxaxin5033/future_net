package com.routesearch.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;



public class Practise {
	Comparator<LinkedList<Road>> comparator;
	Set<String> Vs;
	public Practise(List<Road> roads, Set<String> Vs) {
		this.roadList = roads;
		this.Vs = Vs;
		 comparator=new Comparator<LinkedList<Road>>(){
			public int compare(LinkedList<Road> o1, LinkedList<Road> o2) {  
			      
	            int A =0;  
	            int B= 0;
	            for(Road road :o1)
	            	A +=Integer.parseInt(road.weigth());
	            for(Road road :o2)
	            	B +=Integer.parseInt(road.weigth());
	            if(A > B)  {  
	                return 1;  
	            }else if(A < B){  
	                return -1;  
	            }  
	            else  
	            {  
	            	
	               return 0;
	            }  
	          
	        }  
		};
		
	}

	public List<Road> getroadList() {
		return roadList;
	}

	public void setroadList(List<Road> roadList) {
		this.roadList = roadList;
	}

	public List<String> getbackList() {
		return backList;
	}

	public void setbackList(List<String> backList) {
		this.backList = backList;
	}

	public LinkedList<Road> getRoad() {
		if(! map.isEmpty())
			return map.get(Collections.min(listK));
		return null;
	}

	public void setresultSet(Set<String> resultSet) {
		this.resultSet = resultSet;
	}
	public Set<String> getAllNodes(List<Road> roads) {
		Set<String> nodes = new HashSet<String>();
		for(Road r : roads) {
			if(r!=null){
				nodes.add(r.getBegin());
				nodes.add(r.getEnd());
			}
		}
		return nodes;
	}
	List<Road> roadList = null; 
	LinkedList<Road> resultRoad = new LinkedList<Road>(); 
	List<String> backList = new ArrayList<String>();
	Set<String> resultSet = new HashSet<String>(); 
	Set<Road> cirList = new HashSet<Road>(); 	
	PriorityQueue<LinkedList<Road>> re= new PriorityQueue<LinkedList<Road>>(10000,comparator);
	HashMap<Integer, LinkedList<Road>> map =new HashMap<Integer, LinkedList<Road>>();
	LinkedList<Integer> listK = new LinkedList<Integer>();
	
	Road tmp ;
	public void getAllRoad(String start, Road r,String destination) {
		backList.add(start);
		resultRoad.add(r);
		for (int z = 0; z < roadList.size(); z++) {
			if (roadList.get(z).getBegin().equals(start)) {
				if (roadList.get(z).getEnd().equals(destination)) { 
					
					
					if(getAllNodes(resultRoad).containsAll(Vs)){
						resultRoad.add(roadList.get(z));
						int A= 0;
						LinkedList<Road> path =new LinkedList<Road>();
				        for(int i=1;i<resultRoad.size();i++){
				        	    Road road =resultRoad.get(i);
					            A +=Integer.parseInt(road.weigth());
					            path.add((Road) road.clone());
				        	
				        }
				        
						map.put(A,path);
						listK.add(A);
						resultRoad.remove(roadList.get(z));
						
					}
					
					continue;
				}
				if (!backList.contains(roadList.get(z).getEnd())) {
//					resultRoad.add(roadList.get(z));
//					tmp =roadList.get(z);
					getAllRoad(roadList.get(z).getEnd(), roadList.get(z),destination);
			


				} else {
					cirList.add(roadList.get(z));
					//System.out.println("wwww");
				}
			}
		}
		
		resultRoad.remove(r);
		backList.remove(start);

		
	}

	public Set<Road> getCirList() {
		return cirList;
	}

	public void setCirList(Set<Road> cirList) {
		this.cirList = cirList;
	}
}