import java.sql.Connection;
import java.sql.DriverManager;

class TestConnection {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/digitallocker"; // database name = digitallocker
            String user = "root"; // your MySQL username
            String password = "AS@25528227"; // <-- Replace with your MySQL password

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}