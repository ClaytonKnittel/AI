package neuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author claytonknittel
 * 
 * Stores the neural network in a .txt file in this format:
 * 
 * Neural Network
 * 
 *
 */
public class FileManager {
	
	private String location;
	private File file;
	private FileReader reader;
	private FileWriter writer;
	private String[] raw;
	private int[] layerSize;
	private Layer[] data;
	
	public FileManager(String location) {
		this.location = location;
	}
	
	public String location() {
		return this.location;
	}
	
	public void open() {
		file = new File(location);
		if (!file.exists()) {
			try { file.createNewFile(); } catch (IOException e) {}
		}
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {}
		raw = read();
		extractData();
		try { reader.close(); } catch (IOException e) {}
	}
	
	public void save(Layer[] data) {
		file = new File(location);
		if (file.exists())
			file.delete();
		try {
			file.createNewFile();
			writer = new FileWriter(file);
			writer.write(getSaveData(data));
			writer.close();
		} catch (IOException e) { System.err.println("Couldn't save"); }
	}
	
	private String getSaveData(Layer[] data) {
		String ret = "";
		for (int x = 0; x < data.length; x++)
			ret += data[x].size() + "\n";
		ret += "\n";
		for (int x = 1; x < data.length; x++) {
			for (int j = 0; j < data[x].size(); j++) {
				ret += data[x].bias(j) + "\n";
				for (int i = 0; i < data[x - 1].size(); i++)
					ret += data[x].weight(j, i) + "\n";
				ret += "\n";
			}
		}
		return ret;
	}
	
	public Layer[] getData() {
		return data;
	}
	
	private void extractData() {
		layerSize = new int[0];
		int x;
		for (x = 0; raw[x] != ""; x++)
			layerSize = append(layerSize, Integer.parseInt(raw[x]));
		
		data = new Layer[layerSize.length];
		data[0] = new Layer(layerSize[0], 0);
		float[][] temp;
		int y = x + 1;
		for (int layer = 1; layer < layerSize.length; layer++) {
			temp = new float[layerSize[layer]][0];
			for (int j = 0; j < temp.length; j++) {
				while (y < raw.length) {
					if (raw[y] == "")
						break;
					temp[j] = append(temp[j], Float.parseFloat(raw[y]));
					y ++;
				}
				while (y < raw.length) {
					if (raw[y] != "")
						break;
					y ++;
				}
			}
			data[layer] = new Layer(temp);
		}
	}
	
	private float[] append(float[] x, float a) {
		float[] ret = new float[x.length + 1];
		for (int w = 0; w < x.length; w++)
			ret[w] = x[w];
		ret[x.length] = a;
		return ret;
	}
	
	private int[] append(int[] x, int a) {
		int[] ret = new int[x.length + 1];
		for (int w = 0; w < x.length; w++)
			ret[w] = x[w];
		ret[x.length] = a;
		return ret;
	}
	
	private String[] read() {
		String[] ret = new String[1];
		ret[0] = "";
		int c = 0;
		do {
			try {
				c = reader.read();
			} catch (IOException e) { continue; }
			
			switch (c) {
			case -1:
				break;
			case 10:
				ret = append(ret);
				break;
			default:
				ret[ret.length - 1] += Character.toString((char) c);
			}
		} while (c != -1);
		return ret;
	}
	
	private String[] append(String[] s) {
		String[] ret = new String[s.length + 1];
		for (int x = 0; x < s.length; x++)
			ret[x] = s[x];
		ret[s.length] = "";
		return ret;
	}
}
