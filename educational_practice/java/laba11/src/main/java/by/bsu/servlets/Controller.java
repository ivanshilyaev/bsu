package by.bsu.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String line = req.getParameter("numbers");
        System.out.println(line);
        String[] array = line.split(",");
        int[] numbers = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            numbers[i] = Integer.parseInt(array[i]);
        }
        Arrays.sort(numbers);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numbers.length - 1; ++i) {
            result.append(numbers[i]);
            result.append(", ");
        }
        result.append(numbers[numbers.length - 1]);
        req.setAttribute("sortedNumbers", result);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
