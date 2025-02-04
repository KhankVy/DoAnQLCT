package BUS;

import java.sql.Date;
import java.util.ArrayList;

import DAO.TransactionsDAO;
import MODEL.Budgets;
import MODEL.Transactions;

public class TransactionsBUS {
	private TransactionsDAO dao = new TransactionsDAO();

	public ArrayList<Transactions> getAllTransactions(int user_id) {
		return dao.getAllTransactions(user_id);
	}
	public int insertTransactions(Transactions tr, int user_id) {
		// TODO Auto-generated method stub
		return dao.insertTransactions(tr,user_id);
	}
	public int deleteTransactions(int id,int user_id) {
		// TODO Auto-generated method stub
		return dao.deleteTransactions(id,user_id);
	}
	public int updateTransactions(int user_id, Transactions newtr) {
		// TODO Auto-generated method stub
		return dao.updateTransactions(user_id, newtr);
	}
	public int getTotalSpent(int userId, int categoryId, Date startDate, Date endDate) {
		return dao.getTotalSpent(userId, categoryId, startDate, endDate);
	}
	public Budgets getBudget(int userId, int categoryId, Date date) {
		return dao.getBudget(userId, categoryId, date, date);
	}

	public int checkBudgetAndAlert(int userId, int categoryId, int amount, java.util.Date date){
		return dao.checkBudgetAndAlert(userId, categoryId, amount, date);
	}
}
