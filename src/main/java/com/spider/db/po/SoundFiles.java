package com.spider.db.po;

import java.util.Date;

public class SoundFiles {
    private Long id;

    private Long citySoundId;

    private String title;

    private String description;

    private String bigImgPath;

    private String smaillImgPath;

    private String audioPath;

    private String ower;

    private Date created;

    private String imgFilePath;

    private String audioFilePath;

    private String downlodeFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCitySoundId() {
        return citySoundId;
    }

    public void setCitySoundId(Long citySoundId) {
        this.citySoundId = citySoundId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBigImgPath() {
        return bigImgPath;
    }

    public void setBigImgPath(String bigImgPath) {
        this.bigImgPath = bigImgPath == null ? null : bigImgPath.trim();
    }

    public String getSmaillImgPath() {
        return smaillImgPath;
    }

    public void setSmaillImgPath(String smaillImgPath) {
        this.smaillImgPath = smaillImgPath == null ? null : smaillImgPath.trim();
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath == null ? null : audioPath.trim();
    }

    public String getOwer() {
        return ower;
    }

    public void setOwer(String ower) {
        this.ower = ower == null ? null : ower.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath == null ? null : imgFilePath.trim();
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath == null ? null : audioFilePath.trim();
    }

    public String getDownlodeFlag() {
        return downlodeFlag;
    }

    public void setDownlodeFlag(String downlodeFlag) {
        this.downlodeFlag = downlodeFlag == null ? null : downlodeFlag.trim();
    }
}