package group6.tcss450.uw.edu.chatapp.utils;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Class to encapsulate credentials fields. Building an Object requires a email and password.
 *
 * Optional fields include username, first and last name.
 *
 *
 * @author Charles Bryan
 * @version 1 October 2018
 */
public class Credentials extends AppCompatActivity implements Serializable {
    private static final long serialVersionUID = -1634677417576883013L;


    private final String mUsername;
    private final String mPassword;

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mFirebaseToken;

    private int mID;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {

        private final String mPassword;
        private final String mEmail;

        private String mFirstName = "";
        private String mLastName = "";
        private String mUsername = "";
        private int mID = -1;
        private String mFirebaseToken = "";


        /**
         * Constructs a new Builder.
         *
         * No validation is performed. Ensure that the argument is a
         * valid email before adding here if you wish to perform validation.
         *
         * @param email the email
         * @param password the password
         */
        public Builder(String email, String password) {
            mEmail = email;
            mPassword = password;
        }


        /**
         * Add an optional first name.
         * @param val an optional first name
         * @return
         */
        public Builder addFirstName(final String val) {
            mFirstName = val;
            return this;
        }

        public Builder addFirebaseToken(final String val){
            mFirebaseToken = val;
            return this;
        }

        /**
         * Add an optional last name.
         * @param val an optional last name
         * @return
         */
        public Builder addLastName(final String val) {
            mLastName = val;
            return this;
        }

        /**
         * Add an optional Uuername.
         * @param val an optional Uuername
         * @return
         */
        public Builder addUsername(final String val) {
            mUsername = val;
            return this;
        }

        public Builder addID(final int id){
            mID = id;
            return this;
        }

        public Credentials build() {

            return new Credentials(this);
        }
    }

    /**
     * Construct a Credentials internally from a builder.
     *
     * @param builder the builder used to construct this object
     */
    private Credentials(final Builder builder) {

        mUsername = builder.mUsername;
        mPassword = builder.mPassword;
        mFirstName = builder.mFirstName;
        mLastName = builder.mLastName;
        mEmail = builder.mEmail;
        mID = builder.mID;
        mFirebaseToken = builder.mFirebaseToken;

    }

    public void addToken(String val){
        this.mFirebaseToken = val;
    }

    /**
     * Get the Username.
     * @return the username
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Get the password.
     * @return the password
     */
    public String getPassword() {
        return mPassword;
    }

    /**
     * Get the first name or the empty string if no first name was provided.
     * @return the first name or the empty string if no first name was provided.
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Get the last name or the empty string if no first name was provided.
     * @return the last name or the empty string if no first name was provided.
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     * Get the email or the empty string if no first name was provided.
     * @return the email or the empty string if no first name was provided.
     */
    public String getEmail() {
        return mEmail;
    }

    public int getID(){return mID;}

    public String getToken() { return mFirebaseToken; }

    /**
     * Get all of the fields in a single JSON object. Note, if no values were provided for the
     * optional fields via the Builder, the JSON object will include the empty string for those
     * fields.
     *
     * Keys: username, password, first, last, email
     *
     * @return all of the fields in a single JSON object
     */
    public JSONObject asJSONObject() {
        //build the JSONObject
        JSONObject msg = new JSONObject();
        try {
            msg.put("username", getUsername());
            msg.put("password", getPassword());
            msg.put("first", getFirstName());
            msg.put("last", getLastName());
            msg.put("email", getEmail());
            msg.put("memberid", getID());
            msg.put("token", getToken());
        } catch (JSONException e) {
            Log.wtf("CREDENTIALS", "Error creating JSON: " + e.getMessage());
        }
        return msg;
    }

}
