package chaincode.example;

class Car {

	private String make;

	private String model;

	private String color;

	private String owner;

	public Car() {

	}

	public Car(final String make, final String model, final String color, final String owner) {
		this.make = make;
		this.model = model;
		this.color = color;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getColor() {
		return color;
	}

	public String getOwner() {
		return owner;
	}
}

