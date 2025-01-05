package com.elysiasilly.musalis.common.world.rimestar;

import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.util.MathUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RimestarCommand {

    public static void create(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {

        LiteralCommandNode<CommandSourceStack> source = dispatcher.register(Commands.literal(Musalis.SHORTID)
                        .then(Commands.literal("rimestar").requires(permission -> permission.hasPermission(2))
                                .then(Commands.literal("create")
                                        .executes(css -> {
                                            createRimestar(css.getSource().getLevel(), css.getSource().getPosition());
                                            return 2;
                                        }))
                                .then(Commands.literal("kill")
                                        .executes(css -> {
                                            kill(css.getSource().getLevel());
                                            return 2;
                                        }))
                                .then(Commands.literal("get"))
                                .then(Commands.literal("destroy"))
                                .then(Commands.literal("rollback"))
                                .then(Commands.literal("impact"))
                        )
        );

        dispatcher.register(Commands.literal(Musalis.SHORTID).redirect(source));
    }

    public static void createRimestar(ServerLevel serverLevel, Vec3 pos) {
        RimestarManager manager = (RimestarManager) new RimestarManager().get(serverLevel);
        manager.add(new RimestarObject(pos, serverLevel.getRandom()));
    }

    public static void kill(ServerLevel serverLevel) {
        RimestarManager manager = (RimestarManager) new RimestarManager().get(serverLevel);
        manager.interactables.clear();
        manager.setDirty();
    }

    public static UUID get(ServerLevel serverLevel, Vec3 pos) {
        RimestarManager manager = (RimestarManager) new RimestarManager().get(serverLevel);
        List<RimestarObject> rimestars = manager.interactables;

        List<Vec3> list = new ArrayList<>();
        for(RimestarObject star : rimestars) list.add(star.position);

        int index = MathUtil.vectors.closest(pos, list);

        return rimestars.get(index).ID;
    }
}
