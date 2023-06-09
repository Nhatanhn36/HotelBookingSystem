package HotelBookingSystem;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/HotelServlet")
@MultipartConfig
public class HotelServlet extends HttpServlet {
    private static final long serialVersionUID =  1L;

    private HotelDB hotelDB;
    private List<Booking> bookingList;
    @Resource(name="jdbc/hotel_booking_system")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            hotelDB = new HotelDB(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String theCommand = request.getParameter("command");
            if (theCommand == null) {
                theCommand = "list";
            }
            switch (theCommand) {
                case "ADD":
                    addBooking(request, response);
                    break;
                case "LOAD":
                    loadBooking(request, response);
                    break;
                case "UPDATE":
                    updateBooking(request, response);
                    break;
                case "DELETE":
                    deleteBooking(request, response);
                    break;
                case "LOGIN":
                    login(request, response);
                    break;
                case "SEARCH":
                    searchHotel(request, response);
                    break;
                default:
                    listHotels(request, response);
            }
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listHotels(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Hotel> hotels = hotelDB.getHotel();
        request.setAttribute("HOTEL_LIST", hotels);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.jsp");
        dispatcher.forward(request, response);
    }
    private void searchHotel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String hotelId = request.getParameter("hotelId");
        Hotel hotel = hotelDB.hotelDetails(hotelId);
        request.setAttribute("HOTEL", hotel);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/search-hotel.jsp");
        dispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO: Implement login functionality
    }

    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bookingId = request.getParameter("bookingId");
        hotelDB.deleteBooking(bookingId);
        response.sendRedirect(request.getContextPath() + "/HotelServlet");
    }

    private String uploadFile(Part part, String fileName, String uploadDirectory) throws IOException {
        String filePath = uploadDirectory + File.separator + fileName;

        try (InputStream is = part.getInputStream(); FileOutputStream out = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return filePath;
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return "";
    }

    public void updateBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");
        Booking booking = new Booking(bookingId, status);
        hotelDB.updateBooking(booking);
        response.sendRedirect(request.getContextPath() + "/HotelServlet");
    }

    public void loadBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        Booking booking = (Booking) hotelDB.getBooking(bookingId);
        request.setAttribute("BOOKING", booking);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/load-booking.jsp");
        dispatcher.forward(request, response);
    }

    private void addBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = bookingList.size() +1;
        String hotelName = request.getParameter("hotelName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        Hotel hotel = new Hotel(id,hotelName, address, phone, email);
        hotelDB.bookingHotel(hotel);
        response.sendRedirect(request.getContextPath() + "/HotelServlet");
    }
}
