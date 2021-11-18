package baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MyFileReader {

    MyFileReader(){

    }

    public Inventory readInventoryFromFile(String filePath) throws IOException {
        //Taking a path string as an argument, attempts to open a file.

        Path importPath = Paths.get(filePath);
        if(Files.exists(importPath)){
            //If the file path ends in .html, call parseHTMLFile
            if(filePath.endsWith(".html")){
                return parseHTMLFile(importPath);
            }
            //Else if the file path ends in .json, call parseJSONFile
            else if(filePath.endsWith(".json")){
                return parseJSONFile(importPath);
            }
            //Else if the file path ends in .txt, call parseTSVFile
            else if(filePath.endsWith(".txt")){
                return parseTSVFile(importPath);
            }
            else{
                //If the file has a bad extension, return null.
                return null;
            }
        }else{
            //If you somehow manage to try to read a file that doesn't exist, return null.
            return null;
        }
    }

    private Inventory parseTSVFile(Path importFile){
        //Make a new Inventory
        Inventory importInv = new Inventory();

        String nextSerial;
        String nextName;
        String nextValue;

        try(Scanner fileInput = new Scanner(importFile)){
            //Add the tab delimiter
            fileInput.useDelimiter("\\t|\r\n");
            //Skip the first line of the file
            fileInput.nextLine();

            //While the import file has a next line:
            while(fileInput.hasNext()){
                //Read until encountering a tab, saving the result as a serial number
                nextSerial = fileInput.next();
                //Read until encountering a tab, saving the result as a name
                nextName = fileInput.next();
                //Read until end of line, saving the result as a price
                nextValue = fileInput.next();

                //Add a new InventoryItem to the Inventory using those three parameters, repeat loop
                importInv.addEntry(new InventoryItem(nextSerial, nextName, nextValue));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //When the importFile has no more lines, return the Inventory
        return importInv;
    }

    private Inventory parseHTMLFile(Path importFile) throws IOException {
        //Make a new Inventory
        Inventory importInv = new Inventory();

        String nextSerial;
        String nextName;
        String nextValue;

        try(Scanner fileInput = new Scanner(importFile)){

            while(true) {
                String currentLine = fileInput.nextLine();

                if (currentLine.equals("    <th>Value</th>")) {

                    while (true) {
                        fileInput.nextLine();

                        currentLine = fileInput.nextLine();
                        if (currentLine.equals("</table> ")) {
                            break;
                        }

                        currentLine = fileInput.nextLine();
                        nextSerial = currentLine.replace("<td>", "").replace("</td>", "")
                                .replaceAll("^\s*", "");
                        currentLine = fileInput.nextLine();
                        nextName = currentLine.replace("<td>", "").replace("</td>", "")
                                .replaceAll("^\s*", "");
                        currentLine = fileInput.nextLine();
                        nextValue = currentLine.replace("<td>", "").replace("</td>", "")
                                .replaceAll("^\s*", "");

                        importInv.addEntry(new InventoryItem(nextSerial, nextName, nextValue));
                    }
                    break;
                }
            }

        }

        //Read lines from the importFile until you get the line "<th>Value</th>"
        //Skip one line
        //If the next line is not "</table>":
        //Read the next line as a string, remove the <td> and </td> tags, save the result as a serial number
        //Read the next line as a string, remove the <td> and </td> tags, save the result as a name
        //Read the next line as a string, remove the <td> and </td> tags, save the result as a value
        //Add a new InventoryItem to the Inventory using those three parameters
        //Skip one line, repeat loop
        //Else, return the inventory

        return importInv;
    }

    private Inventory parseJSONFile(Path importFile) throws IOException {
        //Make a Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Make a BufferedReader
        BufferedReader bufferedReader = new BufferedReader(
                new java.io.FileReader("data/test.json"));

        //Make an Inventory from Gson.fromJson(BufferedReader, Inventory.class)
        Inventory importInv = gson.fromJson(bufferedReader, Inventory.class);
        bufferedReader.close();

        //Return the Inventory
        return importInv;
    }
}