package win.ccav.ui;

import org.springframework.beans.factory.annotation.Autowired;
import win.ccav.dao.UserReponsitory;
import win.ccav.model.Users;
import win.ccav.utils.StringUtil;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

////import win.ccav.dao.UserDao;
//import win.ccav.model.User;
//import win.ccav.utils.StringUtil;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginJframe extends JFrame {

    private JPanel contentPane;
    private JTextField userText;
    private JPasswordField passwordText;

    private MainJframe mainJframe;

    public void setMainJframe(MainJframe mainJframe) {
        this.mainJframe = mainJframe;
    }

    @Autowired
    private UserReponsitory userReponsitory;

    public UserReponsitory getUserReponsitory() {
        return userReponsitory;
    }

    public void setUserReponsitory(UserReponsitory userReponsitory) {
        this.userReponsitory = userReponsitory;
    }

    public MainJframe getMainJframe() {
        return mainJframe;
    }

    public void init() {
        this.setVisible(true);
    }

    /**
     * Create the frame.
     */
    public LoginJframe() {


        Font font = new Font("Dialog", Font.PLAIN, 12);
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
        setTitle("用户登录---成都一丁花卉租赁有限公司-v1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.LIGHT_GRAY);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(desktopPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addComponent(desktopPane, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                .addGap(0))
        );

        JLabel label = new JLabel("用户名：");
        label.setBounds(79, 83, 54, 15);
        desktopPane.add(label);

        JLabel label_1 = new JLabel("密  码：");
        label_1.setBounds(79, 122, 54, 15);
        desktopPane.add(label_1);

        userText = new JTextField();
        userText.setBounds(129, 80, 122, 21);
        desktopPane.add(userText);
        userText.setColumns(10);

        JButton btnNewButton = new JButton("登录");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                loginActionPerformed(event);
            }
        });
        btnNewButton.setBounds(275, 79, 93, 23);
        desktopPane.add(btnNewButton);

        JButton button = new JButton("重置");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                resetValueActionPerformed(arg0);
            }
        });
        button.setBounds(275, 118, 93, 23);
        desktopPane.add(button);

        passwordText = new JPasswordField();
        passwordText.setBounds(129, 119, 122, 21);
        desktopPane.add(passwordText);
        contentPane.setLayout(gl_contentPane);
    }

    private void loginActionPerformed(ActionEvent evt) {
        String userName = this.userText.getText();
        String password = new String(this.passwordText.getPassword());
        if (StringUtil.isBlack(userName)) {
            JOptionPane.showMessageDialog(null, "用户名不能为空！");
            return;
        }
        if (StringUtil.isBlack(password)) {
            JOptionPane.showMessageDialog(null, "密码不能为空！");
            return;
        }//接下来做用户登
        Users user = userReponsitory.findByUserNameAndPassWord(userName, password);
        if (user != null) {
            this.dispose();
            mainJframe.setUserid(user.getUserName());
            mainJframe.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "用户名密码不正确！");
        }
    }

    private void resetValueActionPerformed(ActionEvent evt) {
        this.userText.setText("");
        this.passwordText.setText("");
    }
}
