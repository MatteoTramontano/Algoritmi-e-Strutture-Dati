//Matteo Tramontano
//772520
//matteo.tramontano@studio.unibo.it
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.util.*;
import java.io.FileReader;
import java.util.Locale;


public class Esercizio1 {

    public static void main(String args[]){
      Locale.setDefault(Locale.US);
      try{
      File file = new File(args[0]);
      Scanner scan = new Scanner(new FileReader(file));
      //ottengo il seed dal file e lo setto nel random
      long seed = scan.nextLong();
      Random random = new Random();
      random.setSeed(seed);
      //System.out.println("Stampa del seed: "+seed);
      //System.out.println("------------------------------------------------------------------");

      //Non serve solo per andare avanti con lettura file ("SEME")
      String seme=scan.nextLine();

      //ottengo la lista completa delle chiavi dal file
      String chiavi=scan.nextLine();
      System.out.println("Stampa delle chiavi come stringa: "+chiavi);
      System.out.println("------------------------------------------------------------------");
      List <String> listaSplit = Arrays.asList(chiavi.split("\\s* \\s*"));
      ArrayList<String> chiaviFull = new ArrayList<String>(listaSplit);
      System.out.println("Stampa delle chiavi come arraylist : "+listaSplit);
      System.out.println("------------------------------------------------------------------");

      //ottengo la lista delle chiavi scelte dal file
      String chiaviScelte = scan.nextLine();
      List <String> listaSplitSelect = Arrays.asList(chiaviScelte.split("\\s* \\s*"));
      ArrayList <String> selectedKey = new ArrayList<String> (listaSplitSelect);
      System.out.println("Stampa delle CHIAVI SCELTE come arraylist: "+selectedKey);
      //prendo la frase a dal file per levarmela
      String frase=scan.nextLine();
      System.out.println(frase);
      System.out.println("------------------------------------------------------------------");
      //creazione dell'hasmap
      HashMap <String, ArrayList <String> > map2 = new HashMap <String, ArrayList <String>> ();
      //riempo l'hasmap
      for(int i=1 ; i<=listaSplit.size() ; i++){

      String dati=scan.nextLine();
      List <String> dati2= Arrays.asList(dati.split("\\s* \\s*"));
      ArrayList <String> datiFinal = new ArrayList<String>(dati2);
      String keyOne = datiFinal.get(0);
      System.out.println("KEY ONE: "+keyOne);
      datiFinal.remove(0);
      datiFinal.remove(0);
      datiFinal.remove(0);
      datiFinal.removeAll(Collections.singleton(","));
      datiFinal.remove("]");

      map2.put(keyOne,datiFinal);
    }

    //Stampa della mappa CHIAVE - VALORE
   //    for (String i : map2.keySet()) {
   //   System.out.println("key: " + i + " value: " + map2.get(i)+" size: "+map2.get(i).size());
   //
   // }

   //Inizializzazione dei valori che devo ritornare

   String fraseCasuale = "";
   String indici = "";
   //ricerca all'intenro dell'hashmap delle chiavi scelte
   for (int i = 0; i < selectedKey.size(); i++ ){
      if (map2.containsKey(selectedKey.get(i)) == true){

         String key = selectedKey.get(i);
         ArrayList <String> restricted = new ArrayList <String> ();
         restricted= map2.get(key);

         System.out.println("CHIAVE PASSATA: "+key);
         System.out.println("RESTRICTED: "+restricted);
         System.out.println("SIZE: "+restricted.size());
         //setto il mio valore random da 0 alla size dell'arraylist nel quale dovrò applicare la ricerca
         //in questo modo evito di andare out of bounds
         int k = random.nextInt(restricted.size());
         //tengo traccia dell'indice in quanto dovrò stamparlo
         if(i == selectedKey.size()-1)
         {
           indici+=k;
         }
         else
         {
           indici += k+", ";
         }
         //selezione la frase in maniera random dall'arraylist associato alla chiave "key" passata da input
         fraseCasuale += map2.get(key).get(k)+". ";
         scan.close();
         File fileOutput = new File(args[1]);
         FileOutputStream fos = new FileOutputStream(fileOutput);
   			 PrintStream ps = new PrintStream(fos);
         ps.println(fraseCasuale);
         ps.println(indici);
         ps.close();

         //fraseCasuale += restricted.get(k)+". ";
         //System.out.println("FRASE CASUALE: "+restricted.get(k));



         System.out.println("------------------------------------------------------------------");


      }
      else{
        System.out.println("CHIAVE "+i+" NON TROVATA");
      }
   }
      System.out.println("Frase casuale finale: ");
      System.out.println(fraseCasuale);

      System.out.println("Indici casuali finale: ");
      System.out.println(indici);
    }
     catch ( IOException ex ) {
        System.err.println(ex);
        System.exit(1);
    }
}
}
