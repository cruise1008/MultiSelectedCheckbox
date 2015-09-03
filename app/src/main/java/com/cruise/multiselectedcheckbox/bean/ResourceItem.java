package com.cruise.multiselectedcheckbox.bean;

/**
 * Created by cruise on 2015/5/10.
 */
public class ResourceItem {

    private String name;
    private int id;
    private String logo;
    private String pic;
    private String description;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type1) {
        this.type = type1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String s) {
        this.description = s;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String s) {
        this.pic = s;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String s) {
        this.logo = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
