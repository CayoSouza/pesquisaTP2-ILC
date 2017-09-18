package br.com.tp2_ilc.application;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.Pair;

import br.com.tp2_ilc.model.*;


public class Controller implements Initializable{
	
	@FXML private TableView<Proposicao> tabelaProposicao;
	@FXML private TableColumn<Proposicao, Character> colunaCaracter;
	@FXML private TableColumn<Proposicao, String> colunaSentenca;
	
	
	
	@FXML private ComboBox<Character> comboConectivos = new ComboBox<>();
	@FXML private ComboBox<String> comboFormulas1 = new ComboBox<>();
	@FXML private ComboBox<String> comboFormulas2 = new ComboBox<>();
	
	public ObservableList<Proposicao> proposicoes = FXCollections.observableArrayList(
			new Proposicao('a', "a água saudável"),
			new Proposicao('b', "a bola é redonda"),
			new Proposicao('c', "a casa é azul")
			);
	
	ValorVerdade[][] conectivoE = {{ValorVerdade.FALSE, ValorVerdade.FALSE}, {ValorVerdade.FALSE,ValorVerdade.TRUE}};
	ValorVerdade[][] conectivoOU = {{ValorVerdade.FALSE, ValorVerdade.TRUE}, {ValorVerdade.TRUE, ValorVerdade.TRUE}};
	ValorVerdade[][] conectivoOUEXCLUSIVO = {{ValorVerdade.FALSE, ValorVerdade.TRUE}, {ValorVerdade.TRUE, ValorVerdade.FALSE}};
	ValorVerdade[][] conectivoIMPLICACAO = {{ValorVerdade.TRUE, ValorVerdade.TRUE}, {ValorVerdade.FALSE, ValorVerdade.TRUE}};
	ValorVerdade[][] conectivoDUPLAIMPLICACAO = {{ValorVerdade.TRUE,ValorVerdade.FALSE}, {ValorVerdade.FALSE,ValorVerdade.TRUE}};
	ValorVerdade[] conectivoNEGACAO = {ValorVerdade.TRUE,ValorVerdade.FALSE};
	
	public ObservableList<Conectivo> conectivos = FXCollections.observableArrayList(
			new ConectivoBinario('^', conectivoE),
			new ConectivoBinario('v', conectivoOU),
			new ConectivoBinario('⊕', conectivoOUEXCLUSIVO),
			new ConectivoBinario('→', conectivoIMPLICACAO),
			new ConectivoUnario('¬', conectivoNEGACAO),
			new ConectivoBinario('↔', conectivoDUPLAIMPLICACAO));
	
	public ObservableList<FBF> fbfs = FXCollections.observableArrayList(
			new FBF(new Proposicao('a', "a água saudável")),
			new FBF(new Proposicao('b', "a bola é redonda")),
			new FBF(new Proposicao('c', "a casa é azul"))
			);
	@FXML private TableView<FBF> tabelaFbf;
	@FXML private TableColumn<FBF, String> colunaFbf;
//	@FXML private ListView<FBF> listaFbf = new ListView<>(fbfs);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaCaracter.setCellValueFactory(new PropertyValueFactory<Proposicao, Character>("caractere"));
		colunaSentenca.setCellValueFactory(new PropertyValueFactory<Proposicao, String>("sentenca"));
		tabelaProposicao.setItems(proposicoes);
	
