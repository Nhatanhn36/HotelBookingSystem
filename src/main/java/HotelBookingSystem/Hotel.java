package HotelBookingSystem;

public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String address;
    private String phone;
    private String email;

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Hotel(int hotel_id,String hotel_name,String address,String phone,String email){
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}


