package com.tsystems.javaschool.tasks;

import java.io.*;
import java.util.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr
 * Date: 15.01.13
 * Time: 22:18
 */
public class DuplicateFinderImpl implements DuplicateFinder {
    @Override
    public boolean process(File sourceFile, File targetFile) {
        try {
            //list for read lines from sourceFile
            List<String> allLines = new LinkedList();
            //map for counting the number of occurs of unique item
            Map<String, Integer> dubCount = new HashMap<String, Integer>();
            //read all lines and write to list
            Scanner scanner = new Scanner(sourceFile);
            while (scanner.hasNextLine()) {
                String acc = scanner.nextLine();
                allLines.add(acc);

            }
            //HashSet automatically delete all puplicats
            HashSet<String> tango = new HashSet(allLines);
            //list for storing items without duplicates
            List noDup = Arrays.asList(tango.toArray());
            //sort this list
            Collections.sort(noDup);
            //write to map items with them count number
            for (String s : allLines) {
                Integer count = dubCount.get(s);
                dubCount.put(s, count == null ? 1 : (1 + count));
            }

            FileWriter fw;
            //if target file not exists create it
            if(!targetFile.exists()) {
            fw = new FileWriter(targetFile.getName());
            }
            //else append to this file
            else fw = new FileWriter(targetFile,true);
            //write to file
            for (Object s : noDup){

                fw.append(s + "[" + dubCount.get(s) + "]\n");
            }

            fw.close();
            //only if no exceptions
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        DuplicateFinder d = new DuplicateFinderImpl();
        System.out.println(d.process(new File("a.txt"), new File("b.txt")));

    }
}
