package ru.otus.homework.service;

import java.io.Closeable;

public interface IOService extends Closeable {

    String getStringFromConsole();

    void outputString(String s);

}
