package edu.iastate.cs.coms309kk03.Buzzr.Barber;

import edu.iastate.cs.coms309kk03.Buzzr.Barber.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JPA
// SPRING DATA JPA will generate implementation code for the most common CRUD operations - we dont have to write a single query
@Repository
public interface BarberRepository extends JpaRepository<Barber, Integer>
{

}