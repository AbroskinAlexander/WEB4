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
            Transaction trx = null;
            try {
                trx = session.beginTransaction();
                session.save(car);
                trx.commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                if (trx != null) {
                    trx.rollback();
                }
            } finally {
                session.close();
            }
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
        Criteria criteria =
                session.createCriteria(Car.class);
        List<Car> carsToSell = (List<Car>) criteria
                .add(Restrictions.allEq(map))
                .list();
        if (carsToSell.size() != 0) {
            Transaction trx = null;
            try {
                Car car = carsToSell.get(0);
                trx = session.beginTransaction();
                session.delete(car);
                trx.commit();
                DailyReportService.getInstance().updateReport(car.getPrice(), 1L);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                if (trx != null) {
                    trx.rollback();
                }
                session.close();
            }
        }
        session.close();
        return false;

    }

    public void deleteAllCars() {
        Transaction trx = null;
        session.
        try {
            trx = session.beginTransaction();
            session.createQuery("DELETE FROM Car").executeUpdate();
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trx != null) {
                trx.rollback();
            }
        } finally {
            session.close();
        }

    }
}
