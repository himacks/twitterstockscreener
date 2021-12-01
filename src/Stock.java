import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Stock implements Comparable<Stock>
{
	public String ticker;
	public int mentions;
	public ArrayList<String> triggers;
    public String date;
    public String open;
    public String high;
    public String low;
    public String close;
    public String adjusted_close;
    public String volume;
    
    public Stock()
    {
    	triggers = new ArrayList<String>();
    	mentions = 1;
    	ticker = null;
    }
    
    public Stock(String ticker)
    {
    	triggers = new ArrayList<String>();
    	mentions = 1;
    	this.ticker = ticker;
    }
	
	public void setTicker(String t)
	{
		ticker = t;
	}
	
	public void setMentions(int i)
	{
		mentions = i;
	}
	
	public void addTrigger(String level)
	{
		triggers.add(level);
	}
	
	public ArrayList<String> getTriggers()
	{
		return triggers;
	}
	
	
	public String getTicker()
	{
		return ticker;
	}
	
	public int getMentions()
	{
		return mentions;
	}
	
	public boolean equals(Stock otherStock)
	{
		if (getTicker().equals(otherStock.getTicker()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean equals(String otherTicker)
	{
		if (getTicker().equals(otherTicker))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public void addMention()
	{
		mentions += 1;
	}
	
	public int compareTo(Stock s) {
		if (this.getMentions() > s.getMentions())
		{
			return 1;
		}
		else if (this.getMentions() == s.getMentions())
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
	
    public void pullStockData() throws IOException
    {
    	String ticker = "MCD";
    	String inputURL = "https://eodhistoricaldata.com/api/eod/" + ticker + ".US?from=2017-01-05&to=2017-02-10&period=d&fmt=json&api_token=615c9087ef6899.90148702&fmt=json";
    	String textDataFromURL = stringDataFromURL(inputURL);
    	Gson gson = new Gson();
    	
    	
        //Type type = new TypeToken<Stock>(){}.getType();
        
        
        //this = gson.fromJson(textDataFromURL, Stock.class);
    }
    
    public static String stringDataFromURL(String inputURL) throws IOException
    {
         URL url = new URL(inputURL);
         InputStreamReader reader = new InputStreamReader(url.openStream());
         StringBuilder sb = new StringBuilder();
         BufferedReader br = null;
         String content;
    	 
         
         br = new BufferedReader(reader);
         while ((content = br.readLine()) != null) {
             	sb.append(content);
         }

         reader.close();
         br.close();
         return sb.toString();
    }
    
    
	
}

