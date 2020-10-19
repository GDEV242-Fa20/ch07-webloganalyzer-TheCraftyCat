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
    
    // Additions needed for exercise 7.19:
    private int[] dayCounts;
    private int[] monthCounts;
    private int[] yearCounts;

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
        
        // Additions needed for exercise 7.19:
        dayCounts = new int[32];
        monthCounts = new int[13];
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
     * Analyze the daily access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) 
        {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
    }
    
    /**
     * Analyze the monthly access data from the log file.
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) 
        {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
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
    
    /**
     * Print the daily counts.
     * These should have been set with a prior
     * call to analyzeDailyData.
     */
    public void printDailyCounts()
    {
        System.out.println("Day: Count");
        for(int day = 1; day < dayCounts.length; day++) {
            System.out.println(day + ": " + dayCounts[day]);
        }
    }
    
    /**
     * Return the number of the busiest day.
     * This method relies on a prior call to analyzeDailyData.
     * 
     * @return busiestDay The number of the busiest day.
     * 
     * This satisfies exercise 7.19.
     */
    public int busiestDay()
    {
        int busiestDay = 0;
        for(int day = 0; day < dayCounts.length; day++)
        {
            if (dayCounts[day] > dayCounts[busiestDay])
            {
                busiestDay = day;
            }
        }
        
        return busiestDay;
    }
    
    /**
     * Return the number of the questest day.
     * This method relies on a prior call to analyzeDailyData.
     * 
     * @return quietestDay The number of the quietest day.
     * 
     * This satisfies exercise 7.19.
     */
    public int quietestDay()
    {
        int quietestDay = 0;
        for(int day = 0; day < dayCounts.length; day++)
        {
            if (dayCounts[day] < dayCounts[quietestDay])
            {
                quietestDay = day;
            }
        }
        
        return quietestDay;
    }
    
    /**
     * Return the number of monthly accesses recorded in the log file.
     * @return totalByMonth An array of the total number of accesses 
     * recorded in the log file, by month.
     * 
     * This satisfies exercise 7.19.
     */
    public int[] totalAccessesPerMonth()
    {
        int[] totalByMonth = new int[monthCounts.length];
        // Add the value for each month to the array
        for(int month = 0; month < totalByMonth.length; month++)
        {
            totalByMonth[month] += monthCounts[month];
        }
        
        return totalByMonth;
    }
    
    /**
     * Return the number of the busiest month.
     * This method relies on a prior call to analyzeMonthlyData.
     * 
     * @return busiestMonth The number of the busiest month.
     * 
     * This satisfies exercise 7.19.
     */
    public int busiestMonth()
    {
        int busiestMonth = 0;
        for(int month = 0; month < monthCounts.length; month++)
        {
            if (monthCounts[month] > monthCounts[busiestMonth])
            {
                busiestMonth = month;
            }
        }
        
        return busiestMonth;
    }
    
    /**
     * Return the number of the questest month.
     * This method relies on a prior call to analyzeMonthlyData.
     * 
     * @return quietestMonth The number of the quietest month.
     * 
     * This satisfies exercise 7.19.
     */
    public int quietestMonth()
    {
        int quietestMonth = 0;
        for(int month = 0; month < monthCounts.length; month++)
        {
            if (monthCounts[month] < monthCounts[quietestMonth])
            {
                quietestMonth = month;
            }
        }
        
        return quietestMonth;
    }
    
    /**
     * Print the monthly counts.
     * These should have been set with a prior
     * call to analyzeMonthlyData.
     */
    public void printMonthlyCounts()
    {
        System.out.println("Month: Count");
        for(int month = 1; month < monthCounts.length; month++) {
            System.out.println(month + ": " + monthCounts[month]);
        }
    }
}
