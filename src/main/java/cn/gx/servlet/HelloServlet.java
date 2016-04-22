package cn.gx.servlet;

import com.sun.tools.internal.ws.wscompile.ErrorReceiver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by always on 16/3/21.
 */
@WebServlet(name = "HelloServlet",value ="/HelloWorld")
public class HelloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




//        synchronized (getServletContext()){
//            getServletContext().setAttribute("admin","baijuan");
//
//        }

        HttpSession session = request.getSession();
        session.setAttribute("user","shaonan");
        session.setAttribute("user","baijuan");

        PrintWriter writer = response.getWriter();

        writer.write("hello:"+session.getAttribute("user")+"-"+session.getId());
        writer.close();

        response.sendRedirect("hello");

        getServletContext();
        getServletConfig();
//
//        request.getRequestDispatcher("result.jsp").forward(request,response);
//
//        getServletContext().getRequestDispatcher("result.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
