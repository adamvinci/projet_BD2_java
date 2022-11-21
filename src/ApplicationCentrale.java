import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationCentrale {
Main m=new Main();
Connection connection=Main.conn;
Scanner scanner=new Scanner(System.in);
    String sel=BCrypt.gensalt()
    PreparedStatement ajouterUnCours,ajouterEtudiant,inscrireEtudiantAUnCours,ajouterProjet,ajouterGroupe,validerUnGroupe,
    validerToutLesGroupes,visualiserCours;

    public ApplicationCentrale(){
        try {
           ajouterUnCours= connection.prepareStatement("SELECT projet.ajouterCours(?,?,?,?);");
            visualiserCours= connection.prepareStatement("SELECT  projet.visualiserCours();");
            ajouterEtudiant = connection.prepareStatement("SELECT projet.ajouterEtudiant(?,?,?,?);");
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
            bloc=scanner.nextInt();
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
            ajouterUnCours.setString(1,nom);
            System.out.println("Entrer le prenom de l'etudiant");
            prenom=scanner.nextLine();
            ajouterUnCours.setString(1,prenom);
            System.out.println("Entrer l'email de l'etudiant");
            email=scanner.nextLine();
            ajouterUnCours.setString(1,email);
            System.out.println("Entrer le pass de l'etudiant");
            email=scanner.nextLine();
            ajouterUnCours.setString(1,email);
            System.out.println("Entrer le password de l'etudiant");
            password= BCrypt.hashpw(scanner.nextLine(), sel);
            ajouterUnCours.setString(1,password);

            System.out.println(visualiserCours.execute());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
