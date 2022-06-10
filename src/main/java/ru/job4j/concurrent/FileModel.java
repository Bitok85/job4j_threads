package ru.job4j.concurrent;

import java.io.File;

final public class FileModel {

    private final File file;

    public FileModel(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }
}
