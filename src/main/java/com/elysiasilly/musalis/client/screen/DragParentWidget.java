package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DragParentWidget extends BabelWidget {

    public DragParentWidget(BabelWidget parent, BabelScreen screen) {
        super(parent, screen, null);
    }

    @Override
    public void initAfter() {
        //this.draggable = true;
        //this.hoverable = true;
        //this.clickable = true;
        this.bounds.depth = this.parent.bounds.depth + .1f;
    }

    @Override
    public List<BabelWidget> initWidgets() {
        return List.of();
    }

    //@Override
    //public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
    //    if(button == 0) {
    //        this.parent.moveWithChildren(mouseVelocity);
    //    }
    //}
}
