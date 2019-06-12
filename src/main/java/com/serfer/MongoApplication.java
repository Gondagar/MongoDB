package com.serfer;


import com.serfer.model.Customer;
import com.serfer.repository.WorkWithMongo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

public class MongoApplication  {

    private static Logger log = LogManager.getLogger(MongoApplication.class);




    public static void main(String[] args) throws Exception {
        run(args);
    }


    public static void run(String... args) throws Exception {


        Properties prop = new Properties();
        prop.setProperty("host", "localhost");
        prop.setProperty("port", "27017");
        prop.setProperty("dbname", "admin");
        prop.setProperty("login", "");
        prop.setProperty("password", "");
        prop.setProperty("table", "customer");


        WorkWithMongo workWithMongo = new WorkWithMongo(prop);


        workWithMongo.add(new Customer("Alice", "Smith",34));

        Customer alice = workWithMongo.getByFirstName("Alice");

        System.out.println(alice);

        workWithMongo.updateByFirstName(alice.getFirstName(),"Оленка");




    }





}
