package BUS;

import DAO.UsersDAO;
import MODEL.Login;
import MODEL.Users;

public class UsersBus {
	private UsersDAO users = new UsersDAO();
	public boolean checkLogin(Login login) {
		return users.checkLogin(login);
	}

	public boolean authenticateUser(String email, String password) {
		return users.authenticateUser(email, password);
	}
	public int getUserIDFromDatabase(String email) {
		return users.getUserIDFromDatabase(email);
	}

	public int insertUser(Users user) {
		// TODO Auto-generated method stub
		return users.insertUser(user);
	}

	public int updateLastLoginDate(String email) {
		return users.updateLastLoginDate(email);
		// TODO Auto-generated method stub
	}

	public boolean isEmailExists(String email) {
		return users.isEmailExists(email);
	}
	public int validateUser(String username, String password) {
		return users.validateUser(username, password);
	}
}
