/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morpion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner; 


public class Morpion {

    static boolean joueur = true;// joueur 1 ou joueur 2 a ajouter dans la condition
    static String Reponse = "0";//null ?
    static Boolean A1, B1, C1, A2, B2, C2, A3, B3, C3; 
    static int caseA1,caseA2,caseA3,caseB1,caseB2,caseB3,caseC1,caseC2,caseC3 = 0;
    static String Damier[][] = { {" "+A1+" "," "+B1+" "," "+C1+" "},{" "+A2+" "," "+B2+" "," "+C2+" "},{" "+A3+" "," "+B3+" "," "+C3+" "}};
    static String J1, J2;
    static String JOUEUR;
    static String partie;
    static String niveau;
    static String esp = "                 ";
    static String Valeur = " " ;
    static String ParoleIA = "Allé c'est partie !";
    static String NvIA;
    static int PrLeScore;
    static String Affichage_Scores;
    static int scoreJ1 = 0;
    static int scoreJ2 = 0;
    static int nbJoueur = 0;
    static String EtiquetteJ1="";
    static String EtiquetteJ2;
    static ArrayList Restriction = new ArrayList();
    static Random rand = new Random();
    static ArrayList Restricted = new ArrayList(); 
    static Scanner key = new Scanner(System.in);
    static String Colone[] = {"|","|","|"};
    static String Ligne[] = {esp+" --------------"};
    static String nCase[] = {esp+"    A |"," B |", " C"};
    static String Indice[] = {esp+" 1", esp+" 2", esp+" 3"};
    
    
    
