package br.com.tp2_ilc.model;

import java.util.ArrayList;
import java.util.List;

import br.com.tp2_ilc.model.*;
import javafx.beans.value.ObservableValue;

public class Formula{
	private boolean bemFormada;
	private List<Simbolo> simbolos;
	private String simboloz;
	
	public Formula(List<Simbolo> simbolos){
		this.simbolos = simbolos;
		this.bemFormada = false;
	}
	public Formula(Atomo atomo){
		this.simbolos = new ArrayList<Simbolo>();
		this.simbolos.add(atomo);
		this.bemFormada = true;
	}
	public Formula(ConectivoUnario conec_unario, Formula formula){
		this.simbolos = new ArrayList<Simbolo>();
		this.simbolos.add(conec_unario);
		this.simbolos.add(new Parentese('('));
		this.simbolos.addAll(formula.getSimbolos());
		this.simbolos.add(new Parentese(')'));
		
		if(formula.isBemFormada())
			this.bemFormada = true;
		else
			this.bemFormada = false;
	}
	public Formula(ConectivoBinario conec_binario, Formula formula1, Formula formula2 ){
		this.simbolos = new ArrayList<Simbolo>();
		this.simbolos.add(new Parentese('('));
		this.simbolos.addAll(formula1.getSimbolos());
		this.simbolos.add(conec_binario);
		this.simbolos.addAll(formula2.getSimbolos());
		this.simbolos.add(new Parentese(')'));
		
		if(formula1.isBemFormada() && formula2.isBemFormada())
			this.bemFormada = true;
		else
			this.bemFormada = false;
	}
	public List<Simbolo> getSimbolos() {
		return simbolos;
	}
	public String pegaSimbolos() {
		String formula = "";
		for(Simbolo sim: simbolos) {
			formula += sim.getCaractere();
		}
		return formula;
	}
	public String getSimboloz() {
		String formula = "";
		for(Simbolo sim: simbolos) {
			formula += sim.getCaractere();
		}
		this.simboloz = formula;
		return this.simboloz;
	}
	public void setSimboloz(String simboloz) {
		this.simboloz = simboloz;
	}
	public void setSimbolos(List<Simbolo> simbolos) {
		this.simbolos = simbolos;
	}
	public boolean isBemFormada() {
		return bemFormada;
	}
	public void setBemFormada(boolean bemFormada) {
		this.bemFormada = bemFormada;
	}
	
}
