package br.com.tp2_ilc.model;

public class Proposicao extends Atomo {
	private String sentenca; 
	
	public Proposicao(char caractere, String sentenca) {
		super(caractere);
		this.sentenca = sentenca;
		// TODO Auto-generated constructor stub
	}

	public String getSentenca() {
		return sentenca;
	}

	public void setSentenca(String sentenca) {
		this.sentenca = sentenca;
	}

	@Override
	public int getNumeroArgumentos() {
		return 0;
	}
	
}
