package com.archtanlabs.root.essentialoils.database.model;

public class SymptomTable {

    public static final String TABLE_NAME = "symptom";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID_OIL = "idOil";

    private int id;
    private String name;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_ID_OIL + " INTEGER"
                    + ")";

}
