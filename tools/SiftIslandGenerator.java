import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * Generates a Sift-themed island schematic ("normal_sift") for SuperiorSkyblock2.
 * Format: gzip-compressed NBT compound with xSize/ySize/zSize ints and a "blocks" list,
 * where every block is a compound with blockPosition ("x,y,z"), type (material name) and
 * optional states (string compound). See SuperiorSchematicDeserializer for the reader.
 */
public class SiftIslandGenerator {

    private static final int SIZE = 18;
    private static final int TOP_Y = 6; // grass layer

    private static class Block {
        final int x, y, z;
        final String material;
        final Map<String, String> states;

        Block(int x, int y, int z, String material, Map<String, String> states) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.material = material;
            this.states = states;
        }
    }

    private final List<Block> blocks = new ArrayList<>();
    private final Set<String> occupied = new HashSet<>();

    private void add(int x, int y, int z, String material, Map<String, String> states) {
        if (x < 0 || x >= SIZE || z < 0 || z >= SIZE || y < 0 || y >= 13)
            return;
        if (occupied.add(x + "," + y + "," + z))
            blocks.add(new Block(x, y, z, material, states));
    }

    private boolean inDisk(int x, int z) {
        double dx = x - (SIZE - 1) / 2.0;
        double dz = z - (SIZE - 1) / 2.0;
        return dx * dx + dz * dz <= 8.5 * 8.5;
    }

    private void generate() {
        // Ground: stone base, dirt body, grass surface.
        for (int x = 0; x < SIZE; x++) {
            for (int z = 0; z < SIZE; z++) {
                if (!inDisk(x, z))
                    continue;
                add(x, 0, z, "STONE", null);
                add(x, 1, z, "STONE", null);
                add(x, 2, z, "DIRT", null);
                add(x, 3, z, "DIRT", null);
                add(x, 4, z, "DIRT", null);
                add(x, 5, z, "DIRT", null);
                add(x, TOP_Y, z, "GRASS_BLOCK", null);
            }
        }

        // Two oak trees.
        tree(5, 7, 7);
        tree(12, 11, 7);

        // Amethyst cairns (Sift crystals).
        cairn(3, 3, 2);
        cairn(14, 5, 3);
        cairn(13, 13, 2);

        // Glowstone lanterns at the rim.
        add(2, TOP_Y + 1, 10, "GLOWSTONE", null);
        add(15, TOP_Y + 1, 9, "GLOWSTONE", null);

        // Flowers.
        flower(7, 4, "OXEYE_DAISY");
        flower(10, 13, "ALLIUM");
        flower(4, 12, "PINK_TULIP");
        flower(11, 3, "ALLIUM");
        flower(6, 11, "PINK_TULIP");
        flower(12, 7, "OXEYE_DAISY");
    }

    private void tree(int x, int z, int baseY) {
        for (int y = baseY; y < baseY + 4; y++)
            add(x, y, z, "OAK_LOG", null);

        Map<String, String> leaves = new HashMap<>();
        leaves.put("persistent", "true");

        for (int ly = baseY + 2; ly <= baseY + 3; ly++) {
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (Math.abs(dx) == 2 && Math.abs(dz) == 2)
                        continue;
                    add(x + dx, ly, z + dz, "OAK_LEAVES", leaves);
                }
            }
        }
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (Math.abs(dx) + Math.abs(dz) == 2)
                    continue;
                add(x + dx, baseY + 4, z + dz, "OAK_LEAVES", leaves);
            }
        }
        add(x, baseY + 5, z, "OAK_LEAVES", leaves);
    }

    private void cairn(int x, int z, int height) {
        for (int i = 1; i <= height; i++)
            add(x, TOP_Y + i, z, "AMETHYST_BLOCK", null);
    }

    private void flower(int x, int z, String material) {
        add(x, TOP_Y + 1, z, material, null);
    }

    /** Proper writer: blocks list with full compound payloads. */
    /**
     * SuperiorSkyblock2's NBT layout is non-standard:
     * - the root is a type byte + compound payload (no root name);
     * - inside a compound, every entry is written as: type byte, VALUE, KEY.
     */
    private byte[] buildNbt() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buffer);

        out.writeByte(10);
        writeCompoundBody(out);

        out.close();
        return buffer.toByteArray();
    }

    private void writeCompoundBody(DataOutputStream out) throws IOException {
        out.writeByte(3); // TAG_Int
        out.writeInt(SIZE);
        out.writeUTF("xSize");
        out.writeByte(3);
        out.writeInt(13);
        out.writeUTF("ySize");
        out.writeByte(3);
        out.writeInt(SIZE);
        out.writeUTF("zSize");

        out.writeByte(9); // TAG_List
        out.writeByte(10); // element type: compound
        out.writeInt(blocks.size());
        for (Block block : blocks) {
            out.writeByte(8); // TAG_String
            out.writeUTF(block.x + "," + block.y + "," + block.z);
            out.writeUTF("blockPosition");
            out.writeByte(8);
            out.writeUTF(block.material);
            out.writeUTF("type");
            if (block.states != null && !block.states.isEmpty()) {
                out.writeByte(10);
                for (Map.Entry<String, String> state : block.states.entrySet()) {
                    out.writeByte(8);
                    out.writeUTF(state.getValue());
                    out.writeUTF(state.getKey());
                }
                out.writeByte(0);
                out.writeUTF("states");
            }
            out.writeByte(0);
        }
        out.writeUTF("blocks");

        out.writeByte(0);
    }

    public static void main(String[] args) throws IOException {
        SiftIslandGenerator generator = new SiftIslandGenerator();
        generator.generate();

        File output = new File(args.length > 0 ? args[0] : "normal_sift.schematic");
        //noinspection ResultOfMethodCallIgnored
        output.getParentFile().mkdirs();

        try (OutputStream out = new GZIPOutputStream(Files.newOutputStream(output.toPath()))) {
            out.write(generator.buildNbt());
        }

        System.out.println("Wrote " + output.getPath() + " with " + generator.blocks.size() + " blocks.");
    }

}
