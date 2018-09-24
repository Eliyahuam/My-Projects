package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void write(int arg0) throws IOException {		
		out.write(arg0);
	}

	@Override
	public void write(byte[] b) throws IOException {
		byte lastByte = b[0];
		int count = 1;
		
		for (int i = 1; i < b.length; i++) {
			if (b[i] == lastByte) {
				count++;
			} 
			else {
				while (count > 255) {
					out.write(lastByte);
					out.write(255);
					count -= 255;
				}				
				 
				out.write(lastByte); 
				out.write(count);
				
				lastByte = b[i];
				count = 1;
			}
		}		
		
		// write the last byte
		out.write(lastByte);
		out.write(count);
	}	

}
