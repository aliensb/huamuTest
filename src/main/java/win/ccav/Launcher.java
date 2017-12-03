package win.ccav;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import win.ccav.config.AppConfig;
import win.ccav.dao.UserReponsitory;
import win.ccav.model.Users;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */
public class Launcher {
    public void launch() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
