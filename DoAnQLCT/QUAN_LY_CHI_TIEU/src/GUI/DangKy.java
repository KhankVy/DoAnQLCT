package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.UsersBus;
import MODEL.OTPMail;
import MODEL.Users;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.awt.event.ActionEvent;

public class DangKy extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtEmail;
	private JPasswordField txtPass;
	/////////////////////////
	private UsersBus bus = new UsersBus();
	private static int currentUserID;
	private static int currentCargories;
	private String otp;

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int currentUserID) {
		DangKy.currentUserID = currentUserID;
	}

	public static int getCurrentCargories() {
		return currentCargories;
	}

	public static void setCurrentCargories(int currentCargories) {
		DangKy.currentCargories = currentCargories;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangKy frame = new DangKy();
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
	public DangKy() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("ĐĂNG KÝ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_2.setBounds(216, 27, 115, 29);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel = new JLabel("Tên");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(27, 91, 45, 25);
		contentPane.add(lblNewLabel);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(149, 90, 313, 26);
		contentPane.add(txtName);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(27, 136, 82, 25);
		contentPane.add(lblNewLabel_1);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(149, 135, 313, 26);
		contentPane.add(txtEmail);

		JLabel lblMtKhu = new JLabel("Mật khẩu");
		lblMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMtKhu.setBounds(27, 179, 92, 25);
		contentPane.add(lblMtKhu);

		txtPass = new JPasswordField();
		txtPass.setBounds(149, 179, 313, 25);
		contentPane.add(txtPass);

		JButton btnDKy = new JButton("Đăng Ký");
		btnDKy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = txtName.getText();
				String email = txtEmail.getText();
				char[] passwordChars = txtPass.getPassword();
				String password = new String(passwordChars);
				// Mã hóa mật khẩu bằng MD5
				String hashedPassword = hashPassword(password);
				// Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu hay không
				if (bus.isEmailExists(email)) {
					JOptionPane.showMessageDialog(contentPane, "Email already exists!");
					return;
				}
				java.util.Date currentTimestamp = new java.util.Date();
				Users users = new Users(user, email, hashedPassword, null, null);
				users.setUsername(user);
				users.setEmail(email);
				users.setPassword_hash(hashedPassword);
				users.setCreated_at(currentTimestamp);
				users.setLast_login(currentTimestamp);
				int result = bus.insertUser(users);
				if (result > 0) {
					sendOTP(email);
					String userOTP = JOptionPane.showInputDialog(contentPane, "Nhập mã OTP:", "Xác thực OTP",
							JOptionPane.INFORMATION_MESSAGE);
					if (verifyOTP(userOTP)) {
						//int userId = bus.getUserIDFromDatabase(email);
						JOptionPane.showMessageDialog(contentPane, "Registration successful!");
						DangNhap fm = new DangNhap();
						fm.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(btnDKy, "Mã OTP không đúng!");
					}
				}
			}
		});
		btnDKy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDKy.setBounds(208, 234, 123, 42);
		contentPane.add(btnDKy);
	}

	protected String hashPassword(String pass) {
		// TODO Auto-generated method stub
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = md.digest(pass.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashInBytes)
				sb.append(String.format("%02x", b));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void sendOTP(String email) {
		// Tạo một mã OTP ngẫu nhiên
		otp = generateOTP();
		// Gửi mã OTP qua email
		String subject = "Mã OTP đăng nhập";
		String message = "Mã OTP của bạn là: " + otp;
		OTPMail.sendEmail(email, subject, message);
	}

	private static String generateOTP() {
		// Sử dụng lớp Random để tạo một mã OTP ngẫu nhiên có 6 chữ số
		Random random = new Random();
		int otpValue = 100000 + random.nextInt(900000);
		return String.valueOf(otpValue);
	}

	protected boolean verifyOTP(String userOTP) {
		// TODO Auto-generated method stub
		// So sánh mã OTP người dùng nhập vào với mã OTP đã tạo
		return userOTP.equals(otp);
	}
}
