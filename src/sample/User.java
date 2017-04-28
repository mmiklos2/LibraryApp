package sample;

public class User {

	private int user_id;
	private String user_username;
	private String user_email;
	private String user_password;
	private String user_firstname;
	private String user_lastname;
	
	public User(){
		
	}

	public User(String user_username, String user_email, String user_password, String user_firstname, String user_lastname) {
		this.user_username = user_username;
		this.user_email = user_email;
		this.user_password = user_password;
		this.user_firstname = user_firstname;
		this.user_lastname = user_lastname;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_firstname() {
		return user_firstname;
	}
	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}
	public String getUser_lastname() {
		return user_lastname;
	}
	public void setUser_lastname(String user_lastname) {
		this.user_lastname = user_lastname;
	}
}
