import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class StockPull {
		
    public List<StockInfo> getStockData(String inputTextData) throws IOException
    {
    	String inputURL = "https://eodhistoricaldata.com/api/eod/MCD.US?from=2017-01-05&to=2017-02-10&period=d&fmt=json&api_token=615c9087ef6899.90148702&fmt=json";
    	String textDataFromURL = stringDataFromURL(inputURL);
    	Gson gson = new Gson();
        Type type = new TypeToken<List<StockInfo>>(){}.getType();
        
        
        List<StockInfo> stockData = gson.fromJson(textDataFromURL, type);
        
        return stockData;
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
    
    public class StockInfo {
        public String date;
        public String open;
        public String high;
        public String low;
        public String close;
        public String adjusted_close;
        public String volume;
   }

}