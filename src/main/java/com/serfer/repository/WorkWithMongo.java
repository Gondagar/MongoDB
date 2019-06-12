package com.serfer.repository;

import com.mongodb.*;
import com.serfer.model.Customer;


import java.net.UnknownHostException;
import java.util.Properties;

public class WorkWithMongo {

    private MongoClient mongoClient;

    // В нашем случае, этот класс дает
// возможность аутентифицироваться в MongoDB
    private DB db;

    // тут мы будем хранить состояние подключения к БД
    private boolean authenticate;

    // И класс который обеспечит возможность работать
// с коллекциями / таблицами MongoDB
    private DBCollection table;

    public WorkWithMongo(Properties prop) {
        try {
            // Создаем подключение
            mongoClient = new MongoClient( prop.getProperty("host"), Integer.valueOf(prop.getProperty("port")) );

            // Выбираем БД для дальнейшей работы
            db = mongoClient.getDB(prop.getProperty("dbname"));

            // Входим под созданным логином и паролем
            authenticate = db.authenticate(prop.getProperty("login"), prop.getProperty("password").toCharArray());

            // Выбираем коллекцию/таблицу для дальнейшей работы
            table = db.getCollection(prop.getProperty("table"));
        } catch (UnknownHostException e) {
            // Если возникли проблемы при подключении сообщаем об этом
            System.err.println("Don't connect!");
        }
    }

    public void add(Customer customer){
        BasicDBObject document = new BasicDBObject();

        // указываем поле с объекта User
        // это поле будет записываться в коллекцию/таблицу
        document.put("first name", customer.firstName);
        document.put("last name", customer.lastName);
        document.put("age", customer.age);

        // записываем данные в коллекцию/таблицу
        table.insert(document);
    }

    public Customer getByFirstName(String firstName){
        BasicDBObject query = new BasicDBObject();

        // задаем поле и значение поля по которому будем искать
        query.put("first name", firstName);

        // осуществляем поиск
        DBObject result = table.findOne(query);

        // Заполняем сущность полученными данными с коллекции
        Customer customer = new Customer();
        customer.setId(String.valueOf(result.get("_id")));
        customer.setFirstName(String.valueOf(result.get("first name")));
        customer.setLastName(String.valueOf(result.get("last name")));
        customer.setAge(Integer.parseInt(result.get("age").toString()));

        // возвращаем полученного пользователя
        return customer;
    }

    public void deleteByFirstName(String firstName){
        BasicDBObject query = new BasicDBObject();

        // указываем какую запись будем удалять с коллекции
        // задав поле и его текущее значение
        query.put("first name", firstName);

        // удаляем запись с коллекции/таблицы
        table.remove(query);
    }


    public void updateByFirstName(String firstName, String newFirstName){
        BasicDBObject newData = new BasicDBObject();

        // задаем новый логин
        newData.put("first name", newFirstName);

        // указываем обновляемое поле и текущее его значение
        BasicDBObject searchQuery = new BasicDBObject().append("first name", firstName);
        // обновляем запись
        table.update(searchQuery, newData);
    }

}
