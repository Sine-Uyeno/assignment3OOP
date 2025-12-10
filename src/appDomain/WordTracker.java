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
import implementations.BSTree;
import implementations.BSTreeNode;

public class WordTracker {
	public static void main(String[] args) {
		File fileName = new File("res/test1.txt");
		BSTree<String> tree = new BSTree<String>();
		BSTreeNode<String> treeNode = new BSTreeNode<String>();
		Integer lineNumber = 0;
		Boolean repExists = false;
		Scanner scanner = new Scanner(System.in);
		String option = scanner.nextLine();
		scanner.close();
		
		try (Scanner fileReader = new Scanner(fileName)) {
			while (fileReader.hasNextLine()) {
//				if (fileReader.next().contains(",") || fileReader.next().contains(".") || fileReader.next().contains("!")) {
//					tree.add(fileReader.next());
//				}
				tree.add(fileReader.next().toLowerCase());
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/repository.ser"));
			for(int i = 0; i < tree.size(); i++)
			{
				oos.writeObject(tree);
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
				tree = (BSTree<String>) ois.readObject();
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
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		Iterator<String> iterator = tree.inorderIterator();
		
		switch(option) {
			case "pf":
				System.out.println("Displaying -pf format");
				while (iterator.hasNext()) {
					System.out.println("Key : ===" + iterator.next() + "===" + " found in file: " + fileName);
				}
				break;
			case "pl":
				System.out.println("Displaying -pl format");
				for (int i = 0; i < tree.size(); i++) {
					System.out.println("Key : ===" + treeNode.getElement() + "===" + " found in file: " + fileName + " on lines: " + lineNumber);
				}
				break;
			case "po":
				System.out.println("Displaying -po format");
				System.out.println("Key : ===" + tree.inorderIterator() + "=== number of entries: " + " found in file: " + fileName + "on lines: " + lineNumber);
				break;
			default:
				System.out.println("Invalid option.");
		}
	}
}
