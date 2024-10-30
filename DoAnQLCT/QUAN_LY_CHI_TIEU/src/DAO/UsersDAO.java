package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import MODEL.Login;
import MODEL.Users;
import UTILS.Utils;

public class UsersDAO {
	public boolean checkLogin(Login login) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
        try (Connection connection = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
             PreparedStatement stmt = connection.prepareStatement(query)) {
        	stmt.setString(1, login.getEmail());
        	stmt.setString(2, login.getPassword());
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next(); // Trả về true nếu tồn tại người dùng với thông tin đăng nhập đã cung cấp
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	public boolean authenticateUser(String email, String password) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Users> getAllUsers() {
		// TODO Auto-generated method stub
		ArrayList<Users> users = new ArrayList<Users>();
		String query = "SELECT user_id, username FROM users";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("user_id");
				String username = rs.getString("username");
				Users user = new Users(id, username);
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return users;
	}
	public int getUserIDFromDatabase(String email) {
		// TODO Auto-generated method stub
		String query = "SELECT user_id FROM users WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS)) {
        	PreparedStatement pstmt = connection.prepareStatement(query);
        	pstmt.setString(1, email);
        	ResultSet rs = pstmt.executeQuery();
        	if(rs.next())
        		return rs.getInt("user_id");
        	else
        		return 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
		return 0;
	}
	public int insertUser(Users user) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = "INSERT INTO users(username, email, password_hash, created_at, last_login) VALUES (?,?,?,?,?)";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword_hash());
			pstmt.setDate(4, new java.sql.Date(user.getCreated_at().getTime()));
			pstmt.setDate(5, new java.sql.Date(user.getLast_login().getTime()));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	public int updateLastLoginDate(String email) {
		int result = 0;
		String query = "UPDATE users SET last_login = ? WHERE email = ?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			// Thiết lập giá trị ngày và giờ hiện tại
			// Date currentDate = new Date(System.currentTimeMillis());
			String currentDate = LocalDate.now() + "";
			pstmt.setString(1, currentDate);
			// pstmt.setDate(1, currentDate);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isEmailExists(String email) {
	    String query = "SELECT * FROM users WHERE email = ?";
	    try {
	        Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, email); 
	        ResultSet rs = pstmt.executeQuery(); 
	        return rs.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public int validateUser(String email, String password) {
		// TODO Auto-generated method stub
		int userId = -1;
        String query = "SELECT user_id FROM users WHERE email = ? AND password_hash = ?";
        try{
        	Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
	        PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                userId = rs.getInt("user_id");
        }catch(SQLException e) {
        	e.printStackTrace();
            return userId;
        }
        return 1;
	}
}
