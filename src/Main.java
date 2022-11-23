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
        String url="jdbc:postgresql://localhost:5432/postgres";
      //  String url= "jdbc:postgresql://172.24.2.6:5432/dbadamebarhdadi";

        try {
            conn= DriverManager.getConnection(url,"postgres","postgres");
            //conn= DriverManager.getConnection(url,"adamebarhdadi","P3EISJ7DN");
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

    System.out.println("\nBienvenue dans l'application centrale \n");
        System.out.println("1 : Ajouter Un Cours");
        System.out.println("2 : Ajouter Un Etudiant");
        System.out.println("3 : Inscrire Un Etudiant");
        System.out.println("4 : Ajouter Un Projet");
        System.out.println("5 : Ajouter Un Groupe");
        System.out.println("6 : Visualiser Cours");
        System.out.println("7 : Visualiser Projet");
        System.out.println("8 : Visualiser Groupe");
        System.out.println("9 : Valider Un Groupe");
        System.out.println("10 : Valider tout les  Groupe");
        System.out.println("11 : Changer de menu");
        System.out.println("12 : Quitter");

        int choixMenu=scanner.nextInt();
    declaration();
            switch (choixMenu) {
                case 1:
                    appCentrale.ajouterCours();
                    break;
                case 2:
                    appCentrale.ajouterEtudiant();
                    break;
                case 3:
                    appCentrale.inscrireEtudiantACours();
                    break;
                case 4:
                    appCentrale.ajouterProjet();
                    break;
                case 5:
                    appCentrale.ajouterGroupe();
                    break;
                case 6:
                    appCentrale.visualiserCours();
                    break;
                case 7:
                    appCentrale.visualiserProjets();
                    break;
                case 8:
                    appCentrale.visualiserGroupe();
                    break;
                case 9:
                    appCentrale.validerUnGroupe();
                    break;
                case 10:
                    appCentrale.validerToutLesGroupes();
                    break;
                case 11:
                    menu();
                    break;
                case 12:
                    quitter();
            }
    menuCentrale();
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


