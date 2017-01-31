
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Call {
	private int id;
	private Date timeAndDate;
	private String notes;

	public Call(String notes) {

		this(notes, new java.util.Date());
	}

	/**
	 * Constructor for Call if the automatic timestamp needs to be overridden
	 */
	public Call(String notes, Date timestamp) {
		// this defaults to a timestamp of "now"
		this.timeAndDate = timestamp;
		this.notes = notes;
	}

	/**
	 * A simple toString
	 */
	public String toString() {
		return this.timeAndDate + " : " + this.notes;
	}

	public Date getTimeAndDate() {
		return timeAndDate;
	}

	public void setTimeAndDate(Date timeAndDate) {
		this.timeAndDate = timeAndDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	// needed for JPA - ignore until then
	public Call() {
	}
}