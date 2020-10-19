/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Catherine Oldfield
 *          for RVDCC GDEV242 - Fall 2020
 *          from code written by David J. Barnes and Michael Kölling.
 * @version    10/18/2020
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     * @param filename The name of the log file, including the .txt extension
     * 
     * Modifying this constructor to accept a String parameter and 
     * passing that parameter to the call to LogfileReader satisfies
     * exercise 7.12.
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Return the number of accesses recorded in the log file.
     * @return total The total number of accesses recorded in the log file
     * 
     * This satisfies exercise 7.14.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        // Add the value in each element of hourCounts to total.
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            total += hourCounts[hour];
        }
        return total;
    }
    
    /**
     * Return the hour number of the busiest hour.
     * This method relies on a prior call to analyzeHourlyData.
     * 
     * @return busiestHour The hour number of the busiest hour. Note that
     * hour 0 is 12 AM.
     * 
     * This satisfies exercise 7.15.
     */
    public int busiestHour()
    {
        int busiestHour = 0;
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            if (hourCounts[hour] > hourCounts[busiestHour])
            {
                busiestHour = hour;
            }
        }
        
        return busiestHour;
    }
    
    /**
     * Return the hour number of the questest hour.
     * This method relies on a prior call to analyzeHourlyData.
     * 
     * @return quietestHour The hour number of the quietest hour. Note that
     * hour 0 is 12 AM.
     * 
     * This satisfies exercise 7.16.
     */
    public int quietestHour()
    {
        int quietestHour = 0;
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            if (hourCounts[hour] < hourCounts[quietestHour])
            {
                quietestHour = hour;
            }
        }
        
        return quietestHour;
    }
    
    /**
     * Return the hour number of the start of the busiest two-hour period.
     * This method relies on a prior call to analyzeHourlyData.
     * 
     * @return busiestTwoHour The hour number of the start of the busiest 
     * two-hour period. Note that hour 0 is 12 AM.
     * 
     * This satisfies exercise 7.18.
     */
    public int busiestTwoHour()
    {
        int busiestTwoHour = 0;
        int nextHour = 0;
        if (busiestTwoHour == 23) //if at the end of the array
        {
            nextHour = 0; //roll over the array index
        }
        else
        {
            nextHour = busiestTwoHour + 1;
        }
        
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            if (hour == 23) //end of the array?
            {
                if ((hourCounts[hour] + hourCounts[0]) > 
                (hourCounts[busiestTwoHour] + hourCounts[nextHour]))
                {
                    busiestTwoHour = hour;
                }
            }
            else
            {
                if ((hourCounts[hour] + hourCounts[hour + 1]) > 
                (hourCounts[busiestTwoHour] + hourCounts[nextHour]))
                {
                    busiestTwoHour = hour;
                }
            }
        }
        
        return busiestTwoHour;
    }
}
