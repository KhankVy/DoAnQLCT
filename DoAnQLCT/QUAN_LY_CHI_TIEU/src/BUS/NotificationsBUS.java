package BUS;

import java.util.ArrayList;

import DAO.NotificationsDAO;
import MODEL.Notifications;

public class NotificationsBUS {
	private NotificationsDAO noti = new NotificationsDAO();

	public ArrayList<Notifications> getAllNotifications(int userId) {
		return noti.getNotificationsByUserId(userId);
	}

	public int insertNotifications(Notifications no, int user_id) {
		return noti.insertNotifications(no, user_id);
	}

	public int deleteNotifications(int id, int user_id) {
		return noti.deleteNotifications(id, user_id);
	}

	public int updateNotifications(int user_id, Notifications newno) {
		return noti.updateNotifications(user_id, newno);
	}
	public ArrayList<Notifications> getUnreadNotificationsByUserId(int userId){
		return noti.getUnreadNotificationsByUserId(userId);
	}
}
