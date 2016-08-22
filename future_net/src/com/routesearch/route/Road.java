package com.routesearch.route;

import com.routesearch.route.Road;

public class Road implements Cloneable {
	public Road( String count,String begin,String end,String weight) {
		this.begin = begin;
		this.end = end;
		index = count;
		w = weight;
	}
	private String begin;
	private String end;
	private String index;
	private String w;
	
	public String toString() {
		return begin + "->" + end;
	}
	public String getBegin() {
		return begin;
	}
	public String Index() {
		return index;
	}
	public String weigth() {
		return w;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public boolean equals(Object obj) {
		Road r = (Road)obj;
		if(this.getBegin().equals(r.getBegin()) && this.getEnd().equals(r.getEnd())) {
			return true;
		}
		return false;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}