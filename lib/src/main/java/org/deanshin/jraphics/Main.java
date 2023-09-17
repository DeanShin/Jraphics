package org.deanshin.jraphics;

import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        Window window = new Window(Size.pixel(500), Size.pixel(500));
        window.setWidth(Size.pixel(1000));
        application.addWindow(window);
        TimeUnit.SECONDS.sleep(1L);
        Window window2 = new Window(Size.pixel(100), Size.pixel(1000));
        application.addWindow(window2);
        TimeUnit.SECONDS.sleep(1L);
        application.removeWindow(window2);
        TimeUnit.SECONDS.sleep(1L);
        application.addWindow(window2);
    }
}