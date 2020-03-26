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

public class Esercizio4 {
  //definisco il mio grafico come una adiacency list
  static class Graph {

       int V;
       LinkedList <Integer> adjListArray[];
       //costruttore
       Graph(int V)
       {
           this.V = V;
           //La lunghezza del mio array equivale al numero di nodi del grafico
           adjListArray = new LinkedList [V];
           // Creo una nuova lista per ogni nodo
           // in modo da memorizzare i nodi adiacenti ad ogni vertice
           for(int i = 0; i < V ; i++){
               adjListArray[i] = new LinkedList<>();
           }
       }
       //Metodo bfs che oltre ad eseguire la visita restituisce l'ultima posizione che visito prima
       //di tornare al punto di partenza ovvero lo zero
       //utilizzato poi per la stampa del percorso minimo
        int  BFS(int s, int V, Integer [] erdos, Integer [] parent, PrintStream ps){

           int last = 0;
           boolean visited[] = new boolean[V];
           // Creazione della coda per l'implementazione dell ricerca BFS
           LinkedList<Integer> queue = new LinkedList<Integer>();
           //Non setto il nodo radice come visistato in modo che non sappia cheè gia stato visitato e chi ci torni per altra via
           //visited[s]=true;
           queue.add(s);
           //setto tutti i numeri di erdos al massimo (idealmente a più infinito)
           // for(int i = 0; i<V ; i++){
           //   erdos[i] = Integer.MAX_VALUE;
           // }
           boolean trovato = false;
           erdos[s] = 0;
           parent[s] =-1;

           while (queue.size() != 0 && trovato==false )
           {
               //Tolgo l'elemento dalla coda e tengo traccia se sia o meno lo zero
               s = queue.poll();
               //Prendo tutti i vertici adiacenti a s (vertice estratto dalla coda)
               //E li contrassegno nel vettore visited, in modo da non vistitae più volte nodi già visistati

               //Utilizzo un iteratore per visitare i vari nodi collegati al nodo che sto ispezionando
               Iterator <Integer> i = adjListArray[s].listIterator();
               while (i.hasNext())
               {
                   int n = i.next();
                   if (!visited[n] )
                   {
                       //Oltre a settare il nodo n, adiacente a s, come "visited" gli assegno numero il numero di erdos
                       //e tengo traccia del padre tramite il vettore parent
                       visited[n] = true;
                       erdos[n] = erdos[s]+1;
                       parent[n] = s;
                       //System.out.println("PARENT N: "+s+  " => " +n);
                       //Cuore della funzione:
                       //Quando trovo il nodo n = 0 fermo il ciclo, sono tornato al punto di partenza
                       //erdos[n] > 0 sta a significare che non è lo 0 "iniziale" ma che sono partito da 0 e ci sono tornato
                       //Questo è stato possibile non inserendo 0 nel vettore dei visistati la prima volta che l'ho inocntrato
                       if(n == 0 && erdos[n]>0){
                         //Devo tenere traccia del numero di erdos poichè rappresenta il numero minimo di passi per arrivare a esso
                         System.out.println(erdos[n]);
                         ps.println(erdos[n]);
                         //System.out.println("PARENT{n} di "+n+" è "+parent[n]);
                         //Salvo quindi il padre di n in modo da poter ottenere il perocorso minimo tramite la funzione PrintPath
                         last=parent[n];
                         trovato = true;
                         break;

                       }
                       //aggiungo alla coda n in modo da poter continuare la visita
                       queue.add(n);
                       //System.out.println("QUEUE "+queue);
                   }
               }//fine secondo while
          }//fine primo while
          return last;
       }//fine metodo bfs
   }//fine classe

   //funzione ricorsiva per stampare il percorso minimo da un nodo ad un altro di un grafico
   static void printPath(int rad, int arr, Integer [] parent, PrintStream ps){
      //String path = "";
      if ( rad == arr ){
        System.out.println(rad);
        ps.println(rad);

      }
     else{
      printPath(rad , parent[arr] , parent, ps);
      System.out.println(arr);
      ps.println(arr);
      }
   }
   //funzione per aggiungere un nodo adiacente ad un certo vertice
   static void addEdge(Graph graph, int src, int dest){
       //aggiungo un collegamento dal src al nodo dest, non faccio anche il collegamento inverso
       //poichè il grafico è da considerarsi orientato
       graph.adjListArray[src].add(dest);
   }
   //stampa del grafico si console
   static void printGraph(Graph graph) {
       for(int v = 0; v < graph.V; v++) {

           System.out.println("Adjacency list of vertex "+ v);
           System.out.print("head");
           for(Integer i: graph.adjListArray[v]){
               System.out.print(" -> "+i);
           }
           System.out.println("\n");
       }
   }
  //main del programma
  public static void main (String[]args){
    Locale.setDefault(Locale.US);
    try{
      File file = new File(args[0]);
      Scanner scan = new Scanner(new FileReader(file));

      //leggo i dati passati dal file din input, utili per la costruzione della lista di adiacenza
      int nLinee = 0;
      int n = scan.nextInt();
      System.out.println(n);
      scan.nextLine();

      int V = n;
      Graph graph = new Graph(V);
      //creazione del grafico da file di input
      while (nLinee < n){

       while (scan.hasNext()){

        int successivi = scan.nextInt();
        //System.out.println("SUCCESSIVI: "+successivi);

        for (int i = 0; i < successivi; i++){
           addEdge(graph, nLinee, scan.nextInt());

        }

        nLinee++;
        }
        if(scan.hasNextLine()){
             scan.nextLine();
        }
      }
       //stampa il grafico in console
       printGraph(graph);
       System.out.println("");
       //creo i due vettori erdos e parent
       Integer [] erdos = new Integer [V];
       Integer [] parent = new Integer [V];
       scan.close();
       //stampa su file esterno
       File fileOutput = new File(args[1]);
       FileOutputStream fos = new FileOutputStream(fileOutput);
       PrintStream ps = new PrintStream(fos);
       //ps.println();
       //richiamo il metodo che mi restituisce l'elemento precedente a 0 punto di partenza
       //Ovvero l'ultimo passo prima di tornare a lui
       int last  = graph.BFS(0,V, erdos, parent,ps);
       //stampo il perocorso minimo da 0 all'elemento precdente (da 0 a 0 non darebbe il risulato voluto)
       printPath(0,last,parent,ps);
       ps.close();


    }catch ( IOException ex ) {
      System.out.println("ERRORE LETTURA FILE");
      System.err.println(ex);
      System.exit(1);
    }//fine catch
  }//fine main
}//fine classe Esercizio4
