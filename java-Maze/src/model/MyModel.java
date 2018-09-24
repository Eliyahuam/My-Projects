package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithm.mazeGenerators.Maze3d;
import algorithm.mazeGenerators.MyMaze3dGenerator;
import algorithms.demo.MazeAdapter;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.RBFS;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel extends Observable implements Model {
	/**
	 * class with all the tasks
	 */
	private String message;
	private ConcurrentHashMap<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private ConcurrentHashMap<String, Solution> mazesol = new ConcurrentHashMap<String, Solution>();
	ExecutorService executor = Executors.newFixedThreadPool(1);
	

	public MyModel() {
		unDoGzip();
	}

	/**
	 * function to display all files within the folder (in the path)
	 */
	@Override
	public void dir(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {

			message = file.getName() + "\n";
			setChanged();
			notifyObservers("display_message");

		}

	}

	public void Gzip() {
		try {
			FileOutputStream file = new FileOutputStream("resources/mazes.db");
			ObjectOutputStream oUot = new ObjectOutputStream(new GZIPOutputStream(file));
			oUot.writeObject(mazes);
			oUot.writeObject(mazesol);
			oUot.flush();
			oUot.close();
			file.flush();
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void unDoGzip() {
		try {
			ObjectInputStream oIn = new ObjectInputStream(new GZIPInputStream(new FileInputStream("resources/mazes.db")));
			mazes = (ConcurrentHashMap<String, Maze3d>) oIn.readObject();
			mazesol = (ConcurrentHashMap<String, Solution>) oIn.readObject();
		} catch (FileNotFoundException e) {

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		}
	}

	/**
	 * function that generate the maze per user request
	 * 
	 * @param name
	 *            the name of the maze
	 * @param layers
	 *            the dim of the maze
	 * @param rows
	 *            the number of rows of the maze
	 * @param cols
	 *            the number of the columns of the maze
	 */
	@Override
	public void generate_3d_maze(String name, final int layers, final int rows, final int cols) {

		if (mazes.containsKey(name)) {

			message = "The Maze " + name + " Is Already Exists";
			return;
		}

		Future<Maze3d> future =executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				MyMaze3dGenerator mg = new MyMaze3dGenerator();
				Maze3d maze = mg.generate(layers, rows, cols);
				//mazes.put(name, maze);
				
			

				return maze;
			}
		});
		try {
			
			mazes.put(name,future.get());
			message = name + " Has Been Created";
			setChanged();
			notifyObservers("display_message");
			
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * function that display the maze
	 * 
	 * @param name
	 *            the name of the maze
	 */
	@Override
	public void display(String name) {

		Maze3d maze = mazes.get(name);
		message = maze.toString();
		setChanged();
		notifyObservers("display_message");
	}

	/**
	 * function to save the maze in file after compress
	 */
	@Override
	public void saveMaze(String name, String filename) {
		Maze3d maze = null;
		if (mazes.containsKey(name)) {
			maze = mazes.get(name);
		} else {

			message = "The Maze Not exists \n";
			setChanged();
			notifyObservers("display_message");
		}
		OutputStream out = null;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.write(maze.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		message = "The Maze " + name + " Saved To File Name:" + filename + "\n";
		setChanged();
		notifyObservers("display_message");
	}

	/**
	 * function to load the maze from file and decompress
	 */
	@Override
	public void loadMaze(String filename, String newname) {
		InputStream in = null;
		File f = new File(filename);
		if (!f.exists() || f.isDirectory()) {
			message = "File Is Not Exists, Please Choose Other File Name \n";
			setChanged();
			notifyObservers("display_message");
		}
		Path path = Paths.get(filename);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			in = new MyDecompressorInputStream(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int counter = 3;// the number of power
		int size = 1;

		for (int i = 0; i < data.length; i += 2/* moving on the even cell num */) {
			// if counter get to 0 so the power is end
			if (counter <= 0) {
				break;
			} else {

				for (int j = 1; j <= data[i
						+ 1]; j++/* moving on the uneven cell number */) {
					size *= data[i];
					counter--;
				}
			}
		}

		byte b[] = new byte[size + 9];

		try {
			in.read(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Maze3d loaded = new Maze3d(b);
		if (!mazes.containsKey(newname)) {
			mazes.put(newname, loaded);
		} else {
			message = "The Maze Name Alreay Exists ,Please Choose Other Name \n";
			setChanged();
			notifyObservers("display_message");
			return;
		}
		message = "Maze Has Been Loaded \n";
		setChanged();
		notifyObservers("display_message");
	}

	/**
	 * function that return the maze size in the memory
	 */
	@Override
	public void mazeSize(String name) {
		if (!mazes.containsKey(name)) {
			message = "the name of the maze is not exists \n";
			setChanged();
			notifyObservers("display_message");
		}
		int size = ((mazes.get(name).getCols() * mazes.get(name).getLayers() * mazes.get(name).getRows()) + 9) * 4;
		message = "The Size Of The Maze Is: " + size + " Bytes" + "\n";
		setChanged();
		notifyObservers("display_message");
	}

	/**
	 * function that return the size file that the maze saved to
	 */
	@Override
	public void fileSize(String filename) {

		File file = new File(filename);
		double size = 0;
		if (file.exists()) {
			size = file.length();
		} else {
			message = "The File Not Exists Enter Other File Name \n";
			setChanged();
			notifyObservers("display_message");
		}
		message = "The Size Of The File Is: " + size + " Bytes" + "\n";
		setChanged();
		notifyObservers("display_message");
	}

	/**
	 * function to solve the maze
	 */
	@Override
	public void solveMaze(final String mazename, final String algoname) {

		if (!mazes.containsKey(mazename)) {
			message = "Cant To Solve The Maze, The maze not exists \n";
			setChanged();
			notifyObservers("display_message");
			
			return;
		}
		if (mazesol.containsKey(mazename)) {
			message = "Solution already exists, The Solution is:";
			setChanged();
			notifyObservers("display_message");
			message = mazesol.get(mazename).toString();
			setChanged();
			notifyObservers("display_message");
			
			return;
		}
		
		Future <Solution> futuresol=executor.submit(new Callable<Solution>() {
			
			@Override
			public Solution call() throws Exception {
				Solution sol;
				if (algoname.equals("bfs") || algoname.equals("BFS")) {
					MazeAdapter ma = new MazeAdapter(mazes.get(mazename));
					Searcher searchbfs = new BFS();
					 sol = searchbfs.search(ma);

				} else if (algoname.equals("dfs") || algoname.equals("DFS")) {

					MazeAdapter ma = new MazeAdapter(mazes.get(mazename));
					Searcher searchdfs = new DFS();
					sol = searchdfs.search(ma);

				} else if (algoname.equals("rbfs") || algoname.equals("RBFS")) {

					MazeAdapter ma = new MazeAdapter(mazes.get(mazename));
					Searcher searchrbfs = new RBFS();
					sol = searchrbfs.search(ma);
					
					
				} else {
					message = "The Name Of The Algo Is Not Right \n";
					setChanged();
					notifyObservers("display_message");
					return null;
				}

				return sol;
			}
			
		});
		try {
			mazesol.put(mazename, futuresol.get());
			message = "Solution for " + mazename + " ready \n";
			setChanged();
			notifyObservers("display_message");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * function to return the solution of the maze
	 */
	@Override
	public void mazeSol(String mazename) {
		if (mazesol.containsKey(mazename)) {
		message = mazesol.get(mazename).toString();
		setChanged();
		notifyObservers("display_message");
		} else {
			message = "maze sol not exists please solve maze before";
			setChanged();
			notifyObservers("display_message");
		}
	}

	/**
	 * function to return maze 2 dim per cross
	 */
	@Override
	public void crossSectionX(int x, String name, char by) {

		if (!mazes.containsKey(name)) {
			message = "The Name Of The Maze Not Exists Try Other Name \n";
			setChanged();
			notifyObservers("display_message");
		} else {
			Maze3d maze = mazes.get(name);

			int[][] maze2d = null;
			if (by == 'x') {
				if (x < 0 | x > maze.getLayers() - 1) {
					message = "wrong input \n";
					setChanged();
					notifyObservers("display_message");
					return;
				}
				maze2d = maze.getCrossSectionByX(x);
			} else if (by == 'y') {
				if (x < 0 | x > maze.getRows() - 1) {
					message = "wrong input \n";
					setChanged();
					notifyObservers("display_message");
					return;
				}
				maze2d = maze.getCrossSectionByY(x);
			} else if (by == 'z') {
				if (x < 0 | x > maze.getCols() - 1) {
					message = "wrong input \n";
					setChanged();
					notifyObservers("display_message");
					return;
				}
				maze2d = maze.getCrossSectionByZ(x);
			}

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < maze.getRows(); i++) {
				for (int j = 0; j < maze.getCols(); j++) {
					sb.append(maze2d[j][i] + " ");
				}
				sb.append("\n");
			}
			sb.append("\n\n");
			message = sb.toString();
			setChanged();
			notifyObservers("display_message");
		}

	}

	/**
	 * function that close all threads
	 */
	@Override
	public void exit() {
		Gzip();
		executor.shutdown();
		message = "all Threads Killed";
		setChanged();
		notifyObservers("display_message");
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String[] getMazes() {
		String[] mazeNameList;
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, Maze3d> entry : mazes.entrySet()) {
			list.add(entry.getKey());

		}

		mazeNameList = new String[list.size()];
		for (int i = 0; i < mazeNameList.length; i++) {
			mazeNameList[i] = list.get(i);
		}
		return mazeNameList;
	}

	@Override
	public Maze3d mazeToLoad(String name) {
		
		return mazes.get(name);
	}

	@Override
	public Solution sol2Gui(String mazeName) {
		
		return mazesol.get(mazeName);
	}

	@Override
	public void deleteMazeAndSolution(String mazeName) {
		if (mazes.containsKey(mazeName)) {
			mazes.remove(mazeName);
			if (mazesol.containsKey(mazeName)) {
				mazesol.remove(mazeName);
			}
		}
		message=mazeName + " Has Been Removed";
		setChanged();
		notifyObservers("display_message");
		
	}
	

}
