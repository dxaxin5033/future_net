//DirectedEdge.java
/**
 * 杈圭殑鐨勫疄鐜�
 * 
 * @author zhangxin
 * @since 2016-3-9
 * @version V1.0
 */
package com.routesearch.route;

import java.util.concurrent.CountDownLatch;

public class DirectedEdge{
	private final int count;
	private final int v;
	private final int w;
	private final int weight;
	private boolean death = false;
	
//	public DirectedEdge(){
//		v=Integer.MAX_VALUE;
//		w=Integer.MAX_VALUE-1;
//		weight= Integer.MAX_VALUE;
//	}
	public DirectedEdge(int count, int v,int w,int weight){
		this.count =count;
		this.v = v;
		this.w= w;
		this.weight = weight;
	}
	public void setDeath(){ death =true;}
	public void setAlive(){ death =false;}
	public boolean isDeath(){ return death;}
	public int weight(){ return weight;}
	public int from() { return v;}
	public int to() { return w;}
	public int index() { return count;}
	public String toString(){ return v+"|"+w+"|";}
	
}