package MODEL;

import java.util.Date;

public class Users {
	private int user_id;
	private String username, email;
	private String password_hash;
	private Date created_at;
	private Date last_login;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}


	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public Users(int user_id, String username, String email, String password_hash, Date created_at, Date last_login) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
		this.created_at = created_at;
		this.last_login = last_login;
	}

	public Users(String username, String email, String password_hash, Date created_at, Date last_login) {
		super();
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
		this.created_at = created_at;
		this.last_login = last_login;
	}

	public Users(int user_id, String username) {
		super();
		this.user_id = user_id;
		this.username = username;
	}

	public Users(String username, String email, String password_hash) {
		super();
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
	}

	@Override
	public String toString() {
		return email;
	}
	
	
}
