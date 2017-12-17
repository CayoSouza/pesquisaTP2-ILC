package br.com.tp2_ilc.model;

import java.util.List;

import javafx.util.Pair;

public class No<T> {
	private T formula;
	private Pair<Atribuicao, No<T>> filhos;

	public No(T formula, Pair<Atribuicao, No<T>> filhos) {
		super();
		this.formula = formula;
		this.filhos = filhos;
	}

	public T getFormula() {
		return formula;
	}

	public void setFormula(T formula) {
		this.formula = formula;
	}

	public Pair<Atribuicao, No<T>> getFilhos() {
		return filhos;
	}

	public void setFilhos(Pair<Atribuicao, No<T>> filhos) {
		this.filhos = filhos;
	}

}
