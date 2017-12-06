package win.ccav.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


import org.springframework.beans.factory.annotation.Autowired;
import win.ccav.dao.TypeReponsitory;
import win.ccav.model.Type;
import win.ccav.utils.StringUtil;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class TypeManage extends JFrame {

	private JPanel contentPane;
	private JTextField dayUnitPirce;

	private JComboBox typeCom;
	private double curPirce;
	private List<win.ccav.model.Type> types;
	/**
	 * Launch the application.
	 */

	@Autowired
	private TypeReponsitory typeReponsitory;
	public void init(){
	    types=typeReponsitory.findAll();
	    typeCom.setModel(new DefaultComboBoxModel(types.toArray(new win.ccav.model.Type[types.size()])));
		dayUnitPirce.setText(String.valueOf(((win.ccav.model.Type)typeCom.getSelectedItem()).getUnitPrice()));
    }

	/**
	 * Create the frame.
	 */
	public TypeManage() {
		setTitle("类型管理---成都一丁花卉租赁有限公司-v1.0");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 233);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("类型");

		JLabel label = new JLabel("日单价");

		typeCom = new JComboBox();
		typeCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				selectChange();
			}
		});
		dayUnitPirce = new JTextField();
		dayUnitPirce.setColumns(10);
		dayUnitPirce.setText(String.valueOf(curPirce));

		JButton button = new JButton("保存");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(112)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(typeCom, 0, 136, Short.MAX_VALUE)
								.addComponent(dayUnitPirce, 136, 136, 136))
							.addGap(5)))
					.addGap(117))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(typeCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(dayUnitPirce, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(button)
					.addContainerGap(43, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	private void selectChange(){
//		String selectType = (String) typeCom.getSelectedItem();
//		double price=TypeDao.getPirce(selectType);
//		dayUnitPirce.setText(String.valueOf(price));
        win.ccav.model.Type selectType= (win.ccav.model.Type) typeCom.getSelectedItem();
        dayUnitPirce.setText(String.valueOf(selectType.getUnitPrice()));
	}
	private void save(){
        win.ccav.model.Type selectType= (win.ccav.model.Type) typeCom.getSelectedItem();
		String priceStr=dayUnitPirce.getText();
		if(!StringUtil.isDouble(priceStr)){
			JOptionPane.showMessageDialog(null,"输入的不是数字");
		}else{
			    try{
			        selectType.setUnitPrice(new Float(priceStr));
			        typeReponsitory.save(selectType);
                    JOptionPane.showMessageDialog(null,"修改成功");
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"修改异常，请重新再试");
                }
		}
	}
}
