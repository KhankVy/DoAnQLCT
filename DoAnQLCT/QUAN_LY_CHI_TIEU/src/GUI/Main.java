package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import BUS.GetThuChiBUS;
import BUS.NotificationsBUS;
import DAO.NotificationsDAO;
import MODEL.Notifications;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/////////////////////

	private static int currentUserID;
	private static int currentCargories;
	private JTextField txtSoDu;
	private JTextField txtTChi;
	private JTextField txtTThu;
	private GetThuChiBUS thuchi = new GetThuChiBUS();
	private ChartPanel chartPanel;

	public static int getCurrentCargories() {
		return currentCargories;
	}

	public static void setCurrentCargories(int currentCargories) {
		Main.currentCargories = currentCargories;
	}

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int userId) {
		// TODO Auto-generated method stub
		currentUserID = userId;

	}

	public void loginUser(int userID) {
		setCurrentUserID(userID);
		NotificationsBUS notificationsBUS = new NotificationsBUS();
	    ArrayList<Notifications> notifications = notificationsBUS.getAllNotifications(userID);
	    Date currentDate = new Date();
	    boolean foundWaterBillReminder = false;
	    // Hiển thị thông báo nhắc nhở trên giao diện Main
	    for (Notifications notification : notifications) {
	        // Kiểm tra nếu thông báo là thông báo nhắc nhở tới ngày đóng tiền nước
	        if (isSameDate(notification.getDate(), currentDate)) {
	            JOptionPane.showMessageDialog(contentPane, "Ngày đóng tiền đã đến! Vui lòng thanh toán!");
	            foundWaterBillReminder = true;
	            break; // Exit loop once found the water bill reminder
	        }
	    }
	    if (!foundWaterBillReminder) {
	        // If no water bill reminder found, display a message indicating no reminders for today
	        JOptionPane.showMessageDialog(contentPane, "Không có nhắc nhở nào cho hôm nay!");
	    }
	}

	private boolean isSameDate(Date date, Date currentDate) {
		// TODO Auto-generated method stub
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		Calendar cal2 = Calendar.getInstance();
	    cal2.setTime(currentDate);
	    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
	            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 562);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Login");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DangNhap dn = new DangNhap();
				dn.setVisible(true);
				dispose();
			}
		});
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Exit");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_1_1 = new JMenuItem("Categories");
		mntmNewMenuItem_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FmCategories fm = new FmCategories();
				FmCategories.setCurrentUserID(getCurrentUserID());
				fm.setVisible(true);
				dispose();
			}
		});
		mntmNewMenuItem_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmNewMenuItem_1_1);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Transactions");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FmTransactions fm = new FmTransactions();
				FmTransactions.setCurrentUserID(currentUserID);
				fm.setVisible(true);
				dispose();
			}
		});
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_1_2 = new JMenuItem("Budgets");
		mntmNewMenuItem_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FmBudgets fm = new FmBudgets();
				FmBudgets.setCurrentUserID(currentUserID);
				fm.setVisible(true);
				dispose();
			}
		});
		mntmNewMenuItem_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmNewMenuItem_1_2);
		
		JMenuItem mntmNewMenuItem_1_2_1 = new JMenuItem("Notifications");
		mntmNewMenuItem_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FmNotifications fm=new FmNotifications();
				FmNotifications.setCurrentUserID(currentUserID);
				fm.setVisible(true);
				dispose();
			}
		});
		mntmNewMenuItem_1_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmNewMenuItem_1_2_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Số Dư");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

		txtSoDu = new JTextField();
		txtSoDu.setEditable(false);
		txtSoDu.setColumns(10);

		JLabel lblTngThu = new JLabel("Tổng Thu");
		lblTngThu.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblTngChi = new JLabel("Tổng Chi");
		lblTngChi.setFont(new Font("Tahoma", Font.PLAIN, 20));

		txtTChi = new JTextField();
		txtTChi.setEditable(false);
		txtTChi.setColumns(10);

		txtTThu = new JTextField();
		txtTThu.setEditable(false);
		txtTThu.setColumns(10);

		JButton btnBaoCao = new JButton("Báo cáo");
		btnBaoCao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPieChart();
			}
		});
		btnBaoCao.setFont(new Font("Tahoma", Font.PLAIN, 20));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(29)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addGap(29)
								.addComponent(txtSoDu, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
								.addGap(104).addComponent(btnBaoCao))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblTngThu, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtTThu, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblTngChi).addGap(18)
								.addComponent(txtTChi, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addGap(96)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(19)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblTngThu, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTChi, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTngChi, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTThu, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel).addComponent(txtSoDu, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(btnBaoCao))
						.addContainerGap(491, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
		initData();
	}

	@SuppressWarnings("removal")
	private void showPieChart() {
		// TODO Auto-generated method stub
		int totalIncome = thuchi.calculateMonthlyIncome(getCurrentUserID(), getCurrentCargories());
		int totalExpense = thuchi.calculateMonthlyExpense(getCurrentUserID(), getCurrentCargories());
		DefaultPieDataset pie = new DefaultPieDataset();
		pie.setValue("Tổng Thu", totalIncome);
		pie.setValue("Tổng Chi", totalExpense);
		JFreeChart chart = ChartFactory.createPieChart("Biểu đồ thu chi theo tháng", pie, true, true, true);
		PiePlot p = (PiePlot) chart.getPlot();
		chartPanel = new ChartPanel(chart);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setMouseZoomable(true);
		chartPanel.setDomainZoomable(true);
		chartPanel.setDisplayToolTips(true);
		chartPanel.setBounds(70, 150, 600, 300);// setBounds(độ rộng trái, phải, độ rộng trung tâm, chiều cao)
		contentPane.add(chartPanel);
		chartPanel.setLayout(null);
		contentPane.revalidate();
		contentPane.repaint();
	}

	// Hàm tính số dư
	private void initData() {
		int TongTienThu = thuchi.calculateMonthlyIncome(getCurrentUserID(), getCurrentCargories());
		txtTThu.setText(Integer.toString(TongTienThu)); // Hiển thị số dư lên giao diện
		int TongTienChi = thuchi.calculateMonthlyExpense(getCurrentUserID(), getCurrentCargories());
		txtTChi.setText(Integer.toString(TongTienChi)); // Hiển thị số dư lên giao diện

		if (TongTienThu < TongTienChi)
			JOptionPane.showMessageDialog(txtSoDu, "số tiền chi đã vượt quá số tiền thu vào!!!");
		else {
			int sodu = TongTienThu - TongTienChi;
			txtSoDu.setText(Integer.toString(sodu));
		}
	}

}
