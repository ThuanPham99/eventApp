package com.assignment.event.entity;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadForm {
    private String decrip;
    private MultipartFile[] fileDatas;

    public MyUploadForm() {
    }

    public String getDecrip() {
        return this.decrip;
    }

    public void setDecrip(String decrip) {
        this.decrip = decrip;
    }

    public MultipartFile[] getFileDatas() {
        return this.fileDatas;
    }

    public void setFileDatas(MultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }
}
