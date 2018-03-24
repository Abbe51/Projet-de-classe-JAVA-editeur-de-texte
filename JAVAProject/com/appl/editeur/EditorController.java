package com.appl.editeur;





import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.web.HTMLEditor;

import javafx.stage.FileChooser;

import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;





public class EditorController {



	@FXML
   private HTMLEditor htmledit;

	 
	    Stage test;
    
	    @FXML
	    private Button button;

    private Button button3;
    
    ImageView myImageView ;

    Scene scene2;

    @FXML
    private void onNouveau()throws IOException{
    	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pad.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle("Nouveau document - SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    	} catch(Exception e){
    		System.out.println("Impossible d'ouvrir nouveau document");
    	}
    }

   
       
   


    @FXML
    private void onOuvrir() {
    	  FileChooser fileChooser = new FileChooser();
          
          //Set extension filter
          FileChooser.ExtensionFilter extFilterTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
          FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
          fileChooser.getExtensionFilters().addAll(extFilterTXT, extFilterHTML);
           
          //Show open file dialog
          File file = fileChooser.showOpenDialog(null);
          if(file != null){
        	  Stage stage = (Stage) htmledit.getScene().getWindow();
        	  stage.setTitle(file.getName() + " - Swiftpad");
              String textRead = readFile(file);
              htmledit.setHtmlText(textRead);
              
          }
      }
   
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
    

    
   @FXML
    private void onEnregistrerSous() {
	   String stringHtml = htmledit.getHtmlText();
		stripHTMLTags(stringHtml);

   
          FileChooser fileChooser = new FileChooser();
         
        //Set extension filter
        FileChooser.ExtensionFilter extFilterTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
        fileChooser.getExtensionFilters().addAll(extFilterTXT, extFilterHTML);
         
        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            Enregistrer(stringHtml, file);
        }
        }
    
    	
    
    private void Enregistrer(String content ,File file) {
		
    	try {
    		
                FileWriter fileWriter = null;
                 
                fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
     
              
        }
	

	@FXML
    private void onEnregistrer()  {
		String stringHtml = htmledit.getHtmlText();
		stripHTMLTags(stringHtml);
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(null);
		 FileChooser.ExtensionFilter extFiltertTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		 FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
         fileChooser.getExtensionFilters().addAll(extFiltertTXT, extFilterHTML);
		if (file != null) {
           
            try {
                // if file doesn't exist, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
               
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(stringHtml);
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // open a file dialog box
            file = fileChooser.showSaveDialog(null);
            
           
            if (file != null) {
            	
                Stage stage = (Stage) htmledit.getScene().getWindow();
                stage.setTitle(file.getName() + " - Swiftpad");
                try {
                    // if file doesn't exists, then create it
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(stringHtml);
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
	private void stripHTMLTags(String stringHtml) {
		

	        Pattern pattern = Pattern.compile("<[^>]*>");
	        Matcher matcher = pattern.matcher(stringHtml);
	        final StringBuffer sb = new StringBuffer(stringHtml.length());
	        while(matcher.find()) {
	            matcher.appendReplacement(sb, " ");
	        }
	        matcher.appendTail(sb);
	        System.out.println(sb.toString().trim());

	    }

	
		
	






	@FXML
    private void onQuitter() {
        System.exit(0);
    }

    @FXML
    private void onAide() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Aide");
        alert.setContentText("SwiftPad : Rapide pour prendre des notes");
        alert.show();
    }

@FXML
private void onImage()  {
	
	FileChooser openfileChooser = new FileChooser();
	
	myImageView = new ImageView(); 
	
    //Set extension filter
    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
    openfileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
      
    //Show open file dialog
    File file = openfileChooser.showOpenDialog(null);
    if ( file != null){
             try {
        	BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        
            myImageView.setImage(image);
            ImageIO.write(bufferedImage, "jpg", file);
           

         
            htmledit.setHtmlText("<img src=' " + file.toURI() +  "'/>");
           
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
			
		   }
    }


@FXML
 

public void nouveau() throws IOException{
	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pad.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle(" SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    	} catch(Exception e){
    		System.out.println("Impossible d'ouvrir nouveau document");
    	}
    }


@FXML
public void SqlEditor()throws IOException{
	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SqlEditor.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle("Base de données - SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    	} catch(Exception e){
    		System.out.println("");
    	}
    }
	

@FXML
public void fermerfenetre(){
	System.exit(0);
}
@FXML
public void valid2(){
	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fulldatabase.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle(" SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    	} catch(Exception e){
    		System.out.println("");
    	}
    }

@FXML
public void retoursurPAD(){

    }
	

@FXML
public void valid1(){
}
}
	  



	
