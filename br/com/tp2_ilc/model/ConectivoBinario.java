package br.com.tp2_ilc.model;

public class ConectivoBinario extends Conectivo{
	
	private ValorVerdade[][] matrizResultados;
	
	public ConectivoBinario(char caractere, ValorVerdade[][] matriz) {
		super(caractere);
		this.matrizResultados = matriz;
	}
	
	//Válido para a versão em que não havia uma matriz com resultados.
	
	/*public ConectivoBinario(char caractere) {
		super(caractere);	
	}*/
	
	public ValorVerdade retornaValorVerdade(ValorVerdade valorEsquerda, ValorVerdade valorDireita) {
		int posicao1, posicao2;
		if(valorEsquerda == ValorVerdade.TRUE) 
			posicao1 = 1;
		else
			posicao1 = 0;
		
		if(valorDireita == ValorVerdade.TRUE) 
			posicao2 = 1;
		else
			posicao2 = 0;
		
		return matrizResultados[posicao1][posicao2];	
	}

	@Override
	int getNumeroArgumentos() {
		return 2;
	}
}
