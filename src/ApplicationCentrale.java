import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationCentrale {

Connection connection=Main.conn;
Scanner scanner=new Scanner(System.in);

    PreparedStatement ajouterUnCours,ajouterEtudiant,inscrireEtudiantAUnCours,ajouterProjet,ajouterGroupe,validerUnGroupe,
    validerToutLesGroupes;

    public ApplicationCentrale(){
        try {
           ajouterUnCours= connection.prepareStatement("SELECT projet.ajouterCours(?,?,?,?);");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //faire fonction
    }
    public void ajouterCours(){
        System.out.println("Entrer le code du cours");
        try {
            ajouterUnCours.setString(1,scanner.nextLine());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
