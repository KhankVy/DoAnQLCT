package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MODEL.Budgets;
import UTILS.Utils;

public class BudgetsDAO {
	public ArrayList<Budgets> getAllBudgets(int user_id) {
		// TODO Auto-generated method stub
		ArrayList<Budgets> Bud = new ArrayList<Budgets>();
		String query = "SELECT * FROM budgets where user_id=?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int budget_id = rs.getInt("budget_id");
				int category_id = rs.getInt("category_id");
				int amount = rs.getInt("amount");
				Date start_date = rs.getDate("start_date");
				Date end_date = rs.getDate("end_date");
				Budgets bud = new Budgets(budget_id, amount, start_date, end_date, category_id, user_id);
				Bud.add(bud);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return Bud;
	}

	public int insertBudget(Budgets b, int user_id) {
		int result = 0;
		String query = "INSERT INTO Budgets (category_id, amount, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?)";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, b.getCategory_id());
			pstmt.setInt(2, b.getAmount());
			pstmt.setDate(3, new java.sql.Date(b.getStart_date().getTime()));
			pstmt.setDate(4, new java.sql.Date(b.getEnd_date().getTime()));
			pstmt.setInt(5, user_id);
			result = pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
		return result;
	}

	public int deleteBudgets(int id,int user_id) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = "DELETE FROM budgets WHERE budget_id=? and user_id=?";
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

	public int updateBudgets(int user_id, Budgets newbd) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = "UPDATE budgets SET category_id=?,amount=?,start_date=?,end_date=? WHERE budget_id=?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, newbd.getCategory_id());
			pstmt.setInt(2, newbd.getAmount());
			pstmt.setDate(3, new java.sql.Date(newbd.getStart_date().getTime()));
			pstmt.setDate(4, new java.sql.Date(newbd.getEnd_date().getTime()));
			pstmt.setInt(5, newbd.getBudget_id());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return result;
	}

}
