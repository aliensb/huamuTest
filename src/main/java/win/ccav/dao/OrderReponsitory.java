package win.ccav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import win.ccav.model.Orders;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */
@Repository
public interface OrderReponsitory extends JpaRepository<Orders,Integer> {
    @Query(value = "select * from orders where date_format(orderDate,'%Y-%m-%d')= :date",nativeQuery = true)
    List<Orders> findSomedayOrders(@Param(value ="date") String orderDate);

    List<Orders> findAllByTitle(String title);

    List<Orders> findAllByTitleLike(String title);
}
