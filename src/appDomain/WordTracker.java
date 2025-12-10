package appDomain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import implementations.BSTree;

public class WordTracker {
	public static void main(String[] args) throws ClassNotFoundException {
		File file = new File("res/test1.txt");
		BSTree<String> tree = new BSTree<String>();
		ArrayList<String> treeList = new ArrayList<String>();
		Map<Integer, Integer> lineNumbers = new HashMap<>();
		Map<String, Integer> entries = new HashMap<>();
		Integer lineNumber = 0;
		Boolean repExists = false;
		String option = "po";
		
		try (Scanner lineReader = new Scanner(file)) {
	        while (lineReader.hasNextLine()) {
	            lineReader.nextLine();
	            lineNumber++;
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Scanner fileReader = new Scanner(file)) {
			while (fileReader.hasNextLine()) {
				tree.add(fileReader.next().toLowerCase());
				treeList.add(fileReader.next().toLowerCase());
				for (String word : treeList) {
					entries.put(word, entries.getOrDefault(word, 0) + 1);
				}
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/repository.ser"));
			for(int i = 0; i < tree.size(); i++)
			{
				oos.writeObject(treeList);
			}
			repExists = true;
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (repExists == true) {
			try
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("res/repository.ser"));
				for (String word : treeList) {
					tree.add(word);
				}
				ois.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		Iterator<String> iterator = tree.inorderIterator();
		
		switch(option) {
			case "pf":
				System.out.println("Displaying -pf format");
				while (iterator.hasNext()) {
					System.out.println("Key : ===" + iterator.next().replaceAll("\\p{Punct}", "") + "===" + " found in file: " + file);
				}
				break;
			case "pl":
				System.out.println("Displaying -pl format");
				while(iterator.hasNext()) {
					System.out.println("Key : ===" + iterator.next().replaceAll("\\p{Punct}", "") + "===" + " found in file: " + file + " on lines: " + lineNumbers.get(lineNumber));
				}
				break;
			case "po":
				System.out.println("Displaying -po format");
				while (iterator.hasNext()) {
					System.out.println("Key : ===" + iterator.next().replaceAll("\\p{Punct}", "") + "=== number of entries: " + entries.keySet() + " found in file: " + file + " on lines: " + lineNumber);
				}
				break;
			default:
				System.out.println("Invalid option.");
		}
	}
}