		//colunaFbf.setCellValueFactory(f -> f.getValue().pegaSimbolos());
		colunaFbf.setCellValueFactory(new PropertyValueFactory<FBF, String>("simboloz"));
		tabelaFbf.setItems(fbfs);
	}
	
	@FXML
	public void inserirProposicao(ActionEvent event) {
		mostrarDialogoProposicao();
	}
	
	@FXML
	public void inserirFormula(ActionEvent event) {
		mostrarDialogoFormula();
	}
	
	@FXML
	public void deletarProposicao(ActionEvent event) {
		Proposicao selecionada = tabelaProposicao.getSelectionModel().getSelectedItem();
		tabelaProposicao.getItems().remove(selecionada);
	}
	
	@FXML
	public void deletarFormula(ActionEvent event) {
		FBF selecionada = tabelaFbf.getSelectionModel().getSelectedItem();
		tabelaFbf.getItems().remove(selecionada);
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
		TextField txtCaractere = new TextField();
		txtCaractere.setPromptText("Caractere");
		
		//limitar caractere para um
		Pattern pattern = Pattern.compile("\\w");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
		    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});
		txtCaractere.setTextFormatter(formatter);
		
		TextField txtSentenca = new TextField();
		txtSentenca.setPromptText("Sentenca");
		
		grid.add(new Label("Caractere"), 0, 0);
		grid.add(txtCaractere, 1, 0);
		grid.add(new Label("Sentenca"), 0, 1);
		grid.add(txtSentenca, 1, 1);
		
		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> txtCaractere.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if(dialogButton == addButton) 
				return new Pair<>(txtCaractere.getText().charAt(0), txtSentenca.getText());
			else return null;
		});
		
		
		
		Optional<Pair<Character, String>> result = dialog.showAndWait();
		result.ifPresent(caracterSentenca -> {
			//checa se já existe proposicao com o caracter fornecido
			for(Proposicao p : proposicoes) 
				if(p.getCaractere() == txtCaractere.getText().charAt(0)) {
					mostrarDialogoConfirmacao("Caracter já utilizado, a proposição não foi criada.");
					return;
				}
			
			proposicoes.add(new Proposicao(
					txtCaractere.getText().charAt(0),
					txtSentenca.getText()));
		});
	}
	
	public void mostrarDialogoFormula() {
		Dialog<FBF> dialog = new Dialog<>();
		dialog.setTitle("Adicionar formula");
		dialog.setHeaderText("Escolha o conectivo desejado e em seguida a(s) proposicão(oes);");
		ButtonType addButton = new ButtonType("Adicionar", ButtonData.OK_DONE); 
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		comboConectivos.getItems().clear();
		comboFormulas1.getItems().clear();
		comboFormulas2.getItems().clear();
		
		for(Conectivo c : conectivos)
			comboConectivos.getItems().add(c.getCaractere());
		
		for(FBF fbf : fbfs)
			comboFormulas1.getItems().add(fbf.getSimboloz());
		
		for(FBF fbf : fbfs)
			comboFormulas2.getItems().add(fbf.getSimboloz());
		
		grid.add(new Label("Conectivos"), 0, 0);
		grid.add(comboConectivos, 1, 0);
		grid.add(new Label("Formula 1"), 0, 1);
		grid.add(comboFormulas1, 1, 1);
		grid.add(new Label("Formula 2"), 0, 2);
		grid.add(comboFormulas2, 1, 2);
		
		comboConectivos.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
		 	if(newValue.toString().equals("¬")) 
		 		grid.getChildren().remove(4,6);
		 	else if(!grid.getChildren().contains(comboFormulas2)) {
		 		grid.add(new Label("Formula 2"), 0, 2);
				grid.add(comboFormulas2, 1, 2);
			}
		 	
	});
		
		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> comboConectivos.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if(dialogButton == addButton) {
				FBF formula1 = null;
				FBF formula2 = null;
				Conectivo conectivo = null;
				 
				Character stringConectivo = comboConectivos.getSelectionModel().getSelectedItem();
				String stringFormula1 = comboFormulas1.getSelectionModel().getSelectedItem();
				
				for(Conectivo conect : conectivos) 
					if(conect.getCaractere() == stringConectivo)
						conectivo = conect;
				
				if(conectivo instanceof ConectivoBinario) {
					String stringFormula2 = comboFormulas2.getSelectionModel().getSelectedItem();
					for(FBF fbf : fbfs) {
						if(fbf.pegaSimbolos().equals(stringFormula1))
							formula1 = fbf;
						else if(fbf.pegaSimbolos().equals(stringFormula2))
							formula2 = fbf;
					}
					//tenho duas formulas
					return new FBF((ConectivoBinario)conectivo, formula1, formula2);
				}
				else {
					for(FBF fbf : fbfs) 
						if(fbf.pegaSimbolos().equals(stringFormula1))
							formula1 = fbf;
					
					//tenho uma formula
					return new FBF((ConectivoUnario)conectivo, formula1);
				}
			}
			else return null;
		});
		
		
		
		Optional<FBF> result = dialog.showAndWait();
		result.ifPresent(fbf -> {
			//checa se já existe proposicao com o caracter fornecido
//			if(fbf.pegaSimbolos() == txtConectivo.getText().charAt(0)) {
//					mostrarDialogoConfirmacao("Caracter já utilizado, a proposição não foi criada.");
//					return;
//				}
			
			fbfs.add(fbf);
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
