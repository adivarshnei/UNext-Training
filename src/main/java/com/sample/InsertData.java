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

public class InsertData extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java", "root", "adersh")) {
                if (req.getParameter("isSelected").equals("0")) {
                    out.println("<form action=\"InsertData\" method=\"post\">");
                    out.println(
                            "<input type=\"hidden\" name=\"isSelected\" value=\"1\">");
                    out.println("ID: <input type=\"text\" name=\"ID\" /><br>");
                    out.println("Name: <input type=\"text\" name=\"Name\" /><br>");
                    out.println(
                            "Designation: <select name=\"Designation\" id=\"Designation\">");
                    out.println("<option value=\"Associate\">Associate</option>");
                    out.println("<option value=\"Lead\">Lead</option>");
                    out.println("<option value=\"Manager\">Manager</option>");
                    out.println("</select><br>");
                    out.println("Age: <input type=\"text\" name=\"Age\" /><br>");
                    out.println("<input type=\"submit\" value=\"Insert\" />");
                    out.println("</form>");
                } else if (req.getParameter("isSelected").equals("1")) {
                    String query = "INSERT INTO EMPLOYEE (ID, NAME, DESIGNATION, AGE) VALUES ("
                            + req.getParameter("ID") + ", \""
                            + req.getParameter("Name") + "\", \""
                            + req.getParameter("Designation") + "\", "
                            + req.getParameter("Age") + ")";
                    Statement stmt = conn.createStatement();
                    int recordsInserted = stmt.executeUpdate(query);
                    out.println("Query executed: " + query + "<br>");

                    out.println(recordsInserted +
                            (recordsInserted == 1 ? " record inserted."
                                    : " records inserted.")
                            + "<br>");

                    out.println("<input " + "type=\"button\" "
                            + "onclick=\"window.location.href='.'\" "
                            + "value=\"Main Menu\">");

                    query = "SELECT * FROM EMPLOYEE";
                    ResultSet rs = stmt.executeQuery(query);

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
                        out.println("<td>" + rs.getString("AGE") + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");

                    rs.close();
                    stmt.close();
                }
                conn.close();
            } catch (Exception e) {
                out.println(e + "<br>");
                e.printStackTrace();
                out.println("<input " + "type=\"button\" "
                        + "onclick=\"window.location.href='.'\" "
                        + "value=\"Main Menu\">");
            }
        } catch (Exception e) {
            out.println(e + "<br>");
            out.println("<input " + "type=\"button\" "
                    + "onclick=\"window.location.href='.'\" " + "value=\"Main Menu\">");
        }
    }
}
