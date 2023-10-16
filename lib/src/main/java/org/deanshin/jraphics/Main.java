package org.deanshin.jraphics;

import org.deanshin.jraphics.datamodel.*;

import java.awt.Font;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Application application = new Application();
		Window window = new Window(Size.pixel(500), Size.pixel(500))
			.children(List.of(
				AbsoluteBox.builder()
					.x(Size.pixel(100))
					.y(Size.pixel(100))
					.box(box -> box.dimensions(dim -> dim.width(Size.pixel(100)).height(Size.pixel(100)))
						.color(Color.BLACK)
						.children(
							AbsoluteBox.builder()
								.x(Size.pixel(25))
								.y(Size.pixel(25))
								.box(innerBox -> innerBox.dimensions(dim -> dim.width(Size.pixel(50)).height(Size.pixel(50))))
								.build()
						)
					)
					.border(border -> border.all(side -> side.size(Size.pixel(10)).color(Color.WHITE)))
					.build(),
				AbsoluteBox.builder()
					.box(box -> box.dimensions(dim -> dim.width(Size.pixel(100)).height(Size.pixel(100))))
					.x(Size.max(Size.percentage(50), Size.min(Size.pixel(250), Size.percentage(60), Size.pixel(200))))
					.y(Size.percentage(40))
					.border(border -> border.all(side -> side.size(Size.pixel(10)).color(Color.BLACK)))
					.build(),
				RelativeBox.builder()
					.box(box -> box.dimensions(dim -> dim.width(Size.pixel(500)).height(Size.pixel(100)))
						.color(Color.BLACK))
					.build(),
				RelativeBox.builder()
					.padding(padding -> padding.all(Size.pixel(20)))
					.box(box -> box.dimensions(dim -> dim.width(Size.pixel(400)).height(Size.pixel(100)))
						.color(Color.WHITE)
						.child(new Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.", Color.BLACK, new Font("Arial", Font.PLAIN, 12), Size.pixel(12))))
					.build(),
				RelativeBox.builder()
					.box(box -> box.dimensions(dim -> dim.width(Size.pixel(400)).height(Size.pixel(100)))
						.color(Color.BLACK))
					.margin(margin -> margin.top(Size.pixel(100)).left(Size.pixel(50)))
					.build()
			));
		application.addWindow(window);
	}
}