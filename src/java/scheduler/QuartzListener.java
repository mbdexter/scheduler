package scheduler;

import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import static jdk.nashorn.internal.codegen.Compiler.LOG;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

/**
 *
 * @author Marcin
 */
public class QuartzListener implements ServletContextListener {
    Scheduler scheduler = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContext)
    {
        System.out.println("Context initialized");
        
        SchedulerFactory schedulerFactory = new MySchedulerFactory();
        
        try {
                scheduler = schedulerFactory.getScheduler();
                JobDetail jobDetail = new JobDetail("przykład", null,
                                QuartzJob.class);
                Trigger trigger = new CronTrigger("simple", null, "*/5 * * * * ?");
                scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
                e.printStackTrace();
        } catch (ParseException e) {
                e.printStackTrace();
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent servletContext)
    {
        if(servletContext != null && servletContext.getServletContext() != null)
        {
            try
            {
                String delme = servletContext.getServletContext().getInitParameter("eraseOnExit");
                if (delme != null && delme.length() > 0) {
                    File del = new File(delme);
                    if (del.exists()) {
                        System.out.println("Deleting file " + delme);
                        del.delete();
                    }
                }
                
                scheduler.shutdown(true);
            }
            catch (SchedulerException e)
            {
                e.printStackTrace();
            }
        }
    }
}