/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 *
 * @author kenanince
 */
public class RandomHash {

	public static void createHashFile(String inFile) throws FileNotFoundException, IOException, NoSuchAlgorithmException {

		File file = new File(inFile);
		FileReader f = new FileReader(file);

		String originalPath = file.getParent();
		String originalName = file.getName();
		String[] t = originalName.split("\\.");
		String pureName = t[0];

		String hashFile = originalPath + "/" + pureName + "_Hash.csv";
		String hashBinaryFile = originalPath + "/" + pureName + "_hash_binary.csv";

		BufferedReader csvReader = new BufferedReader(new FileReader(inFile));
		String row;
		String in;

		FileWriter csvHashWriter = new FileWriter(hashFile);
		FileWriter csvBinaryHashWriter = new FileWriter(hashBinaryFile);
		int k = 0;
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			in = "";
			for (int i = 0; i < data.length - 1; i++) {
				in += data[i];
			}

			String hash = GFG.toHexString(GFG.getSHA(in));

			String tmp = "";
			String tmpBinary = "";
			for (int i = 0; i < hash.length(); i++) {

				tmp += (int) hash.charAt(i) + ",";
				tmpBinary += RandomHash.hexToBinary(String.valueOf(hash.charAt(i))) + ",";

			}
			tmp += data[data.length - 1] + "\n";
			tmpBinary += data[data.length - 1] + "\n";
			csvHashWriter.append(tmp);
			csvBinaryHashWriter.append(tmpBinary);
			k++;
		}
		csvHashWriter.flush();
		csvHashWriter.close();

		csvBinaryHashWriter.flush();
		csvBinaryHashWriter.close();
	}

	public static String hexToBinary(String hex) {

		// variable to store the converted
		// Binary Sequence
		String binary = "";

		// converting the accepted Hexadecimal
		// string to upper case
		hex = hex.toUpperCase();

		// initializing the HashMap class
		HashMap<Character, String> hashMap = new HashMap<Character, String>();

		// storing the key value pairs
		hashMap.put('0', "0,0,0,0");
		hashMap.put('1', "0,0,0,1");
		hashMap.put('2', "0,0,1,0");
		hashMap.put('3', "0,0,1,1");
		hashMap.put('4', "0,1,0,0");
		hashMap.put('5', "0,1,0,1");
		hashMap.put('6', "0,1,1,0");
		hashMap.put('7', "0,1,1,1");
		hashMap.put('8', "1,0,0,0");
		hashMap.put('9', "1,0,0,1");
		hashMap.put('A', "1,0,1,0");
		hashMap.put('B', "1,0,1,1");
		hashMap.put('C', "1,1,0,0");
		hashMap.put('D', "1,1,0,1");
		hashMap.put('E', "1,1,1,0");
		hashMap.put('F', "1,1,1,1");

		int i;
		char ch;

		// loop to iterate through the length
		// of the Hexadecimal String
		for (i = 0; i < hex.length(); i++) {
			// extracting each character
			ch = hex.charAt(i);

			// checking if the character is
			// present in the keys
			if (hashMap.containsKey(ch)) // adding to the Binary Sequence
			// the corresponding value of
			// the key
			{
				binary += hashMap.get(ch);
			} // returning Invalid Hexadecimal
			// String if the character is
			// not present in the keys
			else {
				binary = "Invalid Hexadecimal String";
				return binary;
			}
		}

		// returning the converted Binary
		return binary;
	}
}
