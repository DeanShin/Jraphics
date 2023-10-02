package org.deanshin.jraphics;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        Window window = new Window(Size.pixel(500), Size.pixel(500))
                .children(List.of(
                        AbsoluteBox.builder()
                                .x(Size.pixel(100))
                                .y(Size.pixel(100))
                                .box(box -> box.dimensions(dim -> dim.width(Size.pixel(100)).height(Size.pixel(100)))
                                        .color(Color.BLACK)
                                )
                                .border(border -> border.all(side -> side.size(Size.pixel(10)).color(Color.WHITE)))
                                .build(),
                        AbsoluteBox.builder()
                                .box(box -> box.dimensions(dim -> dim.width(Size.pixel(100)).height(Size.pixel(100))))
                                .x(Size.pixel(200))
                                .y(Size.pixel(200))
                                .border(border -> border.all(side -> side.size(Size.pixel(10)).color(Color.BLACK)))
                                .build()
                ));
        application.addWindow(window);
    }
}