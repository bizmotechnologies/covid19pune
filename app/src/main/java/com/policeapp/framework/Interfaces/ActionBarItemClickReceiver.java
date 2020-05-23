package com.policeapp.framework.Interfaces;

import android.view.View;

/**
 * Created by Bizmo Technologies
 * This interface is used by the child fragments that wish to receive event
 * when the item on the actionbar is clicked.
 */
public interface ActionBarItemClickReceiver {


    /**
     * This method will be called by th activity and the framework when the
     * actionbar items are clicked.
     * @param view the view that was clicked
     *             @return True - if the event is consumed,False - if the event is not required and not consumed.
     */
    public boolean onMenuItemClick(View view);
}
