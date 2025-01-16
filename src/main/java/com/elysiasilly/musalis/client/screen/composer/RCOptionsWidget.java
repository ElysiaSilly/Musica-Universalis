package com.elysiasilly.musalis.client.screen.composer;

import com.elysiasilly.babel.client.screen.BabelWidget;
import com.elysiasilly.musalis.client.screen.DragParentWidget;

import java.util.List;

@SuppressWarnings({"rawtypes"})
public class RCOptionsWidget extends BabelWidget<BabelWidget, ResonanceComposerScreen> {

    public RCOptionsWidget(ResonanceComposerScreen screen) {
        super(null, screen, null);
    }

    @Override
    public List<BabelWidget<?, ?>> initWidgets() {
        DragParentWidget dragger = new DragParentWidget(this, this.screen);
        //dragger.boundStart = new Vec2(3, 3);
        //dragger.boundEnd = new Vec2(91, 8);

        RCOptionButton noneOption = new RCOptionButton(this, this.screen, ResonanceComposerScreen.Options.NONE);
        //noneOption.boundStart = new Vec2(15, 12);
        //noneOption.boundEnd = new Vec2(31, 28);


        RCOptionButton deleteOption = new RCOptionButton(this, this.screen, ResonanceComposerScreen.Options.DELETE);
        //deleteOption.boundStart = new Vec2(38, 12);
        //deleteOption.boundEnd = new Vec2(54, 28);


        RCOptionButton bindOption = new RCOptionButton(this, this.screen, ResonanceComposerScreen.Options.BIND);
        //bindOption.boundStart = new Vec2(61, 12);
        //bindOption.boundEnd = new Vec2(77, 28);

        return List.of(dragger, noneOption, deleteOption, bindOption);
    }

}
