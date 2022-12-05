import Bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationEtudiante {
    Connection connection = Main.conn;
    Scanner scanner = new Scanner(System.in);
     int idEtudiant;
    PreparedStatement visualiserCoursInscrit, inscriptionGroupe, demissionGroupe, visualiserProjetInscrit, visualiserProjetPasEncoreInscrit, visualiserGroupeIncomplet,
            getUser, transformStringIdIntoIntegerID;

    public ApplicationEtudiante() {
        try {
            getUser = connection.prepareStatement("SELECT * FROM projet.etudiants WHERE email=(?)");
            visualiserCoursInscrit = connection.prepareStatement("SELECT * FROM projet.visualiserCoursParticipe WHERE etudiant=(?);");
            inscriptionGroupe = connection.prepareStatement("SELECT projet.ajouterEtudiantAGroupe (?,?,?);");
            demissionGroupe = connection.prepareStatement("SELECT projet.retirerEtudiantGroupe(?,?);");
            visualiserGroupeIncomplet = connection.prepareStatement("SELECT * FROM projet.visualiserCompositionGroupeIncomplet WHERE projet=(?);");
            transformStringIdIntoIntegerID = connection.prepareStatement("SELECT  id_projet FROM projet.projets WHERE identifiant_projet = (?);");
            visualiserProjetPasEncoreInscrit = connection.prepareStatement("SELECT * FROM projet.visualiserProjetPasEncoreInscrit WHERE etudiant=(?)");
            visualiserProjetInscrit = connection.prepareStatement("SELECT * FROM projet.visualiserProjetsInscrit WHERE etudiant=(?);");
        } catch (SQLException e) {
            System.out.println("\n" + e.getMessage().split("\n")[0] + "\n");
        }
    }

    public void seConnecter() {
        String email, password, passwordDb = " ";
        int idStudent = -1;
        try {
            System.out.println("Entrer votre email");
            email = scanner.nextLine();
            getUser.setString(1, email);
            try (ResultSet set = getUser.executeQuery()) {
                while (set.next()) {
                    idStudent = set.getInt(1);
                    passwordDb = set.getString("password");
                }
            }
            if(idStudent == -1) {
                System.out.println("Cet email n'existe pas\n");
            }else {
                System.out.println("Entrer votre mdp");
                password = scanner.nextLine();
                if (BCrypt.checkpw(password, passwordDb)) {
                    System.out.println("Connexion reussi \n");
                    MenuEtudiant.connexion = true;
                } else {
                    System.out.println("Mauvais identifiant \n");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }
        if (MenuEtudiant.connexion) {
            idEtudiant = idStudent;
        }
    }

    //1
    public void visualiserCoursInscrit() {
        try {
            visualiserCoursInscrit.setInt(1, idEtudiant);
            try (ResultSet set = visualiserCoursInscrit.executeQuery()) {
                System.out.printf("| %-10s | %-15s | %-15s ", set.getMetaData().getColumnName(1), set.getMetaData().getColumnName(2), "Id projet");
                System.out.println();
                while (set.next()) {
                    System.out.printf("| %-10s | %-15s | %-15s ", set.getString(set.getMetaData().getColumnName(1)), set.getString(set.getMetaData().getColumnName(2))
                            , set.getString(set.getMetaData().getColumnName(3)));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("\n" + e.getMessage().split("\n")[0] + "\n");
        }


    }

    //2
    public void inscriptionGroupe() {
        String projet;
        int groupe;

        try {
            System.out.println("Entrer l'identifiant du projet");
            projet = scanner.nextLine();
            inscriptionGroupe.setString(1, projet);

            System.out.println("Entrer le numero du groupe");
            groupe = Integer.parseInt(scanner.nextLine());
            inscriptionGroupe.setInt(2, groupe);

            inscriptionGroupe.setInt(3, idEtudiant);

            inscriptionGroupe.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }
    }

    //3
    public void demissionGroupe() {
        String projet;
        try {
            System.out.println("Id du projet (String)");
            projet = scanner.nextLine();
            demissionGroupe.setString(1, projet);
            demissionGroupe.setInt(2, idEtudiant);
            try (ResultSet set = demissionGroupe.executeQuery()) {
                while (set.next()) {
                    if (set.getInt(1) == 0) {
                        System.out.println("Désincription impossible,l'etudiant n'a pas de groupe pour ce projet ");
                    } else {
                        System.out.println("Désinscription réussi");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }
    }

    //4
    public void visualiserProjetInscrit() {
        try {
            visualiserProjetInscrit.setInt(1, idEtudiant);
            try (ResultSet set = visualiserProjetInscrit.executeQuery()) {
                String column1 = set.getMetaData().getColumnName(1), column2 = set.getMetaData().getColumnName(2), column3 = set.getMetaData().getColumnName(3), column4 = set.getMetaData().getColumnName(4);
                System.out.printf("| %-19s | %-10s | %-9s | %-9s", column1, column2, column3, "Groupe");
                System.out.println();
                while (set.next()) {
                    System.out.printf("| %-19s | %-10s | %-9s | %-9s", set.getString(column1), set.getString(column2), set.getString(column3), set.getString(column4));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }

    }

    //5
    public void visualiserProjetPasEncoreInscrit() {
        try {
            visualiserProjetPasEncoreInscrit.setInt(1, idEtudiant);
            try (ResultSet set = visualiserProjetPasEncoreInscrit.executeQuery()) {
                String column1 = set.getMetaData().getColumnName(1), column2 = set.getMetaData().getColumnName(2), column3 = set.getMetaData().getColumnName(3),
                        column4 = set.getMetaData().getColumnName(4), column5 = set.getMetaData().getColumnName(5),column6=set.getMetaData().getColumnName(6);

                System.out.printf(" | %-10s | %-15s | %-19s | %-10s | %-10s | %-10s ", column1, column2, column3, column4, column5,column6);
                System.out.println();
                while (set.next()) {
                    System.out.printf(" | %-10s | %-15s | %-19s | %-10s | %-10s | %-10s ", set.getString(column1), set.getString(column2), set.getString(column3)
                            , set.getString(column4), set.getString(column5),set.getString(column6));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }

    }

//6
    public void visualiserGroupeIncomplet() {
        int projet = -1;
        String projetString;
        try {
            System.out.println("Id du projet (String)");
            projetString = scanner.nextLine();

            transformStringIdIntoIntegerID.setString(1, projetString);
            try (ResultSet set = transformStringIdIntoIntegerID.executeQuery()) {
                while (set.next()) {
                    projet = set.getInt(1);
                }
            }
            if (projet == -1) {
                System.out.println("Ce projet n'existe pas");
            } else {
                visualiserGroupeIncomplet.setInt(1, projet);
                try (ResultSet set = visualiserGroupeIncomplet.executeQuery()) {

                    System.out.printf(" | %-7s | %-12s | %-12s | %-15s", "Numero", "Nom", "Prénom", "Nombre de places");
                    System.out.println();
                    while (set.next()) {
                        System.out.printf(" | %-7s | %-12s | %-12s | %-15s", set.getString("numero"), set.getString("nom"), set.getString("prenom"),
                                set.getString("nbePlace"));
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0] + "\n");
        }

    }


}
