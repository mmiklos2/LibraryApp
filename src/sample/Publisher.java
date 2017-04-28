package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Publisher {
	public ObservableValue<Integer> publisher_id;
	public SimpleStringProperty publisher_name;
	public SimpleStringProperty publisher_city;
	
	public Publisher(int id, String name, String city){

		publisher_id = new SimpleObjectProperty<>(id);
		publisher_name = new SimpleStringProperty(name);
		publisher_city = new SimpleStringProperty(city);

	}

	public SimpleStringProperty getPublisher_name(){
	    return publisher_name;
    }

    public int getPublisherId(){
	    return publisher_id.getValue();
    }


	
	
}
