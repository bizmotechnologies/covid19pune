package com.policeapp.framework.Interfaces;

/**
 * Created by Bizmo Technologies
 */
public interface NavigationDrawerListner {

    /**
     * This method will take care of the click on the group items in the menu
     * @param key key to map the group items
     */
    public void onGroupClicked(String key);

    /**
     * This method will take care of the click on the child items in the menu
     * @param key key to map the child items
     */
    public void onChildClicked(String key, String parentKey);

    /**
     * This method will handle the click on the non menu items like logout,profile etc
     * @param key key value set for the non menu items
     */
    public void onNonMenuItemClick(String key);
}
