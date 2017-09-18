package br.com.tp2_ilc.application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.util.Pair;

import br.com.tp2_ilc.model.*;


public class Controller implements Initializable{
	
	@FXML
	private TableView<Proposicao> tabelaProposicao;
	@FXML
	private TableColumn<Proposicao, Character> colunaCaracter;
	@FXML
	private TableColumn<Proposicao, String> colunaSentenca;
	
	public ObservableList<Proposicao> lista = FXCollections.observableArrayList(
			new Proposicao('a', "a água saudável"),
			new Proposicao('b', "a bola é redonda"),
			new Proposicao('c', "a casa é azul")
			);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaCaracter.setCellValueFactory(new PropertyValueFactory<Proposicao, Character>("caractere"));
		colunaSentenca.setCellValueFactory(new PropertyValueFactory<Proposicao, String>("sentenca"));
		tabelaProposicao.setItems(lista);
		
	}
	
	@FXML
	public void inserirProposicao(ActionEvent event) {
		mostrarDialogoProposicao();
	}
	
	@FXML
	public void deletarProposicao(ActionEvent event) {
		Proposicao selecionada = tabelaProposicao.getSelectionModel().getSelectedItem();
		tabelaProposicao.getItems().remove(selecionada);
	}
	
	public void mostrarDialogoProposicao() {
		Dialog<Pair<Character, String>> dialog = new Dialog<>();
		dialog.setTitle("Adicionar proposicao");
		dialog.setHeaderText("Insira o caracter e a sentença da proprosição desejada.");
		ButtonType addButton = new ButtonType("Adicionar", ButtonData.OK_DONE); 
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		TextField fieldCaractere = new TextField();
		fieldCaractere.setPromptText("Caractere(apenas a primeira letra sera considerada)");
		
		//limitar caractere para um
		Pattern pattern = Pattern.compile("\\w");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
		    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});
		fieldCaractere.setTextFormatter(formatter);
		
		TextField fieldSentenca = new TextField();
		fieldSentenca.setPromptText("Sentenca");
		
		grid.add(new Label("Caractere"), 0, 0);
		grid.add(fieldCaractere, 1, 0);
		grid.add(new Label("Sentenca"), 0, 1);
		grid.add(fieldSentenca, 1, 1);
		
		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> fieldCaractere.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if(dialogButton == addButton) 
				return new Pair<>(fieldCaractere.getText().charAt(0), fieldSentenca.getText());
			else return null;
		});
		
		
		
		Optional<Pair<Character, String>> result = dialog.showAndWait();
		result.ifPresent(caracterSentenca -> {
			//checa se já existe proposicao com o caracter fornecido
			for(Proposicao p : lista) 
				if(p.getCaractere() == fieldCaractere.getText().charAt(0)) {
					mostrarDialogoConfirmacao("Caracter já utilizado, a proposição não foi criada.");
					return;
				}
			
			lista.add(new Proposicao(
					fieldCaractere.getText().charAt(0),
					fieldSentenca.getText()));
		});
	}
	
	public void mostrarDialogoConfirmacao(String msg) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Informativo");
		dialog.setHeaderText(msg);
		ButtonType cancelButton = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(cancelButton);
		
		GridPane grid = new GridPane();		
		dialog.getDialogPane().setContent(grid);
		dialog.show();
	}

}
