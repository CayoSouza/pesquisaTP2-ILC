package br.com.tp2_ilc.application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.Pair;

import br.com.tp2_ilc.model.*;

public class Controller implements Initializable {

	// componentes de tabela
	@FXML
	private TableView<Proposicao> tabelaProposicao;
	@FXML
	private TableColumn<Proposicao, Character> colunaCaracter;
	@FXML
	private TableColumn<Proposicao, String> colunaSentenca;
	@FXML
	private TableView<FBF> tabelaFbf;
	@FXML
	private TableColumn<FBF, String> colunaFbf;
	@FXML
	private TableView<ObservableList<String>> tableViewTabelaVerdade;
	

	// comboboxes
	@FXML
	private ComboBox<Character> comboConectivos = new ComboBox<>();
	@FXML
	private ComboBox<String> comboFormulas1 = new ComboBox<>();
	@FXML
	private ComboBox<String> comboFormulas2 = new ComboBox<>();

	static final ValorVerdade FALSE = new ValorVerdade('F',false);
	static final ValorVerdade TRUE = new ValorVerdade('V',true);
	
	ValorVerdade[][] conectivoE = { { FALSE, FALSE },
			{ FALSE, TRUE } };
	ValorVerdade[][] conectivoOU = { { FALSE, TRUE },
			{ TRUE, TRUE } };
	ValorVerdade[][] conectivoOUEXCLUSIVO = { { FALSE, TRUE },
			{ TRUE, FALSE } };
	ValorVerdade[][] conectivoIMPLICACAO = { { TRUE, TRUE },
			{ FALSE, TRUE } };
	ValorVerdade[][] conectivoDUPLAIMPLICACAO = { { TRUE, FALSE },
			{ FALSE, TRUE } };
	ValorVerdade[] conectivoNEGACAO = { TRUE, FALSE };

	// listas
	public ObservableList<Proposicao> proposicoes = FXCollections.observableArrayList(
			new Proposicao('a', "a água é saudável"), new Proposicao('b', "a bola é redonda"),
			new Proposicao('c', "a casa é azul"));
	public ObservableList<Conectivo> conectivos = FXCollections.observableArrayList(
			new ConectivoBinario('^', conectivoE), new ConectivoBinario('v', conectivoOU),
			new ConectivoBinario('⊕', conectivoOUEXCLUSIVO), new ConectivoBinario('→', conectivoIMPLICACAO),
			new ConectivoUnario('¬', conectivoNEGACAO), new ConectivoBinario('↔', conectivoDUPLAIMPLICACAO));
	public ObservableList<FBF> fbfs = FXCollections.observableArrayList(new FBF(new Proposicao('a', "a água é saudável")),
			new FBF(new Proposicao('b', "a bola é redonda")), new FBF(new Proposicao('c', "a casa é azul")));
	ObservableList<ObservableList<Atribuicao>> linhas = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaCaracter.setCellValueFactory(new PropertyValueFactory<Proposicao, Character>("caractere"));
		colunaSentenca.setCellValueFactory(new PropertyValueFactory<Proposicao, String>("sentenca"));
		tabelaProposicao.setItems(proposicoes);

