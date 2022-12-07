import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


     Scanner scanner = new Scanner(System.in);
    static Connection conn = null;
    MenuCentrale menuCentrale;
    MenuEtudiant menuEtudiant;

    public static void main(String[] args) {
        Main m = new Main();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL manquant !");
            System.exit(1);
        }
       //String url = "jdbc:postgresql://localhost:5432/postgres";
           String url= "jdbc:postgresql://172.24.2.6:5432/dbadamebarhdadi";

        try {
           // conn = DriverManager.getConnection(url, "postgres", "postgres");
             conn= DriverManager.getConnection(url,"adamebarhdadi","P3EISJ7DN");
        } catch (SQLException e) {
            System.out.println("Impossible de joindre le server !");
            System.exit(1);
        }
        m.declaration();
        m.menu();
    }

    public void declaration() {
        menuCentrale = new MenuCentrale();
        menuEtudiant = new MenuEtudiant();
    }


    public void menu() {
        System.out.println("Choisissez entre l'application centrale et l'application utilisateur \n");
        System.out.println("1 : Application centrale");
        System.out.println("2 : Application utilisateur");
        System.out.println("3 : Quitter");
        declaration();
        int choixMenu = Integer.parseInt(scanner.nextLine());
        switch (choixMenu) {
            case 1:
                menuCentrale.menuCentrale();
            case 2:
                menuEtudiant.menuStudent();
            case 3:
                System.exit(200);
            default:
                menu();
        }
    }


}


