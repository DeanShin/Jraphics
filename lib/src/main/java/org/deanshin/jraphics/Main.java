package org.deanshin.jraphics;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        Window window = new Window(Size.pixel(500), Size.pixel(500))
                .children(List.of(
                        RelativeBox.builder()
                                .box(box -> box.dimensions(dim -> dim.width(Size.pixel(100)).height(Size.pixel(100))))
                                .border(b -> b.all(a -> a.color(Color.BLACK).size(Size.pixel(5))))
                                .build(),
                        RelativeBox.builder()
                                .box(box -> box.dimensions(dim -> dim.width(Size.pixel(100)).height(Size.pixel(100))))
                                .border(border -> border.all(a -> a.color(Color.BLACK).size(Size.pixel(2))))
                                .margin(m -> m.all(Size.pixel(20)))
                                .build()
                ));
        application.addWindow(window);
    }
}