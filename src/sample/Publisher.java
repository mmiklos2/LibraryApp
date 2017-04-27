package sample;

public class Publisher {
	private int publisher_id;
	private String publisher_name;
	private String publisher_city;
	
	public Publisher(){
		
	}

	public Publisher(int publisher_id, String publisher_name, String publisher_city) {
		this.publisher_id = publisher_id;
		this.publisher_name = publisher_name;
		this.publisher_city = publisher_city;
	}

	public int getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(int publisher_id) {
		this.publisher_id = publisher_id;
	}
	public String getPublisher_name() {
		return publisher_name;
	}
	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}
	public String getPublisher_city() {
		return publisher_city;
	}
	public void setPublisher_city(String publisher_city) {
		this.publisher_city = publisher_city;
	}
	
	
}
