package win.ccav.ui;

import win.ccav.dao.UserReponsitory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2017/11/29.
 */
public class MainTest extends JFrame {
    public void init(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(600, 400));
        setVisible(true);
        setState(Frame.NORMAL);
        this.setVisible(true);
        System.out.println(userReponsitory==null);
    }
    private UserReponsitory userReponsitory;
}
