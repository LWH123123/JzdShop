package com.jzd.jzshop.entity;

import java.io.File;
import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/7 17:46
 */
public class EventFile {
    private List<FileBean> file;

    public List<FileBean> getFile() {
        return file;
    }

    public void setFile(List<FileBean> file) {
        this.file = file;
    }

    public static class FileBean{

        private String key;
        private File file;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }

}
