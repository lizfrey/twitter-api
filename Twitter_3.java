
import twitter4j.*;       //set the classpath to lib\twitter4j-core-4.0.4.jar
import java.util.List;
import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

public class Twitter_3
{
   private static PrintStream consolePrint;
   
   public static void main (String []args) throws TwitterException, IOException
   {
      consolePrint = System.out; // this preserves the standard output so we can get to it later      
      TJTwitter bigBird = new TJTwitter(consolePrint);
            
      /*GeoLocation [] location = new GeoLocation[244];
      int [] count = new int[244];
      
      Scanner sc = new Scanner(new File("coords.txt"));
      
      for(int i = 0; i < 244; i++)
      {
         StringTokenizer x = new StringTokenizer(sc.nextLine());
         x.nextToken();
         location[i] = new GeoLocation(Double.parseDouble(x.nextToken()), Double.parseDouble(x.nextToken()));
      }
      for(int i = 0; i<10; i++)
      {
         Twitter twitter = TwitterFactory.getSingleton();
         Query query = new Query("flu");
      
         query.setCount(5);
      
         query.geoCode(location[i],10,"km");
         QueryResult result = twitter.search(query);      
         for (Status status : result.getTweets()) {
            count[i] ++;
         }
      }
      
      for(int i:count)
         System.out.println(i);
      */
      
      GeoLocation [] count = new GeoLocation[100000];
      
      Scanner s = new Scanner(new File("Coordinates.txt"));
      s.nextLine();
      for(int i = 0; i<43191;i++)
      {
         int x = s.nextInt();
         String f = s.next();
         count[x] = new GeoLocation(Double.parseDouble(f.substring(0,f.indexOf(","))),
                                    Double.parseDouble(f.substring(f.indexOf(",")+1)));
      }
      
      Scanner sys = new Scanner(System.in);
      
      System.out.print("Enter your zip code: ");
      
      int zip = sys.nextInt();
      
      Twitter twitter = TwitterFactory.getSingleton();
      Query query = new Query(" coronavirus ");
      
      query.setCount(200);
      
      int [] arr = new int[5];
      
      for(int i = 0; i<arr.length; i++)
      {
         query.geoCode(count[zip],5*i,"km");
         QueryResult 
         result = twitter.search(query);      
         for (Status status : result.getTweets()) 
            arr[i] ++;
      }
      
      System.out.println(graph(arr));
      
      System.out.println("Do you want to warn your friends?");
      
      if(sys.next().equals("yes"))
      {
         System.out.println("Distance (multiple of 5): ");
         
         bigBird.tweetOut(warnFriends(arr,sys.nextInt()));
      }
   }
   
   public static String graph(int [] arr)
   {
      String s = "\n" + "Instances of Coronoaviru Tweets\n__| 10 20 30 40 50 60 70 80 90 ...\n";
      for(int i = 1; i<arr.length+1; i++)
      {
         if(i==1)
            s+=" ";
         s+= i*5 + "|";
         for(int z = 0; z<(arr[i-1])/10+1; z++)
            s+=" - ";
         s+="\n";
      }
      
      return s;
   }
   
   public static String warnFriends(int [] arr, int i) 
   {
      return "Hello! If you live within " + i + 
             " km of me, there are around " + arr[i/5] +
             " people near us with the flu! Take vitamin C!";
      
   }
   
}