package com.gmail.ola;

import com.gmail.ola.models.Attendee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AttendeeController", value = "/attendee")
public class AttendeeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstname = request.getParameter("firstname");
        String efternamn = request.getParameter("lastname");
        String email = request.getParameter("email");

        String food = request.getParameter("food");

        String drinks = request.getParameter("drinks");

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rsKeys = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://nackaola.ck6l2ktgcf8g.us-west-2.rds.amazonaws.com:3306/Nackevent","rosetta","NackEvent2017");
            stm = con.prepareStatement("INSERT INTO Attendee (Firstname, Lastname, Email) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, firstname);
            stm.setString(2, efternamn);
            stm.setString(3, email);
            stm.executeUpdate();
            ResultSet rsKey = stm.getGeneratedKeys();
            rsKey.next();

            int newKey = rsKey.getInt(1);
            List<String> options = new ArrayList<String>(Arrays.asList(drinks));

            options.add(food);
            stm = con.prepareStatement("INSERT INTO Val (Name, Attendeeid) VALUES(?,?)");

            for (String o : options) {
                stm.setString(1, o);
                stm.setInt(2, newKey);
                stm.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rsKeys != null)
                    rsKeys.close();
                if (stm != null)
                    stm.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        response.sendRedirect("/index.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://nackaola.ck6l2ktgcf8g.us-west-2.rds.amazonaws.com:3306/Nackevent","rosetta","NackEvent2017");
            stm = con.createStatement();
            rs = stm.executeQuery("SELECT * FROM Attendee");

            List<Attendee> attendeeList = new ArrayList<>();
            while (rs.next()) {
                attendeeList.add(new Attendee(rs.getString("Firstname"),
                        rs.getString("Lastname"),
                        rs.getString("Email")));
            }
            request.setAttribute("attendeeList", attendeeList);
            request.getRequestDispatcher("/attendees.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stm != null)
                    stm.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}
