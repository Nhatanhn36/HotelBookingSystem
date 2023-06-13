package HotelBookingSystem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDB {
    private DataSource dataSource;

    public HotelDB(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Hotel> getHotel() throws Exception {
        List<Hotel> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try{
            String url = "jdbc:mysql://localhost:3306/hotelbooking";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url,username,password);

            String sql = "select * from hotels order by hotel_name";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()){
                int id = myRs.getInt("hotel_id");
                String hotel_name = myRs.getString("hotel_name");
                String address = myRs.getString("address");
                String phone = myRs.getString("phone");
                String email = myRs.getString("email");

                Hotel hotel = new Hotel(id, hotel_name, address, phone,email);

                students.add(hotel);
            }
            return students;
        }
        finally {
            close(myConn, myStmt, myRs);
        }
    }

    public List<Booking> getBooking(int bookingId) throws Exception {
        List<Booking> bookings = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get a database connection
            connection = dataSource.getConnection();

            // Create SQL query to retrieve booking details
            String sql = "SELECT * FROM bookings WHERE booking_id = ?";
            statement = connection.prepareStatement(sql);

            // Set the parameter values
            statement.setInt(1, bookingId);

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("booking_id");
                String status = resultSet.getString("status");

                // Create a Booking object
                Booking booking = new Booking(id, status);

                // Add the booking to the list
                bookings.add(booking);
            }

        } finally {
            // Close the database resources
            close(connection, statement, resultSet);
        }

        return bookings;
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if (myRs != null){
                myRs.close();
            }

            if (myStmt != null){
                myStmt.close();
            }

            if (myConn != null){
                myConn.close();
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public void bookingHotel(Hotel hotel) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            String url = "jdbc:mysql://localhost:3306/hotelbooking";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            String sql = "insert into hotels" + "(hotel_name, address,phone, email)" + "values (?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, hotel.getHotel_name());
            myStmt.setString(2, hotel.getAddress());
            myStmt.setString(3, hotel.getPhone());
            myStmt.setString(4, hotel.getEmail());

            myStmt.execute();
        }
        finally {
            close(myConn, myStmt, null);
        }
    }

    public Hotel hotelDetails(String HotelID) throws Exception{
        Hotel hotel = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int hotel_id;

        try{
            hotel_id = Integer.parseInt(HotelID);

            String url = "jdbc:mysql://localhost:3306/hotelbooking";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(url, username, password);

            String sql = "select * from hotels where hotel_id=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, hotel_id);

            myRs = myStmt.executeQuery();

            if (myRs.next()){
                String hotel_name = myRs.getString("hotel_name");
                String address = myRs.getString("address");
                String phone = myRs.getString("phone");
                String email = myRs.getString("email");


                hotel = new Hotel(hotel_id, hotel_name, address,phone, email);
            }
            else {
                throw new Exception("Could not find student id: " + hotel_id);
            }
            return hotel;
        }
        finally {
            close(myConn, myStmt, myRs);
        }
    }







    public boolean checkLogin(String username, String password) throws SQLException {
        boolean result = false;
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try{
            String url = "jdbc:mysql://localhost:3306/hotelbooking";
            String us = "root";
            String ps = "";
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(url, us, ps);

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void register(User theUser) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try{
            String url = "jdbc:mysql://localhost:3306/hotelbooking";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(url, username, password);

            String sql = "insert into users" + "(username, password, full_name, email, phone)" + "values (?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, theUser.getUsername());
            myStmt.setString(2, theUser.getPassword());
            myStmt.setString(3, theUser.getFullname());
            myStmt.setString(4, theUser.getEmail());
            myStmt.setString(5, theUser.getPhone());

            myStmt.execute();
        }
        finally {
            close(myConn, myStmt, null);
        }
    }




    public void updateBooking(Booking theBooking) throws Exception { //update cho admin
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            String url = "jdbc:mysql://localhost:3306/hotelbooking";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(url, username, password);

            String sql = "UPDATE bookings SET status = ? WHERE id=?";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, theBooking.getStatus());
            myStmt.setInt(2, theBooking.getId());
            myStmt.execute();
        }
        finally {
            close(myConn, myStmt, null);
        }
    }

    public void deleteBooking(String theStudentId) throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            int studentId = Integer.parseInt(theStudentId);

            String url = "jdbc:mysql://localhost:3306/hotelbooking";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(url, username, password);

            String sql = "delete from bookings where id=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, studentId);

            myStmt.execute();
        }
        finally {
            close(myConn, myStmt, null);
        }
    }


}