package com.elysiasilly.musalis.client.screen.composer;

import com.elysiasilly.babel.client.screen.BabelWidget;

public class RCLeitmotifCatalogueWidget extends BabelWidget<BabelWidget, ResonanceComposerScreen> {
    public RCLeitmotifCatalogueWidget(ResonanceComposerScreen screen) {
        super(null, screen, null);
    }

    RCLeitmotifSearchBar searchBar;
    RCLeitmotifEntriesWidget leitmotifIndex;

    /*
    @Override
    public List<BabelWidget<?, ?>> initWidgets() {
        DragParentWidget dragger = new DragParentWidget(this, this.screen);
        dragger.boundStart = new Vec2(3, 36);
        dragger.boundEnd = new Vec2(91, 41);

        RCLeitmotifSearchBar searchBar = new RCLeitmotifSearchBar(this, this.screen);
        searchBar.boundStart = new Vec2(7, 45);
        searchBar.boundEnd = new Vec2(87, 61);
        this.searchBar = searchBar;

        RCLeitmotifEntriesWidget leitmotifIndex = new RCLeitmotifEntriesWidget(this, this.screen);
        leitmotifIndex.boundStart = new Vec2(7, 66);
        leitmotifIndex.boundEnd = new Vec2(87, 185);
        this.leitmotifIndex = leitmotifIndex;

        return List.of(dragger, searchBar, leitmotifIndex);
    }

     */


}
