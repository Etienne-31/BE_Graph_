package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
 





public class LaunchJunit {

	private static Graph graph_few_nodes,graph,graphBenin,graphCarre;
    private static Node[] nodes;
    static ArcInspector Arcinspecteur;
    final static String mapName = "D:\\Cours ETS\\Insa_3eme_2ndselestre\\BE_Graph\\Maps\\insa.mapgr";
    final String pathName = "D:\\Cours ETS\\Insa_3eme_2ndselestre\\BE_Graph\\Path\\path_fr31insa_rangueil_r2.path";
    final static String mapBenin = "D:\\Cours ETS\\Insa_3eme_2ndselestre\\BE_Graph\\Maps\\benin.mapgr";
    final static String mapCarre = "D:\\Cours ETS\\Insa_3eme_2ndselestre\\BE_Graph\\Maps\\carre.mapgr";

    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

    @BeforeClass
    public static void initAll() throws IOException {

        

        nodes = new Node[5];

         // 10 and 20 meters per seconds
         RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
         speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

         // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
             nodes[i] = new Node(i, null);
        }
        

         // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);

        graph_few_nodes = new Graph("ID", "", Arrays.asList(nodes), null);



        final GraphReader reader = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));     // On va lire la carte voulu donner grâce au path étant donné que les cartes sont stockées localement
        graph = reader.read();                          // graph récupère les données de la carte en les lisant dans le reader

        final GraphReader readerBenin = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapBenin))));
        graphBenin = readerBenin.read();

        final GraphReader readerCarre = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre))));
        graphCarre = readerCarre.read();

        Arcinspecteur = new ArcInspectorFactory().getAllFilters().get(0);
    }

    


    @Test
    public void test_graph_vide() {
        //Le path est nul si l'origin et l'arrivé sont les mêmes
        int depart =3;
        int arrive = 3;

        
		ShortestPathData data = new ShortestPathData(graph, nodes[depart] ,nodes[arrive], Arcinspecteur);
		DijkstraAlgorithm solution_null= new DijkstraAlgorithm(data); 
		ShortestPathSolution path_null = solution_null.run(); 
		 
        
        assertEquals(0, path_null.getPath().getArcs().size());

    }

    @Test
    public void test_djikstra_belman_same_way() throws IOException{

        ShortestPathData data = new ShortestPathData(graph, graph.get(479), graph.get(67), Arcinspecteur); // On crée notre objet data avec les nodes de départ et d'arrivé ce chemin est faisable , vérifié avec belman ford
       
        BellmanFordAlgorithm belman = new BellmanFordAlgorithm(data);
        DijkstraAlgorithm djikstra = new DijkstraAlgorithm(data);
       
        ShortestPathSolution solution_djikstra;   // Cette solution nous permettra d'effectuer nos test
        ShortestPathSolution solution_belman;     // Cette solution est là pour verifier que l'on a bien 
        

        solution_djikstra = djikstra.run();
        solution_belman = belman.run();

        assertEquals(true, solution_djikstra.isFeasible());  // Ce test vérifie que le chemin est bien faisable
        assertArrayEquals(solution_belman.getPath().getArcs().toArray(),  solution_djikstra.getPath().getArcs().toArray()); // Verifie que la liste d'arc est bien la même
        assertEquals(solution_belman.getPath().getMinimumTravelTime(), solution_djikstra.getPath().getMinimumTravelTime(),0.1);

    }
    @Test
    public void test_djikstra_not_feasible() throws IOException {
        ShortestPathSolution solution_djikstra_pas_faisable;

        ShortestPathData data_pas_faisable = new ShortestPathData(graph_few_nodes,nodes[0], nodes[5], Arcinspecteur); // Ce chemin ci n'est aaps faisable
        DijkstraAlgorithm djikstra_pf = new DijkstraAlgorithm(data_pas_faisable);
        solution_djikstra_pas_faisable = djikstra_pf.run();

        assertEquals(false, solution_djikstra_pas_faisable.getPath().isValid());
        
    }
    /// test djikstra longue distance (map benin ) avec test de distance et de minimumtravel time sur les path
    @Test
    public void test_djikstra_longue_distance(){
        ShortestPathSolution solution;
        ShortestPathData data = new ShortestPathData(graphBenin, graphBenin.get(2786), graphBenin.get(67475), Arcinspecteur);
        DijkstraAlgorithm algoDjikstra = new DijkstraAlgorithm(data);
        solution = algoDjikstra.run();

        assertEquals(true, solution.getPath().isValid());
        assertEquals(graphBenin.get(67475),solution.getPath().getDestination());
        
    }

    @Test
    public void test_Djikstra_belman_all_nodes(){
        ShortestPathSolution solutionDjikstra,solutionBelman;
        DijkstraAlgorithm algoDjikstra;
        BellmanFordAlgorithm algoBelman;
        ShortestPathData data;
        List<Node> listeNode = graphCarre.getNodes();
        System.out.println("Il y a "+listeNode.size());
        
        for(Node depart : listeNode){
            for(Node arrive : listeNode){
                System.out.println("Depart ID :"+depart.getId());
                System.out.println("Arrive ID :"+arrive.getId());
                if(depart.getId() != arrive.getId()){
                    data = new ShortestPathData(graphCarre, depart, arrive, Arcinspecteur);
                    algoDjikstra = new DijkstraAlgorithm(data);
                    algoBelman = new BellmanFordAlgorithm(data);
                    solutionDjikstra = algoDjikstra.run();
                    solutionBelman = algoBelman.run();
                   

                    assertArrayEquals(solutionBelman.getPath().getArcs().toArray(), solutionDjikstra.getPath().getArcs().toArray());
                    assertEquals(solutionBelman.getPath().getDestination(), solutionDjikstra.getPath().getDestination());
                  //  assertEquals(solutionBelman.getPath(), solutionDjikstra.getPath());
                    
                }
            }
        }
    }

    
    
    
}
