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

class Esercizio2 {
  //creso la classe Node la quale rappresenta sostanzialmente il mio albero2
  //con i relativi metodo per aggingere figli dx o sx sui quali è basato il programma
  static class Node
 {
     Integer key;
     Node left, right;
     //Node Parent -> nil

     public Node(Integer item){
         key = item;
         left = right = null;
     }

     public void setLeft(Node left){
        this.left = left;
        //left.setParent THIS
    }

    public void setRight(Node right){
        this.right = right;
        //set parent
    }

    public Integer getKey (){
      return key;
    }
 }
//metodo booleano per verificare che l'abero sia completo (da definizione di heap)
 static boolean isComplete(Node root, int index, int numNodi){
       // Un albero vuoto è un albero completo
       if (root == null)
          return true;
       //Se l'indice assegnato al nodo corrrente è maggiore del numero di nodi nell'albero
       //significa che l'albero non è completo
       if (index >= numNodi)
          return false;
       // Si esegue ricorsivamente per sottoalbero sx e dx
       return (isComplete(root.left, 2 * index + 1, numNodi)
           && isComplete(root.right, 2 * index + 2, numNodi));

   }


 //Stampo dell'albero come heap, il che equivale a stamparlo in level
 static String printLevelOrder(Node root){
       // Caso albero vuoto
       String toPrint = "";
       // if(root == null)
       //     return "null";
       //Creo una coda di nodi che mi serve per la visita
       Queue <Node> q = new LinkedList <Node>();
       //Inserisco la radice e inizio la ricerca
       q.add(root);
       while(true){

           int nodeCount = q.size();
           if(nodeCount == 0)
               break;
           //Scorro i nodi della coda e gli Stampo
           //aggiungendo alla coda i nodi x livello
           while(nodeCount > 0){

               Node node = q.peek();
               if(node.getKey() == null){
                 //toPrint +="";
                 //Non fare niente
               }
               else{
               toPrint+=node.key + ", ";
               }
               // System.out.println(toPrint);
               q.remove();

                if(node.left != null){
                   q.add(node.left);
                }
                if(node.right != null){
                    q.add(node.right);
                }
               nodeCount--;
           }
       }
       return toPrint;
   }
    //Il programma è basato sulla lettura dei caratteri "(", ")"e ","
    //e su uno stack di pilaNodi
    //In breve
    //carattere letto: "(" => .Creo il nodo con il caraattere Integer successivo,
    //                        .Lo setto figlio SX dell'ultimo nodo nello stack
    //                        .Aggiungo il nodo appena creato allo Stack
    //carettere letto: ")" => rimuovo l'ultimo nodo dallo Stack
    //carattere letto: "," => .Elimino ultimo nodo dallo Stack,
    //                        .Creo il nodo con il carattere Integer successivo,
    //                        .Setto il nodo come figlio DX dell'ultimo nodo dello Stack
    //                        .Aggiungo il nodo creato allo Stack
    //Il controllo Heap viene fatto durante la creazione dell'albero, in caso non sia heap il ciclo viene interrotto
    //Il controllo di albero completo viene fatto dopo la creaione dell'albero
    public static void main(String args[]){
      try{
           Locale.setDefault(Locale.US);
           File file = new File(args[0]);
           Scanner scan = new Scanner(new FileReader(file));

           String albero = scan.nextLine();
           List<String> albero2 = Arrays.asList(albero.split("\\s* \\s*"));
           ArrayList<String> alberoInput = new ArrayList<String>(albero2);
           //uso uno stack per tenere traccia dei nodi e sfruttare la sua logica LIFO
           Stack <Node> pilaNodi = new Stack <Node> ();
           //booleani usato per controllare a run time se sia un heap
           //se entrambi diventano false l'albero non è un heap di nessun tipo
           boolean isAnHeapMax = true;
           boolean isAnHeapMin = true;
           //Struttura in cui salvo i miei nodi
           ArrayList <Node> listaNodi = new ArrayList <Node>();

          System.out.println(alberoInput);

          for(int i = 0 ; i < alberoInput.size() ; i++){
              if(alberoInput.get(i).equals("(")){
                //caso singolo in cui il nodo sia il primo e quindi la radice
                if(pilaNodi.empty()){
                  System.out.println(alberoInput.get(i+1)+" è la ROOT");
                  Node nodo = new Node (Integer.valueOf(alberoInput.get(i+1)));
                  pilaNodi.push(nodo);
                  listaNodi.add(nodo);
                }
                else{//non è radice
                  //gestione del "-" il trattino significa che non vi è nodo quindi lo tratto come nodo null
                  if(alberoInput.get(i+1).equals("-")){
                    Node nodo = new Node (null);
                    pilaNodi.peek().setLeft(nodo);
                    pilaNodi.add(nodo);
                    listaNodi.add(nodo);
                  }

                  else{//è un numero
                      Node nodo = new Node (Integer.valueOf(alberoInput.get(i+1)));
                      //prima di inserirlo nell'albero controllo se il nodo sia maggiore/minore del padre
                      //in questo modo tengot traccia se sia min-heap o max-HEAP
                      //se non è nessuno dei due brekko
                      if (isAnHeapMax == true && nodo.getKey() > pilaNodi.peek().getKey()){
                         isAnHeapMax = false;
                      }
                      if (isAnHeapMin == true && nodo.getKey() < pilaNodi.peek().getKey()){
                         isAnHeapMin = false;
                      }
                      if (isAnHeapMax == false && isAnHeapMin == false){
                        System.out.println("NO HEAP MAX - NO HEAP MIN - NO HEAP SO");
                        break;
                  }
                  //manitente le prorpietà di heap
                    pilaNodi.peek().setLeft(nodo);
                    pilaNodi.push(nodo);
                    listaNodi.add(nodo);
                }
              }//fine else (non è root)
              }//fine di parentesi aperta
              //caso parentesi chiusa, tolgo semolicemente il nodo dalla coda
             if (alberoInput.get(i).equals(")")){
               pilaNodi.pop();
             }
             if(alberoInput.get(i).equals(",")){
               pilaNodi.pop();

               if (alberoInput.get(i+1).equals("-")){
                 Node nodo = new Node (null);
                 pilaNodi.peek().setRight(nodo);
                 pilaNodi.push(nodo);
                 listaNodi.add(nodo);
               }

               else{
               Node nodo = new Node (Integer.valueOf(alberoInput.get(i+1)));
               //prima di inserirlo nell'albero controllo se il nodo sia maggiore/minore del padre
               //in questo modo tengot traccia se sia min-heap o max-HEAP
               //se non è nessuno dei due brekko
               if (isAnHeapMax == true && nodo.getKey() > pilaNodi.peek().getKey()){
                  isAnHeapMax = false;
               }
               if (isAnHeapMin == true && nodo.getKey() < pilaNodi.peek().getKey()){
                  isAnHeapMin = false;
               }
               if (isAnHeapMax == false && isAnHeapMin == false){
                 System.out.println("NO HEAP MAX - NO HEAP MIN - NO HEAP SO");
                 break;
               }
               //Node padre = new Node (Integer.valueOf(pila.peek()));
               pilaNodi.peek().setRight(nodo);
               //System.out.println(alberoInput.get(i+1)+ " è figlio dx di "+Integer.valueOf(pila.peek()));
               pilaNodi.push(nodo);
               listaNodi.add(nodo);
               }
            }
        }
        //definisco la stringa da restituire in output, inizialmente vuota
        String alb  =  "";
        //rimuovo dalla mia lista i nodi null

        //boolean isComplete = isComplete(listaNodi.get(0),0 , listaNodi.size());


        if((isAnHeapMax == true || isAnHeapMin == true)){
          for(int i = 0 ; i < listaNodi.size() ; i ++) {
            if(listaNodi.get(i).getKey()== null)
              listaNodi.remove(listaNodi.get(i));
          }
          if (isComplete(listaNodi.get(0),0 , listaNodi.size())){
            System.out.println("Is complete : true");;
            Node root = listaNodi.get(0);
            System.out.println("Stampa in level");
            alb = printLevelOrder(root);
            //tolgo dalla stampa " ," finali in modi che risulti come gli output forniti da consegna
            alb = alb.substring(0, alb.length()-2);
            System.out.println(alb);
          }
          else{
            //se non è completo non è un heap e quindi è false
            System.out.println("Is complete : false");
            alb = "false";
            System.out.println(alb);
        }
      }
        //se non è un min heap o max heap resituisce false
        else{
          alb = "false";
          System.out.println(alb);
        }

        scan.close();
        File fileOutput = new File(args[1]);
        FileOutputStream fos = new FileOutputStream(fileOutput);
        PrintStream ps = new PrintStream(fos);
        ps.println(alb);

      }catch ( IOException ex ) {
        System.err.println(ex);
        System.exit(1);
   }
  }
}
