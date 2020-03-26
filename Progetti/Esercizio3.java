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
import java.util.Locale;

class Esercizio3 {


    public static void main(String args[]){
      Locale.setDefault(Locale.US);
      try{
      ArrayList <Integer> semi = new ArrayList <Integer> ();
      ArrayList <Integer> vasi = new ArrayList <Integer> ();
      int size = 0;


      Scanner semiScanner = new Scanner(new FileReader(new File(args[0])));
      Scanner vasiScanner = new Scanner(new FileReader(new File(args[1])));
      int a = 0;
      int b = 0;
      while (semiScanner.hasNext() && vasiScanner.hasNext()){
        if(semiScanner.hasNextInt() && vasiScanner.hasNextInt() ){
          //System.out.println(semiScanner.nextInt());
          //Inserisco i semi e i vasi nei rispettivi arralist creati per essi
          size++;
          a = semiScanner.nextInt();
          Integer n = Integer.valueOf(a);
          b = vasiScanner.nextInt();
          Integer n2 =  Integer.valueOf(b);
          semi.add(a);
          vasi.add(n2);
        }
        else{
          semiScanner.next();
          vasiScanner.next();
          }
      }
      //Dal momento che devo riempire i vasi e che il costo di questo riemppimento è 1 joule anche per lo spostamento di più semi
      //il costo massimo in joule si riduce allo spostamento più "lungo" che devo effettuare tra il seme e il vaso che risultano più lontani
      //di conseguenza l'esercizio è stato risolto ordinando sia i semi che i vasi e tenendo traccia di questo spostamento.

      Collections.sort(semi);
      Collections.sort(vasi);
      System.out.println("SEMI: "+semi);
      System.out.println("VASI: "+vasi);
      System.out.println("SIZE: "+size);

      int max = 0;
      //ricerca del massimo
      max = Math.abs(vasi.get(0)-semi.get(0));
      for(int i = 0 ; i < semi.size(); i++){

        System.out.println("JOULE AL PASSO "+i+": "+Math.abs(vasi.get(i)-semi.get(i)));
        if(Math.abs(vasi.get(i)-semi.get(i)) > max){
          max = Math.abs(vasi.get(i)-semi.get(i));
          }
      }
      System.out.println("MAX: "+max);
      semiScanner.close();
      vasiScanner.close();
      File fileOutput = new File(args[2]);
      FileOutputStream fos = new FileOutputStream(fileOutput);
      PrintStream ps = new PrintStream(fos);
      ps.println(max);



      }catch ( IOException ex ) {
           System.out.println("ERRORE LETTURA FILE");
           System.err.println(ex);
           System.exit(1);
       }

      }
    }
