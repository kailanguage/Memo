package com.kailang.memo.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Memo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "headline")
    private String headline;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "create_date")
    private String create_date;

    public Memo(String headline, String content, String create_date,String type) {
        this.headline = headline;
        this.content = content;
        this.create_date = create_date;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
