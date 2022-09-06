package ar.com.momr.obspringsecurityjwt.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.momr.obspringsecurityjwt.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	
	List<Car> findByDoors(Integer doors);
	
	List<Car> findByManufacturerAndModel(String manufacturer, String model);
	
	List<Car> findByDoorsGreaterThanEqual(Integer doors);
	
	List<Car> findByModelContaining(String model);
	
	//coches de tal año
	List<Car> findByYearIn(List<Integer> years);
	
	List<Car> findByYearBetween(Integer startYear, Integer endYear);
	
	List<Car> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);
	
	//coches disponibles
	List<Car> findByAvailableTrue();
	
	Long deleteAllByAvailableFalse();
	

}
