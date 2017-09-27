package br.com.tp2_ilc.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TabelaVerdade {

	ArrayList<Interpretacao> interpretacoes = new ArrayList<Interpretacao>();
	ArrayList<ValorVerdade> valoresVerdade = new ArrayList<ValorVerdade>();
	ArrayList<Atomo> listaProposicoes = new ArrayList<Atomo>();
	FBF formula;
	
	public TabelaVerdade(FBF formula) {
		this.formula = formula;
		this.listaProposicoes = formula.getProposicoes();
		geraTabelaDeInterpretacoes();
		preencheTabelaVerdade();
	}
	
	public ArrayList<Interpretacao> getInterpretacoes() {
		return interpretacoes;
	}

	public ArrayList<ValorVerdade> getValoresVerdade() {
		return valoresVerdade;
	}

	public FBF getFormula() {
		return formula;
	}

	public ArrayList<Atomo> getListaProposicoes() {
		return listaProposicoes;
	}
	
	/*
	 * Função que para cada interpretação adiciona na lista de valores verdade o valor correspondente a interpretação daquela formula
	 */
	public void preencheTabelaVerdade() {
		for(Interpretacao interpretacao: interpretacoes) 
			valoresVerdade.add(calculaValorFormula(formula, interpretacao));
	}
	
	/*
	 * Função que gerae retorna o valor verdade de uma FBF a partir de uma interpretação.
	 * Ambas recebidas por parâmetro.
	 */
	private ValorVerdade calculaValorFormula(FBF fbf, Interpretacao interpretacao) {
		ValorVerdade valor = null;
		if(fbf.getRaiz().getNumeroArgumentos() == 1) {
			ConectivoUnario conec = (ConectivoUnario) fbf.getRaiz();
			valor = conec.retornaValorVerdade(calculaValorFormula(fbf.getArgumentos().get(0), interpretacao));
		}
		else if(fbf.getRaiz().getNumeroArgumentos() == 2) {
			ConectivoBinario conec = (ConectivoBinario) fbf.getRaiz();
			ValorVerdade valorVerdade1 = calculaValorFormula(fbf.getArgumentos().get(0), interpretacao);
			ValorVerdade valorVerdade2 = calculaValorFormula(fbf.getArgumentos().get(1), interpretacao);
			valor = conec.retornaValorVerdade(valorVerdade1, valorVerdade2);
		}
		else {
			valor = interpretacao.buscaValorVerdade((Proposicao)fbf.getRaiz());
		}
		
		return valor;	
	}
	
	/* Funcao com o objetivo de transformar o numero em sua forma binaria com uma quantidade "n" de casas decimais */
	private static String converteDecimalParaBinario(int valor, int qtd) {
		StringBuilder pattern = new StringBuilder();
		for(int i=0; i<qtd; i++) {
			pattern.append("0");
		}
		DecimalFormat df = new DecimalFormat(pattern.toString());
		return df.format(Integer.parseInt(Integer.toBinaryString(valor)));
	} 
	
	/*Funcao que esta atualmente gerando todas as interpretacoes possiveis para os atomos separados, ainda nao fazendo uso dos conectivos.*/
	public void geraTabelaDeInterpretacoes() {
		String interpretacaoBinaria;
		char[] arrayInterpretacao;
		int numProposicoes = this.listaProposicoes.size();
		Interpretacao interpretacao = null;
		ValorVerdade valorVerdade;
		//Adiciona cada interpretacao a uma lista de interpretacoes 
		for(int i = 0; i < Math.pow(2,numProposicoes);i++) {
			interpretacaoBinaria = converteDecimalParaBinario(i,this.listaProposicoes.size());
			arrayInterpretacao = interpretacaoBinaria.toCharArray();
			interpretacao = new Interpretacao();
			for(int j = 0; j < numProposicoes; j++) {
				if(arrayInterpretacao[j] == '0')
					valorVerdade = ValorVerdade.FALSE;
				else
					valorVerdade = ValorVerdade.TRUE;
				Atribuicao atribuicao = new Atribuicao((Proposicao) this.listaProposicoes.get(j),valorVerdade);
				interpretacao.adicionarAtribuicao(atribuicao);
			}
			interpretacoes.add(interpretacao);
		}
	}

	/**
	 * Método debug
	 */
	public void exibeTabelaVerdade() {
		String proposicoes = "";
		for(Atomo atomo: this.getListaProposicoes()) 
			proposicoes += atomo.getCaractere() + " ";
		
		System.out.println("Proposições: " +proposicoes);
		System.out.println("Formula: " + this.getFormula().getExpressao());
		System.out.println("Interpretações + valor verdade");
		int nInterpretacoes = this.getInterpretacoes().size();
		for(int i = 0; i < nInterpretacoes; i++) {
			String linha = "";
			for (Atribuicao atribuicao: this.getInterpretacoes().get(i).getAtribuicoes()) {
				linha += atribuicao.getValor().getCaractere() +  " ";
			}
			linha += " Valor verdade da linha: "+ this.getValoresVerdade().get(i).getCaractere();
			System.out.println(linha);
		}
	}
}
