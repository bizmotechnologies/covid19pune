package com.policeapp.framework.Interfaces;

/**
 * Created by Bizmo Technologies
 */
public interface LocationFetchListener {

    /**
     * This method will provide a callback when a location fetch is completed
     * @param isAvailable TRUE - new location fetched ,FALSE - new location failed and not available
     */
    public void updatedLocationAvailable(boolean isAvailable);
}
