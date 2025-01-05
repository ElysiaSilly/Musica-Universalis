package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.core.Musalis;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class MUAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Musalis.MODID);

    public static final Supplier<AttachmentType<Float>> RIMESTAR_WEIGHT = ATTACHMENTS.register(
            "rimestar_weight", () -> AttachmentType.builder(() -> 0f).serialize(Codec.FLOAT).build()
    );
}
