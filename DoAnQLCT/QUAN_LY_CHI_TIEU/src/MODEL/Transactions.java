package MODEL;

import java.util.Date;

public class Transactions {
	private int transaction_id, amount;
	private Date date;
	private String description;
	private int category_id, user_id;

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Transactions(int transaction_id, int amount, Date date, String description, int category_id, int user_id) {
		super();
		this.transaction_id = transaction_id;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.category_id = category_id;
		this.user_id = user_id;
	}

	public Transactions(int amount, Date date, String description, int category_id, int user_id) {
		super();
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.category_id = category_id;
		this.user_id = user_id;
	}

	public Object getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
