package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.DailyReportService;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = null;
        List<DailyReport> dailyReports = null;
        try {
            transaction = session.beginTransaction();
            dailyReports = session.createQuery("FROM DailyReport").list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return dailyReports;
    }

    public void updateReport(Long earnings, Long soldCars) {
        List<DailyReport> list = DailyReportService.getInstance().getAllDailyReports();
        Transaction transaction = null;
        try {
            if (list.size() != 0) {
                DailyReport dailyReport = list.get(list.size() - 1);
                dailyReport.setEarnings(dailyReport.getEarnings() + earnings);
                dailyReport.setSoldCars(dailyReport.getSoldCars() + soldCars);
                transaction = session.beginTransaction();
                session.update(dailyReport);
                transaction.commit();
            } else {
                transaction = session.beginTransaction();
                session.save(new DailyReport(earnings, soldCars));
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

    }

    public void addNewDay() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new DailyReport(0L, 0L));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }


    }

    public DailyReport getLastReport() {
        List<DailyReport> list = DailyReportService.getInstance().getAllDailyReports();
        return list.get(list.size() - 2);
    }

    public void deleteAllReports() {
        Transaction trx = null;
        try {
            trx = session.beginTransaction();
            session.createQuery("DELETE FROM DailyReport").executeUpdate();
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
