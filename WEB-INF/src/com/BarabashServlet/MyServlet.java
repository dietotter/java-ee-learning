package com.BarabashServlet;

import java.io.*;
import javax.naming.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

    private final String correctUsername = "handsome";
    private final String correctPassword = "stork";

    private String[] parseQuery(String query){
        String[] splitQuery = query.split("[=&]");

        if(splitQuery.length != 4) return null;  // that means there were either empty fields or some '&' or '=' in the query

        String[] res = {splitQuery[1], splitQuery[3]};

        return res;
    }

    private boolean attemptSignIn(String query){
        String[] parsed = parseQuery(query);

        if(parsed != null && parsed[0].equals(correctUsername) && parsed[1].equals(correctPassword))
            return true;

        return false;
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        // check if username and pwd are correct
        boolean success = attemptSignIn(request.getQueryString());

        // if username or password is incorrect
        if(!success){
            response.sendError(401, "Your username or password is SOOO incorrect!!1");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Response</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Hello, handsome stork. Here is your secret information!</h1>");

            out.println("</body>");
            out.println("</html>");



        } finally {

            out.close();

        }

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        processRequest(request, response);

    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        processRequest(request, response);

    }

}

// javac -d ../classes/ -cp "/Users/barabakens/Documents/apache-tomcat-9.0.2/lib/servlet-api.jar" com/BarabashServlet/MyServlet.java