package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Observable;

/**
 * 
 * CLI- Command Line Interface implemented with while loop that request from the
 * user to enter command the life of the while loop will be up until the user
 * puts "exit" command
 * 
 */
public class Cli extends Observable {
	private BufferedReader in;
	private Writer out;

	public Cli(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;

	}

	private void printCommmandsList() {

		try {

			out.write("\n             ***Commands List*** \n\n");
			out.flush();
			out.write("1.   dir <path> \n");
			out.flush();
			out.write("2.   generate_maze_3d <name> <x> <y> <z> \n");
			out.flush();
			out.write("3.   save_maze <name> <filename> \n");
			out.flush();
			out.write("4.   load_maze <filename> <name> \n");
			out.flush();
			out.write("5.   maze_size <name>\n");
			out.flush();
			out.write("6.   file_size <filename> \n");
			out.flush();
			out.write("7.   solve_maze <name> <algorithm> \n");
			out.flush();
			out.write("8.   display <mazename> \n");
			out.flush();
			out.write("9.   display_solution <name> \n");
			out.flush();
			out.write("10.  display_cross_section_by_x <index> <name> \n");
			out.flush();
			out.write("11.  display_cross_section_by_y <index> <name> \n");
			out.flush();
			out.write("12.  display_cross_section_by_z <index> <name> \n");
			out.flush();
			out.write("13.  exit \n\n");
			out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public void start() {
		Thread cliThread = new Thread(new Runnable() {
			public void run() {
				try {
					printCommmandsList();

					out.write("Enter Command:");
					out.flush();

					String line = in.readLine();

					while (!line.equals(true)) {
						if (line.equals("exit")) {
							setChanged();
							notifyObservers(line);
							break;
						} else {
							setChanged();
							notifyObservers(line);
							printCommmandsList();
							out.write("Enter Command:");
							out.flush();
							line = null;
							line = in.readLine();
						}

					}

					out.write("\n");
					out.flush();
					out.write("* The Program closed Good Bye *");
					out.flush();
					in.close();
					out.close();

				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		cliThread.start();
		try {
			cliThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cliThread.stop();

	}
}
