package lk.sliit.pafassignment;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "CustomerAPI", value = "/CustomerAPI")
public class CustomerAPI extends HttpServlet {
    private final Customer customer = new Customer();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String output = customer.insert(request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("nic"),
                request.getParameter("mobileNo"),
                request.getParameter("email"),
                request.getParameter("address")
        );
        response.getWriter().write(output);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> map = getParasMap(request);
        String output = customer.delete(Long.valueOf(map.get("customerId")));
        response.getWriter().write(output);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> map = getParasMap(request);
        String output = customer.update(
                Long.valueOf(map.get("customerId")),
                map.get("firstName"),
                map.get("lastName"),
                map.get("nic"),
                map.get("mobileNo"),
                map.get("email"),
                map.get("address")
        );
        response.getWriter().write(output);

    }

    private static Map<String, String> getParasMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        try {
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
            String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
            scanner.close();
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] p = param.split("=");
                map.put(p[0], URLDecoder.decode(p[1], "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
