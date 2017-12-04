package win.ccav.ui;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import win.ccav.dao.HuaReponsitory;
import win.ccav.dao.OrderReponsitory;
import win.ccav.dao.TypeReponsitory;
import win.ccav.model.Hua;
import win.ccav.model.OrderDetails;
import win.ccav.model.Orders;
import win.ccav.model.SheetModel;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class OrderManage extends JFrame {

	private JPanel contentPane;
	private JTextField companyName;
	private JComboBox<String> searchYear,searchMonth,searchDate;

	private JTable orderTable;
	private JTable orderDetailsTable;
	private JLabel companyNameLable,orderDateLable,orderIdLable;
	private JTextField amountText;
	private JComboBox typeCom,nameCom;
	private List<win.ccav.model.Type> types;
	private List<Hua> names;
	private int length;
	private JTextField keyWord;

	@Autowired
	private HuaReponsitory huaReponsitory;
	@Autowired
	private TypeReponsitory typeReponsitory;

	@Autowired
	private OrderReponsitory orderReponsitory;

	private Map<String,String> picAddress=new HashMap<>();

	private String defaultPicAddress;

	@Autowired
	private org.springframework.core.env.Environment env;

	/**
	 * Launch the application.
	 */
	public void init(){
		types=typeReponsitory.findAll();
		typeCom.setModel(new DefaultComboBoxModel(types.toArray(new win.ccav.model.Type[types.size()])));
		names=huaReponsitory.findAllByTypeId(((win.ccav.model.Type)typeCom.getSelectedItem()).getId());
		nameCom.setModel(new DefaultComboBoxModel(names.toArray(new Hua[names.size()])));
		defaultPicAddress=env.getProperty("pic");
		initTtale();
	}


	/**
	 * Create the frame.
	 */
	public OrderManage() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("订单管理---成都一丁花卉租赁有限公司-v1.0");
		//字体
		Font font = new Font("微软雅黑",Font.PLAIN,13);
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}

		//字体

		setBounds(100, 100, 893, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.add(desktopPane, BorderLayout.CENTER);

		JLabel label = new JLabel("公司名称：");

		companyName = new JTextField();
		companyName.setColumns(10);

		JButton button = new JButton("查找");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchCompanyName();
			}
		});


		searchYear=new JComboBox<String>();
		searchYear.setBackground(Color.white);
		searchYear.setForeground(new Color(62,62,64));
		searchYear.setFont(new Font("微软雅黑",Font.PLAIN,13));
		searchYear.addItem("未选年");
		for(int i=2010;i<=(new Date().getYear()+1900);i++)
			searchYear.addItem(""+i);

		searchMonth=new JComboBox<String>();
		searchMonth.setBackground(Color.white);
		searchMonth.setForeground(new Color(62,62,64));
		searchMonth.addItem("未选月");
		searchMonth.setFont(new Font("微软雅黑",Font.PLAIN,13));
		for(int i=1;i<=12;i++)
			searchMonth.addItem(""+i);

		searchDate=new JComboBox<String>();
		searchDate.addItem("未选日");
		searchDate.setBackground(Color.white);
		searchDate.setForeground(new Color(62,62,64));
		searchDate.setFont(new Font("微软雅黑",Font.PLAIN,13));
		for(int i=1;i<=31;i++){
			if(i<10){
				searchDate.addItem("0"+i);
			}else{
				searchDate.addItem(""+i);
			}

		}



		JButton button_1 = new JButton("查看某日");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchByDay();
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();

		JLabel label_1 = new JLabel("订单详情：");

		JButton button_2 = new JButton("删除所选");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSelect();
			}
		});

		JButton button_3 = new JButton("添加花木");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addItems();
			}
		});

		JButton btnexcel = new JButton("更新并导出");
		btnexcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateOrders();
