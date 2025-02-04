package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import BUS.BudgetsBUS;
import BUS.CategoríeBUS;
import BUS.UsersBus;
import MODEL.Budgets;
import MODEL.Categories;
import MODEL.Transactions;
import MODEL.Users;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FmBudgets extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAmount;
	private JTextField txtSDate;
	private JTextField txtEDate;
	private JTable table;
	/////////////////////
	private JComboBox cbName;
	private CategoríeBUS bus = new CategoríeBUS();
	private ArrayList<Categories> dsCategory = new ArrayList<Categories>();
	private BudgetsBUS bdbus = new BudgetsBUS();
	private ArrayList<Budgets> dsBudgets = new ArrayList<Budgets>();
	private int index = -1;
	private static int currentUserID;
	private static int currentCategories;

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int currentUserID) {
		FmBudgets.currentUserID = currentUserID;
	}

	public static int getCurrentCategories() {
		return currentCategories;
	}

	public static void setCurrentCategories(int currentCategories) {
		FmBudgets.currentCategories = currentCategories;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FmBudgets frame = new FmBudgets();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FmBudgets() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 728, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBudgets = new JLabel("BUDGETS");
		lblBudgets.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblBudgets.setBounds(286, 48, 146, 34);
		contentPane.add(lblBudgets);

		JLabel lblNewLabel = new JLabel("CategoryName");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(24, 111, 146, 25);
		contentPane.add(lblNewLabel);

		cbName = new JComboBox();
		cbName.setBounds(190, 116, 328, 27);
		contentPane.add(cbName);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAmount.setBounds(24, 159, 69, 25);
		contentPane.add(lblAmount);

		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		txtAmount.setBounds(190, 165, 328, 26);
		contentPane.add(txtAmount);

		JLabel lblStartdate = new JLabel("Start_date");
		lblStartdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblStartdate.setBounds(24, 208, 115, 25);
		contentPane.add(lblStartdate);

		txtSDate = new JTextField();
		txtSDate.setColumns(10);
		txtSDate.setBounds(190, 209, 328, 26);
		contentPane.add(txtSDate);

		JLabel lblEnddate = new JLabel("End_date");
		lblEnddate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnddate.setBounds(24, 254, 115, 25);
		contentPane.add(lblEnddate);

		txtEDate = new JTextField();
		txtEDate.setColumns(10);
		txtEDate.setBounds(190, 253, 328, 26);
		contentPane.add(txtEDate);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				int amount = Integer.parseInt(txtAmount.getText());
				java.sql.Date sdate = java.sql.Date.valueOf(txtSDate.getText());
				java.sql.Date edate = java.sql.Date.valueOf(txtEDate.getText());
				int cate_id = dsCategory.get(cbName.getSelectedIndex() - 1).getCategory_id();
				Budgets bud = new Budgets(amount, sdate, edate, cate_id, user_id);
				bdbus.insertBudget(bud, user_id);
				loadDataTable();
				initData();
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAdd.setBounds(565, 98, 111, 46);
		contentPane.add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Budgets");
					return;
				}
				Budgets bud = dsBudgets.get(index);
				bdbus.deleteBudgets(bud.getBudget_id(), user_id);
				loadDataTable();
				initData();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.setBounds(565, 174, 111, 45);
		contentPane.add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Budgets");
					return;
				}
				int budget_id = dsBudgets.get(index).getBudget_id();
				int amount = Integer.parseInt(txtAmount.getText());
				Date sdate;
				Date edate;
				try {
					sdate = new SimpleDateFormat("yyyy-MM-dd").parse(txtSDate.getText());
					edate = new SimpleDateFormat("yyyy-MM-dd").parse(txtEDate.getText());
				} catch (ParseException ex) {
					ex.printStackTrace();
					return;
				}
				int user_id = getCurrentUserID();
				int cate_id = dsCategory.get(cbName.getSelectedIndex() - 1).getCategory_id();
				Budgets bud = new Budgets(budget_id, amount, sdate, edate, cate_id, user_id);
				bdbus.updateBudgets(user_id, bud);
				loadDataTable();
				initData();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUpdate.setBounds(565, 252, 111, 46);
		contentPane.add(btnUpdate);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 324, 659, 170);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				Budgets bud = dsBudgets.get(index);
				txtAmount.setText(String.valueOf(bud.getAmount()));
				txtSDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(bud.getStart_date()));
				txtEDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(bud.getEnd_date()));
				int cateid = bud.getCategory_id();
				for (int i = 0; i < dsCategory.size(); i++)
					if (dsCategory.get(i).getCategory_id() == cateid) {
						cbName.setSelectedIndex(i + 1);
						break;
					}
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Category Name", "Amount", "Ngày bắt đầu", "Ngày kết thúc" }));
		scrollPane.setViewportView(table);

		JMenuItem mntmNewMenuItem = new JMenuItem("Home");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				main.setVisible(true);
				dispose();
			}
		});
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmNewMenuItem.setBounds(22, 10, 135, 26);
		contentPane.add(mntmNewMenuItem);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		dsCategory = bus.getCategoriesByUser(getCurrentUserID());
		loadDataCBCate();
		dsBudgets = bdbus.getAllBudgets(getCurrentUserID());
		loadDataTable();
	}

	protected void loadDataTable() {
		// TODO Auto-generated method stub
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (Budgets tr : dsBudgets)
			tableModel.addRow(new Object[] { getNameCa(tr.getCategory_id()), tr.getAmount(), tr.getStart_date(),
					tr.getEnd_date() });
		table.setModel(tableModel);
	}

	public String getNameCa(int id) {
		for (Categories cate : dsCategory)
			if (cate.getCategory_id() == id)
				return cate.getName();
		return "";
	}

	private void loadDataCBCate() {
		// TODO Auto-generated method stub
		DefaultComboBoxModel boxModel = new DefaultComboBoxModel<String>();
		boxModel.addElement("None");
		for (Categories cate : dsCategory)
			boxModel.addElement(cate.getName());
		cbName.setModel(boxModel);
	}
}
