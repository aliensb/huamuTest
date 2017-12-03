package win.ccav.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/11/29.
 */
@Entity
@Table
public class Users {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String userName;
    @Column
    private String passWord;
    @Column
    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
