package by.bsu.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final String fileName =
            "/Users/ivansilaev/Desktop/bsu/educational_practice/java/laba11_2/src/main/resources/text.txt";

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
        String word = req.getParameter("word");
        int count = 0;

        // searching word
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                while (line.contains(word)) {
                    ++count;
                    line = line.replaceFirst(word, "");
                }
                line = bufferedReader.readLine();
            }
        }

        String result = "Введённое слово встречается в тексте " + count + " раз";
        req.setAttribute("result", result);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
