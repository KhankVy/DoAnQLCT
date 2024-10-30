package MODEL;

import java.util.Date;

public class Budgets {
	private int budget_id;
	private int amount;
	private Date start_date, end_date;
	private int category_id, user_id;

	public int getBudget_id() {
		return budget_id;
	}

	public void setBudget_id(int budget_id) {
		this.budget_id = budget_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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

	public Budgets(int budget_id, int amount, Date start_date, Date end_date, int category_id, int user_id) {
		super();
		this.budget_id = budget_id;
		this.amount = amount;
		this.start_date = start_date;
		this.end_date = end_date;
		this.category_id = category_id;
		this.user_id = user_id;
	}

	public Budgets(int amount, Date start_date, Date end_date, int category_id, int user_id) {
		super();
		this.amount = amount;
		this.start_date = start_date;
		this.end_date = end_date;
		this.category_id = category_id;
		this.user_id = user_id;
	}
	
}
