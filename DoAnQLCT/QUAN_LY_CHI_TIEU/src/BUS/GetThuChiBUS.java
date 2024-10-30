package BUS;

import DAO.GetThuChiDAO;

public class GetThuChiBUS {
	private GetThuChiDAO dao = new GetThuChiDAO();

	public int calculateMonthlyIncome(int user_id,int cargories) {
		return dao.calculateMonthlyIncome(user_id,cargories);
	}

	public int calculateMonthlyExpense(int user_id,int cargories) {
		return dao.calculateMonthlyExpense(user_id,cargories);
	}
}
