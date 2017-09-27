package br.com.tp2_ilc.model;

import java.util.ArrayList;
import java.util.List;

public class FBF extends Formula {

	private ArrayList<Atomo> proposicoes = new ArrayList<Atomo>();
	private ArrayList<FBF> argumentos = new ArrayList<FBF>();
	private Elemento raiz;

//	public FBF(Atomo atomo) {
//		super(atomo);
//		this.raiz = atomo;
//	}
	
	public FBF(ValorVerdade valorVerdade){
		super(valorVerdade);
		this.raiz = valorVerdade;
		//adicionarProposicaoNaLista(valorVerdade);
	}
	

	public FBF(Proposicao proposicao){
		super(proposicao);
		this.raiz = proposicao;
		adicionarProposicaoNaLista(proposicao);
	}

	public FBF(ConectivoUnario conectivo, FBF fbf){
		super(conectivo, fbf);
		this.raiz = conectivo;
		this.argumentos.add(fbf);
		
		for(Atomo proposicao : fbf.proposicoes)
			adicionarProposicaoNaLista(proposicao);
		
	}

	public FBF(ConectivoBinario conectivo, FBF fbf1, FBF fbf2){
		super(conectivo, fbf1, fbf2);
		this.raiz = conectivo;
		this.argumentos.add(fbf1);
		this.argumentos.add(fbf2);
		
		for(Atomo proposicao : fbf1.proposicoes)
			adicionarProposicaoNaLista(proposicao);
		
		for(Atomo proposicao : fbf2.proposicoes)
			adicionarProposicaoNaLista(proposicao);
		
	}

	public ArrayList<Atomo> getProposicoes() {
		return proposicoes;
	}
	
	public Elemento getRaiz() {
		return raiz;
	}
	public void setRaiz(Elemento raiz) {
		this.raiz = raiz;
	}

	public ArrayList<FBF> getArgumentos() {
		return argumentos;
	}

	public void setArgumentos(ArrayList<FBF> argumentos) {
		this.argumentos = argumentos;
	}
	
	/**
	 * Adiciona na lista somente se o átomo já não existir na mesma.
	 * @param atomo
	 */
	public void adicionarProposicaoNaLista(Atomo atomo){
		if(!this.proposicoes.contains(atomo))
			this.proposicoes.add(atomo);
	}

// Método descontinuado, substituído por recursão nos construtores
//	/**
//	 * Popula a lista de proposicoes recursivamente.
//	 * @return Lista de atomos da FBF.
//	 */
//	public List<Atomo> listarProposicoes(){
//		if(this.raiz instanceof Proposicao){
//			adicionarProposicaoNaLista((Atomo)this.raiz);
//			return proposicoes;
//		}
//		else 
//			for(FBF fbf : this.argumentos){
//				fbf.listarProposicoes();
//				for(Atomo p : fbf.proposicoes)
//					adicionarProposicaoNaLista(p);
//			}
//		return proposicoes;
//	}

	public TabelaVerdade geraTabelaVerdade() {
		return new TabelaVerdade(this);
	}
	
	public void exibeArgumentos() {
		System.out.println(argumentos.get(0).getExpressao());
		
	}

}
