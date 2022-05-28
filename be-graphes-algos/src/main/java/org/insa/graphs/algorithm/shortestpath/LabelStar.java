package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

import javax.swing.ComboBoxEditor;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Point;

import org.insa.graphs.algorithm.AbstractInputData.Mode;

public class LabelStar implements Comparable<LabelStar> {

    Double coutEstime;
    private Node sommetCourant;
    private boolean marque;
    private Double cost;
    private Arc father;
    private Mode algoMode;
    private int vitessGraph;

    public LabelStar(Node Sommet,Node noeudArrive,Mode mode,int vitesseGraph){
        this.sommetCourant = Sommet;
        this.marque = false;
        this.cost = Double.POSITIVE_INFINITY;
        this.father = null;
        this.coutEstime = Point.distance(Sommet.getPoint(), noeudArrive.getPoint());  
        this.algoMode = mode; 
        this.vitessGraph = vitesseGraph;
    }

    public Double getCost(){ return this.cost;}
    public Double getGraphMaxSpeed(){
        Double d = new Double(this.vitessGraph);
        return d;
    }
    public Node getSommetCourant(){return this.sommetCourant;}
    public boolean getMarqueStatus(){return this.marque;}
    public Arc getFather(){return this.father;}

    public Double getTotalCost(){ 
        Double retour;
        Double vitesse = this.getGraphMaxSpeed();
        if(this.algoMode == Mode.LENGTH){
            retour = this.getCost()+this.coutEstime;
        }
        else{
            this.coutEstime = this.coutEstime / vitesse; // On le change en temps 
            retour = this.getCost()+this.coutEstime;
        }
        
        return retour;
    }

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

    public int compareTo(LabelStar label){  // Retourne -1 si objet plus petit 0 sinon  | A revoir 
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
