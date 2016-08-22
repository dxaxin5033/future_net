/**
 * 实现代码文件
 * 
 * @author XXX
 * @since 2016-3-4
 * @version V1.0
 */
package com.routesearch.route;
import java.util.HashSet;
import java.util.Set;

import com.routesearch.route.EdgeWeightedDigraph;
import com.routesearch.route.ExtensiveRoute;

public final class Route
{
    /**
     * 你需要完成功能的入口
     * 
     * @author zhangxin
     * @since 2016-3-12
     * @version V1.0
     */
    
    public static String searchRoute(String graphContent, String condition)
    {
    	String[] conditionStrings = condition.split(",");
    	if(conditionStrings.length>3){
    		System.out.println(" condition.csv not correct !!");
    		return null;
    	}
    	int s = Integer.parseInt(conditionStrings[0]);
    	int t = Integer.parseInt(conditionStrings[1]);
    	Set<Integer> Vs = new HashSet<Integer>();
    	Set<String> vsStrings =new HashSet<String>();
    	for(String vs : ((String) conditionStrings[2].subSequence(0, (conditionStrings[2].length())-1)).split("\\|"))
    		vsStrings.add(vs);
    	for(String vs : ((String) conditionStrings[2].subSequence(0, (conditionStrings[2].length())-1)).split("\\|"))
    		Vs.add(Integer.parseInt(vs));
    	System.out.println(Vs);
    	String[] edges =graphContent.split("\n");
    	String tString;
    	if(edges.length>10){
	        EdgeWeightedDigraph gh = new EdgeWeightedDigraph(graphContent);
	        StretchLineSearch stretchRoute= new  StretchLineSearch(gh, s, t, Vs);
	        stretchRoute.initial();
	        tString =stretchRoute.searchRoute();
	        System.out.print(tString);
    	}else{
    		MapVisit mv = new MapVisit(conditionStrings[0],conditionStrings[1],graphContent,vsStrings);
    		tString =mv.search(); 
    		System.out.println(vsStrings);
    		System.out.print(tString);
    	}
        return  tString;
    }
  

}