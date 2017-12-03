package win.ccav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import win.ccav.model.Hua;

import javax.swing.text.html.HTMLDocument;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */
@Repository
public interface HuaReponsitory extends JpaRepository<Hua,Integer> {
    @Query("select distinct h.type from Hua h")
    List<String> getDistinctHuaType();

    @Query(value = "select distinct h.hname from Hua h where h.type =:type ")
    List<String> findDistinctHuaNameByType(@Param(value = "type") String type);

    List<Hua> findById(Integer id);

    List<Hua> findAll();

    List<Hua> findByHnameAndType(String name,String type);

    Hua findDistinctFirstByTypeAndHname(String name,String type);

    Hua findFirstByHnameAndType(String name,String type);

    List<Hua> findAllBySpecial(Integer special);

    List<Hua> findAllByTypeId(Integer typeId);
}
