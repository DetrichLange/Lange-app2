package baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;

public class MyFileWriter {

    MyFileWriter(){

    }

    public void writeInventoryToFile(String filePath, Inventory saveData) throws IOException {
        //Taking a file path string and an Inventory object as arguments, this method opens a formatter for the file
        //at the specified path.

        Path importPath = Paths.get(filePath);

        try(Formatter ignored = new Formatter(filePath)) {
            //If the path ends in .html, call writeHTMLFile and return its result
            if(filePath.endsWith(".html")){
                writeHTMLFile(importPath, saveData);
            }
            //Else if the path ends in .json, call writeJSONFile and return its result
            else if(filePath.endsWith(".json")){
                writeJSONFile(importPath, saveData);
            }
            //Else if the path ends in .txt, call writeTSVFile and return its result
            else if(filePath.endsWith(".txt")){
                writeTSVFile(importPath, saveData);
            }
        }
        //If the path doesn't end in any of those extensions, or the file can't be found, return false
    }

    private void writeHTMLFile(Path targetFile, Inventory saveData) throws FileNotFoundException {
        StringBuilder outputHTML = new StringBuilder();

        outputHTML.append("""
                <!DOCTYPE html>
                <html>
                <body>
                <style>
                html {
                  font-family: sans-serif;
                }
                                
                table {
                  border-collapse: collapse;
                  border: 2px solid rgb(200,200,200);
                  letter-spacing: 1px;
                  font-size: 0.8rem;
                }
                                
                td, th {
                  border: 1px solid rgb(190,190,190);
                  padding: 10px 20px;
                }
                                
                th {
                  background-color: rgb(235,235,235);
                }
                                
                td {
                  text-align: center;
                }
                                
                tr:nth-child(even) td {
                  background-color: rgb(250,250,250);
                }
                                
                tr:nth-child(odd) td {
                  background-color: rgb(245,245,245);
                }
                                
                caption {
                  padding: 10px;
                }
                </style>
                                
                 <table>
                  <tr>
                    <th>Serial Number</th>
                    <th>Name</th>
                    <th>Value</th>
                  </tr>
                  """);

        for(int i=0;i<saveData.getLength();i++){
            outputHTML.append("  <tr>%n    <td>").append(saveData.getEntry(i).getItemSerialNumber()).append("</td>%n");
            outputHTML.append("    <td>").append(saveData.getEntry(i).getItemName()).append("</td>%n");
            outputHTML.append("    <td>").append(saveData.getEntry(i).getItemValue()).append("</td>%n  </tr>%n");
        }

        outputHTML.append("""
                </table>\040
                                
                </body>
                </html>""");

        try(Formatter output = new Formatter(targetFile.toString())){
            output.format(outputHTML.toString());
        }

        //Create a stringbuilder outputHTML
        //Add the initial HTML tags and style data to outputHTML
        //Add the table header to outputHTML
        //For each item in the Inventory:
        //Add the serial number, name, and value, separated by table formatting tags, to outputHTML
        //Add the HTML closing tags to outputHTML
        //Write outputHTML to targetFile
    }

    private void writeJSONFile(Path targetFile, Inventory saveData) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(gson.toJson(saveData));
        String prettyJsonString = gson.toJson(je);

        try(Formatter output = new Formatter(targetFile.toString())){
            output.format(prettyJsonString);
        }


        //Create a Gson from a GsonBuilder with PrettyPrinting on
        //Create a formatter
        //Print the Gson.toJson(saveData) to the targetFile
    }

    private void writeTSVFile(Path targetFile, Inventory saveData) throws IOException {
        //Open a formatter for the file
        try(Formatter output = new Formatter(targetFile.toString())){
            //Write the header line
            output.format("Serial Number\tName\tValue%n");
            //For each item in the inventory, write a single line to the file
            for(int i=0;i<saveData.getLength();i++){
                //Separate each attribute with a tab
                output.format("%s\t%s\t%s%n", saveData.getEntry(i).getItemSerialNumber(),
                        saveData.getEntry(i).getItemName(), saveData.getEntry(i).getItemValue());
            }
        }
    }

}