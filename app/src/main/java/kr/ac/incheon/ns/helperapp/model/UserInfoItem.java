package kr.ac.incheon.ns.helperapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subin on 2017-03-29.
 */

public class UserInfoItem {

    @SerializedName("return_code") private String return_code;
    @SerializedName("name") private String name;
    @SerializedName("id") private String id;
    @SerializedName("pw") private String pw;
    @SerializedName("uid") private String uid;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
