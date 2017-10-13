package br.com.tp2_ilc.model;

public abstract class Elemento extends Simbolo{
	public Elemento(char caractere) {
		super(caractere);
	}

	public abstract int getNumeroArgumentos();
}
