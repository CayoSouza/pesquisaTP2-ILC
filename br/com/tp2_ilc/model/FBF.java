package br.com.tp2_ilc.model;

import java.util.ArrayList;
import java.util.List;

public class FBF extends Formula {
	private Elemento raiz;
	private ArrayList<FBF> argumentos = new ArrayList<FBF>();
	private ArrayList<Atomo> proposicoes = new ArrayList<Atomo>();

	public FBF(ValorVerdade valorVerdade) {
		super(valorVerdade);
		this.raiz = valorVerdade;
	}

	public FBF(Proposicao proposicao) {
		super(proposicao);
		this.raiz = proposicao;
		adicionarProposicaoNaLista(proposicao);
	}

	public FBF(ConectivoUnario conectivo, FBF fbf) {
		super(conectivo, fbf);
		this.raiz = conectivo;
		this.argumentos.add(fbf);

		for (Atomo proposicao : fbf.proposicoes)
			adicionarProposicaoNaLista(proposicao);
	}

	public FBF(ConectivoBinario conectivo, FBF fbf1, FBF fbf2) {
		super(conectivo, fbf1, fbf2);
		this.raiz = conectivo;
		this.argumentos.add(fbf1);
		this.argumentos.add(fbf2);

		for (Atomo proposicao : fbf1.proposicoes)
			adicionarProposicaoNaLista(proposicao);

		for (Atomo proposicao : fbf2.proposicoes)
			adicionarProposicaoNaLista(proposicao);
	}

	public void adicionarProposicaoNaLista(Atomo atomo) {
		if (!this.proposicoes.contains(atomo))
			this.proposicoes.add(atomo);
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

	public TabelaVerdade geraTabelaVerdade() {
		return new TabelaVerdade(this);
	}

	public void exibeArgumentos() {
		System.out.println(argumentos.get(0).getExpressao());

	}

	public FBF realizaAtribuicao(Atribuicao atribuicao) {
		// atribuicao = proposicao + valor verdade

		for (FBF argumento : this.getArgumentos())
			for(int i=0; i<argumento.getProposicoes().size(); i++)	{
//				if (this.getRaiz().getCaractere() == atribuicao.getProposicao().getCaractere())
//					this.raiz.setCaractere(atribuicao.getProposicao().getCaractere()); 
 				if (atribuicao.getProposicao().getCaractere() == argumento.getProposicoes().get(i).getCaractere()) {
 					argumento.getProposicoes().set(i, atribuicao.getValorVerdade()); 
					this.getProposicoes().set(i, atribuicao.getValorVerdade());
					argumento.raiz = atribuicao.getValorVerdade();
					argumento.getSimbolos().set(i, new Simbolo(atribuicao.getValorCaractere()));
				}
			}
		
		for(Atomo p : this.getProposicoes())
			System.out.println(p.getCaractere());
		
		for(FBF fbf : this.getArgumentos())
			System.out.println(fbf.getExpressao());
		// recursao para atribuir os valores verdade às proposicoes, de acordo com a
		// atribuicao
		// fornecida. Utilizar os argumentos da fbf.

		return this;
	}
}
