package cn.gx.servlet;

import com.sun.tools.internal.ws.wscompile.ErrorReceiver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        try {
            throw new Exception();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        PrintWriter writer = response.getWriter();

        writer.write("hello");
        writer.close();

        request.getRequestDispatcher("result.jsp").forward(request,response);

        getServletContext().getRequestDispatcher("result.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
