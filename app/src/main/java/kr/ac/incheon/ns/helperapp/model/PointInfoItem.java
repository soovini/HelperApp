package kr.ac.incheon.ns.helperapp.model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by subin on 2017-04-10.
 */

public class PointInfoItem {
    @SerializedName("return_code")
    private String return_code;


    public String getRESULT_CODE() {
        return return_code;
    }

    public void setRESULT_CODE(String rESULT_CODE) {
        return_code = rESULT_CODE;
    }


    @SerializedName("list")
    private List<Point> point = new ArrayList<Point>();

    public List<Point> getPoint() {
        return point;
    }

    public class Point {
        @SerializedName("number")
        String number;
        @SerializedName("id")
        String id;
        @SerializedName("point")
        String point;
        @SerializedName("ins_date")
        String ins_date;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getIns_date() {
            return ins_date;
        }

        public void setIns_date(String ins_date) {
            this.ins_date = ins_date;
        }
    }
}
