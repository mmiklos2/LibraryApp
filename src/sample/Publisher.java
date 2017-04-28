package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Publisher {
	public int publisher_id;
	public String publisher_name;
	public String publisher_city;

	public Publisher(int id, String name, String city){

		publisher_id = id;
		publisher_name =name;
		publisher_city = city;

	}
	public Publisher( String name, String city){
		publisher_name =name;
		publisher_city = city;

	}



	public String getPublisher_name(){
	    return publisher_name;
    }
	public String getPublisher_city(){
		return publisher_city;
	}

	public int getPublisherId(){
	    return publisher_id;
    }




}
