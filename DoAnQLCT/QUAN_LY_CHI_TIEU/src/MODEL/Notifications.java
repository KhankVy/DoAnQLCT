package MODEL;

import java.util.Date;

public class Notifications {
	private int notification_id, user_id;
	private String message;
	private Date date;
	private boolean status;

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Notifications(int notification_id, int user_id, String message, Date date, Boolean status) {
		super();
		this.notification_id = notification_id;
		this.user_id = user_id;
		this.message = message;
		this.date = date;
		this.status = status;
	}

	public Notifications(int user_id, String message, Date date, boolean status) {
		super();
		this.user_id = user_id;
		this.message = message;
		this.date = date;
		this.status = status;
	}

	public Notifications(String message, Date date, boolean status) {
		super();
		this.message = message;
		this.date = date;
		this.status = status;
	}
	

}
