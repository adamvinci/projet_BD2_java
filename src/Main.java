import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    private Scanner scanner = new Scanner(System.in);
    static Connection conn=null;
    ApplicationCentrale appCentrale;
    ApplicationEtudiante appEtudiante;
    public static void main (String[]args) {
        Main m=new Main();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL manquant !");
            System.exit(1);
        }
        //localhost: jdbc:postgresql://172.24.2.6:5432/postgres
        String url= "jdbc:postgresql://172.24.2.6:5432/dbadamebarhdadi";

        try {
            // postgres postgres or adamebarhdadi P3EISJ7DN
            conn= DriverManager.getConnection(url,"adamebarhdadi","P3EISJ7DN");
        } catch (SQLException e) {
            System.out.println("Impossible de joindre le server !");
            System.exit(1);
        }
        m.declaration();
        m.menu();
    }

   public void declaration(){
            appCentrale = new ApplicationCentrale();
            appEtudiante = new ApplicationEtudiante();
        }




    public void menu()  {
        System.out.println("Choisissez entre l'application centrale et l'application utilisateur \n");
        System.out.println("1 : Application centrale");
        System.out.println("2 : Application utilisateur");
        System.out.println("3 : Quitter");
        int choixMenu=scanner.nextInt();
        switch (choixMenu){
            case 1:
                menuCentrale();
                break;
            case 2:
                menuStudent();
                break;
            case 3:
                quitter();
                break;

        }
    }
    public void menuCentrale(){
    System.out.println("Bienvenue dans l'application centrale \n");
        System.out.println("1 : Ajouter Un Cours");
    System.out.println("2 : Changer de menu");
        System.out.println("3 : Quitter");
        System.out.println("4 : Visualiser Cours");
        System.out.println("5 : Ajouter Un Etudiant");
        int choixMenu=scanner.nextInt();
        switch (choixMenu){
            case 1:
                appCentrale.ajouterCours();
                break;
            case 2:
                menu();
                break;
            case 3:
                quitter();
            case 4:
                appCentrale.visualiserCours();
                break;
            case 5:
                appCentrale.ajouterEtudiant();
                break;
            default:
                menuCentrale();
                break;
        }
    }
    public void menuStudent(){
        System.out.println("Bienvenue dans l'application etudiante \n");
        System.out.println("1 : Changer de menu");
        System.out.println("3 : Quitter");
        int choixMenu=scanner.nextInt();
        switch (choixMenu){
            case 1:
                menu();
                break;
            case 2:
                quitter();
                break;
        }
    }


    public void menuStudentConnecter(){

    }

    public void quitter(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


