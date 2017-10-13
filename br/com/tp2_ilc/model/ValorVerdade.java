package br.com.tp2_ilc.model;

public class ValorVerdade extends Atomo{
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (valor ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValorVerdade other = (ValorVerdade) obj;
		if (valor != other.valor)
			return false;
		return true;
	}
}
