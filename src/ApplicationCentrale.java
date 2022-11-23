import Bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ApplicationCentrale {
 Main m=new Main();
Connection connection=Main.conn;
Scanner scanner=new Scanner(System.in);
    String sel= BCrypt.gensalt();
    PreparedStatement ajouterUnCours,ajouterEtudiant,inscrireEtudiantAUnCours,ajouterProjet,ajouterGroupe,validerUnGroupe,
    validerToutLesGroupes,visualiserCours;

    public ApplicationCentrale(){
        try {
           ajouterUnCours= connection.prepareStatement("SELECT projet.ajouterCours(?,?,?,?);");
            visualiserCours= connection.prepareStatement("SELECT  projet.visualiserCours();");
            ajouterEtudiant = connection.prepareStatement("SELECT projet.ajouterEtudiant(?,?,?,?);");
            inscrireEtudiantAUnCours=connection.prepareStatement("SELECT projet.inscrireEtudiantACours(?,?);");
            ajouterProjet=connection.prepareStatement("SELECT projet.ajouterProjet(?,?,?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //faire fonction
    }
    public void ajouterCours(){
        String nom; Integer bloc , nbeCredit;
        try {
            System.out.println("Entrer le code du cours");
            ajouterUnCours.setString(1,scanner.nextLine());
            System.out.println("Entrer le nom du cours");
            nom=scanner.nextLine();
            ajouterUnCours.setString(2,nom);
            System.out.println("Entrer le bloc du cours");
            bloc= Integer.valueOf(scanner.nextLine());
            ajouterUnCours.setInt(3,bloc);
            System.out.println("Entrer le nombre de credit du cours");
            nbeCredit=scanner.nextInt();
            ajouterUnCours.setInt(4,nbeCredit);
            ajouterUnCours.execute();
            System.out.println("c bon le S");
           m.menuCentrale();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void visualiserCours(){
        try {
            System.out.println(visualiserCours.execute());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouterEtudiant(){
        String nom,prenom,email,password;
        try {
            System.out.println("Entrer le nom de l'etudiant");
            nom=scanner.nextLine();
            ajouterEtudiant.setString(1,nom);
            System.out.println("Entrer le prenom de l'etudiant");
            prenom=scanner.nextLine();
            ajouterEtudiant.setString(2,prenom);
            System.out.println("Entrer l'email de l'etudiant");
            email=scanner.nextLine();
            ajouterEtudiant.setString(3,email);
            System.out.println("Entrer le password de l'etudiant");
            password= BCrypt.hashpw(scanner.nextLine(), sel);
            ajouterEtudiant.setString(4,password);
            ajouterEtudiant.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void inscrireEtudiantACours(){
        String emailEtudiant;
        try{
            System.out.println("Entrer l'email de l'etudiant");
            emailEtudiant=scanner.nextLine();
            inscrireEtudiantAUnCours.setString(1,emailEtudiant);
            System.out.println("Entrer le code du cours");
            inscrireEtudiantAUnCours.setString(2,scanner.nextLine());
            inscrireEtudiantAUnCours.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void ajouterProjet(){
        String nomProjet,identifiantProjet;
        int nombreGroupePossible;
        String dateDebut,dateFin;
        try{
            System.out.println("Entrer le code du cours ");
            ajouterProjet.setString(1,scanner.nextLine());

            System.out.println("Entrer le nom du projet ");
            nomProjet=scanner.nextLine();
            ajouterProjet.setString(2,nomProjet);

            System.out.println("Entrer la date de debut du projet (yyyy-mm-dd)");
            dateDebut=scanner.nextLine();
            ajouterProjet.setDate(3,  java.sql.Date.valueOf(dateDebut));

            System.out.println("Entrer la date de fin du projet (yyyy-mm-dd)");
            dateFin=scanner.nextLine();
            ajouterProjet.setDate(4,  java.sql.Date.valueOf(dateFin));

            System.out.println("Entrer la nombre de groupe que le projet peux contenir");
            nombreGroupePossible= Integer.parseInt(scanner.nextLine());
            ajouterProjet.setInt(5,nombreGroupePossible);
            System.out.println("Entrer l'identifiant du projet (String)");
            identifiantProjet=scanner.nextLine();
            ajouterProjet.setString(6,identifiantProjet);
            ajouterProjet.execute();
        }catch (SQLException e) {
           System.out.println(e.getMessage().split("\n")[0]);
        }

    }
}
