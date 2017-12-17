package br.com.tp2_ilc.model;

public class Atribuicao {
	private Proposicao proposicao;
	private ValorVerdade valorVerdade;
	
	public Atribuicao(Proposicao proposicao, ValorVerdade valor) {
		this.proposicao = proposicao;
		this.valorVerdade = valor;
	}

	public Proposicao getProposicao() {
		return proposicao;
	}

	public ValorVerdade getValorVerdade() {
		return valorVerdade;
	}
	
	public char getValorCaractere() {
		return this.valorVerdade.getCaractere();
	}
}
