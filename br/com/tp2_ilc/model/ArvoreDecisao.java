package br.com.tp2_ilc.model;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class ArvoreDecisao {
	private FBF raiz;
	List<Pair<ArvoreDecisao, Atribuicao>> filhos = new ArrayList<Pair<ArvoreDecisao, Atribuicao>>();
	
	public ArvoreDecisao(FBF fbf) {
		this.raiz = fbf;
		//para cada atribuicao (todas proposicoes V e F)
		for(Atomo proposicao : this.raiz.getProposicoes()) {
			if(proposicao.getCaractere() != 'V' && proposicao.getCaractere() != 'F') { //nao se pode criar proposicao com caracter V ou F
			//if(!(proposicao instanceof ValorVerdade)) {
				Atribuicao atribuicaoVerdadeira = new Atribuicao((Proposicao)proposicao, new ValorVerdade('V', true));
				FBF formulaNova1 = raiz.realizaAtribuicao(atribuicaoVerdadeira);
				filhos.add(new Pair<>(new ArvoreDecisao(formulaNova1), atribuicaoVerdadeira));
				
				Atribuicao atribuicaoFalsa = new Atribuicao((Proposicao)proposicao, new ValorVerdade('F', false));
				FBF formulaNova2 = raiz.realizaAtribuicao(atribuicaoFalsa);
				filhos.add(new Pair<>(new ArvoreDecisao(formulaNova2), atribuicaoFalsa));				
			}
		}
//			Atribuicao aTrue = new Atribuicao(new Proposicao('a', "cabo natal"), new ValorVerdade('V', true));
//			FBF formulaNova = raiz.realizaAtribuicao(aTrue);
//			//formulaNova.simplifica();
//			filhos.add(new Pair<>(new ArvoreDecisao(formulaNova), aTrue));
	}

	public FBF getRaiz() {
		return raiz;
	}

	public void setRaiz(FBF raiz) {
		this.raiz = raiz;
	}

	public List<Pair<ArvoreDecisao, Atribuicao>> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Pair<ArvoreDecisao, Atribuicao>> filhos) {
		this.filhos = filhos;
	}
}
