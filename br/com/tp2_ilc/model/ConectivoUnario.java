package br.com.tp2_ilc.model;

public class ConectivoUnario extends Conectivo{
	private ValorVerdade[] matrizResultados;
	
	public ConectivoUnario(char caractere, ValorVerdade[] conectivoNEGACAO) {
		super(caractere);
		this.matrizResultados = conectivoNEGACAO;
	}
	
	public ValorVerdade retornaValorVerdade(ValorVerdade valor) {
		int posicao;
		if(valor.equals(new ValorVerdade('V',true)))
			posicao = 1;
		else
			posicao = 0;
		return matrizResultados[posicao];	
	}

	@Override
	public int getNumeroArgumentos() {
		return 1;
	}

}
