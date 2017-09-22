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
		TabelaVerdade tabelaVerdade = new TabelaVerdade(proposicoes, this);
		//passados por construtor
		//tabelaVerdade.setListaProposicoes(proposicoes);
		//tabelaVerdade.setFormula(this);
		tabelaVerdade.geraTabelaDeInterpretacoes();
		tabelaVerdade.preencheTabelaVerdade();
		
		String proposicoes = "";
		for(Atomo atomo: tabelaVerdade.getListaProposicoes()) {
			proposicoes += atomo.getCaractere() + " ";
		}
		
		/*
		 * Exibição da tabela verdade.
		 */
		System.out.println("Proposições: " +proposicoes);
		
		System.out.println("Formula: " + tabelaVerdade.getFormula().pegaSimbolos());
		
		System.out.println("Interpretações + valor verdade");
		int nInterpretacoes = tabelaVerdade.getInterpretacoes().size();
		for(int i = 0; i < nInterpretacoes; i++) {
			String linha = "";
			for (Atribuicao atribuicao: tabelaVerdade.getInterpretacoes().get(i).getAtribuicoes()) {
				linha += atribuicao.getValor().getCaractere() +  " ";
			}
			linha += " Valor verdade da linha: "+ tabelaVerdade.getValoresVerdade().get(i).getCaractere();
			System.out.println(linha);
		}
		return tabelaVerdade;
		//exibeTabelaVerdade(tabelaVerdade);
		/*Debug
		String formula = this.pegaSimbolos();
		int cont = 1;
		for(Interpretacao interpretacao: list) {
			ValorVerdade valorVerdade = valoraFormula(this, interpretacao);
			System.out.println("Interpretacao "+cont + " da formula " +formula);
			System.out.println("Valor: "+ valorVerdade.getCaractere() +"\n");
			cont++;
		}*/
	}
	public void exibeArgumentos() {
		System.out.println(argumentos.get(0).pegaSimbolos());
		
	}

}
