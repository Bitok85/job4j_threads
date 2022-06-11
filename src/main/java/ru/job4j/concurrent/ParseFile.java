package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

final public class ParseFile {

    private static final char UNICODE = 0x80;
    private static final char ASCII_CHARS = 127;

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

    public String getPureContent() throws IOException {
        return getContent(ch -> ch <= ASCII_CHARS);
    }

    public String getAllExceptUnicode() throws IOException {
        return getContent(ch -> ch < UNICODE);
    }

}
