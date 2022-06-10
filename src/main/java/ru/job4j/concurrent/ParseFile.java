package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final FileModel file;
    private final File destFile;

    public ParseFile(FileModel file, File destFile) {
        this.file = file;
        this.destFile = destFile;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        String result;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getFile()))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
            result = output.toString();
        }
        return result;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destFile))) {
            for (int i = 0; i < content.length(); i++) {
                out.write(content.charAt(i));
            }
        }
    }


}
