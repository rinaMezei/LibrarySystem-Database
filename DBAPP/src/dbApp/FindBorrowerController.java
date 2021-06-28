package dbApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.DBConnect;

public class FindBorrowerController {

    @FXML
    private Label lblBorrowerName;

    @FXML
    private TextField txtBorrowerName;

    @FXML
    private Label lblResults;

    @FXML
    private Button btnEnter;

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

    @FXML
    void btnEnterClicked(ActionEvent event) throws SQLException {
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
    			//now try to retrieve the data the user requested
    			
    			PreparedStatement statement = dbConnection.prepareStatement("select BorrowerFName, BorrowerLName, BorrowerAddress, BorowerCity, BorrowerState, BorrowerZip, BorrowerPhoneNum, BorrowerDOB from Borrower where BorrowerFName + ' ' + BorrowerLName =?");
    	        
    	        statement.setString(1, txtBorrowerName.getText());
    			ResultSet rs = statement.executeQuery();
    			System.out.println("executed the statement");
    			
    			if(rs.next() == false)
    			{
    				lblResults.setText("Borrower not found");
    			}
    			else 
    			{
    				HashMap<String,String> map = new HashMap<String,String>();
        			do
        			{
        				map.put("FName", rs.getString("BorrowerFName"));
        				map.put("LName", rs.getString("BorrowerLName"));
        				map.put("Address", rs.getString("BorrowerAddress"));
        				map.put("City", rs.getString("BorowerCity"));
        				map.put("State", rs.getString("BorrowerState"));
        				map.put("Zip", rs.getString("BorrowerZip"));
        				map.put("PhoneNum", rs.getString("BorrowerPhoneNum"));
        				map.put("DOB", rs.getDate("BorrowerDOB").toString());
        			}while (rs.next()); 
        			
        			rs.close();
        			statement.close();
        			dbConnection.close();
        			lblResults.setText("Borrower Name: " + map.get("FName") + " " + map.get("LName") + "\nBorrower Address: " + map.get("Address") 
        			+ "\nBorrower City: " + map.get("City") + "\nBorrower State: " + map.get("State") + "\nBorrower Zip: "
        			+ map.get("Zip") + "\nBorrower Phone Number: " + map.get("PhoneNum") + "\nBorrower DOB: " 
        			+ map.get("DOB"));
        		}
    		}    		
    	}
    	catch (SQLException sqlException) 
    	{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("SQL ERROR");
            alert.setContentText("SQLException couldnt retrieve data " + sqlException.getMessage());
    		
            throw sqlException;
    	}
    }

}
