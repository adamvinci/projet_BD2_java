import java.util.Scanner;

public class MenuEtudiant {
    ApplicationEtudiante appEtudiante = new ApplicationEtudiante();
    static boolean connexion;
    Main m = new Main();
    Scanner scanner = new Scanner(System.in);

    public void menuStudent() {
        System.out.println("1 : Se connecter");
        System.out.println("2 : Changer de menu");
        System.out.println("3 : Quitter");
        int choixMenu = scanner.nextInt();
        switch (choixMenu) {
            case 1 -> {appEtudiante.seConnecter();menuStudentConnecter();}
            case 2-> m.menu();
            case 3-> System.exit(201);
            default-> menuStudent();

        }
    }


    public void menuStudentConnecter() {
        if(connexion) {
            System.out.println("\nBienvenue dans l'application etudiante \n");
            System.out.println("1 : Visualise Cours Inscrit");
            System.out.println("2 : S'inscrire a un groupe");
            System.out.println("3 : Se desinscrire d'un groupe");
            System.out.println("4 : Visualiser Projet Cours Inscrit");
            System.out.println("5 : Visualiser projet pour lequelle pas encore de groupe");
            System.out.println("6 : Visualiser composition groupe incomplet d'un projet");
            System.out.println("7 : Se deconnecter");
            int choixMenu = scanner.nextInt();
            m.declaration();
            switch (choixMenu) {
                case 1 -> appEtudiante.visualiserCoursInscrit();
                case 2 -> appEtudiante.inscriptionGroupe();
                case 3 -> appEtudiante.demissionGroupe();
                case 4 -> appEtudiante.visualiserProjetInscrit();
                case 5 -> appEtudiante.visualiserProjetPasEncoreInscrit();
                case 6 -> appEtudiante.visualiserGroupeIncomplet();
                case 7 -> deconnecter();
                default -> menuStudentConnecter();
            }
            menuStudentConnecter();
        }else{
            menuStudent();
        }
    }

    public void deconnecter() {
        connexion = false;
        System.out.println("Deconnexion réussi \n");
        m.menu();
    }
}
