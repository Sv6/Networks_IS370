
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {


    static boolean checkPhoneNumber(String phone) {
        if (phone.length() != 9) return false;

        for (int i = 1; i < phone.length(); i++)
            if (phone.charAt(i) < 48 && phone.charAt(i) > 57)
                return false;

        return true;
    }

    static boolean checkName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) < 97 || name.charAt(i) > 123)
                return false;
        }
        return true;
    }

    static boolean checkYear(String year) {
        if (year.length() != 4) return false;
        if (year.charAt(0) != 50) return false;
        if (year.charAt(1) != 48) return false;
        if (year.charAt(2) != 50) return false;
        if (year.charAt(3) > 50 || year.charAt(3) < 49) return false;
        return true;
    }

    static boolean checkMonth(String month) {
        if (month.length() != 1)
            if (month.length() != 2)
                return false;

        if (month.length() == 1)
            if (month.charAt(0) < 49 || month.charAt(0) > 57)
                return false;

        if (month.length() == 2)
            return month.charAt(0) == 49 && month.charAt(1) >= 48 && month.charAt(1) <= 50;
        return true;
    }

    static boolean checkDay(String day) {

        if (day.length() != 1)
            if (day.length() != 2)
                return false;

        if (day.length() == 1)
            if (day.charAt(0) < 49 || day.charAt(0) > 57)
                return false;

        if (day.length() == 2)
            if (day.charAt(0) < 49 || day.charAt(0) > 51 || day.charAt(1) < 48 || day.charAt(1) > 57)
                return false;

        if (day.charAt(0) == 51 && day.length() == 2)
            if (day.charAt(1) != 48 && day.charAt(1) != 49)
                return false;
        return true;
    }

    static boolean checkFrom(String from) {
        if (from.length() != 2)
            return false;

        if (from.charAt(0) != 48 && from.charAt(0) != 49 && from.charAt(0) != 50)
            return false;

        if (from.charAt(0) == 48 || from.charAt(0) == 49)
            if (from.charAt(1) < 48 || from.charAt(1) > 57)
                return false;

        if (from.charAt(0) == 50)
            return from.charAt(1) >= 48 && from.charAt(1) <= 52;
        return true;
    }
    
    static boolean checkBoth(String from,String to) {
    	int x =  Integer.parseInt(to) - Integer.parseInt(from);
    	if (0<x && x <4)
    		return true;
    	return false;
    }
    static boolean checkTo(String to) {
        if (to.length() != 2)
            return false;
        if (to.charAt(0) != 48 && to.charAt(0) != 49 && to.charAt(0) != 50)
            return false;
        if (to.charAt(0) == 48 || to.charAt(0) == 49)
            if (to.charAt(1) < 48 || to.charAt(1) > 57)
                return false;

        if (to.charAt(0) == 50)
            return to.charAt(1) >= 48 && to.charAt(1) <= 52;
        return true;
    }

    static boolean checkHours(String hours) {
        if (hours.length() != 1)
            return false;
        return hours.charAt(0) >= 49 && hours.charAt(0) <= 51;
    }

    static boolean chooseStadium(String name) {
        return name.equals("1") || name.equals("2") || name.equals("3");
    }


    public static void main(String[] args) throws IOException {

        String year,month,day,from,to,stadiumName,PhoneNumber,choice;

        Socket Client = new Socket("localhost", 9909);
        System.out.println("Client Created");
        PrintWriter ToServer = new PrintWriter(Client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("Enter your phone number +966:");
            PhoneNumber = scan.next();
            if (!checkPhoneNumber(PhoneNumber))
                System.out.println("ERROR: TRY AGAIN");
        } while (!checkPhoneNumber(PhoneNumber));
        ToServer.println(PhoneNumber);
        String isFound = in.readLine();

        if (isFound.equalsIgnoreCase("Yes")) {
            System.out.print("Welcome Back ");
            System.out.println(in.readLine() + "!");
        }

        else if (isFound.equalsIgnoreCase("No")) {
            System.out.println("Do you wish to register? (y/n)");
            do {
                choice = scan.next();
                if (!(choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")))
                    System.out.println("ERROR: Try again");
            } while (!(choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")));

            if (choice.equalsIgnoreCase("N")) {
                System.out.println("Thanks for cooperating");
                ToServer.println("creating account declined");
                Client.close();
                System.exit(0);
            } else if (choice.equalsIgnoreCase("Y")) {
                ToServer.println("account creating approved");
                String name;
                do {
                    System.out.println("Enter Your name (small letters)");
                    name = scan.next();
                    if (!checkName(name))
                        System.out.println("ERROR: Enter small letters only");
                } while (!checkName(name));
                ToServer.println(name);
                System.out.println("Account created successfully");
            }

        }

        do {
            System.out.println("1- Display Reservation." + "\n2- Reserve Stadium."
                    + "\n3- Modify Reservation." + "\n4- Delete Reservation." + "\n5- Exit.");
            System.out.print("Enter a number from list -> ");
            choice = scan.next();
            if (!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2") || choice.equals("3")
                    || choice.equals("4") || choice.equals("5")))
                System.out.println("ERROR: Enter a number between 1 to 5");
            else {
                switch (choice) {

                    case "1": // --DISPLAY RESERVATION--
                        ToServer.println("1");
                        System.out.println(in.readLine().replace("#", "\n")); // --RESERVATION INFORMATION--
                        break;

                    case "2": // --MAKE RESERVATION--

                        ToServer.println("2");
                        System.out.print(in.readLine().replace("#", "\n"));// --DISPLAY STADIUMS--
                        do {
                            stadiumName = scan.next();
                            if (!chooseStadium(stadiumName))
                                System.out.println("ERROR: try again");
                        } while (!chooseStadium(stadiumName));
                        ToServer.println(stadiumName);
                        System.out.println(in.readLine().replace("#", "\n"));// --YEAR--
                        do {
                            year = scan.next();
                            if (!checkYear(year))
                                System.out.println("ERROR: try again");
                        } while (!checkYear(year));
                        ToServer.println(year);
                        System.out.println(in.readLine().replace("#", "\n"));// --MONTH--
                        do {
                            month = scan.next();
                            if (!checkMonth(month))
                                System.out.println("ERROR: try again");
                        } while (!checkMonth(month));
                        ToServer.println(month);
                        System.out.println(in.readLine().replace("#", "\n"));// --DAY--
                        do {
                            day = scan.next();
                            if (!checkDay(day))
                                System.out.println("ERROR: try again");
                        } while (!checkDay(day));
                        ToServer.println(day);
                        System.out.println(in.readLine().replace("#", "\n"));// RESERVATIONS

                        do {
                        	do {
                        		System.out.println("Choose a valid time. (24 hour format), (Maximum 3 Hours)");
                        		System.out.println("From \n->");
                                from = scan.next();
                                if (!checkFrom(from))
                                    System.out.println("ERROR: try again");
                            } while (!checkFrom(from));
                        	
                        	do {
                        		System.out.println("To #->");
                                to = scan.next();
                                if (!checkTo(to))
                                    System.out.println("ERROR: try again");
                            } while (!checkTo(to));
							
						} while (!checkBoth(from,to));
                        
                        ToServer.println(from);
                        ToServer.println(to);

                        String check = in.readLine();
                        
                        System.out.println(check);
                        if (!check.equalsIgnoreCase("Reservation Completed")) {
                        	System.out.println("Reservation failed because the timeslot has already been reserved");
                        	break;
                        }
                        
                        System.out.println(check);
                        System.out.println(in.readLine().replace("#", "\n"));
                        System.out.println();
                        break;

                    case "3": // --UPDATE RESERVATION--

                        ToServer.println("3");
                        String serverMessage = in.readLine();
                        if (serverMessage.equalsIgnoreCase("There's no reservations")) {
                            System.out.println(serverMessage);
                            break;
                        } else {
                            System.out.println(serverMessage.replace("#", "\n"));// --PREVIOUS RESERVATIONS--

                            System.out.println(in.readLine().replace("#", "\n"));// --UPDATE RESERVATIONS--

                            String nbUpdateRes;
                            do {
                                nbUpdateRes = scan.next();
                                if (nbUpdateRes.length() > 3)
                                    System.out.println("ERROR: try again");
                            } while (!(nbUpdateRes.length() <= 3));
                            ToServer.println(nbUpdateRes); // --Send reservations you want to update to server--

                            System.out.println(in.readLine().replace("#", "\n")); // -- ENTER STADIUM NAME--
                            do {
                                stadiumName = scan.next();
                                if (!chooseStadium(stadiumName))
                                    System.out.println("ERROR: try again");
                            } while (!chooseStadium(stadiumName));
                            ToServer.println(stadiumName);

                            System.out.println(in.readLine().replace("#", "\n"));// --YEAR--
                            do {
                                year = scan.next();
                                if (!checkYear(year))
                                    System.out.println("ERROR: try again");
                            } while (!checkYear(year));
                            ToServer.println(year);

                            System.out.println(in.readLine().replace("#", "\n"));// --MONTH--
                            do {
                                month = scan.next();
                                if (!checkMonth(month))
                                    System.out.println("ERROR: try again");
                            } while (!checkMonth(month));
                            ToServer.println(month);

                            System.out.println(in.readLine().replace("#", "\n"));// --DAY--
                            do {
                                day = scan.next();
                                if (!checkDay(day))
                                    System.out.println("ERROR: try again");
                            } while (!checkDay(day));
                            ToServer.println(day);

                            do {
                            	do {
                            		System.out.println("Choose a valid time. (24 hour format), (Maximum 3 Hours)");
                            		System.out.println("From \n->");
                                    from = scan.next();
                                    if (!checkFrom(from))
                                        System.out.println("ERROR: try again");
                                } while (!checkFrom(from));
                            	
                            	do {
                            		System.out.println("To \n->");
                                    to = scan.next();
                                    if (!checkTo(to))
                                        System.out.println("ERROR: try again");
                                } while (!checkTo(to));
    							
    						} while (!checkBoth(from,to));
                            
                            ToServer.println(from);
                            ToServer.println(to);

                            String check2 = in.readLine();
                            
                            System.out.println(check2);
                            if (!check2.equalsIgnoreCase("RESERVATION COMPLETED")) { // ----------------T3DEL------------- Reservation Number not valid, Your reservation has been updated
                            	System.out.println("This time is not available (reserved)");
                            	break;
                            }

                            System.out.println(in.readLine().replace("#", "\n"));
                        }
                        break;

                    case "4": // --REMOVE RESERVATIONS--

                        ToServer.println("4");
                        String server_message = in.readLine();
                        if (server_message.equalsIgnoreCase("You don't have any reservations")) {
                            System.out.println(server_message);
                        } else {
                            System.out.println(server_message.replace("#", "\n")); // --SHOW RESERVATIONS--
                            System.out.println("Which reservations you wish to delete?");
                            System.out.println("Choose number from the list:");

                            String nbDeletedReservations;

                            do {
                                nbDeletedReservations = scan.next();
                                if (nbDeletedReservations.length() > 3)
                                    System.out.println("ERROR: try again");
                            } while (!(nbDeletedReservations.length() <= 3));
                            ToServer.println(nbDeletedReservations);

                            System.out.println(in.readLine().replace("#", "\n"));

                        }
                        break;

                    case "5": // --EXIT--
                        ToServer.println("5");
                        System.out.println(in.readLine().replace("#", "\n"));
                        Client.close();
                        System.exit(0);
                }
            }
        } while (true);

    }

}