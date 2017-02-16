package serialization;

import static serialization.SerializationUtils.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SrDatabase extends SrBase {

    public static final byte[] HEADER = "GCDB".getBytes();
    public static final short VERSION = 0x0100;
    public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
    private short objectCount;
    public List<SrObject> objects = new ArrayList<SrObject>();

    private SrDatabase() {
    }

    public SrDatabase(String name) {
        setName(name);

        size += HEADER.length + 2 + 1 + 2;
    }

    public void addObject(SrObject object) {
        objects.add(object);
        size += object.getSize();

        objectCount = (short) objects.size();
    }

    public int getSize() {
        return size;
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, HEADER);
        pointer = writeBytes(dest, pointer, VERSION);
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);

        pointer = writeBytes(dest, pointer, objectCount);
        for (SrObject object : objects) {
            pointer = object.getBytes(dest, pointer);
        }

        return pointer;
    }

    public static SrDatabase Deserialize(byte[] data) {
        int pointer = 0;
        assert (readString(data, pointer, HEADER.length).equals(HEADER));
        pointer += HEADER.length;

        if (readShort(data, pointer) != VERSION) {
            System.err.println("Invalid GCDB version!");
            return null;
        }
        pointer += 2;

        byte containerType = readByte(data, pointer++);
        assert (containerType == CONTAINER_TYPE);

        SrDatabase result = new SrDatabase();
        result.nameLength = readShort(data, pointer);
        pointer += 2;
        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += 4;

        result.objectCount = readShort(data, pointer);
        pointer += 2;

        for (int i = 0; i < result.objectCount; i++) {
            SrObject object = SrObject.Deserialize(data, pointer);
            result.objects.add(object);
            pointer += object.getSize();
        }

        return result;
    }

    public SrObject findObject(String name) {
        for (SrObject object : objects) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        return null;
    }

    /**
     * Still in development
     * @param name Name of the object with which it is stored
     * @return
     */
    public SrObject getObject(String name) {
        for (SrObject object : objects) {
            if (object.getName().equals(name)) {
                objects.remove(object);
                return object;
            }
        }
        return null;
    }

    public static SrDatabase DeserializeFromFile(String path) {
        byte[] buffer = null;
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Deserialize(buffer);
    }

    public void serializeToFile(String path) {
        byte[] data = new byte[getSize()];
        getBytes(data, 0);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void dump() {
        System.out.println("-------------------Database-------------------");
        System.out.println("Name: " + this.getName());
        System.out.println("Size: " + this.getSize());
        System.out.println("Object Count: " + this.objects.size());
        for (SrObject obj : this.objects) {
            System.out.println("  -------------------Objects-------------------");
            System.out.println("  Name: " + obj.getName());
            System.out.println("  Size: " + obj.getSize());
            System.out.println("  Field Count: " + obj.fields.size());
            for (SrField field : obj.fields) {
                System.out.println("    -------------------Fields-------------------");
                System.out.println("    Name: " + field.getName());
                String data = "";
                switch (field.type) {
                    case Type.BOOLEAN:
                        data += field.getBoolean();
                        break;
                    case Type.BYTE:
                        data += field.getByte();
                        break;
                    case Type.CHAR:
                        data += field.getChar();
                        break;
                    case Type.DOUBLE:
                        data += field.getDouble();
                        break;
                    case Type.FLOAT:
                        data += field.getFloat();
                        break;
                    case Type.INTEGER:
                        data += field.getInt();
                        break;
                    case Type.LONG:
                        data += field.getLong();
                        break;
                    case Type.SHORT:
                        data += field.getShort();
                        break;
                }
                System.out.println("    Value: " + data);
            }
        }
    }

    public void dumpPartial() {
        System.out.println("-------------------Database-------------------");
        System.out.println("Name: " + this.getName());
        System.out.println("Object Count: " + this.objects.size());
        for (SrObject obj : this.objects) {
            System.out.println("  -------------------Objects-------------------");
            System.out.println("  Name: " + obj.getName());
            System.out.println("  Field Count: " + obj.fields.size());
        }
    }
    
    public void addTimeStamp(){
        SrObject obj = new SrObject("Time");
        long timeStamp = System.currentTimeMillis();
        obj.addField(SrField.Long("TimeStamp", timeStamp));
        this.addObject(obj);
    }
    
    public boolean validatePacket(long time){
        SrObject timeObj = this.findObject("Time");
        long packetTime = timeObj.findField("TimeStamp").getLong();
        long currTime = System.currentTimeMillis();
        if(currTime - packetTime > time){
            //Packet too late, invalidated
            return true;
        }
        return false;
    }
}
