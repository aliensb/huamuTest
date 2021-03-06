package win.ccav.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import win.ccav.ui.*;

/**
 * Created by Administrator on 2017/11/29.
 */
@Configuration
@ComponentScan(basePackages = {"win.ccav"})
@EnableJpaRepositories(basePackages = {"win.ccav.dao"})
public class AppConfig {
    @Bean(initMethod = "init")
    public LoginJframe loginJframe(){
        LoginJframe loginJframe=new LoginJframe();
        loginJframe.setMainJframe(mainJframe());
        return loginJframe;
    }
    @Bean(initMethod = "init")
    public MainJframe mainJframe(){
        return new MainJframe();
    }

    @Bean(initMethod ="init")
    public HuaManage huaManage(){
        return new HuaManage();
    }

    @Bean(initMethod = "init")
    public OrderManage orderManage(){
        return new OrderManage();
    }
    @Bean(initMethod = "init")
    public TypeManage typeManage(){
        return new TypeManage();
    }
}
