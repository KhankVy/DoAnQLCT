package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UTILS.Utils;

public class GetThuChiDAO {

	// Hàm tính tổng thu theo tháng
	public int calculateMonthlyIncome(int user_id,int cargories) {
		int sodu = 0;
		String query = "SELECT SUM(amount) AS sodu FROM transactions tr JOIN categories cate ON tr.category_id = cate.category_id WHERE tr.user_id = ? AND cate.type = 0";

		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sodu = rs.getInt("sodu");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sodu;
	}

	// Hàm tính tổng chi theo tháng
	public int calculateMonthlyExpense(int user_id,int cargories) {
		int sodu = 0;
		String query = "SELECT SUM(amount) AS sodu FROM transactions tr JOIN categories cate ON tr.category_id = cate.category_id WHERE tr.user_id = ? AND cate.type = 1";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sodu = rs.getInt("sodu");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sodu;
	}
}
