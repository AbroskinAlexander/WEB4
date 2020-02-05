package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession()).getLastReport();
    }

    public void updateReport(Long earnings, Long soldCars) {
        new DailyReportDao(sessionFactory.openSession()).updateReport(earnings, soldCars);
    }

    public void newDay() {
        new DailyReportDao(sessionFactory.openSession()).addNewDay();
    }

    public void Delete() {
        new DailyReportDao(sessionFactory.openSession()).deleteAllReports();
    }
}
