package win.ccav.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import win.ccav.dao.HuaReponsitory;
import win.ccav.dao.TypeReponsitory;
import win.ccav.model.Hua;
import win.ccav.model.Type;
import win.ccav.utils.FileUtils;
import win.ccav.utils.StringUtil;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HuaManage extends JFrame {
    private String resPath;
    private JPanel contentPane;
    private List<win.ccav.model.Type> types;
    private List<Hua> names;
    private JComboBox typeCom;
    private JComboBox nameCom;
    private JTextField nameText;
    private JTextField typeText;
    private JLabel label_4;
    private JLabel label_5;
    private JTextField unitPriceText;
    private JLabel label_6;
    private JTextField chengbenText;
    private JLabel picLabel;
    private JButton button_3;
    private String picAddress;
    private String defaultPicAddress;
    private String picSavePath;
    private Hua currentSelectHua;


    @Autowired
    private HuaReponsitory huaReponsitory;
    @Autowired
    private Environment env;

    @Autowired
    private TypeReponsitory typeReponsitory;

    private MainJframe mainJframe;

    public void setMainJframe(MainJframe mainJframe) {
        this.mainJframe = mainJframe;
    }

    public void init() {
        types = typeReponsitory.findAll();
        names=huaReponsitory.findAllByTypeId(types.get(0).getId());
        //初始化界面
        typeCom.setModel(new DefaultComboBoxModel(types.toArray(new win.ccav.model.Type[types.size()])));
        nameCom.setModel(new DefaultComboBoxModel(names.toArray(new Hua[names.size()])));

        currentSelectHua= (Hua) nameCom.getSelectedItem();
        defaultPicAddress = env.getProperty("pic");
        picSavePath=env.getProperty("savePath");
        this.initial(currentSelectHua);
    }

    /**
     * Create the frame.
     */
    public HuaManage() {
        setResizable(false);
        Font font = new Font("Dialog", Font.PLAIN, 12);
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
        this.setTitle("花木信息管理---成都一丁花卉租赁有限公司-v1.0");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(100, 100, 660, 361);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(UIManager.getColor("Button.background"));
        contentPane.add(desktopPane, BorderLayout.CENTER);

        typeCom = new JComboBox();
        typeCom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    typeChange();
                }
            }
        });

        JLabel label = new JLabel("类型：");

        JLabel label_1 = new JLabel("名称：");

        nameCom = new JComboBox();
        nameCom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                if(arg0.getStateChange() == ItemEvent.SELECTED){
                    nameChange();
                }
            }
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JLabel label_3 = new JLabel("花木名称：");

        nameText = new JTextField();
        nameText.setColumns(10);

        typeText = new JTextField();
        typeText.setColumns(10);

        label_4 = new JLabel("花木类型：");

        label_5 = new JLabel("单价：");

        unitPriceText = new JTextField();
        unitPriceText.setEditable(false);
        unitPriceText.setColumns(10);

        label_6 = new JLabel("成本：");

        chengbenText = new JTextField();
        chengbenText.setColumns(10);

        picLabel = new JLabel();
        picLabel.setSize(174, 174);
        JButton button = new JButton("更改图片");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updatePic();
            }
        });

        JButton button_1 = new JButton("保存更改");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateHua();
            }
        });

        JButton button_2 = new JButton("新增花木");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                insertOne();
            }
        });

        button_3 = new JButton("删除花木");
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                deleteHua();
            }
        });
        
        JButton btnNewButton = new JButton("设为快选");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		specialConfirm();
        	}
        });
        GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
        gl_desktopPane.setHorizontalGroup(
        	gl_desktopPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_desktopPane.createSequentialGroup()
        			.addGap(55)
        			.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_desktopPane.createSequentialGroup()
        					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(label_5)
        						.addComponent(label_6)
        						.addGroup(gl_desktopPane.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
        								.addComponent(label, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
        								.addComponent(label_4))))
        					.addGap(18)
        					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(chengbenText, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
        						.addComponent(unitPriceText, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
        						.addComponent(typeText, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
        						.addComponent(nameText, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
        						.addComponent(typeCom, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        			.addGap(75)
        			.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_desktopPane.createSequentialGroup()
        					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(nameCom, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_desktopPane.createSequentialGroup()
        					.addComponent(picLabel, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(button_3, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        						.addComponent(button, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        						.addComponent(button_2, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        						.addComponent(button_1, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
        			.addGap(18))
        );
        gl_desktopPane.setVerticalGroup(
        	gl_desktopPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_desktopPane.createSequentialGroup()
        			.addGap(23)
        			.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label)
        				.addComponent(typeCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_1)
        				.addComponent(nameCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(50)
        			.addGroup(gl_desktopPane.createParallelGroup(Alignment.TRAILING)
        				.addComponent(picLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_desktopPane.createSequentialGroup()
        					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
        						.addComponent(typeText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(label_4))
        					.addGap(18)
        					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addGroup(gl_desktopPane.createSequentialGroup()
        							.addGap(3)
        							.addComponent(label_3)))
        					.addGap(18)
        					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(label_5)
        						.addComponent(unitPriceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
        						.addComponent(label_6)
        						.addComponent(chengbenText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(gl_desktopPane.createSequentialGroup()
        					.addComponent(button)
        					.addGap(14)
        					.addComponent(button_3)
        					.addGap(18)
        					.addComponent(button_2)
        					.addGap(16)
        					.addComponent(button_1)))
        			.addGap(18)
        			.addComponent(btnNewButton)
        			.addContainerGap(38, Short.MAX_VALUE))
        );
        desktopPane.setLayout(gl_desktopPane);
        //this.add(new PicPanel());


    }


    private void initial(Hua hua) {
        String pic = "";
        if (hua == null || hua.getPic() == null) {
            pic = defaultPicAddress;
        }
        else{
            pic = hua.getPic();
        }
        picAddress=pic;
        picLabel.setIcon(getIcon(pic));
        unitPriceText.setText(String.valueOf(hua.getUnitPrice()));
        typeText.setText(hua.getType());
        nameText.setText(hua.getHname());
        chengbenText.setText(String.valueOf(hua.getChengBen()));
    }

    private void typeChange() {
//        String selectType = (String) typeCom.getSelectedItem();
        win.ccav.model.Type selectType=(win.ccav.model.Type) typeCom.getSelectedItem();
        //nameCom.removeAllItems();
        names = huaReponsitory.findAllByTypeId(selectType.getId());
        nameCom.setModel(new DefaultComboBoxModel(names.toArray(new Hua[names.size()])));
//        Hua selectHua= (Hua) nameCom.getSelectedItem();
        currentSelectHua=(Hua) nameCom.getSelectedItem();
        String pic = "";
        if (currentSelectHua == null || currentSelectHua.getPic() == null) {
            //错误提
            JOptionPane.showMessageDialog(null, "数据错误，请检测数据");
            return;
        }else{
            pic = currentSelectHua.getPic();
        }
        picAddress=pic;
        picLabel.setIcon(getIcon(pic));
        unitPriceText.setText(String.valueOf(currentSelectHua.getUnitPrice()));
        typeText.setText(currentSelectHua.getType());
        nameText.setText(currentSelectHua.getHname());
        chengbenText.setText(String.valueOf(currentSelectHua.getChengBen()));
    }

    private void nameChange() {
//        String selectType = (String) typeCom.getSelectedItem();
//        String selectName = (String) nameCom.getSelectedItem();
        win.ccav.model.Type selectType = (win.ccav.model.Type) typeCom.getSelectedItem();
        currentSelectHua=(Hua) nameCom.getSelectedItem();
        String pic = "";
        if (currentSelectHua == null || currentSelectHua.getPic() == null) {
            JOptionPane.showMessageDialog(null, "数据错误，请检测数据");
            return;
        }else{
            pic = currentSelectHua.getPic();
        }
        picAddress=pic;
        picLabel.setIcon(getIcon(pic));
        unitPriceText.setText(String.valueOf(currentSelectHua.getUnitPrice()));
        typeText.setText(currentSelectHua.getType());
        nameText.setText(currentSelectHua.getHname());
        chengbenText.setText(String.valueOf(currentSelectHua.getChengBen()));
    }

    private void updatePic() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("请选择一张图片");
        //chooser.setSelectedFile(new File("out.xls"));
        chooser.setCurrentDirectory(new File("D:/data"));
        int option = chooser.showSaveDialog(null);
        File file = null;
        if (option == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            if (StringUtil.isImage(file)) {//判断选择的是不是图片
                String newFileName = FileUtils.getFileName(FileUtils.getFileSuffix(file.getName()));
                String outFile = 1231231 + newFileName;
                File toFile = new File(outFile);
                try {
                    FileUtils.copy(file, toFile, true);//进行复制
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (toFile.exists()) {//判断复制后之后的文件存在不存在
                    //修改图片，显示修改过后的图片 flag005
                    picAddress = toFile.getAbsolutePath();
                    //picLabel.setIcon(new ImageIcon(toFile.getAbsolutePath()));
                    picLabel.setIcon(getIcon(toFile.getAbsolutePath()));
                    //JOptionPane.showMessageDialog(null, toFile.getAbsolutePath());
                    //picLabel.setText("<html><img scr="+toFile.getAbsolutePath()+"/></html");
                } else {
                    JOptionPane.showMessageDialog(null, "修改失败，请重试");
                }
            } else {
                JOptionPane.showMessageDialog(null, "选择的不是图片，请重新选择！");
            }

        } else {
            return;
        }
    }

    private void updateHua() {
        String type = typeText.getText();
        String hname = nameText.getText();
        String unitPrice = unitPriceText.getText();
        String chengben = chengbenText.getText();
        if (picAddress == null || picAddress.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "选择的图片有问题，请重新选择！");
            return;
        }

        if (StringUtil.isEmpty(type) ||
                StringUtil.isEmpty(hname) ||
                StringUtil.isEmpty(unitPrice) ||
                StringUtil.isEmpty(chengben) ||
                StringUtil.isEmpty(picAddress)) {
            JOptionPane.showMessageDialog(null, "输入值不能为空");
            return;
        }
        if (!StringUtil.isDouble(unitPrice) || !StringUtil.isDouble(chengben)) {
            JOptionPane.showMessageDialog(null, "输入的价格有问题，请重新输入！");
            return;
        } else {
            currentSelectHua.setType(type);
            currentSelectHua.setHname(hname);
            currentSelectHua.setUnitPrice(new Float(unitPrice));
            currentSelectHua.setChengBen(new Float(chengben));
            currentSelectHua.setPic(picAddress);
            try{
                huaReponsitory.save(currentSelectHua);
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "修改失败");
                return;
            }
            JOptionPane.showMessageDialog(null, "修改成功");
        }

    }

   // @Transactional
    private void insertOne() {
        String type = typeText.getText();
        String hname = nameText.getText();
        String unitPrice = unitPriceText.getText();
        String chengben = chengbenText.getText();
        if (picAddress == null || picAddress.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "选择的图片有问题，请重新选择！");
            return;
        }
        if (StringUtil.isEmpty(type) ||
                StringUtil.isEmpty(hname) ||
                StringUtil.isEmpty(unitPrice) ||
                StringUtil.isEmpty(chengben) ||
                StringUtil.isEmpty(picAddress)) {
            JOptionPane.showMessageDialog(null, "输入值不能为空");
            return;
        }
        if (!StringUtil.isDouble(unitPrice) || !StringUtil.isDouble(chengben)) {
            JOptionPane.showMessageDialog(null, "输入的价格有问题，请重新输入！");
            return;
        } else {
            //创建dao，根据类型，名称，删除，然后再来新增
            Hua hua=new Hua();
            hua.setType(type);
            hua.setHname(hname);
            hua.setUnitPrice(new Float(unitPrice));
            hua.setChengBen(new Float(chengben));
            hua.setPic(picAddress);
            try{
                //huaReponsitory.saveAndFlush(currentSelectHua);
                huaReponsitory.save(hua);
               // huaReponsitory.flush();
            }catch (DataIntegrityViolationException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "添加失败,指定的花木已存在！");
                return;
            }
            catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "添加失败");
                return;
            }
            JOptionPane.showMessageDialog(null, "添加成功");
        }
    }

    private void deleteHua() {
        String type = typeText.getText();
        String hname = nameText.getText();
        if (StringUtil.isEmpty(type) ||
                StringUtil.isEmpty(hname)) {
            JOptionPane.showMessageDialog(null, "请选择需要删除的花木");
            return;
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "确定要删除？");
            if (confirm != 0) {
                return;
            }
            try{
                huaReponsitory.delete(currentSelectHua);
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "删除失败");
                return;
            }
            JOptionPane.showMessageDialog(null, "删除成功");
        }
    }

    private ImageIcon getIcon2(String picPath) {
        int width = 174, height = 174; //这是图片和JLable的宽度和高度
        ImageIcon image = new ImageIcon(picPath);//实例化ImageIcon 对象
        image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));//可以用下面三句代码来代替
        return image;
    }

    private ImageIcon getIcon(String picPath) {
        ImageIcon icon = new ImageIcon(picPath);
        int imgWidth = icon.getIconWidth();
        int imgHeight = icon.getIconHeight();
        int conWidth = picLabel.getWidth();
        int conHeight = picLabel.getHeight();
        int reImgWidth;
        int reImgHeight;
        if (imgWidth / imgHeight >= conWidth / conHeight) {
            if (imgWidth > conWidth) {
                reImgWidth = conWidth;
                reImgHeight = imgHeight * reImgWidth / imgWidth;
            } else {
                reImgWidth = imgWidth;
                reImgHeight = imgHeight;
            }
        } else {
            if (imgWidth > conWidth) {
                reImgHeight = conHeight;
                reImgWidth = imgWidth * reImgHeight / imgHeight;
            } else {
                reImgWidth = imgWidth;
                reImgHeight = imgHeight;
            }
        }
        icon = new ImageIcon(icon.getImage().getScaledInstance(reImgWidth, reImgHeight, Image.SCALE_DEFAULT));
        return icon;
    }
    private void specialConfirm(){
    	currentSelectHua.setSpecial(1);
    	huaReponsitory.save(currentSelectHua);
        mainJframe.updateSpecialCom();
        JOptionPane.showMessageDialog(null, "添加成功");
    }
}
