package com.qaelum.dms.ui.model;

/**
 * Created by Einhart on 2/1/2018.
 * © QAELUM NV
 */
public class DmsFile {

    public enum FileType {
        FILE, FOLDER, UNKNOWN, ROOT;

        public static FileType lookup(String type) {
            try {
                return FileType.valueOf(type);
            } catch (IllegalArgumentException ex) {
                return FileType.UNKNOWN;
            }
        }
    }
}
