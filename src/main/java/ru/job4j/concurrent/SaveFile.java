package ru.job4j.concurrent;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

final public class SaveFile {

    private final String src;
    private final File destFile;

    public SaveFile(String src, File destFile) {
        this.src = src;
        this.destFile = destFile;
    }

    public void saveContent() throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destFile))) {
            for (int i = 0; i < src.length(); i++) {
                out.write(src.charAt(i));
            }
        }
    }
}
