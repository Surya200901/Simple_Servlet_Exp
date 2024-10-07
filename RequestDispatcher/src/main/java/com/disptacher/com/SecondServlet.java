package com.disptacher.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SecondServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String Prdname = (String) req.getAttribute("pname");
        String Pquantity = (String) req.getAttribute("pqty");

        if (Pquantity == null || Pquantity.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity is missing or invalid");
            return;
        }

        int prdqyt;
        try {
            prdqyt = Integer.parseInt(Pquantity);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format for quantity");
            return;
        }

        // Business logic
        double price = 20000;
        double TotalSum = (prdqyt * price);

        // Presentation logic
        PrintWriter out = resp.getWriter();
        out.println("<html><body bgcolor='#34eb56'> "
                + "<h1>Product Name: " + Prdname + "<br> Quantity: " + Pquantity + "<br>Total sum to be paid: "
                + TotalSum + "</h1></body></html>");
        out.flush();
        out.close();

        // Persistence logic
        Connection con = null;
        PreparedStatement pstmt = null;
        String iquery = "INSERT INTO pentagonspace.product VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pentagonspace?user=root&password=surya");
            pstmt = con.prepareStatement(iquery);
            pstmt.setString(1, Prdname);
            pstmt.setInt(2, prdqyt);
            pstmt.setDouble(3, TotalSum);
            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}