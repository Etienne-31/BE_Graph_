package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {
    private Node sommetCourant;
    private boolean marque;
    private Double cost;
    private Arc father;

    public Label(Node Sommet){
        this.sommetCourant = Sommet;
        this.marque = false;
        this.cost = Double.POSITIVE_INFINITY;
        this.father = null;   
    }

    //Getteur pour Label
    public Double getCost(){ return this.cost;}
    public Double getTotalCost(){ return this.cost;}
    public Node getSommetCourant(){return this.sommetCourant;}
    public boolean getMarqueStatus(){return this.marque;}
    public Arc getFather(){return this.father;}

    //Setteur pour Label
    public void setCost(Double newCost){this.cost = newCost;}
    public void setMarque(){
        if(this.marque == false){
            this.marque = true;
        }
        else{
            this.marque = false;
        }
    }
    public void setFather(Arc newFather){this.father = newFather;}

    public int compareTo(Label label){  
        int retour = 1;
        if(this.getTotalCost() < label.getTotalCost()){
            retour = -1;
        }
        else if(this.getTotalCost() == label.getTotalCost()){
            retour = 0;
        }
        
        return retour;
    }
    
    
}
