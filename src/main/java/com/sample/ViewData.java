package com.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewData extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java", "root", "adersh")) {
                String query = "SELECT * FROM EMPLOYEE";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                out.println("Query Executed: " + query + "<br>");

                out.println("<input " + "type=\"button\" "
                        + "onclick=\"window.location.href='.'\" "
                        + "value=\"Main Menu\">");

                out.println("<table " + "width=\"100%\" " + "cellspacing=\"1\" "
                        + "cellpadding=\"5\" " + "bgcolor=\"#000000\">");
                out.println("<tr bgcolor=\"#FFFFFF\">");
                out.println("<th>ID</th>");
                out.println("<th>NAME</th>");
                out.println("<th>DESIGNATION</th>");
                out.println("<th>AGE</th>");
                out.println("</tr>");

                while (rs.next()) {
                    out.println("<tr bgcolor=\"#FFFFFF\">");
                    out.println("<td>" + rs.getInt("ID") + "</td>");
                    out.println("<td>" + rs.getString("NAME") + "</td>");
                    out.println("<td>" + rs.getString("DESIGNATION") + "</td>");
                    out.println("<td>" + rs.getInt("AGE") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                out.println(e);
                out.println("<input " + "type=\"button\" "
                        + "onclick=\"window.location.href='.'\" "
                        + "value=\"Main Menu\">");
            }
        } catch (Exception e) {
            out.println(e);
            out.println("<input " + "type=\"button\" "
                    + "onclick=\"window.location.href='.'\" " + "value=\"Main Menu\">");
        }
    }
}
