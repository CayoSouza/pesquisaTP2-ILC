package br.com.tp2_ilc.test;

import java.util.List;

import org.omg.Messaging.SyncScopeHelper;

import br.com.tp2_ilc.model.*;

public class Test {

	public static void main(String[] args) {
		//Matrizes dos valores verdade dos conectivos que serÃƒÂ£o utilizados
		
		final ValorVerdade FALSE = new ValorVerdade('F',false);
		final ValorVerdade TRUE = new ValorVerdade('V',true);
		
		ValorVerdade[][] conectivoE = {{FALSE, FALSE}, {FALSE,TRUE}};
		ValorVerdade[][] conectivoOU = {{FALSE, TRUE}, {TRUE, TRUE}};
		ValorVerdade[][] conectivoOUEXCLUSIVO = {{FALSE, TRUE}, {TRUE, FALSE}};
		ValorVerdade[][] conectivoIMPLICACAO = {{TRUE, TRUE}, {FALSE, TRUE}};
		ValorVerdade[][] conectivoDUPLAIMPLICACAO = {{TRUE,FALSE}, {FALSE,TRUE}};
		ValorVerdade[] conectivoNEGACAO = {TRUE,FALSE};
		
		/*
		 * Como os conectivos sÃƒÂ³ podem ter um caractere como char pensei em padronizar assim:
		 * OU - O
		 * OU EXCLUSIVO - X
		 * E - E
		 * IMPLICAÃƒâ€¡ÃƒÆ’O - I
		 * DUPLA IMPLICAÃƒâ€¡ÃƒÆ’O - D
		 * NEGAÃƒâ€¡ÃƒÆ’O - N
		 */
		
		ConectivoBinario e = new ConectivoBinario('^', conectivoE);
		
		/* TESTE DO CONECTIVO "E" */
		/*System.out.println("CONECTIVO E:");
		System.out.println(e.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.TRUE).getCaractere());
		System.out.println(e.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.FALSE).getCaractere());
		System.out.println(e.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.TRUE).getCaractere());
		System.out.println(e.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.FALSE).getCaractere());*/
		/* FIM TESTE DO CONECTIVO "E" */
		
		ConectivoBinario ou = new ConectivoBinario('v', conectivoOU);
		
		/* TESTE DO CONECTIVO "OU" */
		/*System.out.println("CONECTIVO OU:");
		System.out.println(ou.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.TRUE).getCaractere());
		System.out.println(ou.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.FALSE).getCaractere());
		System.out.println(ou.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.TRUE).getCaractere());
		System.out.println(ou.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.FALSE).getCaractere());*/
		/* FIM TESTE DO CONECTIVO "OU" */
		
		ConectivoBinario ouExclusivo = new ConectivoBinario('⊕', conectivoOUEXCLUSIVO);
		
		/* TESTE DO CONECTIVO "OU EXCLUSIVO" */
		/*System.out.println("CONECTIVO OU EXCLUSIVO:");
		System.out.println(ouExclusivo.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.TRUE).getCaractere());
		System.out.println(ouExclusivo.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.FALSE).getCaractere());
		System.out.println(ouExclusivo.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.TRUE).getCaractere());
		System.out.println(ouExclusivo.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.FALSE).getCaractere());*/
		/* FIM TESTE DO CONECTIVO "OU EXCLUSIVO" */
		
		ConectivoBinario implicacao = new ConectivoBinario('→', conectivoIMPLICACAO);
		
		/* TESTE DO CONECTIVO "IMPLICACAO" */
		/*System.out.println("CONECTIVO IMPLICACAO:");
		System.out.println(implicacao.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.TRUE).getCaractere());
		System.out.println(implicacao.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.FALSE).getCaractere());
		System.out.println(implicacao.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.TRUE).getCaractere());
		System.out.println(implicacao.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.FALSE).getCaractere());*/
		/* FIM TESTE DO CONECTIVO "IMPLICACAO" */

		ConectivoBinario duplaImplicacao = new ConectivoBinario('↔', conectivoDUPLAIMPLICACAO);
		
		/* TESTE DO CONECTIVO "DUPLA IMPLICACAO" */
		/*System.out.println("CONECTIVO DUPLA IMPLICACAO:");
		System.out.println(duplaImplicacao.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.TRUE).getCaractere());
		System.out.println(duplaImplicacao.retornaValorVerdade(ValorVerdade.TRUE, ValorVerdade.FALSE).getCaractere());
		System.out.println(duplaImplicacao.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.TRUE).getCaractere());
		System.out.println(duplaImplicacao.retornaValorVerdade(ValorVerdade.FALSE, ValorVerdade.FALSE).getCaractere());*/
		/* FIM TESTE DO CONECTIVO "DUPLA IMPLICACAO" */
		
		ConectivoUnario negacao = new ConectivoUnario('¬', conectivoNEGACAO);
		
		/*TESTE DO CONECTIVO "NEGAÃƒâ€¡ÃƒÆ’O" */
		/*System.out.println("CONECTIVO NEGAÃƒâ€¡ÃƒÆ’O");
		System.out.println(negacao.retornaValorVerdade(ValorVerdade.FALSE).getCaractere());
		System.out.println(negacao.retornaValorVerdade(ValorVerdade.TRUE).getCaractere());*/
		/* FIM TESTE DO CONECTIVO "NEGAÃƒâ€¡ÃƒÆ’O" */
		
		
		Proposicao b = new Proposicao('B', "bomdia");
		
		FBF fbfB = new FBF(b);
		
		Proposicao c = new Proposicao('C', "cafe");
		
		FBF fbfC = new FBF(c);
		
		FBF fbfBeC = new FBF(e, fbfB, fbfC);
		
		Proposicao a = new Proposicao('A', "amorzinho");
		
		FBF fbfA = new FBF(a);
		
		FBF fbfBouA = new FBF(ou, fbfB, fbfA);
		
		Proposicao d = new Proposicao('D', "dado");
		
		
		/*FBF fbfDxor_fbfB = new FBF(ouExclusivo,fbfD, fbfB);
		FBF fbfDou_fbfAe_fbfBeC_ = new FBF(ou, fbfD, fbfAe_fbfBeC);
		FBF fbfDou_fbfAe_fbfBeC_ouB = new FBF(ou, fbfDou_fbfAe_fbfBeC_, fbfB);
		//List<Atomo> lista = fbfAor_fbfBandC_.listaProposicoes();
		//List<Atomo> lista = fbfBandC.listaProposicoes();
		//List<Atomo> lista = fbfBandA.listaProposicoes();
		//List<Atomo> lista = fbfDou_fbfAou_fbfBeC_.listaProposicoes();
		List<Atomo> lista = fbfDou_fbfAe_fbfBeC_ouB.getProposicoes();
		for(Atomo atomo: lista) {
			System.out.println(atomo.getCaractere());
		}
		
		FBF fbfDimplicaA = new FBF(implicacao, fbfD, fbfA);
		
		fbfDxor_fbfB.geraTabelaVerdade();;
		*/
		
		//fbfDou_fbfAe_fbfBeC_.geraValorFBF();
		
		//fbfBeC.geraValorFBF();
		
		//fbfAe_fbfBeC.geraValorFBF();
		
		//fbfBouA.geraValorFBF();
		
		//fbfA.geraValorFBF();
		
		//fbfDimplicaA.geraValorFBF();
		
		/*FBF fbfBouAe_fbfC = new FBF(e, fbfBouA, fbfC);
		fbfBouAe_fbfC.geraTabelaVerdade();*/
		
		Proposicao p = new Proposicao('P', "Paulo");
		Proposicao r = new Proposicao('R', "Roberto");
		Proposicao s = new Proposicao('S', "Saulo");
		Proposicao q = new Proposicao('Q', "Quintella");
		Proposicao t = new Proposicao('T', "Thales");
		
		FBF fbfP = new FBF(p);
		FBF fbfR = new FBF(r);
		FBF fbfS = new FBF(s);
		FBF fbfQ = new FBF(q);
		FBF fbfT = new FBF(t);
		
		FBF fbfP_e_fbfR = new FBF(e, fbfP, fbfR);
		FBF fbfP_e_fbfR_e_fbfS = new FBF(e, fbfP_e_fbfR, fbfS);
		FBF fbfQ_e_fbfT = new FBF(e, fbfQ, fbfT);
		FBF fbfP_e_fbfR_e_fbfS_ou_fbfQ_e_T = new FBF(ou, fbfP_e_fbfR_e_fbfS, fbfQ_e_fbfT);
		FBF fbfnT = new FBF(negacao, fbfT);
		FBF fbfR_e_fbfnT = new FBF(e, fbfR,fbfnT);
		FBF fbfP_e_fbfR_e_fbfS_ou_fbfQ_e_T_ou_fbfR_e_fbfnT = new FBF(ou, fbfP_e_fbfR_e_fbfS_ou_fbfQ_e_T, fbfR_e_fbfnT);
		TabelaVerdade tv = fbfP_e_fbfR_e_fbfS_ou_fbfQ_e_T_ou_fbfR_e_fbfnT.geraTabelaVerdade();
		tv.exibeTabelaVerdade();
		//tv.exibeTabelaVerdade();
	}
}