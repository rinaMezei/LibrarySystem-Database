package dbApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class FindBranchController {
	
	static Connection dbConnection;
	
    @FXML
    private Label lblBranchName;

    @FXML
    private TextField txtBranchName;

    @FXML
    private Label lblResults;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnEnter;

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
    			
    			PreparedStatement statement = dbConnection.prepareStatement("select BranchName,BranchAddress, BranchCity,BranchState,BranchZip,BranchPhone, BranchFax from Branch where BranchName=?");
    	        
    	        statement.setString(1, txtBranchName.getText());
    			ResultSet rs = statement.executeQuery();
    			System.out.println("executed the statement");
    			
    			if(rs.next() == false)
    			{
    				lblResults.setText("Branch name not found");
    			}
    			else 
    			{
    				HashMap<String,String> map = new HashMap<String,String>();
        			do 
        			{
        				map.put("BranchName", rs.getString("BranchName"));
        				map.put("BranchAddress", rs.getString("BranchAddress"));
        				map.put("BranchCity", rs.getString("BranchCity"));
        				map.put("BranchState", rs.getString("BranchState"));
        				map.put("BranchZip", rs.getString("BranchZip"));
        				map.put("BranchPhone", rs.getString("BranchPhone"));
        				map.put("BranchFax", rs.getString("BranchFax"));
        			}while(rs.next());
        			
        			rs.close();
        			statement.close();
        			dbConnection.close();
        			lblResults.setText("Branch Name: " + map.get("BranchName") + "\nBranch Address: " + map.get("BranchAddress") 
        			+ "\nBranch City: " + map.get("BranchCity") + "\nBranch State: " + map.get("BranchState") + "\nBranch Zip: "
        			+ map.get("BranchZip") + "\nBranch Phone Number: " + map.get("BranchPhone") + "\nBranch Fax Number: " 
        			+ map.get("BranchFax"));
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
