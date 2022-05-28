package org.insa.graphs.algorithm.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import  org.insa.graphs.algorithm.shortestpath.algoCentre;
import java.io.DataInputStream;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.model.Graph;
import org.insa.graphs.algorithm.shortestpath.LabelCentre;

public class testAlgocentre {
    final static String mapCarre = "D:\\Cours ETS\\Insa_3eme_2ndselestre\\BE_Graph\\Maps\\carre.mapgr";
    private static Graph graphCarre;


    @BeforeClass
    public static void initAll() throws IOException {

        final GraphReader readerCarre = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre))));
        graphCarre = readerCarre.read();
        
    }

    @Test
    public void testCentreGraphe(){
        List<LabelCentre> list = algoCentre.algoCentreStatique(1, graphCarre);
        System.out.println("taille liste : "+list.size());
        assertEquals(12, list.get(0).getSommetCourant().getId());
    }
    
}
