
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Random;

public class TCPServer {

    public static void main(String[] args) throws IOException {

        Client clientManager = new Client();
        Reservation reserverationManager = new Reservation();

        System.out.println("Opening Application ...");
        ServerSocket Server = new ServerSocket(9909);
        Socket ClientSocket = Server.accept();
        System.out.println("Connection Created ");
        BufferedReader inputfromClient = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
        PrintWriter outToClient = new PrintWriter(ClientSocket.getOutputStream(), true);
        Random random = new Random();

        String clientFile = "Clients.csv";
        File file1 = new File(clientFile);
        String ResFile = "Reservations.csv";
        File file2 = new File(ResFile);
        try {
            Scanner inputStream1 = new Scanner(file1);
            while (inputStream1.hasNext()) {
                String data = inputStream1.next();
                String[] sentence = data.split(",");
                try {
                    clientManager.addClient(new Client(Integer.parseInt(sentence[0]), (sentence[1])));
                } catch (Exception e) {
                }
            }

            Scanner inputStream2 = new Scanner(file2);
            while (inputStream2.hasNext()) {
                String data = inputStream2.next();
                String[] sentence = data.split(",");
                try {
                    reserverationManager.insertReservation(new Reservation(Integer.parseInt(sentence[0]),
                            Integer.parseInt(sentence[1]), (sentence[2].replace('#' , ' ')), (sentence[3]),(sentence[4]),
                            (sentence[5]),  (sentence[6]), (sentence[7]), Integer.parseInt(sentence[8])));

                } catch (Exception e) {
                }
            }
            inputStream1.close();
            inputStream2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String choice;
        int PhoneNumber = Integer.parseInt(inputfromClient.readLine());
        String isFound = "No";

        {
            for (int i = 0; i < clientManager.getAll().size(); i++)
                if (PhoneNumber == clientManager.getAll().get(i).getPhoneNumber())
                    isFound = "Yes";
            outToClient.println(isFound);
        }

        if (isFound.equalsIgnoreCase("Yes")) {
            for (int i = 0; i < clientManager.getAll().size(); i++)
                if (PhoneNumber == clientManager.getAll().get(i).getPhoneNumber())
                    outToClient.println(clientManager.getAll().get(i).getName());
        }

        String response = inputfromClient.readLine();

        {
            if (response.equalsIgnoreCase("creating account declined")) {
                Server.close();
                System.exit(0);
            }

            if (response.equalsIgnoreCase("account creating approved")) {
                String col_2 = inputfromClient.readLine();
                Client p = new Client(PhoneNumber, col_2);
                clientManager.addClient(p);
                response = inputfromClient.readLine();
            }
        }

        String first_entry = "yes";
        do {System.out.println("here");
            if (!first_entry.equalsIgnoreCase("yes"))
                choice = inputfromClient.readLine();
            else
                choice = response;

            switch (choice) {
                case "1": // --DISPLAY RESERVATIONS--
                    outToClient.println(reserverationManager.displayReservation(PhoneNumber));
                    first_entry = "no";
                    break;

                case "2": // --MAKE RESERVATION--
                    outToClient.println(reserverationManager.displayStadiums());
                    String stadium = inputfromClient.readLine(); 
                    outToClient.println("Enter year ->");
                    String year = inputfromClient.readLine();
                    outToClient.println("Enter month ->");
                    String month = inputfromClient.readLine();
                    outToClient.println("Enter day ->");
                    String day = inputfromClient.readLine();
                    outToClient.println(reserverationManager.reservedStadiumsTime(year, month, day));
                    String hour = inputfromClient.readLine();
                    String to = inputfromClient.readLine();
                    
                    int no_hours = Integer.parseInt(to) - Integer.parseInt(hour);
                    int updatedResNumber = random.nextInt(1000);
                    Reservation reservation = new Reservation(updatedResNumber, PhoneNumber, stadium, year, month, day, hour, to, no_hours);
                    String r = reserverationManager.reserve(reservation);
                    System.out.println(to);
                    System.out.println(r);
                    outToClient.println(r);
                    if (!r.equalsIgnoreCase("RESERVATION COMPLETED"))
                    	break;
                    System.out.println(reserverationManager.getAllReservations().size());
                    outToClient.println("Reservation number: " + updatedResNumber + "#" + "in Stadium: " + stadium
                            + "#" + "At: " + hour + ":00 to " + to + ":00#" + "your price will be: "
                            + reserverationManager.receipt(stadium, no_hours));
                    first_entry = "no";
                    break;

                case "3": // --UPDATE RESERVATION--
                    outToClient.println(reserverationManager.displayReservation(PhoneNumber));
                    if (!(reserverationManager.displayReservation(PhoneNumber)
                            .equalsIgnoreCase("There's no reservations"))) {
                        outToClient.println("Choose reservation number you wish to update: ");
                        int nbUpdateReservation = Integer.parseInt(inputfromClient.readLine());
                        outToClient.println(
                                "choose Stadium " +
                                        "#1) King Fahd International Stadium" +
                                        "#2) Mrsool Park" +
                                        "#3) Prince Faisal bin Fahd Stadium");
                        String stadiumName = inputfromClient.readLine();
                        outToClient.println("Enter year ->");
                        String year_ = inputfromClient.readLine();
                        outToClient.println("Enter month ->");
                        String month_ = inputfromClient.readLine();
                        outToClient.println("Enter day ->");
                        String day_ = inputfromClient.readLine();
                        String hour_ = inputfromClient.readLine();
                        String to_ = inputfromClient.readLine();
                        
                        int no_hours_ = Integer.parseInt(to_) - Integer.parseInt(hour_); 
                        outToClient.println((reserverationManager.updateReservation(nbUpdateReservation,
                                PhoneNumber, stadiumName, year_, month_, day_, hour_, to_, no_hours_)));
                    }
                    else
                    	outToClient.println("There's no reservations");
                    first_entry = "no";
                    break;

                case "4": // --REMOVE RESERVATION--
                    outToClient.println(reserverationManager.displayReservation(PhoneNumber));
                    if (!(reserverationManager.displayReservation(PhoneNumber)
                            .equalsIgnoreCase("You don't have any reservation"))) {
                        System.out.println(reserverationManager.getAllReservations().size());
                        int numberOfAppointmentWantsToDelete = Integer.parseInt(inputfromClient.readLine());
                        outToClient.println(
                                reserverationManager.removeReservation(numberOfAppointmentWantsToDelete, PhoneNumber));
                        System.out.println(reserverationManager.getAllReservations().size());
                    }
                    first_entry = "no";
                    break;

                case "5": // --EXIT--
                    outToClient.println("Thank You, Hava A Nice Day");
                    PrintWriter writeClientCSV = new PrintWriter(file1);
                    PrintWriter writeReservationCSV = new PrintWriter(file2);
                    int to_keep_header1 = 0;
                    int to_keep_header2 = 0;
                    for (int i = 0; i < clientManager.getAll().size(); i++) {
                        if (to_keep_header1 == 0) {
                            writeClientCSV.println("Client Phone Number,name");
                        }
                        writeClientCSV.printf("%d,%s\n",
                                clientManager.getAll().get(i).getPhoneNumber(),
                                clientManager.getAll().get(i).getName());
                        to_keep_header1 = 1;

                    }
                    writeClientCSV.close();
                    for (int i = 0; i < reserverationManager.getAllReservations().size(); i++) {
                        if (to_keep_header2 == 0) {
                            writeReservationCSV
                                    .println("Reservation_No,Phone,Stadium,year,month,day,from,to,no_hours");
                        }
                        writeReservationCSV.printf("%d,%d,%s,%s,%s,%s,%s,%s,%d\n",
                                reserverationManager.getAllReservations().get(i).getReservationNumber(),
                                reserverationManager.getAllReservations().get(i).getPhoneNumber(),
                                reserverationManager.getAllReservations().get(i).getStadium_Name().replace(' ', '#'),
                                reserverationManager.getAllReservations().get(i).getYear(),
                                reserverationManager.getAllReservations().get(i).getMonth(),
                                reserverationManager.getAllReservations().get(i).getDay(),
                                reserverationManager.getAllReservations().get(i).getHour(),
                                reserverationManager.getAllReservations().get(i).getTo(),
                                reserverationManager.getAllReservations().get(i).getno_hours());
                        to_keep_header2 = 1;

                    }
                    writeReservationCSV.close();
                    Server.close();
                    System.exit(0);
            }
        } while (true);

    }

}