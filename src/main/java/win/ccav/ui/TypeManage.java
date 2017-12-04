//package win.ccav.ui;
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//import java.awt.Font;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.UIManager;
//import javax.swing.border.EmptyBorder;
//
//import win.ccav.dao.InitDao;
//import win.ccav.dao.TypeDao;
//import win.ccav.utils.StringUtil;
//
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JComboBox;
//import javax.swing.LayoutStyle.ComponentPlacement;
//import javax.swing.JTextField;
//import javax.swing.JButton;
//import java.awt.event.ItemListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//
//public class TypeManage extends JFrame {
//
//	private JPanel contentPane;
//	private JTextField dayUnitPirce;
//
//	private JComboBox typeCom;
//	private double curPirce;
//	/**
//	 * Launch the application.
//	 */
//
//	{
//		types=InitDao.getTypes();
//		curPirce=TypeDao.getPirce(types[0]);
//	}
//
//
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TypeManage frame = new TypeManage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public TypeManage() {
//		setTitle("类型管理---成都一丁花卉租赁有限公司-v1.0");
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		Font font = new Font("Dialog", Font.PLAIN, 12);
//		java.util.Enumeration keys = UIManager.getDefaults().keys();
//		while (keys.hasMoreElements()) {
//			Object key = keys.nextElement();
//			Object value = UIManager.get(key);
//			if (value instanceof javax.swing.plaf.FontUIResource) {
//				UIManager.put(key, font);
//			}
//		}
//
//		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 445, 233);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//
//		JLabel lblNewLabel = new JLabel("类型");
//
//		JLabel label = new JLabel("日单价");
//
//		typeCom = new JComboBox();
//		typeCom.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent arg0) {
//				selectChange();
//			}
//		});
//		typeCom.setModel(new DefaultComboBoxModel(types));
//
//		dayUnitPirce = new JTextField();
//		dayUnitPirce.setColumns(10);
//		dayUnitPirce.setText(String.valueOf(curPirce));
//
//		JButton button = new JButton("保存");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				save();
//			}
//		});
//		GroupLayout gl_contentPane = new GroupLayout(contentPane);
//		gl_contentPane.setHorizontalGroup(
//			gl_contentPane.createParallelGroup(Alignment.LEADING)
//				.addGroup(gl_contentPane.createSequentialGroup()
//					.addGap(112)
//					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
//						.addComponent(label)
//						.addComponent(lblNewLabel))
//					.addGap(18)
//					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
//						.addComponent(button, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
//						.addGroup(gl_contentPane.createSequentialGroup()
//							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
//								.addComponent(typeCom, 0, 136, Short.MAX_VALUE)
//								.addComponent(dayUnitPirce, 136, 136, 136))
//							.addGap(5)))
//					.addGap(117))
//		);
//		gl_contentPane.setVerticalGroup(
//			gl_contentPane.createParallelGroup(Alignment.LEADING)
//				.addGroup(gl_contentPane.createSequentialGroup()
//					.addGap(40)
//					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
//						.addComponent(lblNewLabel)
//						.addComponent(typeCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//					.addGap(18)
//					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
//						.addComponent(label)
//						.addComponent(dayUnitPirce, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//					.addGap(18)
//					.addComponent(button)
//					.addContainerGap(43, Short.MAX_VALUE))
//		);
//		contentPane.setLayout(gl_contentPane);
//	}
//	private void selectChange(){
//		String selectType = (String) typeCom.getSelectedItem();
//		double price=TypeDao.getPirce(selectType);
//		dayUnitPirce.setText(String.valueOf(price));
//	}
//	private void save(){
//		String selectType = (String) typeCom.getSelectedItem();
//		String priceStr=dayUnitPirce.getText();
//		if(!StringUtil.isDouble(priceStr)){
//			JOptionPane.showMessageDialog(null,"输入的不是数字");
//		}else{
//			if(TypeDao.updatePice(selectType, Double.parseDouble(priceStr))){
//				JOptionPane.showMessageDialog(null,"修改成功");
//			}
//			else{
//				JOptionPane.showMessageDialog(null,"修改异常，请重新再试");
//			}
//		}
//	}
//}
