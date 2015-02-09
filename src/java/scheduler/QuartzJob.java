package scheduler;

//import java.sql.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import knapsack.*;
import com.softtechdesign.ga.GAException;
/**
 *
 * @author Marcin
 */
public class QuartzJob implements Job 
{
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException 
    {
        Calendar calendar = GregorianCalendar.getInstance();
        
        System.out.println(
            "Mamy godzinę: "
            + calendar.get(Calendar.HOUR_OF_DAY) + ":"
            + calendar.get(Calendar.MINUTE) + ":"
            + calendar.get(Calendar.SECOND)
        );
        
        try
        {
            Knapsack knapsack = new Knapsack();
            knapsack.run();
        }
        catch(GAException e)
        {
            System.out.println("GAException");
        }
        
//        try
//        {
//            try
//            {
//                try
//                {
//                    Class.forName("com.mysql.jdbc.Driver").newInstance();
//                    
//                    try
//                    {
//                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/eventsmanager?user=root&password=");
//
//                        Statement stmt = con.createStatement();
//                        ResultSet rs = stmt.executeQuery("SELECT * FROM events");
//
//                        while (rs.next())
//                        {
//                            System.out.println(rs.getString("name"));
//                        }
//                    }
//                    catch(SQLException e)
//                    {
//                        System.out.println("SQLException");
//                    }
//                }
//                catch(IllegalAccessException e)
//                {
//                    System.out.println("IllegalAccessException");
//                }
//            }
//            catch(InstantiationException e)
//            {
//                System.out.println("InstantiationException");
//            }
//        }
//        catch(ClassNotFoundException e)
//        {
//            System.out.println("ClassNotFoundException");
//        }
        
    }
    
}
