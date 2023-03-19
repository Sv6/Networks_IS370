
import java.util.ArrayList;

public class Reservation {
    private int ReservationNumber,PhoneNumber;
    private String Stadium_Name, year,month,day,hour,to;
    private int no_hours;
    private ArrayList<Reservation> allReservations = new ArrayList<Reservation>();

    public Reservation() {

    }

    public Reservation(int Res_no, int phoneNumber, String sN, String year, String month, String day,
                       String hour, String to, int no_hours) {
        ReservationNumber = Res_no;
        PhoneNumber = phoneNumber;
        Stadium_Name = sN;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.to = to;
        this.no_hours = no_hours;
    }

    public void insertReservation(Reservation A) {
        allReservations.add(A);
    }

    private int countReservations(int clientPhone) {
        int count = 0;
        for (int i = 0; i < allReservations.size(); i++)
            if (clientPhone == allReservations.get(i).getPhoneNumber())
                count++;
        return count;
    }

    public String displayReservation(int clientPhone) {
        String reservationInfo = "";
        char zero = '0';
        for (int i = 0; i < allReservations.size(); i++) {
            if (clientPhone == allReservations.get(i).getPhoneNumber()) {
                if (allReservations.get(i).getTo().length() == 1)
                    allReservations.get(i).setTo(zero + allReservations.get(i).getTo());
                reservationInfo += "Retrieving Information...";
                if (countReservations(clientPhone) == 0) {
                    reservationInfo = "There's no reservations";
                    return reservationInfo;
                } else if (countReservations(clientPhone) == 1)
                    reservationInfo += "#You have "
                            + countReservations(clientPhone)
                            + " Reservations#";
                else
                    reservationInfo += "#You have "
                            + countReservations(clientPhone)
                            + " Reservations >>";

                reservationInfo += "#Reservation Number: " + allReservations.get(i).getReservationNumber() + "#Date: "
                        + allReservations.get(i).getYear() + "/" + allReservations.get(i).getMonth() + "/"
                        + allReservations.get(i).getDay() + " At " + allReservations.get(i).getHour() + ":00" + " to "
                        + allReservations.get(i).getTo() + ":00 #At " + allReservations.get(i).getStadium_Name()
                        + "#price: " + allReservations.get(i).receipt(allReservations.get(i).getStadium_Name(),
                        allReservations.get(i).getNo_hours());

                if (countReservations(clientPhone) == 1)
                    return reservationInfo + "# #Information Printed..#";

                for (int j = 1; j < allReservations.size(); j++) {
                    if (clientPhone == allReservations.get(j).getPhoneNumber()) {
                        if (allReservations.get(j).getTo().length() == 1)
                            allReservations.get(j).setTo(zero + allReservations.get(j).getTo());
                        reservationInfo += "# #Reservation Number: " //check later ------------t3del------------
                                + allReservations.get(j).getReservationNumber() + "#Date: "
                                + allReservations.get(j).getYear() + "/" + allReservations.get(j).getMonth() + "/"
                                + allReservations.get(j).getDay() + " At " + allReservations.get(j).getHour() + ":00 "
                                + "to " + allReservations.get(j).getTo() + ":00 #At "
                                + allReservations.get(j).getStadium_Name() + "The price: "
                                + allReservations.get(j).receipt(allReservations.get(j).getStadium_Name(),
                                allReservations.get(j).getNo_hours());
                    }
                }
                return reservationInfo + "# #Information Printed..";
            }

        }
        return "There's no Reservations";
    }


    public String displayStadiums() {
        return "No.    Stadium Name :                     Working hours          price per hour" +
                "#1)    King Fahd International Stadium    08:00 to 23:00             10000 SR" +
                "#2)    Mrsool Park                        08:00 to 23:00             8000 SR" +
                "#3)    Prince Faisal bin Fahd Stadium     13:00 to 22:00             6000 SR#";
    }

    // Display times that can't reserve on it
    public String reservedStadiumsTime(String year, String month, String day) {

        for (int i = 0; i < allReservations.size(); i++) {
            if (allReservations.get(i).getYear().equals(year) && allReservations.get(i).getMonth().equals(month)
                    && allReservations.get(i).getDay().equals(day)) {
                return "Stadium name            reserved hours" + allReservations.get(i).getStadium_Name()
                        + "         From " + allReservations.get(i).getHour() + ":00 to "
                        + allReservations.get(i).getTo() + ":00";

            }
        }

        return "This day is available, choose a time to book";
    }

