package com.our_music.connection;

import android.util.Log;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jared on 11/22/2014.
 *
 * Interface for parsing JSON data
 */
public interface ParseInterface {

    public final String TAG = ParseInterface.class.getSimpleName();

    /**
     * Defined types, subjects, and queries that can be sent in JSONObject to remote server
     */
    public enum MessageType {
        REQUEST, RESPONSE, INIT, LOGIN, QUERY, DB_UPDATE, ADD_FRIEND, ADD_SONG, NEW_USER,
        GET_SONGS, GET_FRIENDS, TOP_TEN, TOP_THREE_FRIENDS, CUSTOM_QUERY;

        private static final Map<String, MessageType> enumMap;
        static {
            Map<String, MessageType> map = new HashMap<String, MessageType>();
            for(MessageType el: MessageType.values()) {
                map.put(el.toString(), el);
            }
            enumMap = Collections.unmodifiableMap(map);
        }

        public static MessageType getType(String type){
            MessageType typeOfMessage = null;
            try{
                typeOfMessage = enumMap.get(type);
            }
            catch (Exception e) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }
            return typeOfMessage;
        }
    }

    public JSONObject getRequest(String...params);
}
