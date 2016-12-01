package br.com.sailboat.medzy.view.adapter.recycler_item;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.medzy.view.adapter.ViewType;

public class SubheaderRecyclerItem implements RecyclerItem {

    @Override
    public int getViewType() {
        return ViewType.SUBHEADER;
    }

}
