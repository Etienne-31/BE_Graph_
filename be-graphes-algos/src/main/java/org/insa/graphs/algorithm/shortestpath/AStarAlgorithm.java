package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.w3c.dom.traversal.NodeIterator;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import java.util.List;
import org.insa.graphs.algorithm.AbstractInputData.Mode;

import org.insa.graphs.algorithm.shortestpath.Label;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
       // System.out.println("Demarrage de A*");
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Node nodeIterator;
        LabelStar labelIterator;
        LabelStar oldLabel;
        double coutArc;
        double coutActuel;
        Mode mode = data.getMode();
        Node destinationNode = data.getDestination();
        Node originNode = data.getOrigin();
        List<Arc> listSuccesor;
        BinaryHeap<LabelStar> tas = new BinaryHeap<LabelStar>(); 
        LabelStar labelTab[] = new LabelStar[data.getGraph().size()];

        int graphMaxSpeed = data.getGraph().getGraphInformation().getMaximumSpeed(); // en km/h et arc cost est en mettre ou seconde  

        if(mode == Mode.LENGTH){
            // Partie initialisation création du tableau de label et des différrentes variables utiles durant l'algo
            
            
            for(int i = 0;i<data.getGraph().size();i++){
                nodeIterator = data.getGraph().get(i);  // On recupere le Node i 
                labelTab[i] = new LabelStar(nodeIterator,destinationNode,mode,graphMaxSpeed); // On rempli dans le tableau en initialisant avec le constructeur de label 
            }
            //Iteration Pour remplir le tableau ainsi que pour l'initialisation de tous les label 
            LabelStar originLabel = new LabelStar(originNode,destinationNode,mode,graphMaxSpeed);
            originLabel.setCost(0.0);  // On initialise le coût de l'origine à 0;
            originLabel.setMarque();
            labelTab[data.getOrigin().getId()] = originLabel; // On initialise la première case du tableau
           // System.out.println("Insertion de l'origine | Pere : "+originLabel.getFather()+" Cout : "+originLabel.getCost()+" Est marque : "+originLabel.getMarqueStatus());
        
            
            tas.insert(originLabel); // On met l'origine dans le tas après avoir fini d'initialiser le tableau 

            // Maintenant que le tableau est rempli l'algorithme se réalise dans cette partie | Je suis l algo du cours p46
            while(tas.isEmpty() == false){   // Tant qu'on aura pas vider le tas la boule while va se réaliser afin de mettre à jour le tableau de Label 

                labelIterator = tas.deleteMin();
                if(labelIterator.getSommetCourant() == destinationNode){
                    break;
                }
               // System.out.println(" Le noeud "+labelIterator.getSommetCourant().getId()+" a été sorti du tas son coût est de ( en distance ) : "+labelIterator.getCost() );
                listSuccesor = labelIterator.getSommetCourant().getSuccessors();
                

               // System.out.println("Noeud_id : "+labelIterator.getSommetCourant().getId()+" | Est marque : "+labelIterator.getMarqueStatus());

                for(Arc arcIterator : listSuccesor){

                   // System.out.println("Origine : "+arcIterator.getOrigin().getId()+" | Destination : "+arcIterator.getDestination().getId()+" | Destination est marque : "+labelTab[arcIterator.getDestination().getId()].getMarqueStatus());

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
                                notifyNodeReached(labelIterator.getSommetCourant());
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

        }
        else{
             // Partie initialisation création du tableau de label et des différrentes variables utiles durant l'algo
            
            
             for(int i = 0;i<data.getGraph().size();i++){
                nodeIterator = data.getGraph().get(i);  // On recupere le Node i 
                labelTab[i] = new LabelStar(nodeIterator,destinationNode,mode,graphMaxSpeed); // On rempli dans le tableau en initialisant avec le constructeur de label 
            }
            //Iteration Pour remplir le tableau ainsi que pour l'initialisation de tous les label 
            LabelStar originLabel = new LabelStar(originNode,destinationNode,mode,graphMaxSpeed);
            originLabel.setCost(0.0);  // On initialise le coût de l'origine à 0;
            originLabel.setMarque();
            labelTab[data.getOrigin().getId()] = originLabel; // On initialise la première case du tableau
           // System.out.println("Insertion de l'origine | Pere : "+originLabel.getFather()+" Cout : "+originLabel.getCost()+" Est marque : "+originLabel.getMarqueStatus());
            
            
            tas.insert(originLabel); // On met l'origine dans le tas après avoir fini d'initialiser le tableau 

            // Maintenant que le tableau est rempli l'algorithme se réalise dans cette partie | Je suis l algo du cours p46
            while(tas.isEmpty() == false){   // Tant qu'on aura pas vider le tas la boule while va se réaliser afin de mettre à jour le tableau de Label 

                labelIterator = tas.deleteMin();
                if(labelIterator.getSommetCourant() == destinationNode){
                    break;
                }
               // System.out.println(" Le noeud "+labelIterator.getSommetCourant().getId()+" a été sorti du tas son coût est de ( en temps ) : "+labelIterator.getCost() );
                listSuccesor = labelIterator.getSommetCourant().getSuccessors();
                

               // System.out.println("Noeud_id : "+labelIterator.getSommetCourant().getId()+" | Est marque : "+labelIterator.getMarqueStatus());

                for(Arc arcIterator : listSuccesor){

                   // System.out.println("Origine : "+arcIterator.getOrigin().getId()+" | Destination : "+arcIterator.getDestination().getId()+" | Destination est marque : "+labelTab[arcIterator.getDestination().getId()].getMarqueStatus());

                    if(labelTab[arcIterator.getDestination().getId()].getMarqueStatus() == false){

                    // System.out.println("La destination : "+arcIterator.getDestination().getId()+" n'est pas marque");

                        labelTab[arcIterator.getDestination().getId()].setMarque();

                        //System.out.println("La destination : "+arcIterator.getDestination().getId()+" a été marqué");

                        oldLabel = labelTab[arcIterator.getDestination().getId()]; // Copie de l'ancienne version du label afin d update ensuite si besoins 

                        coutActuel = labelTab[arcIterator.getDestination().getId()].getCost();
                        coutArc = labelIterator.getCost() + arcIterator.getMinimumTravelTime();
                        
                    // System.out.println(" Le cout actuel pour allé à la destination est de : "+coutActuel+" Le nouveau cout pour acceder à cette destination est de "+coutArc);

                        if(coutActuel > coutArc){
                            labelTab[arcIterator.getDestination().getId()].setCost(coutArc); // Je met à jour dans le tableau 
                                                            
                            if(coutActuel == Double.POSITIVE_INFINITY){
                            // System.out.println("Le sommet n'était pas dans le tas ");
                                labelTab[arcIterator.getDestination().getId()].setFather(arcIterator);   // Je definis le père ( arc ) du label qui va être inseré
                                tas.insert(labelTab[arcIterator.getDestination().getId()]);              // J'insère le nouveau Label
                                notifyNodeReached(labelIterator.getSommetCourant());
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
            

        }
        
       // System.out.println("Le tas a bien été vidé en fonction de "+mode);
        if (labelTab[data.getDestination().getId()] == null) {
            //System.out.println("On ne peut pas faire le chemin defini");
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
            //System.out.println("On  peut faire le chemin defini");
            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labelTab[data.getDestination().getId()].getFather();
            while (arc != null) {
               // System.out.println("Le noeud : "+arc.getDestination().getId()+" a pour père : "+arc.getOrigin().getId());
                arcs.add(arc);
                arc = labelTab[arc.getOrigin().getId()].getFather();
            }
           // System.out.println(" On a passé la boucle ");
            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));
        }      
        
        
        return solution;
    }

}
