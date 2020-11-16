package io;

import io.interfaces.CustomWriter;

public class CustomWriterImpl implements CustomWriter {


    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeln(String message) {
        System.out.println(message);
    }

    @Override
    public void writelnInRed(String message) {
        System.err.println(message);
    }

    @Override
    public void newLine() {
        System.out.println();
    }
}
