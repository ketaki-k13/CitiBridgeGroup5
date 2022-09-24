package com.java.arbitrage.yahoo.stock.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.Map.Entry;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Hello world!
 *
 */
public class YahooStockApi 
{
	static HashMap<String, BigDecimal> hs;
    public static void main( String[] args ) throws IOException
    {
    	HashMap<String, BigDecimal> hs=new HashMap<>();
        String[] stock ={"HDFC.BO", "HDFC.NS","SBILIFE.BO", "SBILIFE.NS","TCS.BO","TCS.NS",
        		"TATASTEEL.BO","TATASTEEL.NS","WIPRO.BO","WIPRO.NS","CIPLA.BO","CIPLA.NS","DIVISLAB.BO","DIVISLAB.NS","SUNPHARMA.BO",
        				"SUNPHARMA.NS","ITC.BO","ITC.NS","APOLLOHOSP.BO","APOLLOHOSP.NS","ADANIPORTS.BO","ADANIPORTS.NS"};
        Stock[]name_stock=new Stock[stock.length];
        for(int i=0;i<stock.length;i++) {
        	name_stock[i]=YahooFinance.get(stock[i]);
        }
        BigDecimal priceofeach[]=new BigDecimal[stock.length];
        for(int i=0;i<stock.length;i++) {
        	priceofeach[i]=name_stock[i].getQuote().getPrice();
        	System.out.println(stock[i]+" : "+priceofeach[i]);
        	if(i%2!=0) {
        		BigDecimal diff;
        		BigDecimal multiplier = new BigDecimal("100");
        		diff=priceofeach[i].subtract(priceofeach[i-1]);
        		diff=diff.divide(priceofeach[i], 8, RoundingMode.HALF_UP);
        		diff=diff.multiply(multiplier);
        		diff=diff.setScale(2,RoundingMode.HALF_UP);
        		System.out.println("Diff: "+diff.abs()+"%");
        		System.out.println();
        		String stockname=stock[i];
        		int iend = stockname.indexOf(".");
        		String subString;
        		if (iend != -1) 
        		{
        		    subString= stockname.substring(0 , iend); 
        		    hs.put(subString, diff.abs());
        		}
        	}
        	
        }

    	YahooStockApi obj=new YahooStockApi();
    	//obj.sortByValue(true); 
    	obj.printMap(hs);
    	
    }
    public static void sortByValue( final boolean order)   
    {  
    //convert HashMap into List   
    List<Entry<String, BigDecimal>> list = new LinkedList<Entry<String, BigDecimal>>(hs.entrySet());  
    //sorting the list elements  
    Collections.sort(list, new Comparator<Entry<String, BigDecimal>>()   
    {  
    public int compare(Entry<String, BigDecimal> o1, Entry<String, BigDecimal> o2)   
    {  
    if (order)   
    {  
    //compare two object and return an integer  
    return o1.getValue().compareTo(o2.getValue());}   
    else   
    {  
    return o2.getValue().compareTo(o1.getValue());  
    }  
    }  
    });  
    //prints the sorted HashMap  
    Map<String, BigDecimal> sortedMap = new LinkedHashMap<String, BigDecimal>();  
    for (Entry<String, BigDecimal> entry : list)   
    {  
    sortedMap.put(entry.getKey(), entry.getValue());  
    }  
    printMap(sortedMap);  
    } 
    
    public static void printMap(Map<String, BigDecimal> map)   
    {  
    System.out.println("Recommenedations of stocks and their Arbitrage");
    for (Entry<String, BigDecimal> entry : map.entrySet())   
    {  
    System.out.println(entry.getKey() +"\t"+entry.getValue());  
    }  
    System.out.println("\n");  
    }  
 
}
