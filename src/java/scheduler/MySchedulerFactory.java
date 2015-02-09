package scheduler;

import java.util.Collection;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Marcin
 */
public class MySchedulerFactory implements SchedulerFactory {
    
    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
 
        @SuppressWarnings("unchecked")
        @Override
        public Collection getAllSchedulers() throws SchedulerException {
                return schedulerFactory.getAllSchedulers();
        }
 
        @Override
        public Scheduler getScheduler() throws SchedulerException {
                Scheduler scheduler = schedulerFactory.getScheduler();
                if (!scheduler.isStarted()) {
                        scheduler.start();
                }
                return scheduler;
        }
 
        @Override
        public Scheduler getScheduler(String schedName) throws SchedulerException {
                Scheduler scheduler = schedulerFactory.getScheduler(schedName);
                if (!scheduler.isStarted()) {
                        scheduler.start();
                }
                return scheduler;
        }
}
