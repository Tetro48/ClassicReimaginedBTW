package net.tetro48.classicaddon;

import api.config.AddonConfig;
import com.google.common.collect.Lists;
import net.minecraft.src.MathHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ModifiableConfigProperty<T> {
	private final AddonConfig addonConfig;
	private T externalValue;
	private T internalValue;
	private T min;
	private T max;
	public String propertyName;
	public List<String> comments;
	protected boolean sync;
	public Consumer<T> callback;

	public ModifiableConfigProperty(AddonConfig config, String propertyName, T defaultValue, Consumer<T> callback) {
		this(config, propertyName, defaultValue, false, callback);
	}

	public ModifiableConfigProperty(AddonConfig config, String propertyName, T defaultValue, boolean sync, Consumer<T> callback, String... comments) {
		this.addonConfig = config;
		this.externalValue = defaultValue;
		this.internalValue = defaultValue;
		this.propertyName = propertyName;
		this.sync = sync;
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
	public T getInternalValue() {
		return this.internalValue;
	}
	public void setInternalValue(T newValue) {
		this.internalValue = newValue;
		if (min != null && max != null) {
			if (newValue instanceof Double doubleValue) {
				newValue = doubleValue < (double)min ? min : doubleValue > (double)max ? max : newValue;
			}
			if (newValue instanceof Long longInt) {
				newValue = longInt < (long)min ? min : longInt > (long)max ? max : newValue;
			}
			if (newValue instanceof Integer integer) {
				newValue = (T)(Integer)MathHelper.clamp_int(integer, (int)min, (int)max);
			}
		}
		this.setAddonConfigValue(newValue);
	}

	public void parseSetInternalValue(String str) {
		if (this.internalValue instanceof Long) {
			setInternalValue((T) (Long) Long.parseLong(str));
		}
		if (this.internalValue instanceof Integer) {
			this.setInternalValue((T) (Integer) Integer.parseInt(str));
		}
		if (this.internalValue instanceof Double) {
			this.setInternalValue((T) (Double) Double.parseDouble(str));
		}
		if (this.internalValue instanceof Boolean) {
			this.setInternalValue((T) (Boolean) Boolean.parseBoolean(str));
		}
		if (this.internalValue instanceof String) {
			this.setInternalValue((T) str);
		}
	}

	public void setInternalValueToAddonConfig() {
		this.internalValue = getAddonConfigValue();
	}

	public void writeToDataStream(DataOutputStream dataStream) throws IOException {

		if (internalValue instanceof Long longInt) {
			dataStream.writeLong(longInt);
		}
		if (internalValue instanceof Integer integer) {
			dataStream.writeLong(integer);
		}
		if (internalValue instanceof Double doubleValue) {
			dataStream.writeDouble(doubleValue);
		}
		if (internalValue instanceof Boolean bool) {
			dataStream.writeBoolean(bool);
		}
		if (internalValue instanceof String string) {
			dataStream.writeUTF(string);
		}
	}

	public T readFromDataStream(DataInputStream dataStream) throws IOException {
		T value = null;
		if (internalValue instanceof Long) {
			value = (T)(Object) dataStream.readLong();
		}
		if (internalValue instanceof Integer) {
			value = (T)(Object) dataStream.readInt();
		}
		if (internalValue instanceof Double) {
			value = (T)(Object) dataStream.readDouble();
		}
		if (internalValue instanceof Boolean) {
			value = (T)(Object) dataStream.readBoolean();
		}
		if (internalValue instanceof String) {
			value = (T) dataStream.readUTF();
		}
		return value;
	}

	public void setExternalValueFromDataStream(DataInputStream dataStream) {
		try {
			this.externalValue = readFromDataStream(dataStream);
		}
		catch (Exception e) {

		}
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
	public boolean canSync() {
		return sync;
	}
	public void resetExternalValue() {
		setExternalValue(this.internalValue);
	}
	public String getPropertyName() {
		return this.propertyName;
	}
}
