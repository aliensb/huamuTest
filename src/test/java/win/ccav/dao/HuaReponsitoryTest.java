package win.ccav.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import win.ccav.App;
import win.ccav.config.AppConfig;
import win.ccav.config.PersistenceJPAConfig;
import win.ccav.model.Hua;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, PersistenceJPAConfig.class})
public class HuaReponsitoryTest {
    @Autowired
    private HuaReponsitory huaReponsitory;

    @Autowired
    private OrderReponsitory orderReponsitory;

    @Test
    public void testType(){
        for(String type:huaReponsitory.getDistinctHuaType()){
            //System.out.println(huaReponsitory.findByType(type));
        }
    }
    @Test
    public void testName(){
        System.out.println(huaReponsitory.findAll());
    }
    @Test
    public void testFindById(){
        System.out.println(huaReponsitory.findDistinctHuaNameByType("特大"));
    }

    @Test
    public void testByNameAndType(){
        System.out.println(huaReponsitory.findByHnameAndType("摇钱树","特大"));
    }
    @Test
    public void test(){
        System.out.println(huaReponsitory.findFirstByHnameAndType("摇钱树","特大"));
    }
    @Test
    public void testOrder(){
        System.out.println(orderReponsitory.findOne(175));
    }


    @Test
    public void testOrderDate(){
        System.out.println(orderReponsitory.findSomedayOrders("2017-12-02"));
    }
    @Test
    public void testOrderLike(){
        System.out.println(orderReponsitory.findAllByTitleLike("%石%"));
    }
}