//				List<SheetModel> l=getItems();
//				for(SheetModel model:l){
//					System.out.println(model.getName());
//				}
			}
		});

		companyNameLable = new JLabel("");

		orderDateLable = new JLabel("");

		JLabel label_2 = new JLabel("类型：");

		typeCom = new JComboBox();
		typeCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				typeChange();
			}
		});
		//typeCom.setModel(new DefaultComboBoxModel(types));

		JLabel label_3 = new JLabel("花名");

		nameCom = new JComboBox();
		nameCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//nameChange();
				//TODO why
			}
		});
		//nameCom.setModel(new DefaultComboBoxModel(names));
		JLabel label_4 = new JLabel("数量：");

		amountText = new JTextField();
		amountText.setColumns(10);

		orderIdLable = new JLabel("");

		keyWord = new JTextField();
		keyWord.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				search();
			}
		});
		keyWord.setColumns(10);

		JButton btnNewButton = new JButton("快速定位");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getSearch();
			}
		});

		JButton button_4 = new JButton("插入");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertToTable();
			}
		});

		GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
		gl_desktopPane.setHorizontalGroup(
			gl_desktopPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_desktopPane.createSequentialGroup()
							.addGap(100)
							.addComponent(label, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(companyName, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
							.addGap(23)
							.addComponent(button, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
							.addGap(49)
							.addComponent(searchYear, 0, 72, Short.MAX_VALUE)
							.addGap(23)
							.addComponent(searchMonth, 0, 61, Short.MAX_VALUE)
							.addGap(23)
							.addComponent(searchDate, 0, 60, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(button_1, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
							.addGap(15))
						.addGroup(Alignment.LEADING, gl_desktopPane.createSequentialGroup()
							.addGap(66)
							.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
								.addGroup(gl_desktopPane.createSequentialGroup()
									.addComponent(label_1)
									.addGap(18)
									.addComponent(orderIdLable)
									.addGap(49)
									.addComponent(companyNameLable)
									.addGap(63)
									.addComponent(orderDateLable))
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
								.addGroup(gl_desktopPane.createSequentialGroup()
									.addComponent(label_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(typeCom, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(label_3)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(nameCom, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(label_4)
									.addGap(24)
									.addComponent(amountText, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(button_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(button_3, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
										.addComponent(button_2)
										.addComponent(keyWord, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnexcel))))))
					.addGap(94))
		);
		gl_desktopPane.setVerticalGroup(
			gl_desktopPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addGap(4)
							.addComponent(label))
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addGap(1)
							.addComponent(companyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(button)
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addGap(1)
							.addComponent(searchYear, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addGap(1)
							.addComponent(searchMonth, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(searchDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_1))))
					.addGap(9)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(orderDateLable)
						.addComponent(companyNameLable)
						.addComponent(orderIdLable))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(typeCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(btnexcel)
						.addComponent(button_2)
						.addComponent(button_3)
						.addComponent(amountText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4)
						.addComponent(nameCom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_4)
						.addComponent(btnNewButton)
						.addComponent(keyWord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);

		orderDetailsTable = new JTable();
		orderDetailsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u7F16\u53F7", "\u4F4D\u7F6E\u4E00", "\u4F4D\u7F6E\u4E8C", "\u690D\u7269\u7C7B\u578B", "\u690D\u7269\u540D\u79F0", "\u6570\u91CF", "\u5355\u4EF7", "\u6210\u672C\u5355\u4EF7", "\u5907\u6CE8"
			}
		));
		scrollPane_1.setViewportView(orderDetailsTable);

		orderTable = new JTable();
		orderTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				tableKeyPressed();
			}
		});

		orderTable.setFont(new Font("微软雅黑",Font.PLAIN,13));
		orderTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u8BA2\u5355\u7F16\u53F7", "\u516C\u53F8\u540D\u79F0", "\u8BA2\u5355\u65E5\u671F", "\u64CD\u4F5C\u7528\u6237"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(orderTable);
		desktopPane.setLayout(gl_desktopPane);
		//默认添加全部数据
	}
	private void searchByDay() {
		// TODO Auto-generated method stub
		String selectYear=searchYear.getSelectedItem().toString();
		String selectMonth=searchMonth.getSelectedItem().toString();
		String selectDay=searchDate.getSelectedItem().toString();
		if(selectYear.equals("未选年")||selectMonth.equals("未选月")||selectDay.equals("未选日")){
			JOptionPane.showMessageDialog(null, "请选择日期");
			return;
		}
		String day=selectYear+"-"+selectMonth+"-"+selectDay;
		List<Orders> orders=orderReponsitory.findSomedayOrders(day);
		DefaultTableModel dtm=(DefaultTableModel)orderTable.getModel();
		dtm.setRowCount(0);
		for(Orders order:orders){
			Vector v=new Vector();
			v.add(order.getOrderId());
			v.add(order.getTitle());
			v.add(order.getOrderDate());
			v.add(order.getUserName());
			dtm.addRow(v);
		}
		//System.out.println(selectYear);
	}

	//按公司名称进行
	private void searchCompanyName(){
		String company=companyName.getText();
		List<Orders> orders=orderReponsitory.findAllByTitleLike("%"+company+"%");
		DefaultTableModel dtm=(DefaultTableModel)orderTable.getModel();
		dtm.setRowCount(0);
		for(Orders order:orders){
			Vector v=new Vector();
			v.add(order.getOrderId());
			v.add(order.getTitle());
			v.add(order.getOrderDate());
			v.add(order.getUserName());
			dtm.addRow(v);
		}
	}
	//默认添加全部数据
	private void initTtale(){
		List<Orders> orders=orderReponsitory.findAll();
		DefaultTableModel dtm=(DefaultTableModel)orderTable.getModel();
		dtm.setRowCount(0);
		for(Orders order:orders){
			Vector v=new Vector();
			v.add(order.getOrderId());
			v.add(order.getTitle());
			v.add(order.getOrderDate());
			v.add(order.getUserName());
			dtm.addRow(v);
		}
	}
	private void tableKeyPressed(){
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int rowNum=orderTable.getSelectedRow();
		//String orderIdStr=(String) orderTable.getValueAt(rowNum, 0);
		int orderId=(int) orderTable.getValueAt(rowNum, 0);
		companyNameLable.setText((String)orderTable.getValueAt(rowNum, 1));
		orderDateLable.setText(sdf.format(orderTable.getValueAt(rowNum, 2)));
		orderIdLable.setText(""+orderId);
		Set<OrderDetails> orderDetails=orderReponsitory.findOne(orderId).getItems();
		DefaultTableModel dtm=(DefaultTableModel)orderDetailsTable.getModel();
		length=dtm.getRowCount();
		dtm.setRowCount(0);
		//存图片地址
		for(OrderDetails order:orderDetails){
			String size=order.getSize();
			String name=order.getHname();
			picAddress.put(size.trim()+name.trim(),order.getPic()==null?defaultPicAddress:order.getPic());
			Vector v=new Vector();
			v.add(order.getNum());
			v.add(order.getAdd1());
			v.add(order.getAdd2());
			v.add(order.getSize());
			v.add(order.getHname());
			v.add(order.getAmount());
			v.add(order.getUnitPrice());
			v.add(order.getUnitPrice());
			v.add(order.getBeizu());
			dtm.addRow(v);
		}
	}

	private void typeChange(){

		win.ccav.model.Type selectType= (win.ccav.model.Type) typeCom.getSelectedItem();
		//nameCom.removeAllItems();
		names=huaReponsitory.findAllByTypeId(selectType.getId());
		nameCom.setModel(new DefaultComboBoxModel(names.toArray(new Hua[names.size()])));
//		String selectName =  (String) nameCom.getSelectedItem();
//		HuaMuModel model=dao.getDetails("test", selectType, selectName);
//		unitPrice.setText(String.valueOf(model.getUnitPrice()));
//		chengbengLab.setText(String.valueOf(model.getChengbeng()));
//		picadd=model.getPic();

	}
	private void nameChange(){

		win.ccav.model.Type selectType= (win.ccav.model.Type) typeCom.getSelectedItem();
		Hua selectName= (Hua) nameCom.getSelectedItem();
//		HuaMuModel model=dao.getDetails("test", selectType, selectName);
//		unitPrice.setText(String.valueOf(model.getUnitPrice()));
//		chengbengLab.setText(String.valueOf(model.getChengbeng()));
//		picadd=model.getPic();
//		if(model.getPic()!=null){
//			testPic.setIcon(new ImageIcon(model.getPic()));
//		}
	}
	private void addItems(){

//		GetDetails dao=new GetDetailsSqlImp();
		DefaultTableModel dtm=(DefaultTableModel) orderDetailsTable.getModel();
		int count=0;
		win.ccav.model.Type selectType =  (win.ccav.model.Type ) typeCom.getSelectedItem();
		Hua selectName =  (Hua) nameCom.getSelectedItem();
		if(dtm.getRowCount()==0){
			return;
		}
		count=(int) dtm.getValueAt(dtm.getRowCount()-1, 0)+1;
		String numberStr=amountText.getText();
		if(!StringUtil.isDigital(numberStr)){
			JOptionPane.showMessageDialog(null, "数量输入有误，请重新选择");
			return;
		}
		Hua hua=selectName;
		Vector v=new Vector();
		v.add(count);
		v.add("请输入位置1");
		v.add("请输入位置2");
		v.add(selectType.getTypeName());
		v.add(selectName.getHname());
		v.add(Integer.parseInt(numberStr));
		v.add(hua.getUnitPrice());
		v.add(hua.getChengBen());
		v.add("");
		dtm.addRow(v);
	}
	private boolean removeSelect(){
		boolean res=false;
		int row=orderDetailsTable.getSelectedRow();
		if(row==-1){
			JOptionPane.showMessageDialog(null, "请选择要删除的行");
		}
		if(row==-1){
			return false;
		}
		DefaultTableModel model = (DefaultTableModel) orderDetailsTable.getModel();
		model.removeRow(row);
		res=true;
		return res;
	}
	//第一步，根据订单号删除该订单的订单详情，第二步更新订单表中的时间，
	// 第三部添加订单，获取到醒的订单号，第四部，写入订单详情，第五步，生成Excel
	private void updateOrders(){
		List<SheetModel> lists=getItems();
		Set<OrderDetails> orderDetails= BeanCopy.fromListSheetModelToSetOrderDtails(lists);
		if(orderIdLable.getText().equals("")||orderIdLable.getText().trim().equals("")){
			return;
		}
		int orderId=Integer.parseInt(orderIdLable.getText());
		Orders order=orderReponsitory.findOne(orderId);
		order.setOrderDate(new Timestamp(System.currentTimeMillis()));
		order.setItems(orderDetails);
		orderReponsitory.save(order);
		try{wirteToExcel(lists,companyNameLable.getText());}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "地址不能为单数，请重新选择");
			return;
		}
		JOptionPane.showMessageDialog(null, "修改成功");
