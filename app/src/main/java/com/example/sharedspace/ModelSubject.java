package com.example.sharedspace;

public class ModelSubject {

    public static final int infoSys = 1;
    public static final int compStruct = 2;
    public static final int algo = 4;

    public int type;
    public int data;
    public String text, code, studying;

    public ModelSubject(int type, String text, int data, String code, String studying) {
        this.type = type;
        this.data = data;
        this.text = text;
        this.code = code;
        this.studying = studying;
    }
}
