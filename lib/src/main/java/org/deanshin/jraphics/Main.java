package org.deanshin.jraphics;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Application application = new Application();
		Window window = new Window(Size.pixel(500), Size.pixel(500))
			.children(List.of(
				AbsoluteBox.builder()
					.box(box -> box.dimensions(dim -> dim
								.width(Size.percentage(100))
								.height(Size.percentage(100))
							)
							.color(Color.BLACK)
					)
					.build(),
				RelativeBox.builder()
					.box(box -> box.dimensions(dim -> dim
							.width(Size.percentage(100))
							.height(Size.percentage(20))
						)
						.children(RelativeBox.builder()
							.box(b -> b.dimensions(dim -> dim
										.width(Size.max(Size.percentage(50), Size.pixel(300)))
										.height(Size.percentage(50))
									)
									.color(Color.WHITE)
							)
							.build()
						)
						.color(Color.BLACK))
					.border(border -> border.all(b -> b.color(Color.WHITE).size(Size.pixel(1))))
					.margin(margin -> margin.top(Size.pixel(100)))
					.build(),
				RelativeBox.builder()
					.box(box -> box.dimensions(dim -> dim
								.width(Size.percentage(80))
								.height(Size.pixel(100))
							)
							.color(Color.WHITE)
							.child(
								Text.builder()
									.text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
									.color(Color.BLACK)
									.lineHeight(Size.pixel(20))
									.align(Text.Align.LEFT)
									.build()
							)
					)
					.padding(padding -> padding.all(Size.pixel(40)))
					.build()
//				,
//				AbsoluteBox.builder()
//					.box(box -> box.dimensions(dim -> dim.width(Size.pixel(100)).height(Size.pixel(100))))
//					.x(Size.max(Size.percentage(50), Size.min(Size.pixel(250), Size.percentage(60), Size.pixel(200))))
//					.y(Size.percentage(40))
//					.border(border -> border.all(side -> side.size(Size.pixel(10)).color(Color.BLACK)))
//					.build(),
//				RelativeBox.builder()
//					.box(box -> box.dimensions(dim -> dim.width(Size.pixel(400)).height(Size.pixel(100)))
//						.color(Color.BLACK))
//					.margin(margin -> margin.top(Size.pixel(100)).left(Size.pixel(50)))
//					.build()
			));
		application.addWindow(window);
	}
}