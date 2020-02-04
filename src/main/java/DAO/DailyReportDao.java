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
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public void updateReport(Long earnings, Long soldCars) {
        List<DailyReport> list = DailyReportService.getInstance().getAllDailyReports();
        if (list.size() != 0) {
            DailyReport dailyReport = list.get(list.size() - 1);
            Transaction transaction = session.beginTransaction();
            dailyReport.setEarnings(dailyReport.getEarnings() + earnings);
            dailyReport.setSoldCars(dailyReport.getSoldCars() + soldCars);
            session.update(dailyReport);
            transaction.commit();
        } else {
            Transaction transaction = session.beginTransaction();
            session.save(new DailyReport(earnings, soldCars));
            transaction.commit();
        }
        session.close();
    }

    public void addNewDay() {
        Transaction transaction = session.beginTransaction();
        session.save(new DailyReport(0L, 0L));
        transaction.commit();
        session.close();
    }

    public DailyReport getLastReport() {
        List<DailyReport> list = DailyReportService.getInstance().getAllDailyReports();
        return list.get(list.size() - 2);
    }

    public void delete() {
        Transaction trx = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
        trx.commit();
        session.close();
    }
}
