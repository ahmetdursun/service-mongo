package com.ibtech.microservices.servicemongo.data;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;


@RepositoryRestResource(collectionResourceRel = "mongocar", path = "mongocar")
public interface CarRepository extends MongoRepository<Car, String> {

	Iterable<Car> findByMakeIgnoringCase(String make);

	GeoResults<Car> findByPositionNear(Point p, Distance d);

}
