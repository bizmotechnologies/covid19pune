package com.policeapp.framework.network.Interface;

/**
 * Created by Bizmo Technologies on 22/09/16.
 */
public interface ErrorConstants {

    //Error from the server with custom content
    public static final String CUSTOM_ERROR="412";
    public static final String CUSTOM_ERROR_409="409";
    //service down time issue
    public static final String INTERNAL_SERVER_ERROR="500";
    //access token not valid
    public static final String INVALID_ACCESS_TOKEN_ERROR="401";
    //header content type issue
    public static final String UNSUPPORTED_CONTENT_TYPE="415";
    //security error due to authorisation error
    public static final String UNAUTHORISED_REQUEST="403";
    //security error due to authorisation error
    public static final String NOT_FOUND="404";
    //security error due to Unavailable token
    public static final String TOKEN_UNAVAILABLE_ERROR="406";


}
