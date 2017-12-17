package br.com.tp2_ilc.test;

import br.com.tp2_ilc.model.ArvoreDecisao;
import br.com.tp2_ilc.model.ConectivoBinario;
import br.com.tp2_ilc.model.FBF;
import br.com.tp2_ilc.model.Proposicao;
import br.com.tp2_ilc.model.ValorVerdade;

public class ArvoreDecisaoTest {

	public static void main(String[] args) {
		final ValorVerdade FALSE = new ValorVerdade('F',false);
		final ValorVerdade TRUE = new ValorVerdade('V',true);
		ValorVerdade[][] conectivoOU = { { FALSE, TRUE },
				{ TRUE, TRUE } };
		ConectivoBinario ou = new ConectivoBinario('v', conectivoOU);
		FBF a = new FBF(new Proposicao('a', "Aaaa"));
		FBF b = new FBF(new Proposicao('b', "Bbbb"));
		FBF AouB = new FBF(ou, a, b);
		
		ArvoreDecisao arvoreDecisao = new ArvoreDecisao(AouB);
		System.out.println(arvoreDecisao.getRaiz().getExpressao());
		System.out.println(arvoreDecisao.getFilhos().get(0).getKey().getRaiz().getExpressao());
		System.out.println(arvoreDecisao.getFilhos().get(1).getKey().getRaiz().getExpressao());
	}
}