		colunaFbf.setCellValueFactory(new PropertyValueFactory<FBF, String>("expressao"));
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
		Proposicao proposicaoSelecionada = tabelaProposicao.getSelectionModel().getSelectedItem();
		proposicoes.remove(proposicaoSelecionada);
		for (FBF fbf : fbfs)
			if (fbf.getExpressao().charAt(0) == proposicaoSelecionada.getCaractere())
				fbfs.remove(fbf);
	}

	@FXML
	public void deletarFormula(ActionEvent event) {
		FBF fbfSelecionada = tabelaFbf.getSelectionModel().getSelectedItem();
		fbfs.remove(fbfSelecionada);
		for (Proposicao p : proposicoes)
			if (fbfSelecionada.getRaiz().getCaractere() == p.getCaractere())
				proposicoes.remove(p);
	}

	public void mostrarDialogoProposicao() {
		Dialog<Pair<Character, String>> dialog = new Dialog<>();
		dialog.setTitle("Adicionar proposicao");
		dialog.setHeaderText("Insira o caracter e a sentenÃƒÂ§a da proprosiÃƒÂ§ÃƒÂ£o desejada.");
		ButtonType addButton = new ButtonType("Adicionar", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		TextField txtCaractere = new TextField();
		txtCaractere.setPromptText("Caractere");

		// limitar caractere para um
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
			if (dialogButton == addButton)
				return new Pair<>(txtCaractere.getText().charAt(0), txtSentenca.getText());
			else
				return null;
		});

		Optional<Pair<Character, String>> result = dialog.showAndWait();
		result.ifPresent(caracterSentenca -> {
			// checa se jÃƒÂ¡ existe proposicao com o caracter fornecido
			// metodo separado com exceÃƒÂ§ÃƒÂ£o
			for (Proposicao p : proposicoes)
				if (p.getCaractere() == txtCaractere.getText().charAt(0)) {
					mostrarDialogoConfirmacao("Caracter jÃƒÂ¡ utilizado, a proposiÃƒÂ§ÃƒÂ£o nÃƒÂ£o foi criada.");
					return;
				}

			Proposicao novaProp = new Proposicao(txtCaractere.getText().charAt(0), txtSentenca.getText());
			proposicoes.add(novaProp);
			FBF novaFbf = new FBF(novaProp);
			fbfs.add(novaFbf);
		});
	}

	public void mostrarDialogoFormula() {
		Dialog<FBF> dialog = new Dialog<>();
		dialog.setTitle("Adicionar formula");
		dialog.setHeaderText("Escolha o conectivo desejado e em seguida a(s) proposicÃƒÂ£o(oes);");
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

		for (Conectivo c : conectivos)
			comboConectivos.getItems().add(c.getCaractere());

		for (FBF fbf : fbfs) {
			comboFormulas1.getItems().add(fbf.getExpressao());
			comboFormulas2.getItems().add(fbf.getExpressao());
		}

		grid.add(new Label("Conectivos"), 0, 0);
		grid.add(comboConectivos, 1, 0);
		grid.add(new Label("Formula 1"), 0, 1);
		grid.add(comboFormulas1, 1, 1);
		grid.add(new Label("Formula 2"), 0, 2);
		grid.add(comboFormulas2, 1, 2);

		comboConectivos.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if (newValue.toString().equals("Ã‚Â¬"))
				grid.getChildren().remove(4, 6);
			else if (!grid.getChildren().contains(comboFormulas2)) {
				grid.add(new Label("Formula 2"), 0, 2);
				grid.add(comboFormulas2, 1, 2);
			}

		});

		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> comboConectivos.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButton) {
				FBF formula1 = null;
				FBF formula2 = null;
				Conectivo conectivo = null;

				Character stringConectivo = comboConectivos.getSelectionModel().getSelectedItem();
				String stringFormula1 = comboFormulas1.getSelectionModel().getSelectedItem();

				for (Conectivo conect : conectivos)
					if (conect.getCaractere() == stringConectivo)
						conectivo = conect;

				if (conectivo.getNumeroArgumentos() == 2) {
					String stringFormula2 = comboFormulas2.getSelectionModel().getSelectedItem();
					for (FBF fbf : fbfs) {
						if (fbf.getExpressao().equals(stringFormula1))
							formula1 = fbf;
						if (fbf.getExpressao().equals(stringFormula2))
							formula2 = fbf;
					}
					// tenho duas formulas
					return new FBF((ConectivoBinario) conectivo, formula1, formula2);
				} else {
					for (FBF fbf : fbfs)
						if (fbf.getExpressao().equals(stringFormula1))
							formula1 = fbf;

					// tenho uma formula
					return new FBF((ConectivoUnario) conectivo, formula1);
				}
			} else
				return null;
		});

		Optional<FBF> result = dialog.showAndWait();
		result.ifPresent(fbf -> {
			// checa se jÃƒÂ¡ existe proposicao com o caracter fornecido
			// if(fbf.pegaSimbolos() == txtConectivo.getText().charAt(0)) {
			// mostrarDialogoConfirmacao("Caracter jÃƒÂ¡ utilizado, a proposiÃƒÂ§ÃƒÂ£o nÃƒÂ£o foi
			// criada.");
			// return;
			// }

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

	public void mostrarTabelaVerdade(MouseEvent event) {
		FBF fbfSelecionada = tabelaFbf.getSelectionModel().getSelectedItem();
		TabelaVerdade tabelaVerdade = fbfSelecionada.geraTabelaVerdade();
		populaTabelaVerdade(tabelaVerdade);
	}

	
	public void populaTabelaVerdade(TabelaVerdade tabelaVerdade) {
			tableViewTabelaVerdade.getColumns().clear();
			ObservableList<ObservableList<String>> linhas = FXCollections.observableArrayList();
			
			List<String> columns = new ArrayList<String>();
			List<String> rows = new ArrayList<String>();
			/*
			 * Cria cada coluna
			 */
			for (Atomo i : tabelaVerdade.getListaProposicoes()) {
				columns.add(String.valueOf(i.getCaractere()));
			}
			columns.add(tabelaVerdade.getFormula().getExpressao());
			
			/*
			 * Insere as colunas na tabela verdade
			 */
			for (int i = 0; i < columns.size();i++) {
				final int finalIdx = i;
				TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(i));
				 column.setCellValueFactory(
                         param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
				 tableViewTabelaVerdade.getColumns().add(column);
			}
			/*
			 * Monta cada linha da tabela verdade
			 */
			for(int i = 0; i < Math.pow(2, columns.size()-1); i++) {
				ObservableList<String> row = FXCollections.observableArrayList();
				row.clear();
				for(Atribuicao atribuicao : tabelaVerdade.getInterpretacoes().get(i).getAtribuicoes()) {
					row.add(String.valueOf(atribuicao.getValorCaractere()));
				}
				row.add(String.valueOf(tabelaVerdade.getValoresVerdade().get(i).getCaractere()));
				linhas.add(row);
			}
			tableViewTabelaVerdade.setItems(linhas);
			
	}
}
