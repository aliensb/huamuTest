package win.ccav.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import win.ccav.App;
import win.ccav.dao.HuaReponsitory;
import win.ccav.dao.OrderReponsitory;
import win.ccav.dao.TypeReponsitory;
import win.ccav.model.Hua;
import win.ccav.model.Orders;
import win.ccav.model.SheetModel;
import win.ccav.model.Type;
import win.ccav.utils.BeanCopy;
import win.ccav.utils.StringUtil;
import win.ccav.utils.ToSheet;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class MainJframe extends JFrame {

	private JPanel contentPane;

	private List<win.ccav.model.Type> types;
	private List<Hua> names;
	private List<Hua> specialHuas;
	private JComboBox typeCom;
	private JComboBox nameCom ;
	private JComboBox specialCom;
	private JLabel unitPrice ;
	private JFileChooser fileChooser;
	private Image img;
	private JLabel testPic ;
	private JTable mainTable;
	private JTextField numberText;
	private JLabel chengbengLab;
	private JTextField address1;
	private JTextField address2;
	private JTextField beizu;
	private String picadd;
	private int count=1;
	private String userid;
	private JTextField companyName;
	private JTextField keyWord;
	private JComboBox priceCom;
	//	private List<win.ccav.model.Type> prices;
	private Map<Integer, win.ccav.model.Type> prices=new HashMap<>();

	@Autowired
	private Environment env;

	@Autowired
	private HuaManage huaManage;

	@Autowired
	private OrderManage orderManage;

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Autowired
	private HuaReponsitory huaReponsitory;

	@Autowired
	private OrderReponsitory orderReponsitory;

	@Autowired
	private TypeReponsitory typeReponsitory;



	public OrderManage getOrderManage() {
		return orderManage;
	}

	public void setOrderManage(OrderManage orderManage) {
		this.orderManage = orderManage;
	}

	private String defaultPicAddress;

	//用于存放当前选择的花木
	private Hua currentSelectHuaMode;

	private Map<String,String> huaMuPicAddress=new HashMap<>();
	public void init(){
		types=typeReponsitory.findAll();
		names=huaReponsitory.findAllByTypeId(types.get(0).getId());
		specialHuas=huaReponsitory.findAllBySpecial(1);
		nameCom.setModel(new DefaultComboBoxModel(names.toArray(new Hua [names.size()])));
		typeCom.setModel(new DefaultComboBoxModel(types.toArray(new win.ccav.model.Type[types.size()])));
		specialCom.setModel(new DefaultComboBoxModel(specialHuas.toArray(new Hua[specialHuas.size()])));
		priceCom.setModel(new DefaultComboBoxModel(types.toArray(new win.ccav.model.Type[types.size()])));
//		String selectType =   ((win.ccav.model.Type)typeCom.getSelectedItem()).getTypeName();
//		String selectName = ((Hua)nameCom.getSelectedItem()).getHname();
		//Hua model=huaReponsitory.findFirstByHnameAndType(selectName,selectType);
		currentSelectHuaMode= (Hua) nameCom.getSelectedItem();
		unitPrice.setText(String.valueOf(((Hua)nameCom.getSelectedItem()).getUnitPrice()));
		chengbengLab.setText(String.valueOf(((Hua)nameCom.getSelectedItem()).getChengBen()));
		defaultPicAddress=env.getProperty("pic");
		//prices=typeReponsitory.findAll();
		for (win.ccav.model.Type type : typeReponsitory.findAll()){
			prices.put(type.getId(),type);
		}
		priceFiled.setText(prices.get(((win.ccav.model.Type)priceCom.getSelectedItem()).getId()).getUnitPrice().toString());
	}

	/**
	 * Create the frame.
	 */
	public MainJframe() {
	//	init();

		setTitle("花木报表---成都一丁花卉租赁有限公司-v1.0");
		/*
		 * 字体
		 * */
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1122, 448);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("菜单");
		mnNewMenu.setIcon(new ImageIcon(App.class.getResource("/win/ccav/ui/bookManager.png")));
		menuBar.add(mnNewMenu);

		JMenuItem menuItem = new JMenuItem("花木管理");
		MainJframe thisMain=this;
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				huaManage.setMainJframe(thisMain);
				huaManage.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem);

		JMenuItem menuItem_2 = new JMenuItem("类型管理");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				TypeManage typeView=new TypeManage();
