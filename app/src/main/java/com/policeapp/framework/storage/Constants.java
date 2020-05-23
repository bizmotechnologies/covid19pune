package com.policeapp.framework.storage;

import java.util.ArrayList;

/**
 * Created by Bizmo Technologies
 */
public class Constants {
    public static ArrayList<String> MY_SUBSCRIPTION_LIST;
    public static  String R_TOKEN ="";
    public static final String ACCOLADES_SET = "Accolades_set";
    public static String DENIED = "denied_permission";
    public static final String CAN_CHECK_PERMISSION="policeapp_permisison_check";
    public static final String PUSH_NOTIFICATION_ID ="user_notification_id";

    public static final String ROLE_TYPE="PATIENT";
    public static final int OTP_TIMER=30000;
    public static final String TERMS_N_CONDITIONS="/master-service/patient/terms-conditions.html";
    public static final int OPD_OVERFLOW_LIMIT =90;

    public static final String DOCTOR_ACCOLADES="doctorAccolades";
    public static final String ENTITY_ACCOLADES="entityAccolades";


    private Constants() {
    }


    public static final String ACCESS_TOKEN ="access_sec_token";
    public static final String REFRESH_TOKEN = "policeapp_refresh_token";

    //payload for fragments
    public static final String PAYLOAD="payload";
    public static final String PAYLOADNEW="payloadnew";



    //Generic dialog Messages
    public static final String INTERNAL_SERVER_ERROR="Oops Something went Wrong.Please contact our customer care service for further assistance.";
    public static final String CONDITION_FAILED_ERROR="Request to the server was incorrect.Please contact out customer care service for further assistance.";
    public static final String LOGOUT_FAILED_ERROR="Unable to logout user.Please contact out customer care service for further assistance.";


    //user details
    public static final String USER_ROLE="policeapp_user_role";
    public static final String USER_NAME="policeapp_user_name";
    public static final String MOBILE_NUMBER="mobile_number";
    public static final String SELECTED_LANGUAGE_ID="user_language_id";
    public static final String SELECTED_LANGUAGE_NAME="user_language_name";
    public static final String USER_CITY="user_seleced_city";
    public static final String USER_ID="user_policeapp_id";
    public static final String USER_PROFILE_DETALS = "user_profile_details";
    public static final String IS_LOGIN = "user_is_login";

    public static final String USER_LOCALITY="user_selected_locality";
    //Dialog fragment constants
    public static final String DIALOG_TITLE="dialog_title";
    public static final String DIALOG_CONTENT="dialog_content";
    public static final String DIALOG_ACTION="dialog_action";

    //Location constants
    public static final String SELECTED_LOCATION="policeapp_user_location";

    //find page serier
    public static final String COMING_FROM ="user_coming_from";
    public static final String COMING_FROM_PAYLOAD ="coming_from_payload";
    public static final String BOOKING_ID ="coming_from_payload";
    public static final String PAYLOAD_NAME ="payload_speciality_name";
    public static final String PAYLOAD_ID ="payload_component_id";

    public static final String DEEPLINK_MY_PROFILE="deeplink_to_myprofile";


    //===================================================REDESIGN================================================

    public static final String SELECTEC_CITY="selectedCitybBean";




}