    public String reserve(Reservation a) {
    	for(Reservation r:allReservations) {
    		
    		int af = Integer.parseInt(a.hour);
    		int at = Integer.parseInt(a.to);
    		int rf = Integer.parseInt(r.hour);
    		int rt = Integer.parseInt(r.to);
    		System.err.println(af);
    		if ((a.Stadium_Name == r.Stadium_Name && a.year==r.year && a.month==r.month && a.day == r.day && ((af <= rt && rt <= at) ||( af<= rf && rf <=at)) ))
    			return "RESERVATION FAILED"; //-----------------------------------------T3DEL------------------------------
    	}
        	allReservations.add(a);
        return "RESERVATION COMPLETED";

    }

    public int receipt(String s, int no_hours) {
        int receipt = 0;

        switch (s) {
        	case "1" :
        		;
            case "King Fahd International Stadium":
                receipt = 10000 * no_hours;
                break;
            case "2" :
        		;
            case "Mrsool Park" :
                receipt = 8000 * no_hours;
                break;
            case "3" :
        		;
            case "Prince Faisal bin Fahd Stadium":
                receipt = 6000 * no_hours;
                break;
        }
        return receipt;
    }

    public String updateReservation(int res_no, int PhoneNumber, String std_name, String year, String month,
                                    String day, String hour, String to, int no_hours) {
        ArrayList<Integer> Reservations = new ArrayList<>();

        for (int i = 0; i < allReservations.size(); i++) {
            if (allReservations.get(i).getPhoneNumber() == PhoneNumber) {
                Reservations.add(allReservations.get(i).getReservationNumber());
            }
        }

        for (int i = 0; i < Reservations.size(); i++)
            if (Reservations.get(i) == res_no)
                for (int j = 0; j < allReservations.size(); j++)
                    if (allReservations.get(j).getReservationNumber() == res_no) {
                        allReservations.get(j).setStadium_Name(std_name);
                        allReservations.get(j).setYear(year);
                        allReservations.get(j).setMonth(month);
                        allReservations.get(j).setDay(day);
                        allReservations.get(j).setHour(hour);
                        allReservations.get(j).setTo(to);
                        allReservations.get(j).setno_hours(no_hours);
                        return "Your reservation has been updated";
                    }

        return "Reservation Number not valid";

    }

    public String removeReservation(int reservationNumber, int PhoneNumber) {
        ArrayList<Integer> Appointments = new ArrayList<>();

        for (int i = 0; i < allReservations.size(); i++) {
            if (allReservations.get(i).getPhoneNumber() == PhoneNumber) {
                Appointments.add(allReservations.get(i).getReservationNumber());
            }
        }

        for (int i = 0; i < Appointments.size(); i++)
            if (Appointments.get(i) == reservationNumber)
                for (int j = 0; j < allReservations.size(); j++)
                    if (allReservations.get(j).getReservationNumber() == reservationNumber) {
                        allReservations.remove(j);
                        return "Reservation is successfully Deleted";
                    }

        return "There's no reservation with that number";

    }


    public int getReservationNumber() { return ReservationNumber; }

    public void setReservationNumber(int reservationNumber) { ReservationNumber = reservationNumber; }

    public int getPhoneNumber() { return PhoneNumber; }

    public void setPhoneNumber(int phoneNumber) { PhoneNumber = phoneNumber; }

    public String getStadium_Name() {
         switch(Stadium_Name) {
             case "1": return "King Fahd International Stadium";
             case "2": return "Mrsool Park";
             case "3": return "Prince Faisal bin Fahd Stadium";
             default: return Stadium_Name;
         }
    }

    public void setStadium_Name(String stadium_Name) { Stadium_Name = stadium_Name; }

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public String getDay() { return day; }

    public void setDay(String day) { this.day = day; }

    public String getHour() { return hour; }

    public void setHour(String hour) { this.hour = hour; }

    public String getTo() { return to; }

    public void setTo(String to) { this.to = to; }

    public int getno_hours() { return no_hours; }

    public void setno_hours(int no_hours) { this.no_hours = no_hours; }

    public int getNo_hours() { return no_hours; }

    public ArrayList<Reservation> getAllReservations() { return allReservations; }

}