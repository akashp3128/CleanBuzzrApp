package edu.iastate.cs.coms309kk03.Buzzr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//This class start embedded Tomcat server hosting our Spring Boot web Application!

@SpringBootApplication
public class BuzzrApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(BuzzrApplication.class, args);
	}

}