////
//		else{
//			JOptionPane.showMessageDialog(null, "修改失败，请重试");
//		}
	}
	//编写一个方法，来将所有orderDetails中的数据封装成一个list来返回，并在上面调用


	private List<SheetModel> getItems(){

		//强制更新table的数据
		CellEditor ce = orderDetailsTable.getCellEditor();
		if (ce != null) {
			orderDetailsTable.getCellEditor().stopCellEditing();
		}
		List<SheetModel> orders=new ArrayList<SheetModel>();
		DefaultTableModel dtm=(DefaultTableModel)orderDetailsTable.getModel();

		Vector vt=dtm.getDataVector();

		for(int i=0;i<vt.size();i++){
			SheetModel model=new SheetModel();
			Vector v=(Vector) vt.get(i);
			model.setNum((int) v.get(0));
			model.setAdd1((String)v.get(1));
			model.setAdd2((String)v.get(2));
			model.setSize((String) v.get(3));
			model.setName((String) v.get(4));
			model.setAmount(Integer.parseInt((v.get(5).toString())));
			model.setUnitPrice(Double.parseDouble(v.get(6).toString()));
			model.setCBUnitPrice(Double.parseDouble(v.get(7).toString()));
			model.setRemark((String) v.get(8));
			model.setTotalPrice(model.getAmount()*model.getUnitPrice());
			model.setCBTotalPrice(model.getAmount()+model.getCBUnitPrice());
			model.setPic(picAddress.get(model.getSize().trim()+model.getName().trim()));
			orders.add(model);
		}
		return orders;
	}

	private List<SheetModel> sortList(List<SheetModel> lists){
		Collections.sort(lists);
		int i=1;
		for(SheetModel sm:lists){
			sm.setNum(i++);
		}
		return lists;
	}
	private void wirteToExcel(List<SheetModel> lists,String title){
		try {

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
			lists=sortList(lists);
			//就有一个一对多的关系，就可以拿到一个订单详情
			ToSheet.writeToSheel(lists,file.getAbsolutePath(),title);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void insertToTable(){
		CellEditor ce = orderDetailsTable.getCellEditor();
		if (ce != null) {
			orderDetailsTable.getCellEditor().stopCellEditing();
		}
		DefaultTableModel dtm=(DefaultTableModel) orderDetailsTable.getModel();
		int row=orderDetailsTable.getSelectedRow();

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
	private SheetModel getHua(){
		CellEditor ce = orderDetailsTable.getCellEditor();
		if (ce != null) {
			orderDetailsTable.getCellEditor().stopCellEditing();
		}

		DefaultTableModel dtm=(DefaultTableModel) orderDetailsTable.getModel();
		int count=0;
		Hua selectName= (Hua) nameCom.getSelectedItem();
		if(dtm.getRowCount()==0){
			return null;
		}
		count=(int) dtm.getValueAt(dtm.getRowCount()-1, 0)+1;
		String numberStr=amountText.getText();
		if(!StringUtil.isDigital(numberStr)){
			JOptionPane.showMessageDialog(null, "数量输入有误，请重新选择");
			return null ;
		}
		SheetModel sh=new SheetModel();
		sh.setNum(count);
		sh.setAdd1("请输入位置1");
		sh.setAdd2("请输入位置2");
		sh.setSize(selectName.getType());
		sh.setName(selectName.getHname());
		sh.setAmount(Integer.parseInt(numberStr));
		sh.setUnitPrice(selectName.getUnitPrice());
		sh.setCBUnitPrice(selectName.getChengBen());
		sh.setRemark("");
		return sh;
	}
	List<Integer> searchResult=new ArrayList<Integer>();
	int searchIndex=0;
	private void search(){
		searchResult.clear();
		int row=orderDetailsTable.getSelectedRow();
		//mainTable.setSelectionMode(row+1);
		ListSelectionModel model = orderDetailsTable.getSelectionModel();
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
		CellEditor ce = orderDetailsTable.getCellEditor();
		if (ce != null) {
			orderDetailsTable.getCellEditor().stopCellEditing();
		}
		//System.out.println(searchResult);
		if(searchResult.size()==0){
			JOptionPane.showMessageDialog(null, "没有对应项，请从新查询");
			return;
		}
		if(searchIndex>=searchResult.size()){
			searchIndex=0;
		}
		ListSelectionModel model = orderDetailsTable.getSelectionModel();
		model.setSelectionInterval(searchResult.get(searchIndex), searchResult.get(searchIndex));
		searchIndex++;
	}

}
