package com.efnilite.skematic.elements.fawe.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Fawe - Fill faces")
@Description("Fill the faces of a cuboidregion with a pattern")
@Since("2.1")
public class EffCuboidFaces extends Effect {

    private Expression<CuboidRegion> cuboid;
    private Expression<ItemType> blocks;

    static {
        Skript.registerEffect(EffCuboidFaces.class, "fill faces of %fawe cuboidregions% with %itemtypes%",
                "fill %fawe cuboidregions%'[s] faces with %itemtypes%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        blocks = (Expression<ItemType>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        ItemType[] blocks = this.blocks.getArray(e);

        if (blocks == null || cuboid == null) {
            return;
        }

        EditSession session = FaweUtil.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.makeCuboidFaces(cuboid, FaweUtil.parsePattern(blocks));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fill faces of " + cuboid.toString(e, debug) + " with pattern " + blocks.toString(e, debug);
    }
}
