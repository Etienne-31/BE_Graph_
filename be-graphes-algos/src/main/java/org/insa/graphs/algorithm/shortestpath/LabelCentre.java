package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class LabelCentre  implements Comparable<LabelCentre> {

    private Double ecartement;
    private Node sommetCourant;
   

    public LabelCentre(Node sommet){
        this.sommetCourant = sommet;
        this.ecartement = null;
    }

    public Double getEcartement(){return this.ecartement;}
    public Node getSommetCourant(){return this.sommetCourant;}
    public void setEcartement(Double newEcartement){this.ecartement = newEcartement;}
    
    public int compareTo(LabelCentre label){  
        int retour = 1;
        if(this.getEcartement() < label.getEcartement()){
            retour = -1;
        }
        else if(this.getEcartement() == label.getEcartement()){
            retour = 0;
        }
        
        return retour;
    }
    
}
