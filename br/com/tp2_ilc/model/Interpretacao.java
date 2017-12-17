package br.com.tp2_ilc.model;

import java.util.ArrayList;

public class Interpretacao {
	ArrayList<Atribuicao> atribuicoes = new ArrayList<Atribuicao>();

	public ArrayList<Atribuicao> getAtribuicoes() {
		return atribuicoes;
	}

	public void setAtribuicoes(ArrayList<Atribuicao> atribuicoes) {
		this.atribuicoes = atribuicoes;
	}
	
	public void adicionarAtribuicao(Atribuicao atribuicao) {
		atribuicoes.add(atribuicao);
	}
	
	public Atribuicao buscaAtribuicao(Proposicao proposicao){
		for(Atribuicao a : atribuicoes) {
			if(a.getProposicao() == proposicao)
				return a;	
		}
		return null;
	}
	public ValorVerdade buscaValorVerdade(Proposicao proposicao){
		for(Atribuicao a : atribuicoes) {
			if(a.getProposicao() == proposicao)
				return a.getValorVerdade();	
		}
		return null;
	}
}
