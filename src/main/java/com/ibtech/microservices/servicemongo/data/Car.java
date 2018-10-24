package com.ibtech.microservices.servicemongo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.io.Serializable;
import java.math.BigInteger;

@Document
public class Car implements Serializable {

	@Id
	private String id;

	private String make;

	private String model;

	private int year;

	@GeoSpatialIndexed(name = "position")
	private Point position;

	Car() {
	}

	public Car(String make, String model, int year, Point position) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.position = position;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return make + " " + model + " " + year;
	}

}
