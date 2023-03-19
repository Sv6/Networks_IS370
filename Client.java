import java.util.ArrayList;

public class Client {

    private int phoneNumber;
    private String name;
    private ArrayList<Client> allClients = new ArrayList<Client>();

    public Client() {}

    public Client(int pN, String name) {
        this.phoneNumber = pN;
        this.name = name;
    }

    public void addClient(Client p) { allClients.add(p); }

    public ArrayList<Client> getAll() { return allClients; }

    public int getPhoneNumber() { return phoneNumber; }

    public String getName() { return name; }


}
