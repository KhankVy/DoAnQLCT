package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.CategoríeBUS;
import MODEL.Categories;
import MODEL.Users;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;

public class FmCategories extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	///////////////////
	private CategoríeBUS bus = new CategoríeBUS();
	private ArrayList<Categories> dsCategory = new ArrayList<Categories>();
	private ArrayList<Users> dsUsers = new ArrayList<Users>();
	private int index = -1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static int currentUserID;
	private static int currentCategories;
	private JTable table;

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int userID) {
		currentUserID = userID;
	}

	public static int getCurrentCategories() {
		return currentCategories;
	}

	public static void setCurrentCategories(int categories) {
		currentCategories = categories;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Categories frame = new Categories();
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
	public FmCategories() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCategories = new JLabel("CATEGORIES");
		lblCategories.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCategories.setBounds(206, 46, 170, 25);
		contentPane.add(lblCategories);

		JLabel lblTnDanhMc = new JLabel("Tên danh mục");
		lblTnDanhMc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTnDanhMc.setBounds(22, 88, 134, 25);
		contentPane.add(lblTnDanhMc);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(181, 90, 298, 26);
		contentPane.add(txtName);

		JLabel lblTyoe = new JLabel("Loại danh mục");
		lblTyoe.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTyoe.setBounds(22, 137, 142, 25);
		contentPane.add(lblTyoe);

		JRadioButton rdbCTieu = new JRadioButton("Chi Tiêu");
		buttonGroup.add(rdbCTieu);
		rdbCTieu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbCTieu.setBounds(181, 141, 103, 21);
		contentPane.add(rdbCTieu);

		JRadioButton rdbtnThuNhp = new JRadioButton("Thu Nhập");
		buttonGroup.add(rdbtnThuNhp);
		rdbtnThuNhp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnThuNhp.setBounds(358, 141, 121, 21);
		contentPane.add(rdbtnThuNhp);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				String name = txtName.getText();
				boolean type;
				if (rdbCTieu.isSelected())
					type = true;
				else
					type = false;
				Categories ca = new Categories(name, type, user_id);
				if (bus.insertCategories(ca, user_id) > 0) {
					JOptionPane.showMessageDialog(contentPane, "Thêm thành công!!");
					Categories cate = new Categories(name, type);
					dsCategory.add(cate);
					dsCategory = bus.getCategoriesByUser(user_id);
					loadDataTable();
				} else
					JOptionPane.showMessageDialog(contentPane, "Thêm thất bại!!");
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAdd.setBounds(77, 201, 82, 33);
		contentPane.add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Categories");
					return;
				}
				Categories cate = dsCategory.get(index);
				if (bus.deleteCategories(cate.getCategory_id(), user_id) > 0) {
					JOptionPane.showMessageDialog(contentPane, "Xóa thành công!!");
					dsCategory.remove(index);
					loadDataTable();
				} else {
					JOptionPane.showMessageDialog(contentPane, "Xóa thất bại");
				}

			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.setBounds(212, 201, 106, 33);
		contentPane.add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Categories");
					return;
				}
				String name = txtName.getText();
				boolean type;
				if (rdbCTieu.isSelected())
					type = true;
				else
					type = false;
				int id = dsCategory.get(index).getCategory_id();
				Categories cate = new Categories(id, name, type, user_id);
				if (bus.updateCategories(user_id, cate) > 0) {
					JOptionPane.showMessageDialog(contentPane, "Cập nhật thành công!!");
					dsCategory.set(index, cate);
					loadDataTable();
					index = -1;
				} else
					JOptionPane.showMessageDialog(contentPane, "Cập nhật thất bại!!");
			}

		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUpdate.setBounds(380, 201, 99, 33);
		contentPane.add(btnUpdate);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 262, 509, 181);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				Categories ca = dsCategory.get(index);
				txtName.setText(ca.getName());
				if (ca.isType())
					rdbCTieu.setSelected(true);
				else
					rdbtnThuNhp.setSelected(true);
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Tên danh mục", "Loại danh mục" }));

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
		loadDataTable();
	}

	private void loadDataTable() {
		dsCategory = bus.getCategoriesByUser(getCurrentUserID());
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (Categories ca : dsCategory) {
			String type = ca.isType() ? "Chi Tiêu" : "Thu Nhập";
			if (getCurrentUserID() == ca.getUser_id())
				tableModel.addRow(new Object[] { ca.getName(), type });
		}
		table.setModel(tableModel);
	}

	public String getUsername(int id) {
		for (Users user : dsUsers)
			if (user.getUser_id() == id)
				return user.getUsername();
		return "";
	}

	public void loginUser(int userID) {
		setCurrentUserID(userID);
	}
}
