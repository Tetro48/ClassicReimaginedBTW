package net.tetro48.classicaddon;

import api.config.AddonConfig;
import com.google.common.collect.Lists;
import net.minecraft.src.MathHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ModifiableConfigProperty<T> extends ConfigPropertyShell<T> {
	private final AddonConfig addonConfig;
	private T externalValue;
	public List<String> comments;
	public Consumer<T> callback;

	public ModifiableConfigProperty(AddonConfig config, String propertyName, T defaultValue, Consumer<T> callback) {
		this(config, propertyName, defaultValue, false, callback);
	}

	public ModifiableConfigProperty(AddonConfig config, String propertyName, T defaultValue, boolean sync, Consumer<T> callback, String... comments) {
		super(propertyName, sync, defaultValue);
		this.addonConfig = config;
		this.externalValue = defaultValue;
		this.callback = callback;
		this.comments = Lists.newArrayList(comments);
	}

	public ModifiableConfigProperty<T> register() {
		setAddonConfigValue(this.internalValue);
		return this;
	}

	public void setAddonConfigValue(T newValue) {
		if (newValue instanceof Integer integer) {
			if (min != null && max != null) {
				this.addonConfig.registerInt(propertyName, integer, (int)min, (int)max, comments);
			} else {
				this.addonConfig.registerInt(propertyName, integer, comments);
			}
		}
		if (newValue instanceof Long longInt) {
			if (min != null && max != null) {
				this.addonConfig.registerLong(propertyName, longInt, (long)min, (long)max, comments);
			} else {
				this.addonConfig.registerLong(propertyName, longInt, comments);
			}
		}
		if (newValue instanceof Double doubleValue) {
			if (min != null && max != null) {
				this.addonConfig.registerDouble(propertyName, doubleValue, (double)min, (double)max, comments);
			} else {
				this.addonConfig.registerDouble(propertyName, doubleValue, comments);
			}
		}
		if (newValue instanceof String string) {
			this.addonConfig.registerString(propertyName, string, comments);
		}
		if (newValue instanceof Boolean bool) {
			this.addonConfig.registerBoolean(propertyName, bool, comments);
		}
	}

	public T getAddonConfigValue() {
		T value = null;
		if (internalValue instanceof Long) {
			value = (T)(Object) addonConfig.getLong(propertyName);
		}
		if (internalValue instanceof Integer) {
			value = (T)(Object) addonConfig.getInt(propertyName);
		}
		if (internalValue instanceof Double) {
			value = (T)(Object) addonConfig.getDouble(propertyName);
		}
		if (internalValue instanceof Boolean) {
			value = (T)(Object) addonConfig.getBoolean(propertyName);
		}
		if (internalValue instanceof String) {
			value = (T) addonConfig.getString(propertyName);
		}
		return value;
	}

	public T getExternalValue() {
		return this.externalValue;
	}

	public void setInternalValue(T newValue) {
		super.setInternalValue(newValue);
		this.setAddonConfigValue(newValue);
	}

	public void setExternalValueFromDataStream(DataInputStream dataStream) {
		try {
			this.externalValue = (T) readFromDataStream(dataStream);
		}
		catch (Exception e) {

		}
	}

	public void setInternalValueToAddonConfig() {
		this.internalValue = getAddonConfigValue();
	}

	public void setExternalValue(T newValue) {
		this.externalValue = newValue;
		this.callback.accept(newValue);
	}
	public ModifiableConfigProperty<T> setMinMax(T min, T max) {
		this.min = min;
		this.max = max;
		return this;
	}
	public void resetExternalValue() {
		setExternalValue(this.internalValue);
	}
}
