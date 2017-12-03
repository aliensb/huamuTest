package win.ccav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import win.ccav.model.Users;

/**
 * Created by Administrator on 2017/11/29.
 */
@Repository
public interface UserReponsitory  extends JpaRepository<Users,Integer> {
    Users findByUserNameAndPassWord(String username,String password);
}
