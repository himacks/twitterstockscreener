import twitter4j.*;
import twitter4j.conf.*;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StockScreen {

  public static Twitter getTwitterInstance() {

    //Create variables to use as input for Twitter API login

    String CONSUMER_KEY = "6X6y8ndpeKHBdn4t5An8KrqEy";
    String CONSUMER_SECRET = "OQn55CFSKdUyagZvhvjtH0PiI0o3WPegxBAB15PrgDIcMoTvaw";
    String ACCESS_TOKEN = "932029146187776000-AZ0sR66Z6bgg0hz2wtxLY3XatQPa1C4";
    String ACCESS_TOKEN_SECRET = "eYF8fhBvIhLeRDTLTia9rbrTNpdlRtnf7ZYAEMmibGHQd";

    //Create Twitter Scraper Object

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
    .setOAuthConsumerSecret(CONSUMER_SECRET).setOAuthAccessToken(ACCESS_TOKEN)
    .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

    TwitterFactory tf = new TwitterFactory(cb.build());
    return tf.getInstance();
  }

  //Uses the TwitterScraper object to scrap twitter search feed for specific
  //keywords and outputs it as an ArrayList.

  public static ArrayList<String> scrapeTimeline(Twitter twitterIntegration, String searchTerm)
  {
    ArrayList<String> tweets = new ArrayList<String>();

    //Creates a query search using searchTerm
    //For each tweet found, its text body is added to an ArrayList

    try {
      Query query = new Query(searchTerm);
      query.setCount(100);
      QueryResult result = twitterIntegration.search(query);

      for (Status status : result.getTweets()) {
          tweets.add(status.getText());
      }
    }
    catch (TwitterException e) {
        e.printStackTrace();
    }

    return tweets;
  }

  //Using the raw Twitter feed composed of tweets on the timeline mentioning
  //the key search them, it will then parse through each tweet, find each ticker
  //then add it to a master list of all tickers found on the tweet timeline.

  public static ArrayList<Stock> convertTickers(ArrayList<String> timelineInput)
  {
    ArrayList<Stock> stockList = new ArrayList<Stock>();
    ArrayList<String> tickers = new ArrayList<String>();
    String keyChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ$";
    String numChars = "0123456789.";

    //Parses through each individual tweet one by one looking for "$" to then
    //add the text following the "$" to an ArrayList, "$" signifies a ticker
    //is being mentioned.

    for (String item : timelineInput)
    {
      //System.out.println("----------------------------");
      //System.out.println(item);
      item = item.toUpperCase();
      String lines[] = item.split("\\r?\\n");

      for (String currentLine : lines)
      {
    	  while(currentLine.indexOf("$") != -1)
    	  {
            String newTicker = "";
            int i = currentLine.indexOf("$");
            int k = i;
            
            while (i < currentLine.length() && keyChars.indexOf(currentLine.charAt(i)) != -1)
            {
              newTicker += currentLine.charAt(i);
              i+=1;
            }
            
            String trigger = "";
            
            String[] keywords = {"UNDER ", "BELOW ", "ABOVE ", "OVER ", "<", ">"};
            
            for(String word : keywords)
            {
          	  if(currentLine.indexOf(word) != -1)
          	  {
          		  trigger = currentLine;
          		  break;
          	  }
            }
            System.out.println("Found Ticker : " + newTicker);
            if(!tickers.contains(newTicker) && newTicker.length() > 1)
            {
            	Stock stock = new Stock(newTicker);
        		//stock.addTrigger(trigger);
        		stockList.add(stock);
        		tickers.add(newTicker);
        		if(trigger.length() > 0)
        		{
            		stock.addTrigger(trigger);
        		}
            }
            else
            {
            	for (Stock stock : stockList)
            	{
            		if(stock.equals(newTicker))
            		{
            			stock.addMention();
            			if(trigger.length() > 0)
                		{
                    		stock.addTrigger(trigger);
                		}
            		}
            	}
            }
            
            currentLine = currentLine.substring(0, k) + currentLine.substring(k + 1);
            
            
    	  }
      }
          
          
          
          
          
    }

    return stockList;

  }

  public static ArrayList<Stock> sortTickers(ArrayList<Stock> tickerlist)
  {
	  Collections.sort(tickerlist, Collections.reverseOrder());
	  return tickerlist;
	  
  }

  public static void listTickers(ArrayList<Stock> tickerList) throws IOException
  {
	StockPull stockPuller = new StockPull();
	  
    System.out.println();
    System.out.println();

    System.out.println("Here are the results!");

    for (Stock stock : tickerList)
    {
      if (stock.getMentions() >= 4)
      {
    	  System.out.println(stock.getTicker() + " Data : ");
    	  System.out.println("    Mentions: " + stock.getMentions());
    	  
    	  System.out.println("    Trigger Levels: ");
    	  if(!stock.getTriggers().isEmpty())
    	  {
    		  for(String item : stock.getTriggers())
    		  {
    			  System.out.println("        " + item);
    		  }
    	  }
    	  
    	  
    	  
    	  //List<StockPull.StockInfo> stockData = stockPuller.getStockData("TICKER HERE");
    	  
    	  //System.out.println("Stock Price High: " + stockData.get(0).high);
    	  
      }
    }
    
  }

  public static String findTickerData(String inputTicker)
  {
	String todo = "test";
    return(todo);
  }

  public static void main (String[] args)
  {
    Twitter twitterIntegration = getTwitterInstance();

    Scanner scan = new Scanner(System.in);

    //CHANGE THIS LINE TO THE PROPER DATE SEARCH TERMS

    //String searchTerm = "Watchlist OR WL 10-7 OR 10-07 -filter:retweets since:2021-10-06";
    String searchTerm = "\"WL\" \"11-29\" -filter:retweets since:2021-11-27";
    //String searchTerm2 = "\"WL\" \"11-08\" -filter:retweets since:2021-11-07";
    String searchTerm3 = "\"watchlist\" \"11-29\" -filter:retweets since:2021-11-27";
    //String searchTerm4 = "\"watchlist\" \"11-08\" -filter:retweets since:2021-11-07";
    String searchTerm5 = "\"watch list\" \"11-29\" -filter:retweets since:2021-11-27";
    //String searchTerm6 = "\"watch list\" \"11-8\" -filter:retweets since:2021-11-07";

    String[] searchList = {searchTerm,searchTerm3, searchTerm5};
    //String[] searchList = {searchTerm};


    ArrayList<String> timelineTweets = new ArrayList<String>();
    
    for (String item : searchList)
    {
    	timelineTweets.addAll(scrapeTimeline(twitterIntegration, item));
    }

    ArrayList<Stock> stockList = sortTickers(convertTickers(timelineTweets));

    try {
		listTickers(stockList);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

  }
}
