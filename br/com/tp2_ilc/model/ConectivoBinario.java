package br.com.tp2_ilc.model;

public class ConectivoBinario extends Conectivo{
	
	private ValorVerdade[][] matrizResultados;
	
	public ConectivoBinario(char caractere, ValorVerdade[][] matriz) {
		super(caractere);
		this.matrizResultados = matriz;
	}
	
	public ValorVerdade retornaValorVerdade(ValorVerdade valorEsquerda, ValorVerdade valorDireita) {
		int posicao1, posicao2;
		if(valorEsquerda.equals(new ValorVerdade('V',true))) 
			posicao1 = 1;
		else
			posicao1 = 0;
		
		if(valorDireita.equals(new ValorVerdade('V',true))) 
			posicao2 = 1;
		else
			posicao2 = 0;
		
		return matrizResultados[posicao1][posicao2];	
	}

	@Override
	public int getNumeroArgumentos() {
		return 2;
	}
}
