package win.ccav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import win.ccav.model.Orders;

/**
 * Created by Administrator on 2017/11/30.
 */
public interface OrderReponsitory extends JpaRepository<Orders,Integer> {

}
