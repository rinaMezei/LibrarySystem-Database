package dbApp;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.DBConnect;

public class ReturnBookController {

    @FXML
    private Label lblLibraryCardID;

    @FXML
    private TextField txtLibraryCardID;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnEnter;

    @FXML
    private Button btnReturnBook;

    @FXML
    private ListView<String> listViewBooks;

    @FXML
    void btnBackClicked(ActionEvent event) throws IOException{
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
    	listViewBooks.getItems().clear();
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
				PreparedStatement libraryIDStatement = dbConnection.prepareStatement("select LibraryCardID\r\n" + 
    					"from LibraryCard");
    	        ArrayList<Integer> libraryCards = new ArrayList<Integer>();
    			ResultSet rsLibraryCard = libraryIDStatement.executeQuery();
    			while (rsLibraryCard.next())
    			{
    				libraryCards.add(rsLibraryCard.getInt(("LibraryCardID")));
    			}
    			if(!libraryCards.contains(Integer.parseInt(txtLibraryCardID.getText())))
    			{
    				Alert alert = new Alert(AlertType.ERROR);
    				alert.setContentText("Invalid Library Card ID");
    				alert.showAndWait();
    			}
    			else
    			{
        			PreparedStatement booksStatement = dbConnection.prepareStatement("select bookBranch.bookID, book.bookTitle, Author.AuthorFName + ' ' + Author.AuthorLName as Author\r\n" + 
        					"from Author inner join Book_Author\r\n" + 
        					"on author.AuthorID= book_author.AuthorID \r\n" + 
        					"inner join book \r\n" + 
        					"on book_author.ISBN = book.ISBN \r\n" + 
        					"inner join BookBranch\r\n" + 
        					"on Book.ISBN = BookBranch.ISBN \r\n" + 
        					"inner join BorrowedBook \r\n" + 
        					"on bookbranch.BookID = BorrowedBook.BookID\r\n" + 
        					"where BorrowedBook.LibraryCardID = ? and DateReturned is null");
        			booksStatement.setInt(1, Integer.parseInt(txtLibraryCardID.getText()));
        			ResultSet rsBooks = booksStatement.executeQuery();
        			if(rsBooks.next() == false)
        			{
        				listViewBooks.getItems().add("No books checked out");
        			}
        			else 
        			{
            			do
            			{
            				listViewBooks.getItems().add(rsBooks.getInt("BookID") + ", " + rsBooks.getString("BookTitle") + ", " + rsBooks.getString("Author"));
            			}while (rsBooks.next()); 
            			
            			rsBooks.close();
            			rsLibraryCard.close();
            			libraryIDStatement.close();
            			booksStatement.close();
            			dbConnection.close();
            		}
        			listViewBooks.setVisible(true);
        	    	btnReturnBook.setVisible(true);
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

    @FXML
    void btnReturnBookClicked(ActionEvent event) {
    	Connection dbConnection=null;
    	String selectedBook = listViewBooks.getSelectionModel().getSelectedItem();
    	String [] splitInfo = selectedBook.split(",");
    	int bookID = Integer.parseInt(splitInfo[0]);
    	try {
    		DBConnect.connectDB();
    		dbConnection = DBConnect.getConnection();
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
	    		dbConnection.setAutoCommit(false);
	            System.out.println("setting up Callable Statement");
	            CallableStatement cStatement = dbConnection.prepareCall("{call usp_returnBorrowedBook(?,?)}");
	            System.out.println("all done set up Callable Statement");
	            //sets up header information
	            cStatement.setInt(1, bookID);
	            cStatement.setInt(2,Integer.parseInt(txtLibraryCardID.getText()));
	            ((SQLServerCallableStatement)cStatement).execute();
	            dbConnection.commit();
	            dbConnection.close();
	            Alert alert =new Alert(AlertType.INFORMATION);
	            alert.setContentText("Returned book successfully");
	            alert.showAndWait();
    		}
    	}
    	catch(SQLException sqlE){
            try{
                 dbConnection.rollback();
                 dbConnection.close();
                 Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("sql errors occurred " + sqlE.getMessage());
                alert.showAndWait();
            }
            catch(SQLException sqlE2){
            
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("sql errors occurred " + sqlE2.getMessage());
                alert.showAndWait();
            }
        }
        
    }

}
