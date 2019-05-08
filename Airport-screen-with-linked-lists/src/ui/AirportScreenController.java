package ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Airport;
import model.Airport.SortingsTypes;
import model.Flight;

public class AirportScreenController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BorderPane bpPane;

	@FXML
	private TableView<Flight> tv;

	@FXML
	private Label lbTime;

	@FXML
	private TextField tfNumberFlights;


	@FXML
	private Label lbtimeSystem;

	@FXML
	private Label lbInfoSearch;

	private ObservableList<Flight> list;

	private Airport airport;


	@FXML
	public void generateFlights(ActionEvent event) throws IOException {
		long initTime = System.currentTimeMillis();
		airport.setFirst(null);
	//	list.clear();
		int flightsNumber = Integer.parseInt(tfNumberFlights.getText());
		airport.generateFlights(flightsNumber);
		tv.setItems(list);
		ObservableList<Flight> list = obsevableFlights();
		long totalTime = System.currentTimeMillis() - initTime;
		lbtimeSystem.setText(totalTime + " ms were needed to sort.");
		
	}

	@FXML
	public void initialize() throws IOException {
		airport = new Airport();
		airport.generateFlights(15);
		intializeTableView();
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void intializeTableView(){

		TableColumn<Flight, Date> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


		TableColumn<Flight, String> timeColumn = new TableColumn<>("Time");
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("hour"));


		TableColumn<Flight, String> airlineColumn = new TableColumn<>("Airline");
		airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));


		TableColumn<Flight, String> flightColumn = new TableColumn<>("Flight Number");
		flightColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));;


		TableColumn<Flight, String> toColumn = new TableColumn<>("To");
		toColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));;


		TableColumn<Flight, String> gateColumn = new TableColumn<>("Gate");
		gateColumn.setCellValueFactory(new PropertyValueFactory<>("gate"));

		tv = new TableView<>();
		tv.setItems(obsevableFlights());
		tv.getColumns().addAll(dateColumn, timeColumn, airlineColumn, flightColumn, toColumn, gateColumn);
		bpPane.setCenter(tv);
	}

	public ObservableList<Flight> obsevableFlights(){

		list = FXCollections.observableArrayList();
		Flight current = airport.getFirst();
    	while(current!=null) {
    	list.add(current);
    	current = current.getNext();
    	}
		return list;
	}

	@FXML
	void sortAirline(ActionEvent event) {
		long initTime = System.currentTimeMillis();
		airport.setSortingsTypes(SortingsTypes.AIRLINE);
		airport.sort();
		long totalTime = System.currentTimeMillis() - initTime;
		ObservableList<Flight> list = obsevableFlights();
		tv.setItems(list);
		lbtimeSystem.setText(totalTime + " ms were needed to sort.");
	}

	@FXML
	void sortGate(ActionEvent event) {
		long initTime = System.currentTimeMillis();
		airport.setSortingsTypes(SortingsTypes.GATE);
		airport.sort();
		long totalTime = System.currentTimeMillis() - initTime;
		ObservableList<Flight> list = obsevableFlights();
		tv.setItems(list);
		lbtimeSystem.setText(totalTime + " ms were needed to sort.");
	}

	@FXML
	void sortDestination(ActionEvent event) {
		long initTime = System.currentTimeMillis();
		airport.setSortingsTypes(SortingsTypes.DESTINATION);
		airport.sort();
		long totalTime = System.currentTimeMillis() - initTime;
		ObservableList<Flight> list = obsevableFlights();
		tv.setItems(list);
		lbtimeSystem.setText(totalTime + " ms were needed to sort.");
	}

	@FXML
	void sortFlightNumber(ActionEvent event) {
		long initTime = System.currentTimeMillis();
		airport.setSortingsTypes(SortingsTypes.FLIGHT_NUMBER);
		airport.sort();
		long totalTime = System.currentTimeMillis() - initTime;
		ObservableList<Flight> list = obsevableFlights();
		tv.setItems(list);
		lbtimeSystem.setText(totalTime + " ms were needed to sort.");
	}

	@FXML
	void sortTime(ActionEvent event) {
		long initTime = System.currentTimeMillis();
		airport.setSortingsTypes(SortingsTypes.TIME);
		airport.sort();
		long totalTime = System.currentTimeMillis() - initTime;
		ObservableList<Flight> list = obsevableFlights();
		tv.setItems(list);
		lbtimeSystem.setText(totalTime + " ms were needed to sort.");
	}

	@FXML
	void sortDate(ActionEvent event) {
		long initTime = System.currentTimeMillis();
		airport.setSortingsTypes(SortingsTypes.DATE);
		airport.sort();
		long totalTime = System.currentTimeMillis() - initTime;
		ObservableList<Flight> list = obsevableFlights();
		tv.setItems(list);
		lbtimeSystem.setText(totalTime + " ms were needed to sort.");
	}
	 @FXML
	    void searchAirline(ActionEvent event) {
	    	askForInput(SortingsTypes.AIRLINE);
	    }

	    @FXML
	    void searchGate(ActionEvent event) {
	    	askForInput(SortingsTypes.GATE);
	    }

	
	    @FXML
	    void searchDestination(ActionEvent event) {
	    	askForInput(SortingsTypes.DESTINATION);
	    }

	    @FXML
	    void searchFlightNumber(ActionEvent event) {
	    	askForInput(SortingsTypes.FLIGHT_NUMBER);
	    }


	 void askForInput(SortingsTypes s) {
	    	Stage dialog = new Stage();
	        VBox dialogVbox = new VBox(20);
	        Label title = new Label();
	        title.setText("Please input the searched ");
	        switch(s) {
			case AIRLINE:
				title.setText(title.getText() + "airline name.");
				break;
			case GATE:
				title.setText(title.getText() + "boarding gate.");
				break;
			case DESTINATION:
				title.setText(title.getText() + "destination city name.");
				break;
			case FLIGHT_NUMBER:
				title.setText(title.getText() + "flight number.");
				break;
			case TIME:
				title.setText(title.getText() + "date in YYYY-MM-AA HH:MM AM/PM format.");
				break;
	        }
	        TextField inputTF = new TextField();
	        inputTF.setMaxWidth(150);
	        Button search = new Button("Search");
	        search.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					try {
						String msg = inputTF.getText();
						long initTime = System.currentTimeMillis();
						Flight found = airport.search(s, msg);
						long totalTime = System.currentTimeMillis() - initTime;
						lbtimeSystem.setText(totalTime + " ms were needed to search.");
						ObservableList<Flight> list = obsevableFlights();
						tv.setItems(list);
					}catch(NumberFormatException | IndexOutOfBoundsException e) {
						Alert errorMessage = new Alert(AlertType.ERROR);
			    		errorMessage.setContentText("Please write the date in YYYY-MM-AA HH:MM AM/PM format.");
			    		errorMessage.show();
					}finally {
						dialog.close();
					}
				}
				
	        	
	        });
	        dialogVbox.setPadding(new Insets(14,14,14,14));
	        dialogVbox.setSpacing(8);
	        dialogVbox.setAlignment(Pos.CENTER);
	        dialogVbox.getChildren().addAll(title,inputTF,search);
	        Scene dialogScene = new Scene(dialogVbox, 500, 150);
	        dialog.setScene(dialogScene);
	        dialog.setTitle("Input");
	        dialog.setResizable(false);
	        dialog.show();
	    }



}