//				typeView.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem_2);

		JMenuItem menuItem_1 = new JMenuItem("订单管理");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO OrderManage
//				OrderManage orderMView=new OrderManage();
//				orderMView.setVisible(true);
				orderManage.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel label = new JLabel("类型：");

		typeCom = new JComboBox();
		typeCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if(e.getStateChange() == ItemEvent.SELECTED) {
					typeChange();
				}
			}
		});
		//TODO 放到init里面去了
		//typeCom.setModel(new DefaultComboBoxModel(types.toArray(new String[types.size()])));
		//typeCom.setModel(new DefaultComboBoxModel(new Integer []{1,3,4,5}));
		JLabel label_1 = new JLabel("名称：");

		nameCom = new JComboBox();
		nameCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					nameChange();
				}
			}
		});


		JLabel label_3 = new JLabel("单价：");

		unitPrice = new JLabel("0");
		unitPrice.setToolTipText("");

		JLabel lblNewLabel = new JLabel("（元/月）");

		testPic = new JLabel();

		JScrollPane scrollPane = new JScrollPane();

		JButton btnexcel = new JButton("生成Excel");
		btnexcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeExcle();
			}
		});

		JButton button = new JButton("添加");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItems();
			}
		});

		JLabel label_2 = new JLabel("成本：");

		chengbengLab = new JLabel("0");

		JLabel label_4 = new JLabel("元   数量：");

		numberText = new JTextField();
		numberText.setColumns(10);

		JLabel label_5 = new JLabel("位置一：");

		address1 = new JTextField();
		address1.setText("请输入地址....");
		address1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				address1.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtil.isBlack(address1.getText())){
					address1.setText("请输入地址....");
				}
			}
		});
		address1.setColumns(10);

		address2 = new JTextField();
		address2.setColumns(10);

		JLabel label_6 = new JLabel("位置二：");

		JLabel label_7 = new JLabel("备注：");

		beizu = new JTextField();
		beizu.setColumns(10);

		JButton button_1 = new JButton("删除所选");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "TEST");
				int var=JOptionPane.showConfirmDialog(null, "确定要删除？");
				if(var==0){removeSelect();}
				else{return;}
			}
		});

		JLabel label_8 = new JLabel("公司名称：");

		companyName = new JTextField();
		companyName.setColumns(10);

		JButton button_2 = new JButton("插入");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertToTable();
			}
		});

		JButton btnNewButton = new JButton("快速定位");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getSearch();
			}
		});

		keyWord = new JTextField();
		keyWord.addFocusListener(new FocusAdapter() {
			//失去焦点，那在失去焦点的时候，将list填充，并选中list中的第一个元素
			@Override
			public void focusLost(FocusEvent arg0) {
				search();
			}
		});
		keyWord.setColumns(10);
		
		specialCom = new JComboBox();

		specialCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					specialChange();
				}
			}
		});
		
		JLabel label_9 = new JLabel("快选：");
		
		JButton priceButton = new JButton("修改单价");
		priceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				priceConfirm();
			}

		});
		
		priceFiled = new JTextField();
		priceFiled.setColumns(10);
		
		priceCom = new JComboBox();
		priceCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					priceChange();
				}
			}
		});

		//JDesktopPane desktopPane = new JDesktopPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1096, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(418, Short.MAX_VALUE)
					.addComponent(priceCom, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(priceFiled, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(priceButton)
					.addGap(18)
					.addComponent(keyWord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(button_1)
					.addGap(18)
					.addComponent(btnexcel)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(484)
							.addComponent(testPic))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_9)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(specialCom, 0, 125, Short.MAX_VALUE)
									.addGap(25)
									.addComponent(label, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(typeCom, 0, 72, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(nameCom, 0, 108, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(6))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(58)
									.addComponent(address1, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
									.addGap(30)
									.addComponent(label_6)
									.addGap(18)
									.addComponent(address2, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_5)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(unitPrice, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(label_2, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(chengbengLab, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(label_4, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(numberText, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(37)
									.addComponent(label_7)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(beizu, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(label_8)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(companyName, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(button_2, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
						.addComponent(button, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
					.addGap(8))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(testPic)
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(chengbengLab)
						.addComponent(label_4)
						.addComponent(numberText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button)
						.addComponent(lblNewLabel)
						.addComponent(unitPrice, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(nameCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(typeCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(label_9)
						.addComponent(specialCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(label_5)
							.addComponent(address1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_6))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(address2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_7)
							.addComponent(beizu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_8)
							.addComponent(companyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(button_2)))
					.addGap(14)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnexcel)
						.addComponent(button_1)
						.addComponent(btnNewButton)
						.addComponent(keyWord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(priceButton)
						.addComponent(priceFiled, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(priceCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5))
		);

		mainTable = new JTable();
		mainTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u7F16\u53F7", "\u4F4D\u7F6E\u4E00", "\u4F4D\u7F6E\u4E8C", "\u690D\u7269\u7C7B\u578B", "\u690D\u7269\u540D\u79F0", "\u6570\u91CF", "\u5355\u4EF7", "\u6210\u672C\u5355\u4EF7", "\u5907\u6CE8"
			}
		));
		scrollPane.setViewportView(mainTable);
		contentPane.setLayout(gl_contentPane);
		this.setLocationRelativeTo(null);
	//	GetDetails dao=new GetDetailsSqlImp();
//		String selectType =  (String) typeCom.getSelectedItem();
//		String selectName =  (String) nameCom.getSelectedItem();
//		Hua model=huaReponsitory.findFirstByHnameAndType(selectName,selectType);
//		unitPrice.setText(String.valueOf(model.getUnitPrice()));
//		chengbengLab.setText(String.valueOf(model.getChengBen()));
	}



	private void specialChange() {
		currentSelectHuaMode=(Hua) specialCom.getSelectedItem();
		win.ccav.model.Type selectType= typeReponsitory.findOne(currentSelectHuaMode.getTypeId());
//		typeCom.setSelectedItem(selectType);
//		nameCom.setSelectedItem(currentSelectHuaMode);
		int flag=0;//更新前的type和更新的type是不是一样，是一样，那就不会触发typeChange事件
		//那就需要手动刷新nameCom的状态
		for(int i=0;i<typeCom.getItemCount();i++){
			if(selectType.equals(typeCom.getItemAt(i))){
				if(typeCom.getItemAt(i).equals(selectType)){
					flag=1;
				}
				typeCom.setSelectedIndex(i);
				typeCom.updateUI();
				break;
			}
		}
		if(flag==1){
			for(int i=0;i<nameCom.getItemCount();i++){
				if(currentSelectHuaMode!=null&&currentSelectHuaMode.equals(nameCom.getItemAt(i))){
					nameCom.setSelectedIndex(i);
					nameCom.updateUI();
					break;
				}
			}
		}
		currentSelectHuaMode.setUnitPrice(prices.get(currentSelectHuaMode.getTypeId()).getUnitPrice());
		unitPrice.setText(String.valueOf(currentSelectHuaMode.getUnitPrice()));
		chengbengLab.setText(String.valueOf(currentSelectHuaMode.getChengBen()));
	}

	private void typeChange(){
		//GetDetails dao=new GetDetailsSqlImp();
		//String selectType =  (String) typeCom.getSelectedItem();
		win.ccav.model.Type selectType=((win.ccav.model.Type)typeCom.getSelectedItem());
		//nameCom.removeAllItems();
		//names=huaReponsitory.findDistinctHuaNameByType(selectType);
		names=huaReponsitory.findAllByTypeId(selectType.getId());
		nameCom.setModel(new DefaultComboBoxModel(names.toArray(new Hua[names.size()])));
//
		for(int i=0;i<nameCom.getItemCount();i++){
			if(currentSelectHuaMode!=null&&currentSelectHuaMode.equals(nameCom.getItemAt(i))){
				nameCom.setSelectedIndex(i);

				nameCom.updateUI();
				break;
			}
		}

//		Hua selectHua=
		currentSelectHuaMode=(Hua)nameCom.getSelectedItem();
		if(currentSelectHuaMode==null){
			//提示
			JOptionPane.showMessageDialog(null, "选择的花木数据有问题，请检测它花木的数据");
			return;
		}
		currentSelectHuaMode.setUnitPrice(prices.get(currentSelectHuaMode.getTypeId()).getUnitPrice());
		unitPrice.setText(String.valueOf(currentSelectHuaMode.getUnitPrice()));
		chengbengLab.setText(String.valueOf(currentSelectHuaMode.getChengBen()));
	}
	private void nameChange(){
		//GetDetails dao=new GetDetailsSqlImp();
//		String selectType =  (String) typeCom.getSelectedItem();
//		String selectName =  (String) nameCom.getSelectedItem();
//		currentSelectHuaMode=huaReponsitory.findFirstByHnameAndType(selectName,selectType);
		currentSelectHuaMode=(Hua) nameCom.getSelectedItem();
		if(currentSelectHuaMode==null){
			//提示
			JOptionPane.showMessageDialog(null, "选择的花木数据有问题，请检测它花木的数据");
		}
		currentSelectHuaMode.setUnitPrice(prices.get(currentSelectHuaMode.getTypeId()).getUnitPrice());
		unitPrice.setText(String.valueOf(currentSelectHuaMode.getUnitPrice()));
		chengbengLab.setText(String.valueOf(currentSelectHuaMode.getChengBen()));
	}
	private void addItems(){
		//这个将一个花木的type:hname作为key将该花木的pic地址放到map里面去
		DefaultTableModel dtm=(DefaultTableModel) mainTable.getModel();
//		bank
//		String selectType =  (String) typeCom.getSelectedItem();
//		String selectName =  (String) nameCom.getSelectedItem();
		String selectType=currentSelectHuaMode.getType();
		String selectName=currentSelectHuaMode.getHname();
		huaMuPicAddress.put(selectName.trim()+":"+selectType.trim(),currentSelectHuaMode.getPic()==null?defaultPicAddress:currentSelectHuaMode.getPic());
		String number=numberText.getText();
		//String chengbeng=chengbengLab.getText();
		String danjia=unitPrice.getText();
		String add1=address1.getText();
		String add2=address2.getText();
		String bz=beizu.getText();
		if(StringUtil.isBlack(danjia)||danjia.equals("0")){
			JOptionPane.showMessageDialog(null, "请选择一种花木");
			return;
		}if(!StringUtil.isDigital(number)){
			JOptionPane.showMessageDialog(null, "数量输入有误，请重新选择");
			return;
		}
		Vector v=new Vector();
		v.add(count++);
		v.add(add1);
		v.add(add2);
		v.add(currentSelectHuaMode.getType());
		v.add(currentSelectHuaMode.getHname());
		v.add(number);
		v.add(currentSelectHuaMode.getUnitPrice());
		v.add(currentSelectHuaMode.getChengBen());
		v.add(bz);
		dtm.addRow(v);
	}
	private void makeExcle(){
		CellEditor ce = mainTable.getCellEditor();
		if (ce != null) {
			mainTable.getCellEditor().stopCellEditing();
		}
		DefaultTableModel dtm=(DefaultTableModel) mainTable.getModel();
		//GetDetails dao=new GetDetailsSqlImp();
		Vector vt=dtm.getDataVector();
		//HuaMuModel model=dao.getDetails(resPath, selectType, selectName);
		List<SheetModel> lists=new ArrayList<SheetModel>();
		for(int i=0;i<vt.size();i++){
			Vector v=(Vector) vt.get(i);
			SheetModel sh=new SheetModel();
			sh.setNum((int)v.get(0));
			sh.setAdd1((String)v.get(1));
			sh.setAdd2((String)v.get(2));
			sh.setSize((String)v.get(3));
			sh.setName((String)v.get(4));
			sh.setAmount(Integer.parseInt(v.get(5).toString()));
			sh.setUnitPrice(Double.parseDouble(v.get(6).toString()));
			sh.setCBUnitPrice(Double.parseDouble(v.get(7).toString()));
			sh.setRemark((String)v.get(8));
			sh.setCBTotalPrice((Integer.parseInt(v.get(5).toString())*(Double.parseDouble(v.get(7).toString()))));
			sh.setTotalPrice((Integer.parseInt(v.get(5).toString())*(Double.parseDouble(v.get(6).toString()))));
			//sh.setPic(dao.getDetails(resPath, sh.getSize(), sh.getName()).getPic());
			sh.setPic(huaMuPicAddress.get(sh.getName().trim()+":"+sh.getSize().trim()));
            lists.add(sh);
		}
		try {
			String title=companyName.getText();
			if(title==null||title.equals("")||title.trim().equals("")){
				title="花木清单";
			}
			JFileChooser chooser=new JFileChooser();
            chooser.setDialogTitle("请选择保存路径");
            SimpleDateFormat fa = new SimpleDateFormat("yyyy-MM-dd");
    	    String dtn = fa.format(new Date());
    	    String fileName=title+dtn+".xls";
            chooser.setSelectedFile(new File(fileName));
			chooser.setCurrentDirectory(new File("D:/data"));
			int option=chooser.showSaveDialog(null);
            File file=null;
			if(option==JFileChooser.APPROVE_OPTION){
				file=chooser.getSelectedFile();
			}else {
                return;
            }
			//lists=sortList(lists);

			SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		    Orders orders=new Orders(title,new Timestamp(System.currentTimeMillis()),userid,BeanCopy.fromListSheetModelToSetOrderDtails(lists) );

            orders.setTitle(title);

            orderReponsitory.save(orders);
//		    int orderid=orderDao.insertToOrder(dt, userid,title);//向订单表写入一条数据，返回的是写入后的订单号
//		    orderDao.insertOrderDetails(lists, orderid);//根据上面的订单号，将订单详情写入到订单详情表里面，这样一来
//		   										//就有一个一对多的关系，就可以拿到一个订单详情

		    try{ToSheet.writeToSheel(lists,file.getAbsolutePath(),title);}
		    catch(Exception e){
		    	 JOptionPane.showMessageDialog(null, "地址不能为单数，请重新选择");
		    	 return;
		    }
		    JOptionPane.showMessageDialog(null, "导出成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private boolean removeSelect(){
		boolean res=false;
		int row=mainTable.getSelectedRow();

		if(row==-1){
			return false;
		}
		DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
		model.removeRow(row);
		res=true;
		return res;
	}
	//将用户选择的list进行排序，将地址一相同的元素放到一起
	private List<SheetModel> sortList(List<SheetModel> lists){
		Collections.sort(lists);
		int i=1;
		for(SheetModel sm:lists){
			sm.setNum(i++);
		}
		return lists;
	}
	//插入一个新数据，思路，将原来的全部数据全部取出来放到ｌｉｓｔ里面，然后在对ｌｉｓｔ插入，最后将ｌｉｓｔ转成ｖｅｃｔｏｒ然后更新ｍｏｄｅｌ

	private void insertToTable(){
		DefaultTableModel dtm=(DefaultTableModel) mainTable.getModel();
		int row=mainTable.getSelectedRow();
		//GetDetails dao=new GetDetailsSqlImp();
		Vector vt=dtm.getDataVector();
		//HuaMuModel model=dao.getDetails(resPath, selectType, selectName);
		List<SheetModel> lists=new ArrayList<SheetModel>();
		for(int i=0;i<vt.size();i++){
			Vector v=(Vector) vt.get(i);
			SheetModel sh=new SheetModel();
			sh.setNum((int)v.get(0));
			sh.setAdd1((String)v.get(1));
			sh.setAdd2((String)v.get(2));
			sh.setSize((String)v.get(3));
			sh.setName((String)v.get(4));
			sh.setAmount(Integer.parseInt(v.get(5).toString()));
			sh.setUnitPrice(Double.parseDouble(v.get(6).toString()));
			sh.setCBUnitPrice(Double.parseDouble(v.get(7).toString()));
			sh.setRemark(v.get(8).toString());
			lists.add(sh);
		}
		SheetModel sh=getHua();
		//System.out.println(row+"mmmmmmm");
		if(sh==null){
			return;
		}
		if(row<0||row>lists.size()){
			JOptionPane.showMessageDialog(null, "请选添加的位置");
			return;
		}
		lists.add(row, sh);
		int i=1;
		dtm.setRowCount(0);
		for(SheetModel model:lists){
			Vector v=new Vector();
			v.add(i++);
			v.add(model.getAdd1());
			v.add(model.getAdd2());
			v.add(model.getSize());
			v.add(model.getName());
			v.add(model.getAmount());
			v.add(model.getUnitPrice());
			v.add(model.getCBUnitPrice());
			v.add(model.getRemark());
			dtm.addRow(v);
		}
	}


	//将用户添加的单个花收集起来
	private SheetModel getHua(){
		String selectType =  (String) typeCom.getSelectedItem();
		String selectName =  (String) nameCom.getSelectedItem();
		String number=numberText.getText();
		String chengbeng=chengbengLab.getText();
		String danjia=unitPrice.getText();
		String add1=address1.getText();
		String add2=address2.getText();
		String bz=beizu.getText();
		if(StringUtil.isEmpty(danjia)||danjia.equals("0")){
			JOptionPane.showMessageDialog(null, "请选择一种花木");
			return null;
		}if(!StringUtil.isDigital(number)){
			JOptionPane.showMessageDialog(null, "数量输入有误，请重新选择");
			return null;
		}
		SheetModel sh=new SheetModel();
		sh.setNum(0);
		sh.setAdd1(add1);
		sh.setAdd2(add2);
		sh.setSize(selectType);
		sh.setName(selectName);
		sh.setAmount(Integer.parseInt(number));
		sh.setUnitPrice(Double.parseDouble((danjia)));
		sh.setCBUnitPrice(Double.parseDouble(chengbeng));
		sh.setRemark(bz);
		return sh;
	}
	List<Integer> searchResult=new ArrayList<Integer>();
	int searchIndex=0;
	private JTextField priceFiled;
	private void search(){
		searchResult.clear();
		int row=mainTable.getSelectedRow();
		//mainTable.setSelectionMode(row+1);
		ListSelectionModel model = mainTable.getSelectionModel();
		List<SheetModel> items=getItems();
		int i=0;
		for(SheetModel sh:items){
			if(sh.toString().contains(keyWord.getText())){
				searchResult.add(i);
				i++;
				continue;
			}
			i++;
		}
		return;
	}
	private void getSearch(){
		//System.out.println(searchResult);
		if(searchResult.size()==0){
			JOptionPane.showMessageDialog(null, "没有对应项，请从新查询");
			return;
		}
		if(searchIndex>=searchResult.size()){
			searchIndex=0;
		}
		ListSelectionModel model = mainTable.getSelectionModel();
		model.setSelectionInterval(searchResult.get(searchIndex), searchResult.get(searchIndex));
		searchIndex++;
	}
	private List<SheetModel> getItems(){
		DefaultTableModel dtm=(DefaultTableModel) mainTable.getModel();
		Vector vt=dtm.getDataVector();
		//HuaMuModel model=dao.getDetails(resPath, selectType, selectName);
		List<SheetModel> lists=new ArrayList<SheetModel>();
		for(int i=0;i<vt.size();i++){
			Vector v=(Vector) vt.get(i);
			SheetModel sh=new SheetModel();
			sh.setNum((int)v.get(0));
			sh.setAdd1((String)v.get(1));
			sh.setAdd2((String)v.get(2));
			sh.setSize((String)v.get(3));
			sh.setName((String)v.get(4));
			sh.setAmount(Integer.parseInt(v.get(5).toString()));
			sh.setUnitPrice(Double.parseDouble(v.get(6).toString()));
			sh.setCBUnitPrice(Double.parseDouble(v.get(7).toString()));
			sh.setRemark(v.get(8).toString());
			lists.add(sh);
		}
		return lists;
	}
	
	private void priceChange() {
		win.ccav.model.Type selectType= (win.ccav.model.Type) priceCom.getSelectedItem();
		priceFiled.setText(prices.get(selectType.getId()).getUnitPrice().toString());
//		prices.put(selectType.getId(),);
	}

	private void priceConfirm() {
		if(!StringUtil.isDouble(priceFiled.getText())){
			JOptionPane.showMessageDialog(null, "请输入正确的数字");
			return;
		}
		win.ccav.model.Type selectType= (win.ccav.model.Type) priceCom.getSelectedItem();
		prices.get(selectType.getId()).setUnitPrice(new Float(priceFiled.getText()));
	}
	protected void updateSpecialCom(){
		specialHuas=huaReponsitory.findAllBySpecial(1);
		specialCom.setModel(new DefaultComboBoxModel(specialHuas.toArray(new Hua[specialHuas.size()])));
	}
}

