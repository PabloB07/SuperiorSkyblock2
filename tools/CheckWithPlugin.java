import com.bgsoftware.superiorskyblock.tag.CompoundTag;
import com.bgsoftware.superiorskyblock.tag.ListTag;
import com.bgsoftware.superiorskyblock.tag.Tag;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/** Verifies the generated schematic with the plugin's own NBT tag classes. */
public class CheckWithPlugin {

    public static void main(String[] args) throws IOException {
        try (DataInputStream in = new DataInputStream(
                new GZIPInputStream(new FileInputStream(args[0])))) {
            Tag<?> root = Tag.fromStream(in, 0);
            CompoundTag compound = (CompoundTag) root;
            System.out.println("parsed root OK");
            System.out.println("xSize=" + compound.getInt("xSize").orElse(-1));
            System.out.println("ySize=" + compound.getInt("ySize").orElse(-1));
            System.out.println("zSize=" + compound.getInt("zSize").orElse(-1));
            ListTag blocks = compound.getList("blocks").orElse(null);
            System.out.println("blocks=" + (blocks == null ? "MISSING" : String.valueOf(blocks.size())));
        } catch (Exception error) {
            System.out.println("PARSE FAILED: " + error);
        }
    }

}
