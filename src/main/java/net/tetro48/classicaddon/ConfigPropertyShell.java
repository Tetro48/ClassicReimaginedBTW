package net.tetro48.classicaddon;

import net.minecraft.src.MathHelper;

import java.io.DataOutputStream;
import java.io.IOException;

public class ConfigPropertyShell<T> {
	public String propertyName;
	protected T internalValue;
	public T min;
	public T max;
	protected boolean sync;

	public ConfigPropertyShell(String propertyName, boolean sync, T defaultValue) {
		this.propertyName = propertyName;
		this.sync = sync;
		this.internalValue = defaultValue;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public T getInternalValue() {
		return this.internalValue;
	}

	public void setInternalValue(T newValue) {
		if (min != null && max != null) {
			if (newValue instanceof Double doubleValue) {
				newValue = doubleValue < (double)min ? min : doubleValue > (double)max ? max : newValue;
			}
			if (newValue instanceof Long longInt) {
				newValue = longInt < (long)min ? min : longInt > (long)max ? max : newValue;
			}
			if (newValue instanceof Integer integer) {
				newValue = (T)(Integer) MathHelper.clamp_int(integer, (int)min, (int)max);
			}
		}
		this.internalValue = newValue;
	}

	public void parseSetInternalValue(String str) {
		if (this.internalValue instanceof Long) {
			this.setInternalValue((T) (Long) Long.parseLong(str));
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

	public void writeToDataStream(DataOutputStream dataStream) throws IOException {
		dataStream.writeUTF(internalValue.toString());
	}

	public ConfigPropertyShell<T> setMinMax(T min, T max) {
		this.min = min;
		this.max = max;
		return this;
	}

	public boolean canSync() {
		return sync;
	}
}
