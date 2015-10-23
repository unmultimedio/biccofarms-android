package com.biccofarms.basededatos;

import android.provider.BaseColumns;

/**
 * Created by julian on 10/19/15.
 */
public class DBContract {
    public static final class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DRINK = "favorite_drink";
        public static final String COLUMN_NAME_SPORT = "favorite_sport";
    }
}
