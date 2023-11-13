package org.deanshin.jraphics;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

public class TestParentComponent extends Component<TestParentComponent.Properties, TestParentComponent.State> {
	public TestParentComponent(Properties properties, String key) {
		super(properties, key);
	}

	@Override
	protected TestParentComponent.State initializeState() {
		return new State();
	}

	@Override
	public List<Element> getChildren() {
		return List.of(
			RelativeBox.builder()
				.box(builder -> builder.dimensions(dim -> dim.width(Size.pixel(250)).height(Size.pixel(250)))
					.color(Color.BLACK))
				.padding(padding -> padding.all(Size.pixel(25)))
				.children(
					new TestComponent(new TestComponent.Properties(this.properties.text + " 1"), "comp1"),
					new TestComponent(new TestComponent.Properties(this.properties.text + " 2"), "comp2")
				)
				.build()
		);
	}

	public record Properties(String text) {

	}

	public record State() {

	}
}
