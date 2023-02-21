package com.archtanlabs.root.essentialoils.database.model;

public class OilTable {

        public static final String TABLE_NAME = "oil";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";

        private int id;
        private String name;

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + COLUMN_NAME + " TEXT  "
                        + ")";

}
