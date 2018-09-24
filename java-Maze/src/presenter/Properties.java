package presenter;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class Properties {
	private  int numOfCols ;
	private  int numOfRows ;
	private   int numOfFloors ;
	private   int numOfThreads ;
	private   String AlgorithmForMazeSolution ;

	
	public Properties(int numOfCols, int numOfRows, int numOfFloors, int numOfThreads,
			String algorithmForMazeSolution) {
		super();
		this.numOfCols = numOfCols;
		this.numOfRows = numOfRows;
		this.numOfFloors = numOfFloors;
		this.numOfThreads = numOfThreads;
		
		this.AlgorithmForMazeSolution = algorithmForMazeSolution;
	

	}
	public Properties () {
		
	}
	public void writeToXML() {
		try {
			
			XMLOutputFactory xmlOutput=XMLOutputFactory.newFactory();
			XMLStreamWriter streamWriter=xmlOutput.createXMLStreamWriter(new FileOutputStream("resources/properties.xml"));
			
			streamWriter.writeStartDocument("1.0");
			streamWriter.writeStartElement("MazeLength");
			streamWriter.writeAttribute("floor",Integer.toString(getNumOfFloors()));
			streamWriter.writeAttribute("rows",Integer.toString(getNumOfRows()));
			streamWriter.writeAttribute("cols",Integer.toString(getNumOfCols()));
			streamWriter.writeAttribute("algorithm",getAlgorithmForMazeSolution());
			streamWriter.writeEndElement();
			streamWriter.writeEndDocument();
			streamWriter.flush();
			streamWriter.close();
			
			
			
			
		} catch (Exception e) { System.out.println("problem");}
	}
	public void readFromXml() {
		try {
			
			XMLInputFactory inputStream=XMLInputFactory.newFactory();
			XMLStreamReader	streamReader=inputStream.createXMLStreamReader(new FileInputStream("properties.xml"));
			
			while(streamReader.hasNext()) {
			int typeEvent=streamReader.getEventType();
			switch (typeEvent)  {
			case XMLStreamConstants.START_ELEMENT:
			
			
			numOfFloors=Integer.parseInt(streamReader.getAttributeValue(0)); //floors
			numOfRows=Integer.parseInt(streamReader.getAttributeValue(1)); //rows
			numOfCols=Integer.parseInt(streamReader.getAttributeValue(2)); //cols
			AlgorithmForMazeSolution=streamReader.getAttributeValue(3); //algo
			break;
			}
			streamReader.next();
			}
			streamReader.close();
			
			
		} catch (Exception e) {
		}
	}

	public int getNumOfCols() {
		return numOfCols;
	}


	public int getNumOfRows() {
		return numOfRows;
	}


	public int getNumOfFloors() {
		return numOfFloors;
	}


	public int getNumOfThreads() {
		return numOfThreads;
	}



	public String getAlgorithmForMazeSolution() {
		return AlgorithmForMazeSolution;
	}


	
}