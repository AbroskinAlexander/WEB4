package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;
import java.util.Map;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public boolean addCar(Car car) {
        return new CarDao(sessionFactory.openSession()).addCar(car);

    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public boolean soldCar(Map<String, String> map) {
        return new CarDao(sessionFactory.openSession()).soldCar(map);
    }

    public void Delete() {
        new CarDao(sessionFactory.openSession()).delete();
    }
}
