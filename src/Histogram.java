import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Histogram {
	
	public static String readFile(String file) throws Exception {		
		String data = "";		
		//read in file as string
		data = new String(Files.readAllBytes(Paths.get(file)));
		return data;
	}
	
	public static Map<String, Integer> processData (String data) {
		Map<String, Integer> map = new HashMap<>();
		//set to lowercase and split words by whitespace
		String s[] = data.toLowerCase().split("\\s");
		for(int i = 0; i < s.length; i++) {
			//remove all non alphanumeric chars
			s[i] = s[i].replaceAll("[^a-z0-9]", "");
			if(map.containsKey(s[i])){ //if it exists in map
				map.put(s[i], map.get(s[i]) + 1); //increment the count
			} else { //if it doesnt exist
				map.put(s[i], 1); //put it in map and set count to 1
			}			
		}
		return map;
	}
	
	public static void print(Map<String, Integer> map) {
		int longest = 0;
		//change system output to output.txt
		try {
			File output = new File("output.txt");
			if (output.exists() == false){
				output.createNewFile();
			}
			System.setOut(new PrintStream(output));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//find longest word so all pipes are in same position
		for (String key : map.keySet()){
			if (longest < key.length()){
				longest = key.length();				
			}
		}
		final int longestFinal = longest;
		//use stream to print data
		map.entrySet().stream()
			.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
			.forEach((entry) -> {
				for (int j = entry.getKey().length(); j < longestFinal; j++ ){
					System.out.print(" ");
				}
				System.out.print(entry.getKey() + " | ");
				for (int k = 0; k < entry.getValue(); k++){
					System.out.print("=");
				};
				System.out.println(" (" + entry.getValue() + ")");
			});		
	}

	public static void main(String[] args) throws Exception {
		//read the data
		String data = readFile("input.txt");
		//process the data
		Map<String, Integer> map = processData(data);
		//output the desired data
		print(map);		
	}

}
