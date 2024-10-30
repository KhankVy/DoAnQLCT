package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import MODEL.Categories;
import UTILS.Utils;

public class CategoriesDAO {

	public ArrayList<Categories> getCategoriesByUser(int userID) {
		// TODO Auto-generated method stub
		ArrayList<Categories> Ca = new ArrayList<Categories>();
		String query = "SELECT * FROM categories where user_id=?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int category_id = rs.getInt("category_id");
				String name = rs.getString("name");
				Boolean type = rs.getBoolean("type");
				Categories ca = new Categories(category_id, name, type, userID);
				Ca.add(ca);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return Ca;
	}

	public int insertCategories(Categories ca, int userID) {
		// TODO Auto-generated method stub
		int result=0;
		if (!isUserExists(userID)) {
			JOptionPane.showMessageDialog(null, "User ID không hợp lệ");
			return 0;
		}
		String query = "INSERT INTO categories(name, type, user_id) VALUES (?,?,?)";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			int generatedId = 0;
			pstmt.setString(1, ca.getName());
			pstmt.setBoolean(2, ca.isType());
			pstmt.setInt(3, userID);
			result=pstmt.executeUpdate();
			if (result > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					generatedId=rs.getInt(1);
					ca.setCategory_id(generatedId);
//					ca.setCategory_id(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	public int deleteCategories(int id,int user_id) {
		// TODO Auto-generated method stub
		int result = 0;
		if (!isCategoryExists(id)) {
			JOptionPane.showMessageDialog(null, "Category ID không hợp lệ");
			return 0;
		}
		String query = "DELETE FROM categories WHERE category_id=? and user_id=?";
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

	public int updateCategories(int user_id,Categories newca) {
		// TODO Auto-generated method stub
		if (!isUserExists(user_id) || !isCategoryExists(newca.getCategory_id())) {
			JOptionPane.showMessageDialog(null, "User ID hoặc Category ID không hợp lệ");
			return 0;
		}
		int result = 0;
		String query = "UPDATE categories SET name=?,type=? WHERE category_id=? ";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newca.getName());
			pstmt.setBoolean(2, newca.isType());
			pstmt.setInt(3, newca.getCategory_id());
			result = pstmt.executeUpdate();
			int generatedId=0;
			if (result > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					generatedId=rs.getInt(1);
					newca.setCategory_id(generatedId);
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	public boolean isUserExists(int user_id) {
		String query="SELECT COUNT(*) FROM users WHERE user_id = ?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				//return rs.getInt(1) > 0;
				boolean exists = rs.getInt(1) > 0;
				return exists;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	public boolean isCategoryExists(int category_id) {
		String query = "SELECT COUNT(*) FROM categories WHERE category_id = ?";
		try {
			Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USER, Utils.PASS);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, category_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				//return rs.getInt(1) > 0;
				boolean exists = rs.getInt(1) > 0;
				return exists;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
