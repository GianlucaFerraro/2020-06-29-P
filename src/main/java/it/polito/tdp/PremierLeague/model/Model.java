package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	Graph <Match, DefaultWeightedEdge> grafo ;
	PremierLeagueDAO dao ;
	Map <Integer, Match> idMap = new HashMap <> ();
	
	
	public Model () {
		grafo = new SimpleWeightedGraph <> (DefaultWeightedEdge.class);
		dao = new PremierLeagueDAO ();
		this.dao.listAllMatches(idMap);
	}
	
	
	public void creaGrafo (Month mese, int minuti) {
		List <Match> temp = dao.getVertici(idMap, mese);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, mese));
		
		for(Adiacenza a: dao.getAdiacenze (idMap , mese, minuti)) {
			Graphs.addEdge(this.grafo, a.getM1(), a.getM2(), minuti);
			
		}
	}
	
	
	
	
	public int nVertici () {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi () {
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> connessioniMax (Month mese, int minuti) {
		int max = 0;
		List <Adiacenza> connessioniMax = new LinkedList <> ();
		for(Adiacenza a : dao.getAdiacenze(idMap, mese, minuti)) {
			if(a.getPeso()>max)
				max= a.getPeso();
		}
		for(Adiacenza b: dao.getAdiacenze(idMap, mese, minuti)) {
			if(b.getPeso()==max)
				connessioniMax.add(b);
		}
		return connessioniMax;
	}
}
