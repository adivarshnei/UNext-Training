package com.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteData extends HttpServlet {
  @Override
      throws ServletException, IOException {
    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      try (Connection conn = DriverManager.getConnection(
          "jdbc:mysql://127.1.1.1:3306/java", "root", "adersh")) {
        if (req.getParameter("isSelected").equals("2")) {
          String query = "";

          if (req.getParameter("Column").equals("ID")) {
            query = "DELETE FROM EMPLOYEE WHERE ID = "
                + req.getParameter("rowSelected");
          } else if (req.getParameter("Column").equals("Name")) {
            query = "DELETE FROM EMPLOYEE WHERE NAME = \""
                + req.getParameter("rowSelected") + "\"";
          } else if (req.getParameter("Column").equals("Designation")) {
            query = "DELETE FROM EMPLOYEE WHERE DESIGNATION = \""
                + req.getParameter("rowSelected") + "\"";
          } else if (req.getParameter("Column").equals("Age")) {
            query = "DELETE FROM EMPLOYEE WHERE AGE = "
                + req.getParameter("rowSelected");
          }

          out.println("Query executed: " + query + "<br>");

          Statement stmt = conn.createStatement();
          int rowsDeleted = stmt.executeUpdate(query);

          out.println(rowsDeleted + ((rowsDeleted == 1) ? " row" : " rows")
              + " updated.<br>");

          stmt.close();
        }

        this.printTable(out, conn);

        if (req.getParameter("isSelected").equals("0")) {
          out.println("<form action=\"DeleteData\" method=\"post\">");
          out.println(
              "<input type=\"hidden\" name=\"isSelected\" value=\"1\">");
          out.println("<select name=\"Column\" id=\"Column\">");
          out.println("<option value=\"ID\">ID</option>");
          out.println("<option value=\"Name\">Name</option>");
          out.println("<option value=\"Designation\">Designation</option>");
          out.println("<option value=\"Age\">Age</option>");
          out.println("</select><br>");
          out.println("<input type=\"submit\" value=\"Next\">");
          out.println("</form>");
        } else if (req.getParameter("isSelected").equals("1")) {
          String query = "SELECT * FROM EMPLOYEE";
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(query);
          if (req.getParameter("Column").equals("ID")) {
            ArrayList<Integer> ids = new ArrayList<Integer>();

            while (rs.next()) {
              ids.add(rs.getInt("ID"));
            }

            out.println("<form action=\"DeleteData\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"Column\" value=\"ID\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println("Delete Row with: "
                + "<select name=\"rowSelected\" id=\"rowSelected\">");

            for (Integer id : ids) {
              out.println("<option value=" + id + ">" + id + "</option>");
            }

            out.println("</select><br>");
            out.println("<input type=\"submit\" value=\"Delete\">");
            out.println("</form>");
          } else if (req.getParameter("Column").equals("Name")) {
            ArrayList<String> names = new ArrayList<String>();

            while (rs.next()) {
              names.add(rs.getString("Name"));
            }

            out.println("<form action=\"DeleteData\" method=\"post\">");
            out.println(
                "<input type=\"hidden\" name=\"Column\" value=\"Name\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println("Delete Row with Name: "
                + "<select name=\"rowSelected\" id=\"rowSelected\">");

            for (String name : names) {
              out.println("<option value=" + name + ">" + name + "</option>");
            }

            out.println("</select><br>");
            out.println("<input type=\"submit\" value=\"Delete\">");
            out.println("</form>");
          } else if (req.getParameter("Column").equals("Designation")) {
            ArrayList<String> designations = new ArrayList<String>();

            while (rs.next()) {
              designations.add(rs.getString("Name"));
            }

            out.println("<form action=\"DeleteData\" method=\"post\">");
            out.println(
                "<input type=\"hidden\" name=\"Column\" value=\"Name\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println("Delete Row with Designation: "
                + "<select name=\"rowSelected\" id=\"rowSelected\">");

            for (String designation : designations) {
              out.println("<option value=" + designation + ">" + designation
                  + "</option>");
            }

            out.println("</select><br>");
            out.println("<input type=\"submit\" value=\"Delete\">");
            out.println("</form>");
          } else if (req.getParameter("Column").equals("Age")) {
            ArrayList<Integer> ages = new ArrayList<Integer>();

            while (rs.next()) {
              ages.add(rs.getInt("Age"));
            }

            out.println("<form action=\"DeleteData\" method=\"post\">");
            out.println(
                "<input type=\"hidden\" name=\"Column\" value=\"Age\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println("Delete Row with Age: "
                + "<select name=\"rowSelected\" id=\"rowSelected\">");
            for (Integer age : ages) {
              out.println("<option value=" + age + ">" + age + "</option>");
            }

            out.println("</select><br>");
            out.println("<input type=\"submit\" value=\"Delete\">");
            out.println("</form>");
          }

          rs.close();
          stmt.close();
        }

        conn.close();
      } catch (Exception e) {
        out.println(e);
        System.out.println(e.getStackTrace()[0].getLineNumber());
        out.println("<input " + "type=\"button\" "
            + "onclick=\"window.location.href='.'\" "
            + "value=\"Main Menu\">");
      }
    } catch (Exception e) {
      out.println(e);
      System.out.println(e.getStackTrace()[0].getLineNumber());
      out.println("<input " + "type=\"button\" "
          + "onclick=\"window.location.href='.'\" "
          + "value=\"Main Menu\">");
    }
  }

  private void printTable(PrintWriter out, Connection conn)
      throws SQLException {
    String query = "SELECT * FROM EMPLOYEE";
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(query);
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
      out.println("<td>" + rs.getString("AGE") + "</td>");
      out.println("</tr>");
    }

    out.println("</table>");

    rs.close();
    stmt.close();
  }
}
