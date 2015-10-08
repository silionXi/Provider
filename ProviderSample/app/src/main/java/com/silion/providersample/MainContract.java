package com.silion.providersample;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by silion on 2015/10/8.
 */
public class MainContract implements BaseColumns {
    public static final String SORT_OERDER = "_ID ASC";

    public interface Tables {
        public static final String USER = "user";
    }

    public interface User {
        public static final Uri URI = Uri.parse("content://com.silion.sampleprovider/user");
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String PHONE = "phone";
        public static final String DIR = "vnd.android.cusor.dir/vnd.com.silion.provider.user";
        public static final String ITEM = "vnd.android.cusor.item/vnd.com.silion.provider.user";
    }
}
