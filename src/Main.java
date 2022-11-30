import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    private Scanner scanner = new Scanner(System.in);
    static Connection conn=null;
    ApplicationCentrale appCentrale;
    ApplicationEtudiante appEtudiante;
    static boolean connexion;
    public static void main (String[]args) {
        Main m=new Main();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL manquant !");
            System.exit(1);
        }
      //  String url="jdbc:postgresql://localhost:5432/postgres";
        String url= "jdbc:postgresql://172.24.2.6:5432/dbadamebarhdadi";

        try {
           // conn= DriverManager.getConnection(url,"postgres","postgres");
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
            case 2:
                menuStudent();
            case 3:
                System.exit(200);
        }
    }
    public void menuCentrale(){

    System.out.println("\nBienvenue dans l'application centrale \n");
        System.out.println("1 : Ajouter Un Cours");
        System.out.println("2 : Ajouter Un Etudiant");
        System.out.println("3 : Inscrire Un Etudiant a un cours");
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
                    System.exit(200);
            }
    menuCentrale();
    }
    public void menuStudent(){
        System.out.println("1 : Se connecter");
        System.out.println("2 : Changer de menu");
        System.out.println("3 : Quitter");
        int choixMenu=scanner.nextInt();
        switch (choixMenu){
            case 1:
                appEtudiante.seConnecter();
                if(connexion){
                    menuStudentConnecter();
                }
                else{
                    menuStudent();
                }
            case 2:
                menu();
            case 3:
               System.exit(201);

        }
    }


    public void menuStudentConnecter(){
        System.out.println("\nBienvenue dans l'application etudiante \n");
        declaration();
        System.out.println("1 : Visualise Cours Inscrit");
        System.out.println("2 : S'inscrire a un groupe");
        System.out.println("3 : Se desinscrire d'un groupe");
        System.out.println("4 : Visualiser Projet Cours Inscrit");
        System.out.println("5 : Visualiser projet pour lequelle pas encore de groupe");
        System.out.println("6 : Visualiser composition groupe d'un projet");
        System.out.println("7 : Se deconnecter");
        int choixMenu=scanner.nextInt();
        declaration();
        switch (choixMenu) {
            case 1:
                appEtudiante.visualiserCoursInscrit();
                break;
            case 2:
                appEtudiante.inscriptionGroupe();
                break;
            case 3:
                appEtudiante.demissionGroupe();
                break;
            case 4:
                appEtudiante.visualiserProjetInscrit();
                break;
            case 5:
                appEtudiante.visualiserProjetPasEncoreInscrit();
                break;
            case 6:
               appEtudiante.visualiserGroupeIncomplet();
                break;
            case 7:
                deconnecter();
                break;
        }
        menuStudentConnecter();
    }

    public void deconnecter(){
            connexion = false;
            System.out.println("Deconnexion réussi \n");
            menu();
        }


}


