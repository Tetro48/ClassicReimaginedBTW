package net.tetro48.classicaddon;

import net.minecraft.src.MathHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConfigPropertyShell<T> {
	public static final String STRING_TYPE_LONG = "typeLong";
	public static final String STRING_TYPE_INT = "typeInt";
	public static final String STRING_TYPE_DOUBLE = "typeDouble";
	public static final String STRING_TYPE_BOOL = "typeBool";
	public static final String STRING_TYPE_STRING = "typeString";
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
		if (internalValue instanceof Long longInt) {
			dataStream.writeUTF(STRING_TYPE_LONG);
			dataStream.writeLong(longInt);
		}
		if (internalValue instanceof Integer integer) {
			dataStream.writeUTF(STRING_TYPE_INT);
			dataStream.writeInt(integer);
		}
		if (internalValue instanceof Double doubleValue) {
			dataStream.writeUTF(STRING_TYPE_DOUBLE);
			dataStream.writeDouble(doubleValue);
		}
		if (internalValue instanceof Boolean bool) {
			dataStream.writeUTF(STRING_TYPE_BOOL);
			dataStream.writeBoolean(bool);
		}
		if (internalValue instanceof String string) {
			dataStream.writeUTF(STRING_TYPE_STRING);
			dataStream.writeUTF(string);
		}
	}

	public static Object readFromDataStream(DataInputStream dataStream) throws IOException {
		String typeString = dataStream.readUTF();
		Object value = null;
		if (typeString.equals(STRING_TYPE_LONG)) {
			value = dataStream.readLong();
		}
		if (typeString.equals(STRING_TYPE_INT)) {
			value = dataStream.readInt();
		}
		if (typeString.equals(STRING_TYPE_DOUBLE)) {
			value = dataStream.readDouble();
		}
		if (typeString.equals(STRING_TYPE_BOOL)) {
			value = dataStream.readBoolean();
		}
		if (typeString.equals(STRING_TYPE_STRING)) {
			value = dataStream.readUTF();
		}
		return value;
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
