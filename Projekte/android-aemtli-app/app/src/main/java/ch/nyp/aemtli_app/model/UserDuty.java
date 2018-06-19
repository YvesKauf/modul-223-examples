package ch.nyp.aemtli_app.model;

import android.arch.persistence.room.Embedded;

/**
 *
 */
public class UserDuty {
	@Embedded
	User user;

	@Embedded
	Duty duty;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Duty getDuty() {
		return duty;
	}

	public void setDuty(Duty duty) {
		this.duty = duty;
	}
}
