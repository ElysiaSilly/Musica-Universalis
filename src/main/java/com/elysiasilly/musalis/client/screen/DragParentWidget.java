package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.babel.client.gui.WidgetBounds;
import com.elysiasilly.babel.client.gui.widget.IClickListenerWidget;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DragParentWidget extends BabelWidget implements IClickListenerWidget {

    public DragParentWidget(BabelWidget parent, BabelScreen screen) {
        super(parent, screen, new WidgetBounds(0, 0, 8, 4));
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

    @Override
    public void onClick(int button) {

    }
}
