package dbApp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.DBConnect;

public class ListBorrowersController implements Initializable {

    @FXML
    private ListView<String> listViewBorrowers;

    @FXML
    private Button btnBack;

    @FXML
    void btnBackClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("LibrarySystemApp.fxml"));
    	Stage mainStage = (Stage) btnBack.getScene().getWindow();
	     
    	BorderPane root = (BorderPane) loader.load(getClass().getResource("LibrarySystemApp.fxml"));
    	//modify the scene of the stage to display this file

    	//instantiate scene based on the new layout of the fxml that we just loaded
    	Scene nextScene = new Scene(root,400,400);
    	
    	mainStage.setScene(nextScene);
		mainStage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listViewBorrowers.getItems().clear();
		try {
    		DBConnect.connectDB();
    		Connection dbConnection = DBConnect.getConnection();
    		if(dbConnection == null)
    		{
    			Alert alert = new Alert (AlertType.ERROR);
    			alert.setContentText("data not available at this time");
    			alert.showAndWait();
    		}
    		else
    		{
    			System.out.println("got a database connection");
				PreparedStatement statement = dbConnection.prepareStatement("select BorrowerFName, BorrowerLName, BorrowerAddress, BorowerCity, BorrowerState, BorrowerZip, BorrowerPhoneNum, BorrowerDOB from Borrower");
		       
				ResultSet rs = statement.executeQuery();
				if(rs.next() == false)
    			{
    				listViewBorrowers.getItems().add("No borrowers available");
    			}
    			else 
    			{
    				ArrayList<String> arrayString = new ArrayList<String>();
        			do
        			{
        				arrayString.add(rs.getString("BorrowerFName") + " " + rs.getString("BorrowerLName") + " " + rs.getDate("BorrowerDOB").toString());
        			}while (rs.next());
        			
        			rs.close();
        			statement.close();
        			dbConnection.close();
        			
        			for(String s: arrayString)
        			{
        				listViewBorrowers.getItems().add(s);
        			}
    			}
    		}
		}
		catch (SQLException sqlException) 
    	{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("SQL ERROR");
            alert.setContentText("SQLException couldnt retrieve data " + sqlException.getMessage());
    		
           
    	}
	}
}
