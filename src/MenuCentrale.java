import java.util.Scanner;

public class MenuCentrale {

    ApplicationCentrale appCentrale = new ApplicationCentrale();
     Scanner scanner = new Scanner(System.in);
    Main m = new Main();

    public void menuCentrale() {

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

        int choixMenu = scanner.nextInt();
        m.declaration();
        switch (choixMenu) {
            case 1 -> appCentrale.ajouterCours();
            case 2 -> appCentrale.ajouterEtudiant();
            case 3 -> appCentrale.inscrireEtudiantACours();
            case 4 -> appCentrale.ajouterProjet();
            case 5 -> appCentrale.ajouterGroupe();
            case 6 -> appCentrale.visualiserCours();
            case 7 -> appCentrale.visualiserProjets();
            case 8 -> appCentrale.visualiserGroupe();
            case 9 -> appCentrale.validerUnGroupe();
            case 10 -> appCentrale.validerToutLesGroupes();
            case 11 -> m.menu();
            case 12 -> System.exit(200);
            default -> menuCentrale();
        }
        menuCentrale();
    }
}
