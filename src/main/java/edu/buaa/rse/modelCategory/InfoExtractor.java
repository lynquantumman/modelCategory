package edu.buaa.rse.modelCategory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
/**
*@author Li Yaonan
*@date 2017/10/10
*@description This program is to select info from given systemModel file describing 
*the components and the connection component
*/
public class InfoExtractor{
    String modelFile;
    String destinationDirectory;
    
//    software and hardware are independent files
//    srcFile should be changed
    public InfoExtractor(String modelFile, String destinationDirectory){
    	this.modelFile = modelFile;
        this.destinationDirectory = destinationDirectory;
    }
// <componentInstance name="politpole1" category="device">
//   </componentInstance>
    public void infoExtract(){
        /**phase 1: to extract the component info from srcFile
        *    use SAXReader to extract info
        *  phase 2: to write the info into the desinationDirectory via SAXReader
        */
		String outputDirectory = this.destinationDirectory;
		File hardwareFile = new File(this.modelFile);
		FileInputStream hardwareStream = null;
		File componentFile = new File(outputDirectory+"/componentCategory.info");
		FileOutputStream componentOs = null;
		JSONObject jsnob = null;
		try{
			componentOs = new FileOutputStream(componentFile);
			PrintWriter componentPw = new PrintWriter(componentOs);
			
			hardwareStream = new FileInputStream(hardwareFile);
			//========================================hardware println
			SAXReader hardwareReader = new SAXReader();
			Document hardwareDoc = hardwareReader.read(hardwareStream);
			org.dom4j.Element hardRootElement = hardwareDoc.getRootElement();
			List<org.dom4j.Element> hardElementsLikeComponents = hardRootElement.elements();
			for (org.dom4j.Element itemLikeComponents : hardElementsLikeComponents) {
				if ("components".equals(itemLikeComponents.getName()) ) {
					List<org.dom4j.Element> elementsLikeComponent = itemLikeComponents.elements();
					for (org.dom4j.Element itemLikeComponent : elementsLikeComponent) {
						String cpntName = itemLikeComponent.attribute("name").getValue();
						String cpntCategory = itemLikeComponent.attribute("category").getValue();
						
						jsnob = new JSONObject();
						jsnob.put(cpntName, cpntCategory);
//						componentPw.println(cpntName + ":" + cpntName);
					}
				}
			}
			componentPw.println(jsnob.toString());
			componentOs.flush();
			componentPw.flush();

			
		}catch(IOException ioe){
			ioe.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			if(hardwareStream !=null && componentOs!=null){
				try {
					hardwareStream.close();
					componentOs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
    	

   
    
}