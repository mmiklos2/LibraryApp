import java.util.Date;

public class Books_On_Loan {

	private Date date_due;
	private boolean returned;
	
	public Books_On_Loan(){
		
	}

	public Date getDate_due() {
		return date_due;
	}

	public void setDate_due(Date date_due) {
		this.date_due = date_due;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	
}
