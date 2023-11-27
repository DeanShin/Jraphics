package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.Application;
import org.deanshin.jraphics.datamodel.Window;

import java.util.List;

import static org.deanshin.jraphics.datamodel.Size.pixel;

public class Main {
	public static void main(String[] args) {
		Application application = new Application();
		Window window = new Window(pixel(500), pixel(500))
			.children(List.of(
				new ScreenManager()
			));
		application.addWindow(window);
	}
}
