package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Publisher {
	public final ObservableValue<Integer> publisher_id;
	public SimpleStringProperty publisher_name;
	public SimpleStringProperty publisher_city;
	
	public Publisher(int id, String name, String city){

		publisher_id = new SimpleObjectProperty<>(id);
		publisher_name = new SimpleStringProperty(name);
		publisher_city = new SimpleStringProperty(city);

	}
	

	
	
}