    public static void main(String[] args) throws IOException, InterruptedException {

        
        
        Restriction.add("A1");
        Restriction.add("A2");
        Restriction.add("A3");
        Restriction.add("B1");
        Restriction.add("B2");
        Restriction.add("B3");
        Restriction.add("C1");
        Restriction.add("C2");
        Restriction.add("C3");
        
        A1=A2=A3=B1=B2=B3=C1=C2=C3 = null;
        
        
        LectureScore();
        LesJoueur();
        
        PrLeScore = 0;

        for (int i = 0; i<3; ++i){
            for (int z =0; z<3; ++z){
                Damier[i][z] = "   ";
            }
        }
        
        ////////////////////// main //////////////////////////////////
        
        while(true){
            
            System.out.println();
            
            for (int i = 0; i<3; ++i){
                System.out.print(nCase[i]);
            }

            System.out.println();
            System.out.println(esp+"   ------------");

            for (int i = 0; i<3; ++i){
                System.out.print(Indice[i]);

                for (int z=0; z<3; ++z){

                    System.out.print(Colone[z]);
                    
                    System.out.print(Damier[i][z]);
                    
                    if(z==2 && i==1 && nbJoueur == 1){
                        System.out.print("|"+esp+"| l'IA a dit : "+ParoleIA+" ");
                    }
                }
                
                System.out.println("|");
                System.out.println(Ligne[0]);

            }
            
            System.out.println();
            Affichage_Scores = "[ "+J1+" : "+scoreJ1+" | "+J2+NvIA+" : "+scoreJ2+" ]";
            printer(Affichage_Scores);
            System.out.println();

            Win(JOUEUR);
            
            do{
                
                if (nbJoueur == 2){
                    System.out.print("Rentrer des coordonnés (ex A1, 2c...) : ");
                    Reponse=Answer(key.next());
                }else{
                    if (joueur == true){
                        System.out.print("Rentrer des coordonnés (ex A1, 2c...) : ");
                        Reponse=Answer(key.next());
                    }else{
                        Reponse=Answer(IA(Reponse));
                    }
                }
                
                
            }while(!(Restriction.contains(Reponse)));
            
            Marquage(Reponse);
            joueur = joueur != true;
  
        }
  
    }
    /////////////////////// Affichage sur le quadrillage //////////////////////////
    static String Answer(String R){

        if (joueur == true){
                Valeur = " "+EtiquetteJ1+" ";
                
            }else{
                Valeur = " "+EtiquetteJ2+" ";
            }
        
        R  = verifCoordonne(R);
        
        if (Restricted.contains(R)){
            System.err.println("Case deja jouer.");
            return null;
        }
        
        if ("A1".equals(R) && (A1 == null)){  
            Damier[0][0] = Valeur; 
            Restricted.add(R);
        }
            
        if ("A2".equals(R) && (A2 == null)){
            Damier[1][0] = Valeur;
            Restricted.add(R);
        }
        
        if ("A3".equals(R) && (A3 == null)){
            Damier[2][0] = Valeur; 
            Restricted.add(R);
        }
 
        if ("B1".equals(R) && (B1 == null)){
            Damier[0][1] = Valeur; 
            Restricted.add(R);
        }

        if ("B2".equals(R) && (B2 == null)){
            Damier[1][1] = Valeur;  
            Restricted.add(R);
        }
          
        if ("B3".equals(R) && (B3 == null)){
            Damier[2][1] = Valeur; 
            Restricted.add(R);
        }

        if ("C1".equals(R) && (C1 == null)){
            Damier[0][2] = Valeur;
            Restricted.add(R);
        }
 
        if ("C2".equals(R) && (C2 == null)){
            Damier[1][2] = Valeur;
            Restricted.add(R);
        }
           
        if ("C3".equals(R) && (C3 == null)){
            Damier[2][2] = Valeur;  
            Restricted.add(R);
        }
        
        if ("Q".equals(R)){ /// quite a tout moment
            
            R = Quit(R);
            return R;
            
        }
        
        if (!(Restriction.contains(R))){
            System.err.println("Case invalide.");
            return null;
        }
        
        return R;  
    }
    ////////////////////////// marquage de la case pour savoir qui a gagner ////////////////////////////////
    static void Marquage(String W){
        
        if ("A1".equals(W)){
            if(joueur == true){
                A1 = true;
                ++caseA1;
            }else{
                A1 = false;
                --caseA1;
            }
        }
        if ("A2".equals(W)){
            if(joueur == true){
                A2 = true;
                ++caseA2;
            }else{
                A2 = false;
                --caseA2;
            }
        }
        if ("A3".equals(W)){
            if(joueur == true){
                A3 = true;
                ++caseA3;
            }else{
                A3 = false;
                --caseA3;
            }
        }
        if ("B1".equals(W)){
            if(joueur == true){
                B1 = true;
                ++caseB1;
            }else{
                B1 = false;
                --caseB1;
            }
        }
        if ("B2".equals(W)){
            if(joueur == true){
                B2 = true;
                ++caseB2;
            }else{
                B2 = false;
                --caseB2;
            }
        }
        if ("B3".equals(W)){
            if(joueur == true){
                B3 = true;
                ++caseB3;
            }else{
                B3 = false;
                --caseB3;
            }
        }
        if ("C1".equals(W)){
            if(joueur == true){
                C1 = true;
                ++caseC1;
            }else{
                C1 = false;
                --caseC1;
            }
        }
        if ("C2".equals(W)){
            if(joueur == true){
                C2 = true;
                ++caseC2;
            }else{
                C2 = false;
                --caseC2;
            }
        }
        if ("C3".equals(W)){
            if(joueur == true){
                C3 = true;
                ++caseC3;
            }else{
                C3 = false;
                --caseC3;
            }
        }
        
    }
    ///////////////////////////////// les conditions d'un match gagner perdu ou nul ////////////////////////////////
    static void Win(String W){
        
        if ((A1!=null) && (B1!=null) && (C1!=null)){
            if (((A1== true) && (B1== true) && (C1==true)) || (A1== false) && (B1== false) && (C1==false)){ // probleme
                InverseWin(W);
            }
        }    
        
        if ((A2!=null) && (B2!=null) && (C2!=null)){
            if (((A2== true) && (B2== true) && (C2==true)) || ((A2== false) && (B2== false) && (C2==false))){
                InverseWin(W);
            }
                
        }    
        if ((A3!=null) && (B3!=null) && (C3!=null)){
            if (((A3== true) && (B3== true) && (C3==true)) || ((A3== false) && (B3== false) && (C3==false))){
                InverseWin(W);
            }  
        }  
        if ((A1!=null) && (A2!=null) && (A3!=null)){
            if (((A1== true) && (A2== true) && (A3==true)) || ((A1== false) && (A2== false) && (A3==false))){
                InverseWin(W);
            }
                
        }    
        if ((B1!=null) && (B2!=null) && (B3!=null)){
            if (((B1 == true) && (B2== true) && (B3==true)) || ((B1== false) && (B2== false) && (B3==false))){
                InverseWin(W);
            }
                
        }    
        if ((C1!=null) && (C2!=null) && (C3!=null)){
            if (((C1== true) && (C2== true) && (C3==true)) || ((C1== false) && (C2== false) && (C3==false))){
                InverseWin(W);
            }  
        } 
        
        if ((A1!=null) && (B2!=null) && (C3!=null)){
            if (((A1== true) && (B2== true) && (C3==true)) || ((A1== false) && (B2== false) && (C3==false))){
                InverseWin(W);
            }  
        } 
        
        if ((A3!=null) && (B2!=null) && (C1!=null)){
            if (((A3== true) && (B2== true) && (C1==true)) || ((A3== false) && (B2== false) && (C1==false))){
                InverseWin(W);
            }  
        } 
        
        if ((A1!=null) && (A2!=null) && (A3!=null) && (B1!=null) && (B2!=null) && (B3!=null) && (C1!=null) && (C2!=null) && (C3!=null)){
            
            do{
            System.out.print("Match nul, recommencer (o/n) ? ");
            partie = Restart(key.next());
        }while(!("o".equals(partie)));
            
            
        }
        //////////////////// reeinverse les joueur car bug /////////////////
        if ("r".equals(W)){
            if (joueur == true){
                    JOUEUR = J2;
                }else{
                    JOUEUR = J1;
                }
        }else{
        
            if(nbJoueur==2){
                System.out.println("Au tour de "+JOUEUR+" de jouer !"); 

                if (joueur == true){
                    JOUEUR = J2;
                }else{
                    JOUEUR = J1;
                }


            }else{
                if (joueur == true){

                    System.out.println("Au tour de "+JOUEUR+" de jouer !");
                    JOUEUR = J2;

                }else{
                    System.out.print("Au tour de "+JOUEUR+" de jouer !");
                    JOUEUR = J1;

                }
         ////////////////////////////////////////////////////////////////////////////
            }
        }

    }
    ////////////////////////////////////// affiche qui le winner ////////////////////////////////
    static void InverseWin(String Gagnant){

        if (joueur == false){
            Gagnant = J1;
            ++scoreJ1;
        }else{
            Gagnant = J2;
            ++scoreJ2;
        }
        EcritureScore(scoreJ1, scoreJ2);
        System.out.println(Gagnant+" remporte la partie!");
        
        
        
        do{
            
            System.out.print("Rejouer la partie (o/n) ? ");
            partie = Restart(key.next());
            break;
        }while(true);
 
    }
    ///////////////////// restart la partie ///////////////////////////////////
    static String Restart(String P){
        
        
        String LowerCase = P.toLowerCase();//minuscule
        P = LowerCase;
      
        if ("n".equals(P) || "q".equals(P)){
            System.out.println();
            if (nbJoueur == 2){
                System.out.println("Merci d'avoir joué "+J1+" et "+J2+".");
            }else{
                System.out.println("Merci d'avoir joué "+J1+".");
            }
            System.exit(0);
        }
        
        if ("o".equals(P) ||"r".equals(P)){  
            
            System.out.println();
            Restricted.clear();
            A1=A2=A3=B1=B2=B3=C1=C2=C3=null;
            caseA1=caseA2=caseA3=caseB1=caseB2=caseB3=caseC1=caseC2=caseC3=0;
            String RenouvellementChoix;
            do{
                
                    if (nbJoueur ==2){
                        System.out.print("Et vous voulez toujours continuer a jouer l'un contre l'autre (o/n) ? ");
                    }else{
                        System.out.print("Et tu veut toujours continuer a jouer contre l'IA (o/n) ? ");
                    }
                    RenouvellementChoix = key.next();
                    
                    if("o".equals(RenouvellementChoix) || "O".equals(RenouvellementChoix)){
                        if(nbJoueur == 1){
                            
                            do{
                                System.out.print("Et tu veut rester sur le même niveaux (o/n) ? ");
                                RenouvellementChoix = key.next();
                                //

                                if ("n".equals(RenouvellementChoix) || "N".equals(RenouvellementChoix)){
                                    System.out.println();
                                    niveau = ChoixNiveaux(niveau);
                                    NvIA=IndicationIa(niveau);
                                    J2 = "l'IA ";
                                    break;
                                }else if("o".equals(RenouvellementChoix) || "O".equals(RenouvellementChoix)){
                                    break;
                                }else{
                                    System.err.println("Veuillez choisir 'o' ou 'n'.");
                                }
                            }while(true);
                            
                            break;
                            
                        }else{
                            break;
                        }

                    }
                    else if ("n".equals(RenouvellementChoix) || "N".equals(RenouvellementChoix)){
                        System.out.println();
                        LesJoueur();
                        break;
                    }else{
                        System.err.println("Veuillez repondre 'o' ou 'n'.");
                    }

            }while(true);

            System.out.println();
            
            for (int i = 0; i<3; ++i){
                for (int z =0; z<3; ++z){
                    Damier[i][z] = "   ";
                }
            } 
            for (int i = 0; i<3; ++i){
                System.out.print(nCase[i]);
            }

            System.out.println();
            System.out.println(esp+"   ------------");

            for (int i = 0; i<3; ++i){
                System.out.print(Indice[i]);

                for (int z=0; z<3; ++z){

                    System.out.print(Colone[z]);
                    System.out.print(Damier[i][z]);
                }
                System.out.println("|");
                System.out.println(Ligne[0]);

            }
            System.out.println();
            Affichage_Scores = "[ "+J1+" : "+scoreJ1+" | "+J2+NvIA+" : "+scoreJ2+" ]";
            printer(Affichage_Scores);
            System.out.println();
            
            if ("o".equals(P)){ 
                System.out.print("Cette fois ci c'est... ");
            }else{
                if (joueur == false){
                    JOUEUR = J2;
                }else{
                   JOUEUR = J1;
                }
                System.out.println("On reprend, c'est au tour de "+JOUEUR+" de jouer ! ");
                Win(P);
            }
            
        }
        else{
            System.err.println("Veuillez entrer 'o' ou 'n'");
        }

        return P;
    }
    ///////////// fonctionnaliter qui permet d'inverse les coordonner a1 ou 1a /////////////////////
    static String verifCoordonne(String V){ 
        
        
        String[] splitArray; 
        //StringBuffer Correction;
        
        String UpperCase = V.toUpperCase();//MAJUSCULE
        V = UpperCase;
        
           
        String separation = V;//SEPARATION
        splitArray = separation.split("");
        
        if ((splitArray.length < 2) || (splitArray.length > 2)) {
            return V;
            
        }else{

            if ("A".equals(splitArray[0]) || "B".equals(splitArray[0]) || "C".equals(splitArray[0])){

                V = splitArray[0]+splitArray[1];

            }
            else{
                V = splitArray[1] + splitArray[0];
            }
        }
        
        return V;
    }
    ////////////////// Le joueur 2 ne peut cboisir le meme pseudo que le j1 ///////////////////////////////////
    static String Etiquette(String V){

        String UpperCase = V.toUpperCase();//MAJUSCULE
        V = UpperCase;
        
        if(V.length()>1){
            System.err.println("Erreur ! Un seul caractère demander.");
            System.out.print("On la refait, donne moi un signe valide : "); 
            return V+="0";
        }else if (EtiquetteJ1.equals(V)){
            System.err.print("Oups! "+J1+" à déja choisi ce caractére, choisi un autre : ");
            return V+="0";
            
        }else{
            return V;
        }

    }
    /////////////////// ecris le score dans un fichier /////////////////////////////////////////////////
    static void EcritureScore(int sj1, int sj2){
        
        String chemin = "C:\\Users\\DanyL\\Desktop\\test.txt";
        File fichier =new File(chemin); 
        try {
                // Creation du fichier
            fichier.createNewFile();
                // creation d'un writer (un écrivain)
            FileWriter writer = new FileWriter(fichier);
            writer.write(J1+" : "+sj1+" | "+J2+NvIA+" : "+sj2);
            writer.close();
            
       } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
       }
    }
    ////////////////// Et affiche seulement le denier score effectuer ////////////////////////////////////////
    static void LectureScore()throws FileNotFoundException, IOException{

	String fichier ="C:\\Users\\DanyL\\Desktop\\test.txt";
        InputStream ips = new FileInputStream(fichier); 
        InputStreamReader ipsr =new InputStreamReader(ips);
        BufferedReader Lecture = new BufferedReader(ipsr);
        String ligne = Lecture.readLine();
        String PrLePrinter;
  
	
		//lecture du fichier texte	
        try{
            
            if ((ligne)!=null){
                PrLeScore = 1;
                PrLePrinter = "Rappel du dernier score !\n";
                printer(PrLePrinter);
                PrLePrinter ="|| "+ligne+" ||\n";
		printer(PrLePrinter);
                
            }else{
                System.out.println("Pas de score à affiché.\n");
            }
		Lecture.close();
                
        }		
	catch (Exception e){
            System.err.println("Erreur, pas de fichier trouver.");
	}
    }
    
    static int nJoueur(int nb){
        
        switch (nb) {
            case 1:
                return nb;
            case 2:
                return nb;
            default:
                System.err.println("1 : contre l'IA,\n2 : contre un autre joueur.");
                return 0;
        }
    }
    //////////////// fait appel a lintelligence artificielle //////////////////////////////
    static String IA( String IA) throws InterruptedException{
        
        //Random rand = new Random();//////////////
        System.out.print(" #  Réfléxion - [");
        
        do{
            IA = Intelligence(IA);
        }while(Restricted.contains(IA));
        
        System.out.println("]");

        return IA;
    }
    //////////// fonctionnaliter pr faciliter l'entrer des nievaux //////////////////////////////////////
    static String niveaux (String N){
        
        String[] splitArray; 
        
        String LowerCase = N.toLowerCase();//MAJUSCULE
        N = LowerCase;
        
           
        String separation = N;//SEPARATION
        splitArray = separation.split("");
        
        if("f".equals(splitArray[0]) || "m".equals(splitArray[0]) || "d".equals(splitArray[0])){
            return N;
        }else{
            System.err.println("Niveaux invalide.");
            System.err.print("Veuillez rentrer un niveau valide : ");
            return "";
        }    
    }
    //////////////////// intelligence artificielle avec facile moyen et difficille ainsi que quelque fonctionnaliter en plus //////////////
    static String Intelligence(String IA) throws InterruptedException{
        
        ArrayList List_difficile = new ArrayList();
        String InfoBulleDef[] = {"ZzzzzzzZ","Tu va perdre !","Hmm pas mal !", "Ne crois pas que ce sera aussi facile!", "Quand meme un peu de respect...", "Hmm essaye encore!", "Passons au chose sérieuse !", "Dommage pour toi ;)"};
        String InfoBulleAtt[] = {"Quel fair play !", "Sympa merci!", "Oh ouiii !", "C'est bon ça !", "Allé hop et de un :D", "Au suivant !"};
        String InfoBulleAléa[] = {"On va essayé...", "Mouais...", "J'éspere que ça va marcher !", "-_-", "Et si on essayais comme ça ?", "Voyons ce que sa donne !", "Qui ne tente rien n'a rien !"};
        String InfoBulleFacile[] = {"ZzzzzzzZ", "J'ai la flemme", "Abracadabra !", "De toute manière tu triche.", "Tu trouve pas qu'il fait beau ?"};
        List_difficile.clear();
        
        int times = 5;
        int chrono = 825;
        
        ParoleIA = InfoBulleAtt[rand.nextInt(5)]; 
        
        
        
        if("f".equals(niveau)){ 
            IA = (Restriction.get(rand.nextInt(9))).toString();
            times=1;
            ParoleIA = InfoBulleFacile[rand.nextInt(5)];
        }

        ///////// a peaufiner
        else if(caseA1+caseA2==-2 && !Restricted.contains("A3")){
            IA ="A3"; 
        }
        else if(caseA1+caseA3==-2 && !Restricted.contains("A2")){
            IA ="A2";
        }
        else if(caseA2+caseA3==-2 && !Restricted.contains("A1")){
            IA ="A1";
        }
                ////////////////
        else if(caseB1+caseB2==-2 && !Restricted.contains("B3")){
            IA ="B3"; 
        }
        else if(caseB1+caseB3==-2 && !Restricted.contains("B2")){
            IA ="B2";
        }
        else if(caseB2+caseB3==-2 && !Restricted.contains("B1")){
            IA ="B1";
        }
                //////////////////
        else if(caseC1+caseC2==-2 && !Restricted.contains("C3")){
            IA ="C3"; 
        }
        else if(caseC1+caseC3==-2 && !Restricted.contains("C2")){
            IA ="C2";
        }
        else if(caseC2+caseC3==-2 && !Restricted.contains("C1")){
            IA ="C1";
        }
        
        //////////////////////////////////////////////////////////
        
        else if(caseA1+caseB1==-2 && !Restricted.contains("C1")){
            IA="C1";
        }
        else if(caseA1+caseC1==-2 && !Restricted.contains("B1")){
            IA="B1";
        }
        else if(caseC1+caseB1==-2 && !Restricted.contains("A1")){
            IA="A1";
        }
        ///////////////
        else if(caseA2+caseB2==-2 && !Restricted.contains("C2")){
            IA="C2";
        }
        else if(caseA2+caseC2==-2 && !Restricted.contains("B2")){
            IA="B2";
        }
        else if(caseC2+caseB2==-2 && !Restricted.contains("A2")){
            IA="A2";
        }
        ///////////////////
        else if(caseA3+caseB3==-2 && !Restricted.contains("C3")){
            IA="C3";
        }
        else if(caseA3+caseC3==-2 && !Restricted.contains("B3")){
            IA="B3";
        }
        else if(caseC3+caseB3==-2 && !Restricted.contains("A3")){
            IA="A3";
        }
        
        //////////////////////////////////////////////////////
        /////////////////////////////////////////////////////
        else if(caseA1+caseB2==-2 && !Restricted.contains("C3")){
            IA="C3";
        }
        else if(caseA1+caseC3==-2 && !Restricted.contains("B2")){
            IA="B2";
        }
        else if(caseB2+caseC3==-2 && !Restricted.contains("A1")){
            IA="A1";
        }
            ////////////
        else if(caseA3+caseB2==-2 && !Restricted.contains("C1")){
            IA="C1";
        }
        else if(caseA3+caseC1==-2 && !Restricted.contains("B2")){
            IA="B2";
        }
        else if(caseB2+caseC1==-2 && !Restricted.contains("A3")){
            IA="A3";
        }
            ///////////////////////////////////////////////////////
      
        else{
            
            
            ParoleIA = InfoBulleDef[rand.nextInt(8)];
            
            if(caseA1+caseA2==2 && !Restricted.contains("A3")){
                IA ="A3"; 
            }
            
            else if(caseA1+caseA3==2 && !Restricted.contains("A2")){
                IA ="A2";
            }
            else if(caseA2+caseA3==2 && !Restricted.contains("A1")){
                IA ="A1";
            }
                    ////////////////
            else if(caseB1+caseB2==2 && !Restricted.contains("B3")){
                IA ="B3"; 
            }
            else if(caseB1+caseB3==2 && !Restricted.contains("B2")){
                IA ="B2";
            }
            else if(caseB2+caseB3==2 && !Restricted.contains("B1")){
                IA ="B1";
            }
                    //////////////////
            else if(caseC1+caseC2==2 && !Restricted.contains("C3")){
                IA ="C3"; 
            }
            else if(caseC1+caseC3==2 && !Restricted.contains("C2")){
                IA ="C2";
            }
            else if(caseC2+caseC3==2 && !Restricted.contains("C1")){
                IA ="C1";
                
            }

            //////////////////////////////////////////////////////////

            else if(caseA1+caseB1==2 && !Restricted.contains("C1")){
                IA="C1";
            }
            else if(caseA1+caseC1==2 && !Restricted.contains("B1")){
                IA="B1";
            }
            else if(caseC1+caseB1==2 && !Restricted.contains("A1")){
                IA="A1";
            }
            ///////////////
            else if(caseA2+caseB2==2 && !Restricted.contains("C2")){
                IA="C2";
            }
            else if(caseA2+caseC2==2 && !Restricted.contains("B2")){
                IA="B2";
            }
            else if(caseC2+caseB2==2 && !Restricted.contains("A2")){
                IA="A2";
            }
            ///////////////////
            else if(caseA3+caseB3==2 && !Restricted.contains("C3")){
                IA="C3";
            }
            else if(caseA3+caseC3==2 && !Restricted.contains("B3")){
                IA="B3";
                
            }
            else if(caseC3+caseB3==2 && !Restricted.contains("A3")){
                IA="A3";

            }

            /////////////////////////////////////////////////////
            else if(caseA1+caseB2==2 && !Restricted.contains("C3")){
                IA="C3";
            }
            else if(caseA1+caseC3==2 && !Restricted.contains("B2")){
                IA="B2";
            }
            else if(caseB2+caseC3==2 && !Restricted.contains("A1")){
                IA="A1";
            }
            ////////////
            else if(caseA3+caseB2==2 && !Restricted.contains("C1")){
                IA="C1";
            }
            else if(caseA3+caseC1==2 && !Restricted.contains("B2")){
                IA="B2";
            }
            else if(caseB2+caseC1==2 && !Restricted.contains("A3")){
                IA="A3";
            }
            // ok
            
            
            else if((caseA1== 0 && caseA2 ==0 && caseA3 ==0 && caseB1 ==0  && caseB2 ==0  
                    && caseB3 ==0  && caseC1 ==0  && caseC2 ==0  && caseC3 ==0) && "d".equals(niveau)){ // tour numero 1 quoi quil arrive 
                
                List_difficile.add("A1");
                List_difficile.add("A3");
                List_difficile.add("C1");
                List_difficile.add("C3");
                IA = (List_difficile.get(rand.nextInt(4))).toString();
                times=3;
            }
            
            else if((caseB1 == 1 || caseB3 == 1 || caseA2 == 1 || caseC2 == 1) && (caseA1== 0 && caseA3 ==0 && caseB2 ==0  && caseC1 ==0  && caseC3 ==0)){
                List_difficile.add("A1");
                List_difficile.add("A3");
                List_difficile.add("C1");
                List_difficile.add("C3");
                IA = (List_difficile.get(rand.nextInt(4))).toString();
                times=3;
            }
            
            
            else if((caseA1 == 1 || caseA3 == 1 || caseC1 == 1 || caseC3 == 1) && "d".equals(niveau) && !Restricted.contains("B2")){ // rentre une seul fois o moins 
                IA = "B2";
                times=3;
            }

            
            
            else if("d".equals(niveau) && !Restricted.contains("B2")){ 
                IA = "B2";
                times=3;
            }
            
            //////////////////////////////////////////////////  condition crucial de match nul ou de win ///////////////////////////////////////////////////////////////////
            
            else if("d".equals(niveau) && Restricted.contains("B2")){
                
                if ((caseA1 == 1 || caseA3 == 1 || caseC1 == 1 || caseC3 == 1) && (caseA1 == 0 || caseA3 == 0 || caseC1 == 0 || caseC3 == 0)){
                    List_difficile.add("A2");
                    List_difficile.add("B1");
                    List_difficile.add("B3");
                    List_difficile.add("C2");
                    IA = (List_difficile.get(rand.nextInt(4))).toString();
                    times=4;
                }else if(caseA1 == 0 || caseA3 == 0 || caseC1 == 0 || caseC3 == 0 ){
                    List_difficile.add("A1");
                    List_difficile.add("A3");
                    List_difficile.add("C1");
                    List_difficile.add("C3");
                    IA = (List_difficile.get(rand.nextInt(4))).toString();
                    times=1;
                }else{
                    IA = (Restriction.get(rand.nextInt(9))).toString();
                    times=2;
                }
                
                
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            else{
                IA = (Restriction.get(rand.nextInt(9))).toString();
                times = 3;
                ParoleIA = InfoBulleAléa[rand.nextInt(7)];
            }
        }
        
        //////////// temps de reflexion /////////////////////
                
        while(times!=0){
            
            Thread.sleep(chrono); // selon la reflexion
            
            System.out.print(":");
            
            switch(times){
                case 5:
                    chrono = 275;
                    break;
                case 3:
                    chrono = 270;
                    break;
                case 1:
                    chrono = 100;
                    break;
            }
            --times;
        }
        
        
        
        return IA;
    }
    ///////////////// Choix du niveaux ///////////////////////////////////
    static String ChoixNiveaux(String niveau){
            
            //System.out.println();
            System.out.println(esp+" --------------");
            System.out.println(esp+"| Facile    (f)|");
            System.out.println(esp+"| Moyen     (m)|");
            System.out.println(esp+"| Difficile (d)|");
            System.out.println(esp+" --------------\n");
            
            System.out.print("Et quel niveaux de difficulté souhaitez vous jouer ? ");
            do{
                niveau = niveaux(key.next());
            }while("".equals(niveau));
            
            System.out.println();
            System.out.print("Prepare toi a jouer contre l'ordinateur !\n");
            
            return niveau;
    }
    ///////////////////// esthetique pour l'affichage sur le score ////////////////////
    static String IndicationIa( String Z){
        
        switch (Z) {
            case "f":
                NvIA = "(facile)";
                break;
            case "m":
                NvIA = "(moyen)";
                break;
            default:
                NvIA = "(difficile)";
                break;
        }
        
        return NvIA;
    }
    /////////////// fonctionaliter qui permet de centrer le score //////////////////////////////
    static void printer(String str) {
            
            int size = 52;
            String Indication = "";
            
            if(PrLeScore == 1){
                size = 170;
            }else{
                Indication = "( q : quit )";
            }

            int left = (size - str.length()) / 2;
            int right = size - left - str.length();
            String repeatedChar = " ";
            
            StringBuilder buff = new StringBuilder();
            
            for (int i = 0; i < left; i++) {
                buff.append(repeatedChar);
            }
            
            buff.append(str);
            
            for (int i = 0; i < right; i++) {
                buff.append(repeatedChar);
            }
          
            
            System.out.println(buff.toString()+Indication);
        }
    
    /////////////// Fonction pr rentrer le nombre de joueur ///////////////////////////////
    static void LesJoueur (){
        
        EtiquetteJ1 = EtiquetteJ2 = "";
        NvIA = "";
        scoreJ1 = scoreJ2 = 0; 
        
        do{
            try{
                System.out.print("Taper le nombre de joueur ( 1 ou 2) : ");
                nbJoueur = nJoueur(key.nextInt());
            }catch (InputMismatchException e){
                System.err.println("Donnée incorrect.");
            }
            
            key.nextLine();
                    
        }while(nbJoueur == 0);

        System.out.println();
        /////////////////////////////////////////////////
        
        do{
            
            System.out.print("Pseudo Joueur 1 : ");
            J1 = key.next();
            if(nbJoueur ==1 && ("IA".equals(J1) || "ia".equals(J1) || "Ia".equals(J1) || "iA".equals(J1))){
                System.err.println("Attention ceci est le pseudo que porte l'ordinateur !");
            }else{
                break;
            }
        }while(true);
        ////////////////////////////////////////////////////////
        System.out.print("Et quel signe veux tu utilisez "+J1+" (1 caractère) ? ");
        
        do{
            EtiquetteJ1=Etiquette(key.next());
        }while(EtiquetteJ1.length()>1);
        
        System.out.println();
        
        if(nbJoueur == 2){
        
            while(true){
                System.out.print("Pseudo Joueur 2 : ");
                J2 = key.next();
                
                if (!J1.equals(J2)){
                    break;
                }else{
                    System.err.println("Le joueur 1 a dèja choisi ce pseudo.");
                }
            }

            System.out.print("Et quel signe veux tu utilisez "+J2+" (1 caractère) ? ");
            do{
                EtiquetteJ2=Etiquette(key.next());
            }while(EtiquetteJ2.length()>1);
            
        }else{///condition pour joeur 1 de pas choisir le meme signe que l'ia 
            
            String V;
            
            do{
                char c = (char)(rand.nextInt(26) + 97);// ascii au hasard
                V = Character.toString(c); 
                String UpperCase = V.toUpperCase();//MAJUSCULE
                V = UpperCase;
            }while(V.equals(EtiquetteJ1));
            
            EtiquetteJ2 = V;
            niveau = ChoixNiveaux(niveau);
            
            NvIA=IndicationIa(niveau);
            
            J2 = "l'IA ";
        }
        
        JOUEUR = J1;
        
    }
    /////////////////// Fonction pour quiter recommencer ou continuer ///////////////////////////
    static String Quit(String R){
        String[] splitArray; 
            
        System.out.println();
        System.out.println(esp+" --------------");
        System.out.println(esp+"| Continuer (c)|");
        System.out.println(esp+"| Restart   (r)|");
        System.out.println(esp+"| Quit      (q)|");
        System.out.println(esp+" --------------\n");  
            
        do{
            System.out.print("Que veut-tu faire ? ");
            Reponse=key.next();
                
            String LowerCase = Reponse.toLowerCase();
            Reponse = LowerCase;
            String separation = Reponse;//SEPARATION
            splitArray = separation.split("");
              
            if("c".equals(splitArray[0])){
                if(JOUEUR.equals(J2)){
                    System.out.println("C'est toujours au tour de "+J1+" de jouer !");
                }else{
                    System.out.println("C'est toujours au tour de "+J2+" de jouer !");
                }
                return null;
            }else if("r".equals(splitArray[0]) || "q".equals(splitArray[0])){
                Restart(splitArray[0]);
                break;
            }else{
                System.err.println("Veuillez entrer une des reponses ci-dessus."); 
            }
        }while(true);
            
        return null;
    }

}