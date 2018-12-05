package com.irving.wcs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        String name = req.getParameter("name");
        req.setAttribute("sessionId", sessionId);
        req.setAttribute("name", name);
        req.getRequestDispatcher("/WEB-INF/test.jsp").forward(req, resp);
//        resp.sendRedirect();
    }
}
