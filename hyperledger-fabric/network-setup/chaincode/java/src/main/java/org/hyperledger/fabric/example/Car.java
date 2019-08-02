package org.hyperledger.fabric.example;

import com.owlike.genson.annotation.JsonProperty;

public class Car {

	private String make;
	private String model;
	private String color;
	private String owner;

	public Car(@JsonProperty("make") final String make,
			   @JsonProperty("model") final String model,
			   @JsonProperty("color") final String color,
			   @JsonProperty("owner") final String owner) {
		this.make = make;
		this.model = model;
		this.color = color;
		this.owner = owner;
	}
}