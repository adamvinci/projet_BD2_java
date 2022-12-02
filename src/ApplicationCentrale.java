import Bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ApplicationCentrale {
    Connection connection = Main.conn;
    Scanner scanner = new Scanner(System.in);
    String sel = BCrypt.gensalt();
    PreparedStatement ajouterUnCours, ajouterEtudiant, inscrireEtudiantAUnCours, ajouterProjet, ajouterGroupe, validerUnGroupe,
            validerToutLesGroupes, visualiserCours, visualiserProjet, visualiserGroupe, transformStringIdIntoIntegerID;

    public ApplicationCentrale() {
        try {
            ajouterUnCours = connection.prepareStatement("SELECT projet.ajouterCours(?,?,?,?);");
            ajouterEtudiant = connection.prepareStatement("SELECT projet.ajouterEtudiant(?,?,?,?);");
            inscrireEtudiantAUnCours = connection.prepareStatement("SELECT projet.inscrireEtudiantACours(?,?);");
            ajouterProjet = connection.prepareStatement("SELECT projet.ajouterProjet(?,?,?,?,?)");
            ajouterGroupe = connection.prepareStatement("SELECT projet.ajouterGroupes(?,?,?)");
            visualiserCours = connection.prepareStatement("SELECT * FROM visualiserCours() t(code CHAR(8), nom VARCHAR(255), projet VARCHAR);");
            visualiserProjet = connection.prepareStatement("SELECT * FROM projet.visualiserProjets");
            visualiserGroupe = connection.prepareStatement("SELECT * FROM projet.visualiserCompositions WHERE projet=(?);");
            transformStringIdIntoIntegerID = connection.prepareStatement("SELECT  id_projet FROM projet.projets WHERE identifiant_projet = (?);");
            validerUnGroupe = connection.prepareStatement("SELECT projet.validerGroupe(?,?);");
            validerToutLesGroupes = connection.prepareStatement("SELECT projet.validerToutGroupe(?);");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //faire fonction
    }
//1
    public void ajouterCours() {
        String nom;
        int bloc, nbeCredit;
        try {
            System.out.println("Entrer le code du cours");
            ajouterUnCours.setString(1, scanner.nextLine());
            System.out.println("Entrer le nom du cours");
            nom = scanner.nextLine();
            ajouterUnCours.setString(2, nom);
            System.out.println("Entrer le bloc du cours");
            bloc = Integer.parseInt(scanner.nextLine());
            ajouterUnCours.setInt(3, bloc);
            System.out.println("Entrer le nombre de credit du cours");
            nbeCredit = scanner.nextInt();
            ajouterUnCours.setInt(4, nbeCredit);
            ajouterUnCours.execute();

        } catch (SQLException | NoSuchElementException | NumberFormatException e) {
            System.out.println("\n" + e.getMessage().split("\n")[0] + "\n");
        }

    }

//2
    public void ajouterEtudiant() {
        String nom, prenom, email, password;
        try {
            System.out.println("Entrer le nom de l'etudiant");
            nom = scanner.nextLine();
            ajouterEtudiant.setString(1, nom);
            System.out.println("Entrer le prenom de l'etudiant");
            prenom = scanner.nextLine();
            ajouterEtudiant.setString(2, prenom);
            System.out.println("Entrer l'email de l'etudiant");
            email = scanner.nextLine();
            ajouterEtudiant.setString(3, email);
            System.out.println("Entrer le password de l'etudiant");
            password = BCrypt.hashpw(scanner.nextLine(), sel);
            ajouterEtudiant.setString(4, password);
            ajouterEtudiant.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }

    }
//3
    public void inscrireEtudiantACours() {
        String emailEtudiant;
        try {
            System.out.println("Entrer l'email de l'etudiant");
            emailEtudiant = scanner.nextLine();
            inscrireEtudiantAUnCours.setString(1, emailEtudiant);
            System.out.println("Entrer le code du cours");
            inscrireEtudiantAUnCours.setString(2, scanner.nextLine());
            inscrireEtudiantAUnCours.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }
    }
//4
    public void ajouterProjet() {
        String nomProjet, identifiantProjet;
        String dateDebut, dateFin;
        try {
            System.out.println("Entrer le code du cours ");
            ajouterProjet.setString(1, scanner.nextLine());

            System.out.println("Entrer le nom du projet ");
            nomProjet = scanner.nextLine();
            ajouterProjet.setString(2, nomProjet);

            System.out.println("Entrer la date de debut du projet (yyyy-mm-dd)");
            dateDebut = scanner.nextLine();
            ajouterProjet.setDate(3, java.sql.Date.valueOf(dateDebut));

            System.out.println("Entrer la date de fin du projet (yyyy-mm-dd)");
            dateFin = scanner.nextLine();
            ajouterProjet.setDate(4, java.sql.Date.valueOf(dateFin));


            System.out.println("Entrer l'identifiant du projet (String)");
            identifiantProjet = scanner.nextLine();
            ajouterProjet.setString(5, identifiantProjet);
            ajouterProjet.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }
    }
//5
    public void ajouterGroupe() {
        String projet;
        int tailleMaxGroupe ,nbeGroupe;
        try {
            System.out.println("Identifiant du projet ou il faut rajouter un groupe (String)");
            projet = scanner.nextLine();
            ajouterGroupe.setString(1, projet);

            System.out.println("Entrer le nombre de groupe ");
            nbeGroupe = Integer.parseInt(scanner.nextLine());
            ajouterGroupe.setInt(2, nbeGroupe);

            System.out.println("Capacité max du groupe");
            tailleMaxGroupe = Integer.parseInt(scanner.nextLine());
            ajouterGroupe.setInt(3, tailleMaxGroupe);

            ajouterGroupe.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }

    }
//6
    public void visualiserCours() {
        try {
            try (ResultSet set = visualiserCours.executeQuery();) {
                System.out.println();
                System.out.printf(" | %-8s | %-10s | %-5s  ", set.getMetaData().getColumnName(1), set.getMetaData().getColumnName(2), set.getMetaData().getColumnName(3));
                System.out.println();

                while (set.next()) {
                    System.out.printf(" | %-8s | %-10s | %-5s  ", set.getString("code"), set.getString("nom"), set.getString("projet"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("\n" + e.getMessage().split("\n")[0] + "\n");
        }
    }
//7
    public void visualiserProjets() {
        try {

            try (ResultSet set = visualiserProjet.executeQuery()) {

                String column1 = set.getMetaData().getColumnName(1), column2 = set.getMetaData().getColumnName(2), column3 = set.getMetaData().getColumnName(3),
                        column4 = set.getMetaData().getColumnName(4), column5 = set.getMetaData().getColumnName(5), column6 = set.getMetaData().getColumnName(6);

                System.out.println();
                System.out.printf("| %-20s | %-10s | %-10s | %-24s | %-28s | %-21s ", column1, column2, column3, column4, column5, column6);
                System.out.println();

                while (set.next()) {
                    System.out.printf("| %-20s | %-10s | %-10s | %-24s | %-28s | %-21s ", set.getString(column1), set.getString(column2),
                            set.getString(column3), set.getString(column4), set.getString(column5), set.getString(column6));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("\n" + e.getMessage().split("\n")[0] + "\n");
        }
    }
//8
    public void visualiserGroupe() {
        int projet = -1;
        String projetString;
        try {
            System.out.println("Id du projet (String)");
            projetString = scanner.nextLine();

            transformStringIdIntoIntegerID.setString(1, projetString);
            try (ResultSet set1 = transformStringIdIntoIntegerID.executeQuery()) {
                while (set1.next()) {
                    projet = set1.getInt(1);
                }
            }

            visualiserGroupe.setInt(1, projet);
            try (ResultSet set = visualiserGroupe.executeQuery()) {
                System.out.printf(" | %-7s | %-12s | %-12s | %-9s | %-9s  ", "Numero", "Nom", "Prénom", "Complet?", "Valider?");
                System.out.println();
                while (set.next()) {
                    System.out.printf(" | %-7s | %-12s | %-12s | %-9s | %-9s ", set.getString("numero"), set.getString("nom"), set.getString("prenom"),
                            set.getBoolean("complet"), set.getBoolean("valider"));
                    System.out.println();
                }
            }

        } catch (SQLException e) {
            System.out.println("\n" + e.getMessage().split("\n")[0] + "\n");
        }

    }
//9
    public void validerUnGroupe() {
        int numeroGroupe;
        String identifiantProjet;
        try {
            System.out.println("Numero du groupe a valider");
            numeroGroupe = Integer.parseInt(scanner.nextLine());
            validerUnGroupe.setInt(1, numeroGroupe);

            System.out.println("Id du projet (String)");
            identifiantProjet = scanner.nextLine();
            validerUnGroupe.setString(2, identifiantProjet);

            validerUnGroupe.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }
    }
//10
    public void validerToutLesGroupes() {
        String identifiantProjet;
        try {
            System.out.println("Id du projet (String)");
            identifiantProjet = scanner.nextLine();
            validerToutLesGroupes.setString(1, identifiantProjet);

            validerToutLesGroupes.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }

    }

}
