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

public class UpdateData extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      try (Connection conn = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/java", "root", "adersh")) {
        if (req.getParameter("isSelected").equals("2")) {
          String query = "";

          if (req.getParameter("Column").equals("ID")) {
            query = "UPDATE EMPLOYEE SET NAME = \"" + req.getParameter("Name")
                + "\", DESIGNATION = \"" + req.getParameter("Designation")
                + "\", AGE = " + req.getParameter("Age") + " WHERE ID = "
                + req.getParameter("rowSelected");
          } else if (req.getParameter("Column").equals("Name")) {
            query = "UPDATE EMPLOYEE SET ID = " + req.getParameter("ID")
                + ", DESIGNATION = \"" + req.getParameter("Designation")
                + "\", AGE = " + req.getParameter("Age") + " WHERE NAME = \""
                + req.getParameter("rowSelected") + "\"";
          } else if (req.getParameter("Column").equals("Designation")) {
            query = "UPDATE EMPLOYEE SET ID = " + req.getParameter("ID")
                + ", NAME = \"" + req.getParameter("Name")
                + "\", AGE = " + req.getParameter("Age")
                + " WHERE DESIGNATION = \"" + req.getParameter("rowSelected")
                + "\"";
          } else if (req.getParameter("Column").equals("Age")) {
            query = "UPDATE EMPLOYEE SET ID = " + req.getParameter("ID")
                + ", NAME = \"" + req.getParameter("Name")
                + "\", DESIGNATION = \"" + req.getParameter("Designation")
                + "\" WHERE AGE = " + req.getParameter("rowSelected");
          }

          out.println("Query Executed: " + query + "<br>");

          Statement stmt = conn.createStatement();
          int rowsUpdated = stmt.executeUpdate(query);

          out.println(rowsUpdated + ((rowsUpdated == 1) ? " row" : " rows")
              + " updated.<br>");
        }

        this.printTable(out, conn);

        if (req.getParameter("isSelected").equals("0")) {
          out.println("<form action=\"UpdateData\" method=\"post\">");
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

            out.println("<form action=\"UpdateData\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"Column\" value=\"ID\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println(
                "For Row: <select name=\"rowSelected\" id=\"rowSelected\">");

            for (Integer id : ids) {
              out.println("<option value=" + id + ">" + id + "</option>");
            }

            out.println("</select><br>");
            out.println("New Name: <input type=\"text\" name=\"Name\"><br>");
            out.println(
                "New Designation: <select name=\"Designation\" id=\"Designation\">");
            out.println("<option value=\"Associate\">Associate</option>");
            out.println("<option value=\"Lead\">Lead</option>");
            out.println("<option value=\"Manager\">Manager</option>");
            out.println("</select><br>");
            out.println("New Age: <input type=\"text\" name=\"Age\"><br>");
            out.println("<input type=\"submit\" value=\"Update\">");
            out.println("</form>");
          } else if (req.getParameter("Column").equals("Name")) {
            ArrayList<String> names = new ArrayList<String>();

            while (rs.next()) {
              names.add(rs.getString("Name"));
            }

            out.println("<form action=\"UpdateData\" method=\"post\">");
            out.println(
                "<input type=\"hidden\" name=\"Column\" value=\"Name\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println(
                "For Row: <select name=\"rowSelected\" id=\"rowSelected\">");

            for (String name : names) {
              out.println("<option value=" + name + ">" + name + "</option>");
            }

            out.println("</select><br>");
            out.println("New ID: <input type=\"text\" name=\"ID\"><br>");
            out.println(
                "New Designation: <select name=\"Designation\" id=\"Designation\">");
            out.println("<option value=\"Associate\">Associate</option>");
            out.println("<option value=\"Lead\">Lead</option>");
            out.println("<option value=\"Manager\">Manager</option>");
            out.println("</select><br>");
            out.println("New Age: <input type=\"text\" name=\"Age\"><br>");
            out.println("<input type=\"submit\" value=\"Update\">");
            out.println("</form>");
          } else if (req.getParameter("Column").equals("Designation")) {
            ArrayList<String> designations = new ArrayList<String>();

            while (rs.next()) {
              designations.add(rs.getString("Designation"));
            }

            out.println("<form action=\"UpdateData\" method=\"post\">");
            out.println(
                "<input type=\"hidden\" name=\"Column\" value=\"Designation\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println(
                "For Row: <select name=\"rowSelected\" id=\"rowSelected\">");

            for (String designation : designations) {
              out.println("<option value=" + designation + ">" + designation
                  + "</option>");
            }

            out.println("</select><br>");
            out.println("New ID: <input type=\"text\" name=\"ID\"><br>");
            out.println("New Name: <input type=\"text\" name=\"Name\"><br>");
            out.println("New Age: <input type=\"text\" name=\"Age\"><br>");
            out.println("<input type=\"submit\" value=\"Update\">");
            out.println("</form>");
          } else if (req.getParameter("Column").equals("Age")) {
            ArrayList<Integer> ages = new ArrayList<Integer>();

            while (rs.next()) {
              ages.add(rs.getInt("Age"));
            }

            out.println("<form action=\"UpdateData\" method=\"post\">");
            out.println(
                "<input type=\"hidden\" name=\"Column\" value=\"Age\">");
            out.println(
                "<input type=\"hidden\" name=\"isSelected\" value=\"2\">");
            out.println(
                "For Row: <select name=\"rowSelected\" id=\"rowSelected\">");

            for (Integer age : ages) {
              out.println("<option value=" + age + ">" + age + "</option>");
            }

            out.println("</select><br>");
            out.println("New ID: <input type=\"text\" name=\"ID\"><br>");
            out.println("New Name: <input type=\"text\" name=\"Name\"><br>");
            out.println(
                "New Designation: <select name=\"Designation\" id=\"Designation\">");
            out.println("<option value=\"Associate\">Associate</option>");
            out.println("<option value=\"Lead\">Lead</option>");
            out.println("<option value=\"Manager\">Manager</option>");
            out.println("</select><br>");
            out.println("<input type=\"submit\" value=\"Update\">");
            out.println("</form>");
          }

          rs.close();
          stmt.close();
        }

        conn.close();
      } catch (Exception e) {
        out.println(e);
        out.println("<input " + "type=\"button\" "
            + "onclick=\"window.location.href='.'\" "
            + "value=\"Main Menu\">");
      }
    } catch (

    Exception e) {
      out.println(e);
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
