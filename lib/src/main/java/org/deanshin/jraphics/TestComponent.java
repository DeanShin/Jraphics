package org.deanshin.jraphics;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

public class TestComponent extends Component<TestComponent.Properties, TestComponent.State> {

	protected TestComponent(Properties properties) {
		super(properties);
	}

	@Override
	protected void initializeState() {
		this.state = new State();
	}

	@Override
	public List<Element> getChildren() {
		return List.of(
			RelativeBox.builder()
				.box(box -> box.dimensions(d -> d.width(Size.pixel(100)).height(Size.pixel(100)))
					.color(Color.BLACK))
				.children(
					RelativeBox.builder()
						.box(box -> box.dimensions(d -> d.width(Size.pixel(80)).height(Size.pixel(80)))
							.color(Color.WHITE))
						.margin(margin -> margin.all(Size.pixel(10)))
						.child(Text.builder()
							.text(this.properties.text)
							.color(Color.BLACK)
							.build())
						.build()
				)
				.border(border -> border.all(builder -> builder.size(Size.pixel(1)).color(Color.WHITE)))
				.build()
		);
	}

	public record Properties(String text) {

	}

	public record State() {

	}
}
