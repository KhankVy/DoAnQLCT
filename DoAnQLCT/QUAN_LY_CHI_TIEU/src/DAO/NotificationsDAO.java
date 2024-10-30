package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import MODEL.Notifications;
import UTILS.Utils;

public class NotificationsDAO {

	public ArrayList<Notifications> getNotificationsByUserId(int userId) {
		ArrayList<Notifications> notifications = new ArrayList<>();
		String query = "SELECT * FROM notifications WHERE user_id = ?";
		try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int notificationId = rs.getInt("notification_id");
				String message = rs.getString("message");
				Date date = rs.getDate("date");
				Boolean status = rs.getBoolean("status");
				notifications.add(new Notifications(notificationId, userId, message, date, status));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notifications;
	}

	public int insertNotifications(Notifications no, int user_id) {
		String query = "INSERT INTO notifications(user_id, message, date, status) VALUES (?,?,?,?)";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user_id);
			pstmt.setString(2, no.getMessage());
			pstmt.setDate(3, new java.sql.Date(no.getDate().getTime()));
			pstmt.setBoolean(4, no.isStatus());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int deleteNotifications(int id, int user_id) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = "DELETE FROM notifications WHERE notification_id=? and user_id=?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, user_id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	public int updateNotifications(int user_id, Notifications newno) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = "UPDATE notifications SET message=?,date=?,status=? WHERE notification_id=?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newno.getMessage());
			pstmt.setDate(2, new java.sql.Date(newno.getDate().getTime()));
			pstmt.setBoolean(3, newno.isStatus());
			pstmt.setInt(4, newno.getNotification_id());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	// Phương thức để lấy tất cả các thông báo chưa đọc
	public ArrayList<Notifications> getUnreadNotificationsByUserId(int userId) {
		ArrayList<Notifications> notifications = new ArrayList<>();
		String query = "SELECT * FROM notifications WHERE user_id = ? AND status = false";
		try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int notificationId = rs.getInt("notification_id");
				String message = rs.getString("message");
				Date date = rs.getDate("date");
				boolean status = rs.getBoolean("status");
				notifications.add(new Notifications(notificationId, userId, message, date, status));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notifications;
	}
}
