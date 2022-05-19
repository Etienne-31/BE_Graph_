package org.insa.graphs.gui.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;
 
public class LaunchJunit {

	private static Graph emptyGraph, one_node_graph,graph;
	private static Path non_connex_path,emptyPath,singleNodePath,path;
    private static Node[] nodes,emptyNodesList,oneNodeList;

    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

    @BeforeClass
    public static void initAll() throws IOException {

        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                        speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        nodes = new Node[5];
        emptyNodesList = new Node[0];
        oneNodeList = new Node[1];

        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }
        oneNodeList[0] = new Node(0,null);


    }
    
    
}
