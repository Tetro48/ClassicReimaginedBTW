package net.tetro48.classicaddon;

import java.util.function.Consumer;

public class SynchronizedConfigProperty {
	private String externalValue;
	private String internalValue;
	public String propertyName;
	public Consumer<String> callback;
	public SynchronizedConfigProperty(String propertyName, String defaultValue, Consumer<String> callback) {
		this.externalValue = defaultValue;
		this.internalValue = defaultValue;
		this.propertyName = propertyName;
		this.callback = callback;
	}
	public String getExternalValue() {
		return this.externalValue;
	}
	public String getInternalValue() {
		return this.internalValue;
	}
	public void setInternalValue(String newValue) {
		this.internalValue = newValue;
	}
	public void setExternalValue(String newValue) {
		this.externalValue = newValue;
		this.callback.accept(newValue);
	}
	public void resetExternalValue() {
		setExternalValue(this.internalValue);
	}
	public String getPropertyName() {
		return this.propertyName;
	}
}
