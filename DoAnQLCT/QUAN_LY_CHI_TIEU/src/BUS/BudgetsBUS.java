package BUS;

import java.util.ArrayList;

import DAO.BudgetsDAO;
import MODEL.Budgets;

public class BudgetsBUS {
	private BudgetsDAO bud = new BudgetsDAO();

	public ArrayList<Budgets> getAllBudgets(int user_id) {
		return bud.getAllBudgets(user_id);
	}

	public int insertBudget(Budgets b, int user_id) {
		return bud.insertBudget(b, user_id);
	}

	public int deleteBudgets(int id,int user_id) {
		return bud.deleteBudgets(id,user_id);
	}

	public int updateBudgets(int user_id, Budgets newbd) {
		return bud.updateBudgets(user_id, newbd);
	}

}
