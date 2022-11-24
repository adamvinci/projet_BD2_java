import Bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationEtudiante {
    Connection connection=Main.conn;
    Scanner scanner=new Scanner(System.in);
    static int idEtudiant;
   // PreparedStatement ....
    PreparedStatement visualiserCoursInscrit,inscriptionGroupe,demissionGroupe,visualiserProjetInscrit,visualiserProjetPasEncoreInscrit,visualiserGroupeIncomplet,
    getIdFromEmail,getPasswordFromEmail,transformStringIdIntoIntegerID;

    public ApplicationEtudiante(){
        try {
            getIdFromEmail=connection.prepareStatement("SELECT projet.transformEmailIntoId(?)");
            getPasswordFromEmail=connection.prepareStatement("SELECT password FROM projet.etudiants WHERE email=(?)");
            visualiserCoursInscrit=connection.prepareStatement("SELECT projet");
            inscriptionGroupe=connection.prepareStatement("SELECT projet.ajouterEtudiantAGroupe (?,?,?);");
            demissionGroupe=connection.prepareStatement("SELECT projet.retirerEtudiantGroupe(?,?);");
            visualiserGroupeIncomplet=connection.prepareStatement("SELECT * FROM projet.visualiserCompositionGroupeIncomplet WHERE projet=(?);");
            transformStringIdIntoIntegerID=connection.prepareStatement("SELECT  projet.transformStringIdIntoIntegerID (?);");
            visualiserProjetPasEncoreInscrit=connection.prepareStatement("SELECT * FROM projet.visualiserProjetPasEncoreInscrit WHERE etudiant=(?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //faire fonction
    }
    public void seConnecter(){
        String email,password,passwordDb=""; int idStudent=-1;
        try {
            System.out.println("Entrer votre email");
            email=scanner.nextLine();
            getIdFromEmail.setString(1,email);
            getIdFromEmail.executeQuery();
           while(getIdFromEmail.getResultSet().next()){
                idStudent = getIdFromEmail.getResultSet().getInt(1);
            }
            if(idStudent == 0) {
                System.out.println("Cet email n'existe pas\n");
            }else{
                System.out.println("Entrer votre mdp");
                password=scanner.nextLine();
                 getPasswordFromEmail.setString(1,email);
                getPasswordFromEmail.execute();
                while (getPasswordFromEmail.getResultSet().next()){
                    passwordDb = getPasswordFromEmail.getResultSet().getString(1);
                }
                if(BCrypt.checkpw(password,passwordDb)){
                    System.out.println("Connexion reussi \n");
                    Main.connexion =true;
                }else{
                    System.out.println("Mauvais mot de passe \n") ;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0]+"\n");
        }
        if(Main.connexion) {
            idEtudiant = idStudent;
            System.out.println(idEtudiant);
        }
    }


    public void visualiserCoursInscrit(){


    }

    public void inscriptionGroupe(){
        String projet; int groupe;

            try {
                System.out.println("Entrer l'identifiant du projet");
                projet=scanner.nextLine();
                inscriptionGroupe.setString(1,projet);

                System.out.println("Entrer le numero du groupe");
                groupe= Integer.parseInt(scanner.nextLine());
                inscriptionGroupe.setInt(2,groupe);
                System.out.println(idEtudiant);
                inscriptionGroupe.setInt(3,idEtudiant);

                inscriptionGroupe.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage().split("\n")[0]+"\n");
            }
    }

    public void demissionGroupe(){
        String projet;
        try {
            System.out.println("Id du projet (String)");
            projet=scanner.nextLine();
            demissionGroupe.setString(1,projet);
            demissionGroupe.setInt(2,idEtudiant);
            demissionGroupe.executeQuery();
            while (demissionGroupe.getResultSet().next()){
                if(demissionGroupe.getResultSet().getInt(1)==0) {
                    System.out.println("Désincription impossible,l'etudiant n'est pas inscrit a ce cours ou projet");
                }else{
                    System.out.println("Désinscription réussi");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage().split("\n")[0]+"\n");
        }
    }

    public void visualiserGroupeIncomplet(){
        int projet=-1; ResultSet set; String projetString;
        try {
            System.out.println("Id du projet (String)");
            projetString=scanner.nextLine();

            transformStringIdIntoIntegerID.setString(1,projetString);
            transformStringIdIntoIntegerID.executeQuery();
            while(transformStringIdIntoIntegerID.getResultSet().next()){
                projet = transformStringIdIntoIntegerID.getResultSet().getInt(1);
            }

            visualiserGroupeIncomplet.setInt(1,projet);
           set= visualiserGroupeIncomplet.executeQuery();

           System.out.printf(" | %-7s | %-12s | %-12s | %-15s","Numero","Nom","Prénom","Nombre de places");
            System.out.println();
           while (set.next()){
                          System.out.printf(" | %-7s | %-12s | %-12s | %-15s",set.getString("numero"),set.getString("nom"),set.getString("prenom"),
                       set.getString("nbePlace"));
               System.out.println();
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void visualiserProjetPasEncoreInscrit(){
        ResultSet set;
        try {
            visualiserProjetPasEncoreInscrit.setInt(1,idEtudiant);
            set=visualiserProjetPasEncoreInscrit.executeQuery();
           String column1=set.getMetaData().getColumnName(1), column2=set.getMetaData().getColumnName(2), column3=set.getMetaData().getColumnName(3),
                    column4=set.getMetaData().getColumnName(4), column5=set.getMetaData().getColumnName(5);

            System.out.printf(" | %-9s | %-10s | %-17s | %-10s | %-10s",column1,column2,column3,column4,column5);
            System.out.println();
            while (set.next()){
                System.out.printf(" | %-9s | %-10s | %-17s | %-10s | %-10s",set.getString(column1),set.getString(column2),set.getString(column3)
                        ,set.getString(column4),set.getString(column5));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
