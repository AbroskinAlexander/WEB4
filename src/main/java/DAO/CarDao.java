package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import service.CarService;
import service.DailyReportService;

import java.util.List;
import java.util.Map;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public boolean addCar(Car car) {
        Criteria criteria =
                session.createCriteria(Car.class);
        List<Car> cars = (List<Car>) criteria
                .add(Restrictions.eq("brand", car.getBrand()))
                .list();
        if (cars.size() < 10) {
            Transaction trx = session.beginTransaction();
            session.save(car);
            trx.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    public List<Car> getAllCars() {
        Criteria criteria =
                session.createCriteria(Car.class);
        List<Car> result = (List<Car>) criteria.list();
        session.close();
        return result;
    }

    public boolean soldCar(Map<String, String> map) {
        List<Car> test = CarService.getInstance().getAllCars();
        Criteria criteria =
                session.createCriteria(Car.class);
        List<Car> carsToSell = (List<Car>) criteria
                .add(Restrictions.allEq(map))
                .list();
        if (carsToSell.size() != 0) {
            Car car = carsToSell.get(0);
            Transaction trx = session.beginTransaction();
            session.delete(car);
            trx.commit();
            session.close();
            DailyReportService.getInstance().updateReport(car.getPrice(), 1L);
            return true;
        }
        session.close();
        return false;

    }

    public void delete() {
        Transaction trx = session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        trx.commit();
        session.close();
    }
}
