package chaincode.fabcar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
class Car {

	private String key;

	private String make;

	private String model;

	private String color;

	private String owner;
}

