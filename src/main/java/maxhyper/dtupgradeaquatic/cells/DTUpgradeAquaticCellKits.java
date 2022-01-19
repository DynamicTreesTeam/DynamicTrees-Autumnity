package maxhyper.dtupgradeaquatic.cells;

import com.ferreusveritas.dynamictrees.api.cells.Cell;
import com.ferreusveritas.dynamictrees.api.cells.CellKit;
import com.ferreusveritas.dynamictrees.api.cells.CellNull;
import com.ferreusveritas.dynamictrees.api.cells.CellSolver;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.cells.*;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import maxhyper.dtupgradeaquatic.DynamicTreesUpgradeAquatic;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class DTUpgradeAquaticCellKits {

    public static void register(final Registry<CellKit> registry) {
        registry.registerAll(RIVER);
    }

    public static final CellKit RIVER = new CellKit(new ResourceLocation(DynamicTreesUpgradeAquatic.MOD_ID, "river")) {

        private final Cell riverBranch = new Cell(){
            @Override
            public int getValue() { return 3; }

            final int[] map = {3, 5, 3, 3, 3, 3};

            @Override
            public int getValueFromSide(Direction side) {
                return map[side.ordinal()];
            }
        };

        private final Cell[] riverLeafCells = {
                CellNull.NULL_CELL,
                new RiverLeafCell(1),
                new RiverLeafCell(2),
                new RiverLeafCell(3),
                new RiverLeafCell(4),
                new RiverLeafCell(5),
                new RiverLeafCell(6),
                new RiverLeafCell(7)
        };

        private final CellKits.BasicSolver riverSolver =
                new CellKits.BasicSolver(new short[]{0x0514, 0x0411, 0x0312, 0x0221});

        @Override
        public Cell getCellForLeaves(int hydro) {
            return riverLeafCells[hydro];
        }

        @Override
        public Cell getCellForBranch(int radius, int meta) {
            if (radius == 1) {
                return riverBranch;
            } else {
                return CellNull.NULL_CELL;
            }
        }

        @Override
        public SimpleVoxmap getLeafCluster() {
            return RIVER_LEAF_CLUSTER;
        }

        @Override
        public CellSolver getCellSolver() {
            return riverSolver;
        }

        @Override
        public int getDefaultHydration() {
            return 4;
        }

    };

    public static final SimpleVoxmap RIVER_LEAF_CLUSTER = new SimpleVoxmap(3, 3, 3, new byte[]{
            //Layer 0(Bottom)
            1, 2, 1,
            2, 0, 2,
            1, 2, 1,
            //Layer 1 (Mid)
            0, 1, 0,
            1, 4, 1,
            0, 1, 0,
            //Layer 2 (Top)
            0, 0, 0,
            0, 1, 0,
            0, 0, 0,
    }).setCenter(new BlockPos(1, 0, 1));

}
