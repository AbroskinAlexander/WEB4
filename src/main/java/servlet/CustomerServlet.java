package servlet;

import com.google.gson.Gson;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> pamMap = new HashMap<>();
        pamMap.put("brand", req.getParameter("brand"));
        pamMap.put("model", req.getParameter("model"));
        pamMap.put("licensePlate", req.getParameter("licensePlate"));
        if (CarService.getInstance().soldCar(pamMap)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus((HttpServletResponse.SC_BAD_REQUEST));

    }
}
