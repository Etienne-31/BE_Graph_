package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;

import java.util.ArrayList;
import java.util.List;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.shortestpath.LabelCentre;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
public class algoCentre{

    

    public algoCentre(Integer nombreStand,Graph graph){

    }

    private static Double DjikstraToGetEcartement(Node depart,Graph graph){
        //////////////////////////// Cette partie sert à verifier que le node est bien dans le graph sinon Djikstra renvera -1
        Double ecartement = -1.00;
        for(int i = 0;i<graph.size();i++){
            if(depart == graph.get(i)){
                ecartement = 0.00;
                break;
            }
        }
        if(ecartement == -1){
            return ecartement;
        }
        ////////////////////////////////////////////////////
        ////////////////////Cette partie de la fonction servira à initialiser le tableau de label ainsi que ce qui nous servira dans l'algorithme 
        List<Arc> listSuccesor;
        Node nodeIterator;
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        Label labelTab[] = new Label[graph.size()];
        
        for(int i = 0;i<graph.size();i++){
            nodeIterator = graph.get(i);  // On recupere le Node i 
            labelTab[i] = new Label(nodeIterator); // On rempli dans le tableau en initialisant avec le constructeur de label 
        }
        Label originLabel = new Label(depart);
        originLabel.setCost(0.0);  // On initialise le coût de l'origine à 0;
        originLabel.setMarque();
        labelTab[depart.getId()] = originLabel; // On initialise la première case du tableau
        tas.insert(originLabel); // On met l'origine dans le tas après avoir fini d'initialiser le tableau 
        /////////////////////////
        ////////Cette partie de l'agorithme servira à mettre à jour le coûts pour aller à chaque noeud depuis le noeud de depart
        ///////Comme il n'y a pas de destination, nous allons iterer jusqu'à avoir fait tous les noeuds du graphe
        Label labelIterator;
        Label oldLabel;
        Double coutActuel;
        Double coutArc;
        while(tas.isEmpty() == false){   // Tant qu'on aura pas vider le tas la boule while va se réaliser afin de mettre à jour le tableau de Label 

            labelIterator = tas.deleteMin();
            
            //System.out.println(" Le noeud "+labelIterator.getSommetCourant().getId()+" a été sorti du tas son coût est de : "+labelIterator.getCost() );
            listSuccesor = labelIterator.getSommetCourant().getSuccessors();
            

            //System.out.println("Noeud_id : "+labelIterator.getSommetCourant().getId()+" | Est marque : "+labelIterator.getMarqueStatus());

            for(Arc arcIterator : listSuccesor){

                //System.out.println("Origine : "+arcIterator.getOrigin().getId()+" | Destination : "+arcIterator.getDestination().getId()+" | Destination est marque : "+labelTab[arcIterator.getDestination().getId()].getMarqueStatus());

                if(labelTab[arcIterator.getDestination().getId()].getMarqueStatus() == false){

                   // System.out.println("La destination : "+arcIterator.getDestination().getId()+" n'est pas marque");

                    labelTab[arcIterator.getDestination().getId()].setMarque();

                    //System.out.println("La destination : "+arcIterator.getDestination().getId()+" a été marqué");

                    oldLabel = labelTab[arcIterator.getDestination().getId()]; // Copie de l'ancienne version du label afin d update ensuite si besoins 

                    coutActuel = labelTab[arcIterator.getDestination().getId()].getCost();
                    coutArc = labelIterator.getCost() + arcIterator.getLength();
                     
                   // System.out.println(" Le cout actuel pour allé à la destination est de : "+coutActuel+" Le nouveau cout pour acceder à cette destination est de "+coutArc);

                    if(coutActuel > coutArc){
                        labelTab[arcIterator.getDestination().getId()].setCost(coutArc); // Je met à jour dans le tableau 
                                                        
                        if(coutActuel == Double.POSITIVE_INFINITY){
                           // System.out.println("Le sommet n'était pas dans le tas ");
                            labelTab[arcIterator.getDestination().getId()].setFather(arcIterator);   // Je definis le père ( arc ) du label qui va être inseré
                            tas.insert(labelTab[arcIterator.getDestination().getId()]);              // J'insère le nouveau Label
                        }
                        else{
                           // System.out.println("Le sommet était dans le tas ");
                            labelTab[arcIterator.getDestination().getId()].setFather(arcIterator); // pas sur pour celui mais doit etre la si il y a deux arc qui vont au mem node , l orgine sera la même mais l arc different 
                            tas.Update(oldLabel,labelTab[arcIterator.getDestination().getId()]);   // j'update l'ancien label avec les nouvel information du pere et de cout
                        }
                    }  
                }
            }
        }
        ////////////////////////// A ce niveau la, tous les coûts pour chaque sommets a été mis à jour il faut juste trouver l'écartement le plus faible
        ecartement = -1.00;  // On définit ecartement à -1 afin de l'initialiser comme étant une valeur impossible à avoir pour le coût d'un chemin
        Double valeur;
        for(int j = 0;j<labelTab.length-1;j++){
            if(ecartement == -1.00){
                ecartement = labelTab[j].getTotalCost();
            }
            else if(ecartement <= labelTab[j].getTotalCost()){
                ecartement = labelTab[j].getTotalCost();
            }
            
        }
        if(ecartement <= 0){
            ecartement = -1.00;
        }
        ////////////////////////////////////////////////////////////
        return ecartement;
    }

    public static List<LabelCentre> algoCentreStatique(int nombreStand,Graph graph){
        List<LabelCentre> listRetour = new ArrayList<LabelCentre>();
        BinaryHeap<LabelCentre> tas = new BinaryHeap<LabelCentre>();
        LabelCentre labelTab[] = new LabelCentre[graph.size()];
        Node nodeIterator;
        Double ecartementIterator;
        for(int i = 0;i<graph.size();i++){
            nodeIterator = graph.get(i);  // On recupere le Node i 
            labelTab[i] = new LabelCentre(nodeIterator); // On rempli dans le tableau en initialisant avec le constructeur de label 
        }
        
        for(int i = 0;i<labelTab.length-1;i++){
            ecartementIterator = DjikstraToGetEcartement(labelTab[i].getSommetCourant(), graph); // On récupère l'ecartement du sommet
            System.out.println("AlgoCentre : ecartement Node : "+labelTab[i].getSommetCourant().getId()+" ecartement = "+ecartementIterator);
            labelTab[i].setEcartement(ecartementIterator);                           //On le met à jour 
            tas.insert(labelTab[i]);                                                /// On l'insert dans le tas pour qu'il aille se mettre à sa place
        }
        System.out.println("AlgoCentre : Fin de remplissage du tas ");
        System.out.println("AlgoCentre : affichage du tas  ");
        System.out.println("AlgoCentre : "+tas.toString());
        LabelCentre labelCentreIterator;
        for(int j = 0;j<nombreStand;j++){
            labelCentreIterator = tas.deleteMin();              //On va aller retirer le Label avec l ecartement le plus petit ecartement
            System.out.println("AlgoCentre : label mis dans la liste : "+labelCentreIterator.getSommetCourant().getId());
            listRetour.add(labelCentreIterator);               // On va inserer dans la liste les label ayant l'écartement le plus petit
        }


        System.out.println("AlgoCentre : Fin");

        return listRetour;

    }
    
    
}
