package io;

import io.interfaces.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomReader implements Reader {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public  String read() throws IOException {
        return reader.readLine();
    }
}
