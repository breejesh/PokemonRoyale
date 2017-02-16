package serialization;

import static serialization.SerializationUtils.*;

import java.util.ArrayList;
import java.util.List;

public class SrObject extends SrBase {

	public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
	private short fieldCount;
	public List<SrField> fields = new ArrayList<SrField>();
	private short stringCount;
	public List<SrString> strings = new ArrayList<SrString>();
	private short arrayCount;
	public List<SrArray> arrays = new ArrayList<SrArray>();
	
	private SrObject() {
	}
	
	public SrObject(String name) {
		size += 1 + 2 + 2 + 2;
		setName(name);
	}
	
	public void addField(SrField field) {
		fields.add(field);
		size += field.getSize();
		
		fieldCount = (short)fields.size();
	}
	
	public void addString(SrString string) {
		strings.add(string);
		size += string.getSize();
		
		stringCount = (short)strings.size();
	}

	public void addArray(SrArray array) {
		arrays.add(array);
		size += array.getSize();
		
		arrayCount = (short)arrays.size();
	}
	
	public int getSize() {
		return size;
	}
	
	public SrField findField(String name) {
		for (SrField field : fields) {
			if (field.getName().equals(name))
				return field;
		}
		return null;
	}
	
	public SrString findString(String name) {
		for (SrString string : strings) {
			if (string.getName().equals(name))
				return string;
		}
		return null;
	}

	public SrArray findArray(String name) {
		for (SrArray array : arrays) {
			if (array.getName().equals(name))
				return array;
		}
		return null;
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		
		pointer = writeBytes(dest, pointer, fieldCount);
		for (SrField field : fields)
			pointer = field.getBytes(dest, pointer);
		
		pointer = writeBytes(dest, pointer, stringCount);
		for (SrString string : strings)
			pointer = string.getBytes(dest, pointer);
		
		pointer = writeBytes(dest, pointer, arrayCount);
		for (SrArray array : arrays)
			pointer = array.getBytes(dest, pointer);
		
		return pointer;
	}
	
	public static SrObject Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		SrObject result = new SrObject();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
		// Early-out: pointer += result.size - sizeOffset - result.nameLength;
		
		result.fieldCount = readShort(data, pointer);
		pointer += 2;
		
		for (int i = 0; i < result.fieldCount; i++) {
			SrField field = SrField.Deserialize(data, pointer);
			result.fields.add(field);
			pointer += field.getSize();
		}
		
		result.stringCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.stringCount; i++) {
			SrString string = SrString.Deserialize(data, pointer);
			result.strings.add(string);
			pointer += string.getSize();
		}

		result.arrayCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.arrayCount; i++) {
			SrArray array = SrArray.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer += array.getSize();
		}
		
		return result;
	}
	
}
