package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import BUS.NotificationsBUS;
import MODEL.Categories;
import MODEL.Notifications;
import MODEL.Transactions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FmNotifications extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static int currentUserID;
	private static int currentCargories;
	private JTextField txtMessage;
	private JTextField txtDate;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable table;
	private NotificationsBUS bus = new NotificationsBUS();
	private ArrayList<Notifications> dsNotifications = new ArrayList<Notifications>();
	private int index = -1;

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int currentUserID) {
		FmNotifications.currentUserID = currentUserID;
	}

	public static int getCurrentCargories() {
		return currentCargories;
	}

	public static void setCurrentCargories(int currentCargories) {
		FmNotifications.currentCargories = currentCargories;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FmNotifications frame = new FmNotifications();
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
	public FmNotifications() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("NOTIFICATIONS");
		lblNewLabel.setBounds(232, 44, 203, 32);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblNewLabel_1 = new JLabel("Message");
		lblNewLabel_1.setBounds(35, 102, 100, 25);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		txtMessage = new JTextField();
		txtMessage.setBounds(153, 94, 323, 26);
		txtMessage.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Date");
		lblNewLabel_1_1.setBounds(35, 146, 100, 25);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		txtDate = new JTextField();
		txtDate.setBounds(153, 145, 323, 26);
		txtDate.setColumns(10);

		JLabel lblNewLabel_1_1_1 = new JLabel("Status");
		lblNewLabel_1_1_1.setBounds(35, 191, 100, 25);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JRadioButton rdbDaDoc = new JRadioButton("Đã Đọc");
		rdbDaDoc.setBounds(153, 189, 103, 31);
		buttonGroup.add(rdbDaDoc);
		rdbDaDoc.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JRadioButton rdbCDoc = new JRadioButton("Chưa Đọc");
		rdbCDoc.setBounds(340, 189, 136, 31);
		buttonGroup.add(rdbCDoc);
		rdbCDoc.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				String message = txtMessage.getText();
				try {
					java.sql.Date date = java.sql.Date.valueOf(txtDate.getText());
					boolean status = rdbDaDoc.isSelected();
					Notifications no = new Notifications(user_id, message, date, status);
					int result = bus.insertNotifications(no, user_id);
					if (result > 0) {
						JOptionPane.showMessageDialog(contentPane, "Thêm thành công!!");
						// Cập nhật danh sách nhắc nhở và bảng dữ liệu
						dsNotifications.add(no);
						loadDataTable();
					} else {
						JOptionPane.showMessageDialog(contentPane, "Thêm thất bại!!");
					}
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(contentPane,
							"Ngày không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
				}

			}
		});
		btnAdd.setBounds(523, 94, 111, 33);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Transactions");
					return;
				}
				Notifications no = dsNotifications.get(index);
				bus.deleteNotifications(no.getNotification_id(), user_id);
				dsNotifications.remove(index);
				loadDataTable();
			}
		});
		btnDelete.setBounds(523, 145, 111, 33);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user_id = getCurrentUserID();
				if (index < 0) {
					JOptionPane.showMessageDialog(contentPane, "Bạn chưa chọn Notifications");
					return;
				}
				int notification_id = dsNotifications.get(index).getNotification_id();
				String message = txtMessage.getText();
				java.sql.Date date = java.sql.Date.valueOf(txtDate.getText());
				Boolean status = rdbDaDoc.isSelected();
				Notifications no = new Notifications(notification_id, user_id, message, date, status);
				if (bus.updateNotifications(user_id, no) > 0) {
					JOptionPane.showMessageDialog(contentPane, "Cập nhật thành công!!");
					dsNotifications.set(index, no);
					loadDataTable();
					index = -1;
				} else
					JOptionPane.showMessageDialog(contentPane, "Cập nhật thất bại!!");
			}
		});
		btnUpdate.setBounds(523, 196, 111, 33);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.setLayout(null);

		contentPane.add(lblNewLabel);
		contentPane.add(lblNewLabel_1);
		contentPane.add(txtMessage);
		contentPane.add(lblNewLabel_1_1);
		contentPane.add(txtDate);
		contentPane.add(lblNewLabel_1_1_1);
		contentPane.add(rdbDaDoc);
		contentPane.add(rdbCDoc);
		contentPane.add(btnAdd);
		contentPane.add(btnDelete);
		contentPane.add(btnUpdate);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 259, 600, 220);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Message", "Date", "Status" }));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				Notifications no = dsNotifications.get(index);
				txtMessage.setText(no.getMessage());
				txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(no.getDate()));
				if (no.isStatus())
					rdbDaDoc.setSelected(true);
				else
					rdbCDoc.setSelected(true);

			}
		});
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
		dsNotifications = bus.getAllNotifications(currentUserID);
		loadDataTable();
	}

	private void loadDataTable() {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (Notifications no : dsNotifications) {
			String type = no.isStatus() ? "Đã Đọc" : "Chưa Đọc";
			if (getCurrentUserID() == no.getUser_id())
				tableModel.addRow(new Object[] { no.getMessage(), no.getDate(), type });
		}
		table.setModel(tableModel);
	}

}
