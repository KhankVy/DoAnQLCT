package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import MODEL.Budgets;
import MODEL.Transactions;
import UTILS.Utils;

public class TransactionsDAO {
	public ArrayList<Transactions> getAllTransactions(int user_id) {
		// TODO Auto-generated method stub
		ArrayList<Transactions> Tr = new ArrayList<Transactions>();
		String query = "SELECT * FROM transactions where user_id=?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int transaction_id = rs.getInt("transaction_id");
				int amount = rs.getInt("amount");
				Date date = rs.getDate("date");
				String description = rs.getString("description");
				int category_id = rs.getInt("category_id");
				Transactions tr = new Transactions(transaction_id, amount, date, description, category_id, user_id);
				Tr.add(tr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return Tr;
	}

	public int insertTransactions(Transactions tr, int user_id) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = "INSERT INTO transactions(amount, date, description, category_id, user_id) VALUES (?,?,?,?,?)";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, tr.getAmount());
			pstmt.setDate(2, new java.sql.Date(tr.getDate().getTime()));
			pstmt.setString(3, tr.getDescription());
			pstmt.setInt(4, tr.getCategory_id());
			pstmt.setInt(5, user_id);
			result = pstmt.executeUpdate();
			checkBudgetAndAlert(user_id, tr.getCategory_id(), tr.getAmount(), tr.getDate());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	public int deleteTransactions(int id,int user_id) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = "DELETE FROM transactions WHERE transaction_id=? and user_id=?";
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

	public int updateTransactions(int user_id, Transactions newtr) {
		// TODO Auto-generated method stub
		int result = 0;
	    try {
	        int previousAmount = getTransactionAmount(newtr.getTransaction_id());
	        int totalSpent = getTotalSpent(user_id, newtr.getCategory_id(), getFirstDateOfMonth(newtr.getDate()), getLastDateOfMonth(newtr.getDate()));
	        int newTotalSpent = totalSpent - previousAmount + newtr.getAmount();

	        Budgets budget = getBudget(user_id, newtr.getCategory_id(), getFirstDateOfMonth(newtr.getDate()), getLastDateOfMonth(newtr.getDate()));
	        if (budget != null && newTotalSpent > budget.getAmount()) {
	            // Gọi sendAlert để hiển thị thông báo vượt ngân sách
	            sendAlert(user_id, newtr.getCategory_id(), newTotalSpent, budget.getAmount());
	        } else {
	            String query = "UPDATE transactions SET amount=?, date=?, description=?, category_id=? WHERE transaction_id=?";
	            Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setInt(1, newtr.getAmount());
	            pstmt.setDate(2, new java.sql.Date(newtr.getDate().getTime()));
	            pstmt.setString(3, newtr.getDescription());
	            pstmt.setInt(4, newtr.getCategory_id());
	            pstmt.setInt(5, newtr.getTransaction_id());
	            result = pstmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	    return result;
	}
	private int getTransactionAmount(int transactionId) {
	    String query = "SELECT amount FROM transactions WHERE transaction_id = ?";
	    try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setInt(1, transactionId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("amount");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	public int getTotalSpent(int userId, int categoryId, java.util.Date startDate, java.util.Date endDate) {
		int totalSpent = 0;
		String query = "SELECT SUM(amount) AS totalSpent FROM transactions WHERE user_id = ? AND category_id = ? AND date BETWEEN ? AND ?";
		try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			pstmt.setInt(2, categoryId);
			pstmt.setDate(3, new java.sql.Date(startDate.getTime()));
			pstmt.setDate(4, new java.sql.Date(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				totalSpent = rs.getInt("totalSpent");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalSpent;
	}

	public Budgets getBudget(int userId, int categoryId, java.util.Date startDate, java.util.Date endDate) {
		String query = "SELECT * FROM budgets WHERE user_id = ? AND category_id = ? AND start_date <= ? AND end_date >= ?";
		try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			pstmt.setInt(2, categoryId);
			pstmt.setDate(3, new java.sql.Date(startDate.getTime()));
			pstmt.setDate(4, new java.sql.Date(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int budgetId = rs.getInt("budget_id");
				int amount = rs.getInt("amount");
				//return new Budgets(budgetId, userId, rs.getDate("end_date"), rs.getDate("start_date"), categoryId,amount);
				return new Budgets(budgetId, amount, startDate, endDate, categoryId, userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int checkBudgetAndAlert(int userId, int categoryId, int amount, java.util.Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Budgets budget = getBudget(userId, categoryId, getFirstDateOfMonth(sqlDate), getLastDateOfMonth(sqlDate));
		if (budget != null) {
			int totalSpent = getTotalSpent(userId, categoryId, budget.getStart_date(), budget.getEnd_date());
			if (totalSpent > budget.getAmount()) {
				sendAlert(userId, categoryId, totalSpent, budget.getAmount());
				return 1;
//            	 String message = "Alert: You have spent " + (totalSpent + amount) + " which exceeds the budget of " + budget.getAmount() + " for category " + categoryId;
//                 dao.insertNotifications(new Notifications(0, userId, message, new java.util.Date(), true));
//                 return 1;
			}
		}
		return 0;
	}

	private java.sql.Date getLastDateOfMonth(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	private java.sql.Date getFirstDateOfMonth(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	private void sendAlert(int userId, int categoryId, int totalSpent, int budgetAmount) {
		//JOptionPane.showMessageDialog(null, "Bạn đã vượt hạn mức ngân sách ", "Budget Alert",JOptionPane.WARNING_MESSAGE);
		 String message = "Bạn đã vượt hạn mức ngân sách! Tổng chi tiêu: " + totalSpent + " VND, Hạn mức: " + budgetAmount + " VND.";
		    JOptionPane.showMessageDialog(null, message, "Budget Alert", JOptionPane.WARNING_MESSAGE);
	}

}
