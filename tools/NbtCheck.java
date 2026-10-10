import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * Minimal reader to verify schematics in SuperiorSkyblock2's non-standard NBT layout:
 * root = type byte + compound payload (no name); compound entries = type byte, VALUE, KEY.
 */
public class NbtCheck {

    private DataInputStream in;

    public static void main(String[] args) throws IOException {
        NbtCheck check = new NbtCheck();
        check.in = new DataInputStream(new GZIPInputStream(new FileInputStream(args[0])));
        int type = check.in.readByte();
        System.out.println("root type = " + type);
        check.compound("");
    }

    private void compound(String path) throws IOException {
        int blocksSeen = 0;
        while (true) {
            int type = in.readByte();
            if (type == 0)
                break;
            if (type == 3) {
                int value = in.readInt();
                String key = readKey();
                if (key.equals("xSize") || key.equals("ySize") || key.equals("zSize"))
                    System.out.println(path + key + " = " + value);
            } else if (type == 9) {
                int elementType = in.readByte();
                int length = in.readInt();
                String key = readKey();
                System.out.println(path + key + ": list of " + length + " (element type " + elementType + ")");
                if (key.equals("blocks")) {
                    for (int i = 0; i < length; i++) {
                        compoundElement("block#" + i + ".");
                        blocksSeen++;
                    }
                    System.out.println("blocks parsed: " + blocksSeen);
                }
            } else {
                throw new IOException("Unexpected tag type " + type + " in " + path);
            }
        }
    }

    private void compoundElement(String path) throws IOException {
        int keys = 0;
        while (true) {
            int type = in.readByte();
            if (type == 0)
                break;
            if (type == 8) {
                String value = in.readUTF();
                String key = readKey();
                if (keys < 5)
                    System.out.println("  (" + path + ") T8 value=(" + value + ") key=(" + key + ")");
                keys++;
            } else if (type == 10) {
                int nested = compoundElementNested(path);
                String key = readKey();
                if (keys < 5)
                    System.out.println("  [" + path + "] T10 K=<" + key + "> (" + nested + " keys)");
                keys++;
            } else {
                throw new IOException("Unexpected tag type " + type + " in " + path);
            }
        }
    }

    private int compoundElementNested(String path) throws IOException {
        int keys = 0;
        while (true) {
            int type = in.readByte();
            if (type == 0)
                break;
            if (type == 8) {
                in.readUTF(); // value
                readKey();
                keys++;
            } else {
                throw new IOException("Unexpected tag type " + type + " in " + path);
            }
        }
        return keys;
    }

    private String readKey() throws IOException {
        int keyLength = in.readShort() & 0xFFFF;
        byte[] keyBytes = new byte[keyLength];
        in.readFully(keyBytes);
        return new String(keyBytes, "UTF-8");
    }

}
