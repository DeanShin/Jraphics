package org.deanshin.jraphics;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        Window window = new Window(Size.pixel(500), Size.pixel(500));
        window.setWidth(Size.pixel(1000));
        application.addWindow(window);
        TimeUnit.SECONDS.sleep(1L);
        Window window2 = new Window(Size.pixel(400), Size.pixel(1000));
        application.addWindow(window2);
        TimeUnit.SECONDS.sleep(1L);
        application.removeWindow(window2);
        TimeUnit.SECONDS.sleep(1L);
        application.addWindow(window2);
    }
}