package messages.global;

import java.io.Serializable;

public class ParticipantInfo implements Serializable {
	
	private int id;
	private String name;

	public ParticipantInfo(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
