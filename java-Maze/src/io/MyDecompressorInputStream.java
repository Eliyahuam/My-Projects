package io;

import java.io.IOException;
import java.io.InputStream;



public class MyDecompressorInputStream extends InputStream {

	InputStream in;
	
	// constructor
	public MyDecompressorInputStream(InputStream in){
		this.in = in;
	}
	
	
	@Override
	public int read() throws IOException {
	    return	in.read();
	}
	
	
	public int read(byte[] b){
		
	try {
		for (int i=0; i<b.length; i++){
			 
		     b[i]=(byte) in.read();
		     
		     // the next int in the input stream
			 int temp = in.read();
			 // go over the counter 
			 for(int j=1; j<temp; j++){
				 b[i+1]= b[i];
				 i++;
			 }
			 
			}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		
		//fix this
		return 0;
	}

}
