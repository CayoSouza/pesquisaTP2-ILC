package br.com.tp2_ilc.model;

public class ValorVerdade extends Atomo{
	public static ValorVerdade TRUE = new ValorVerdade('V',true);
	public static ValorVerdade FALSE = new ValorVerdade('F',false);
	
	public boolean valor;

	public ValorVerdade(char caractere, boolean valor){
		super(caractere);
		this.valor = valor;
	}
	
	public boolean getValor() {
		return valor;
	}

	@Override
	public int getNumeroArgumentos() {
		return 0;
	}
}
