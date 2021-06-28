package dbApp;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LibrarySystemAppController {

    @FXML
    private MenuBar menuBar;
    
    @FXML
    private MenuItem findBranchMenu;

    @FXML
    private MenuItem findBookISBNMenu;

    @FXML
    private MenuItem findBookBranchMenu;

    @FXML
    private MenuItem findBorrowerMenu;

    @FXML
    private MenuItem listBorrowersMenu;

    @FXML
    private MenuItem borrowBookMenu;

    @FXML
    private MenuItem returnBookMenu;
    
    @FXML
    private MenuItem btnExit;

    @FXML
    void borrowBookClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("FindBranchFXML.fxml"));
    	//BorderPane customerPane = (BorderPane) loader.load();
    	//Scene custScene = new Scene(customerPane);
    	
     
    	Parent root = loader.load(getClass().getResource("BorrowBookFXML.fxml"));
    	 
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	Stage mainStage = (Stage) menuBar.getScene().getWindow();
    	mainStage.setScene(nextScene);
		mainStage.show();
    }

    @FXML
    void findBookBranchClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("FindBranchFXML.fxml"));
    	//BorderPane customerPane = (BorderPane) loader.load();
    	//Scene custScene = new Scene(customerPane);
    	
     
    	Parent root = loader.load(getClass().getResource("FindBookBranchFXML.fxml"));
    	 
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	Stage mainStage = (Stage) menuBar.getScene().getWindow();
    	mainStage.setScene(nextScene);
		mainStage.show();
    }

    @FXML
    void findBookISBNClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("FindBranchFXML.fxml"));
    	//BorderPane customerPane = (BorderPane) loader.load();
    	//Scene custScene = new Scene(customerPane);
    	
     
    	Parent root = loader.load(getClass().getResource("FindBookISBNFXML.fxml"));
    	 
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	Stage mainStage = (Stage) menuBar.getScene().getWindow();
    	mainStage.setScene(nextScene);
		mainStage.show();
    }

    @FXML
    void findBorrowerClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("FindBranchFXML.fxml"));
    	//BorderPane customerPane = (BorderPane) loader.load();
    	//Scene custScene = new Scene(customerPane);
    	
     
    	Parent root = loader.load(getClass().getResource("FindBorrowerFXML.fxml"));
    	 
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	Stage mainStage = (Stage) menuBar.getScene().getWindow();
    	mainStage.setScene(nextScene);
		mainStage.show();
    }

    @FXML
    void findBranchClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("FindBranchFXML.fxml"));
    	//BorderPane customerPane = (BorderPane) loader.load();
    	//Scene custScene = new Scene(customerPane);
    	
     
    	Parent root = loader.load(getClass().getResource("FindBranchFXML.fxml"));
    	 
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	Stage mainStage = (Stage) menuBar.getScene().getWindow();
    	mainStage.setScene(nextScene);
		mainStage.show();
    }

    @FXML
    void listBorrowersClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("FindBranchFXML.fxml"));
    	//BorderPane customerPane = (BorderPane) loader.load();
    	//Scene custScene = new Scene(customerPane);
    	
     
    	Parent root = loader.load(getClass().getResource("ListBorrowersFXML.fxml"));
    	 
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	Stage mainStage = (Stage) menuBar.getScene().getWindow();
    	mainStage.setTitle("List Borrowers");
    	mainStage.setScene(nextScene);
    	mainStage.show();
    }

    @FXML
    void returnBookClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("FindBranchFXML.fxml"));
    	//BorderPane customerPane = (BorderPane) loader.load();
    	//Scene custScene = new Scene(customerPane);
    	
     
    	Parent root = loader.load(getClass().getResource("ReturnBookFXML.fxml"));
    	 
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	Stage mainStage = (Stage) menuBar.getScene().getWindow();
    	mainStage.setScene(nextScene);
		mainStage.show();
    }
    
    @FXML
    void btnExitClicked(ActionEvent event) {
    	Alert alert = new Alert (AlertType.INFORMATION);
    	alert.setContentText("Exiting...Have a good day!");
    	alert.showAndWait();
    	System.exit(0);
    }

}
