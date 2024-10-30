package BUS;

import java.util.ArrayList;

import DAO.CategoriesDAO;
import MODEL.Categories;

public class CategoríeBUS {
	private CategoriesDAO dao = new CategoriesDAO();

	public ArrayList<Categories> getCategoriesByUser(int user_id) {
		return dao.getCategoriesByUser(user_id);
	}
	public int insertCategories(Categories ca, int userID) {
		// TODO Auto-generated method stub
		return dao.insertCategories(ca,userID);
	}
	public int deleteCategories(int id,int user_id) {
		// TODO Auto-generated method stub
		return dao.deleteCategories(id,user_id);
	}
	public int updateCategories(int user_id, Categories newsv) {
		// TODO Auto-generated method stub
		return dao.updateCategories(user_id, newsv);
	}
	public boolean isUserExists(int user_id) {
		return dao.isUserExists(user_id);
	}
	public boolean isCategoryExists(int category_id) {
		return dao.isCategoryExists(category_id);
	}
}
