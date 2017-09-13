package br.com.tp2_ilc.model;

public class Atribuicao {
	private Proposicao proposicao;
	private ValorVerdade valor;
	
	public Atribuicao(Proposicao proprosicao, ValorVerdade valor) {
		this.proposicao = proprosicao;
		this.valor = valor;
	}

	public Proposicao getProprosicao() {
		return proposicao;
	}

	public ValorVerdade getValor() {
		return valor;
	}
}
