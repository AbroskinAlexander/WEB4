package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        Long price = Long.parseLong(req.getParameter("price"));
        Car car = new Car(brand, model, licensePlate, price);
        if (CarService.getInstance().addCar(car)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
