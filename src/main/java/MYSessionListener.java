/**
 * Created by always on 16/3/24.
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener()
public class MYSessionListener implements HttpSessionBindingListener{

    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        
    }

    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
