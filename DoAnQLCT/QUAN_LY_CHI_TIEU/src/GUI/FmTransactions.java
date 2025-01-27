package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import BUS.CategoríeBUS;
import BUS.TransactionsBUS;
import BUS.UsersBus;
import MODEL.Categories;
import MODEL.Transactions;
import MODEL.Users;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JMenuItem;

public class FmTransactions extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDate;
	private JTextField txtAmount;
	private JTextField txtDescription;
	private JTable table;
	private JComboBox cbName;
	/////////////////////////
	private CategoríeBUS bus = new CategoríeBUS();
	private ArrayList<Categories> dsCategory = new ArrayList<Categories>();
	private TransactionsBUS trbus = new TransactionsBUS();
	private ArrayList<Transactions> dsTransactions = new ArrayList<Transactions>();
	private int index = -1;
	private static int currentUserID;
	private static int currentCategories;

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int currentUserID) {
		FmTransactions.currentUserID = currentUserID;
	}

	public static int getCurrentCategories() {
		return currentCategories;
	}

	public static void setCurrentCategories(int currentCategories) {
		FmTransactions.currentCategories = currentCategories;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FmTransactions frame = new FmTransactions();
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
	public FmTransactions() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("TRANSACTIONS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(314, 62, 211, 29);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1_1 = new JLabel("Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(60, 128, 88, 25);
		contentPane.add(lblNewLabel_1_1);

		cbName = new JComboBox();

		cbName.setBounds(192, 128, 355, 25);
		contentPane.add(cbName);

		JLabel lblNewLabel_1_2 = new JLabel("Date");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(60, 177, 111, 25);
		contentPane.add(lblNewLabel_1_2);

		txtDate = new JTextField();
		txtDate.setColumns(10);
		txtDate.setBounds(192, 178, 355, 31);
		contentPane.add(txtDate);

		JLabel lblNewLabel_1_3 = new JLabel("Amount");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(60, 228, 88, 25);
		contentPane.add(lblNewLabel_1_3);

		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		txtAmount.setBounds(192, 229, 355, 31);
		contentPane.add(txtAmount);

		JLabel lblNewLabel_1_3_1 = new JLabel("Description");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_3_1.setBounds(60, 281, 114, 25);
		contentPane.add(lblNewLabel_1_3_1);

		txtDescription = new JTextField();
		txtDescription.setColumns(10);
		txtDescription.setBounds(192, 281, 355, 73);
		contentPane.add(txtDescription);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				int amount = Integer.parseInt(txtAmount.getText());
				java.sql.Date date = java.sql.Date.valueOf(txtDate.getText());
				String des = txtDescription.getText();
				int cate_id = dsCategory.get(cbName.getSelectedIndex() - 1).getCategory_id();
				Transactions tr = new Transactions(amount, date, des, cate_id, user_id);
				trbus.insertTransactions(tr, user_id);
				loadDataTable();
				initData();
				trbus.checkBudgetAndAlert(user_id, cate_id, amount, date);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAdd.setBounds(605, 117, 111, 57);
		contentPane.add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id=getCurrentUserID();
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Transactions");
					return;
				}
				Transactions tr = dsTransactions.get(index);
				trbus.deleteTransactions(tr.getTransaction_id(),user_id);
				loadDataTable();
				initData();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.setBounds(605, 198, 111, 57);
		contentPane.add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Transactions");
					return;
				}
				int transaction_id = dsTransactions.get(index).getTransaction_id();
				int amount = Integer.parseInt(txtAmount.getText());
				java.sql.Date date = java.sql.Date.valueOf(txtDate.getText());
				String des = txtDescription.getText();
				int cate_id = dsCategory.get(cbName.getSelectedIndex() - 1).getCategory_id();
				Transactions tr = new Transactions(transaction_id, amount, date, des, cate_id, user_id);
				trbus.updateTransactions(user_id, tr);
				dsTransactions.set(index, tr);
				loadDataTable();
				initData();
				trbus.checkBudgetAndAlert(user_id, cate_id, amount, date);

			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUpdate.setBounds(605, 285, 111, 57);
		contentPane.add(btnUpdate);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 394, 675, 169);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				if (index >= 0 && index < dsTransactions.size()) {
					Transactions tr = dsTransactions.get(index);
					txtAmount.setText(String.valueOf(tr.getAmount()));
					txtDescription.setText(tr.getDescription());
					txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(tr.getDate()));
					int cateid = tr.getCategory_id();
					for (int i = 0; i < dsCategory.size(); i++)
						if (dsCategory.get(i).getCategory_id() == cateid) {
							cbName.setSelectedIndex(i + 1);
							break;
						}
				}

			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Tên danh mục", "Ngày", "Amount", "Description" }));
		scrollPane.setViewportView(table);

		JMenuItem mntmNewMenuItem = new JMenuItem("Home");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				main.setVisible(true);
				dispose();
			}
		});
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem.setBounds(10, 10, 135, 26);
		contentPane.add(mntmNewMenuItem);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		dsCategory = bus.getCategoriesByUser(getCurrentUserID());
		loadDataCBCate();
		dsTransactions = trbus.getAllTransactions(getCurrentUserID());
		loadDataTable();
	}

	protected void loadDataTable() {
		// TODO Auto-generated method stub
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (Transactions tr : dsTransactions)
			tableModel.addRow(
					new Object[] { getNameCa(tr.getCategory_id()), tr.getDate(), tr.getAmount(), tr.getDescription() });
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
