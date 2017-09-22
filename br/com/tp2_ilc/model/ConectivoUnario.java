package br.com.tp2_ilc.model;

public class ConectivoUnario extends Conectivo{

	private ValorVerdade[] matrizResultados;
	
	//Válido para a versão em que não havia uma matriz com resultados.
	
	/*public ConectivoUnario(char caractere) {
		super(caractere);
		// TODO Auto-generated constructor stub
	}*/
	public ConectivoUnario(char caractere, ValorVerdade[] conectivoNEGACAO) {
		super(caractere);
		this.matrizResultados = conectivoNEGACAO;
	}
	
	public ValorVerdade retornaValorVerdade(ValorVerdade valor) {
		int posicao;
		if(valor == ValorVerdade.TRUE)
			posicao = 1;
		else
			posicao = 0;
		return matrizResultados[posicao];	
	}

	@Override
	int getNumeroArgumentos() {
		return 1;
	}

}
