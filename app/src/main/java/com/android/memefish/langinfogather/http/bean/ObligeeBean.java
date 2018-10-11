package com.android.memefish.langinfogather.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/28 0028.
 */

public class ObligeeBean implements Serializable{

    	private String QLRMID;
    	private String Prenumbering;
    	private String DoorNumber;
    	private String AddTime;
    	private String XZQINFOID;
    	private String QLRMC;
    	private String QLRNumber;
    	private List<Obligee> qlrlist;

    	public static class Obligee implements Serializable{
    	    private String QLRID;
    	    private String QLRMC;
    	    private String QLRLX;
    	    private String QLRLXMC;
    	    private String AddTime;
    	    private String QLRNumber;
    	    private String QLRType;

            public String getQLRID() {
                return QLRID;
            }

            public void setQLRID(String QLRID) {
                this.QLRID = QLRID;
            }

            public String getQLRMC() {
                return QLRMC;
            }

            public void setQLRMC(String QLRMC) {
                this.QLRMC = QLRMC;
            }

            public String getQLRLX() {
                return QLRLX;
            }

            public void setQLRLX(String QLRLX) {
                this.QLRLX = QLRLX;
            }

            public String getQLRLXMC() {
                return QLRLXMC;
            }

            public void setQLRLXMC(String QLRLXMC) {
                this.QLRLXMC = QLRLXMC;
            }

            public String getAddTime() {
                return AddTime;
            }

            public void setAddTime(String addTime) {
                AddTime = addTime;
            }

            public String getQLRNumber() {
                return QLRNumber;
            }

            public void setQLRNumber(String QLRNumber) {
                this.QLRNumber = QLRNumber;
            }

            public String getQLRType() {
                return QLRType;
            }

            public void setQLRType(String QLRType) {
                this.QLRType = QLRType;
            }
        }
    public String getQLRMID() {
        return QLRMID;
    }

    public void setQLRMID(String QLRMID) {
        this.QLRMID = QLRMID;
    }

    public String getPrenumbering() {
        return Prenumbering;
    }

    public void setPrenumbering(String prenumbering) {
        Prenumbering = prenumbering;
    }

    public String getDoorNumber() {
        return DoorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        DoorNumber = doorNumber;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getXZQINFOID() {
        return XZQINFOID;
    }

    public void setXZQINFOID(String XZQINFOID) {
        this.XZQINFOID = XZQINFOID;
    }

    public String getQLRMC() {
        return QLRMC;
    }

    public void setQLRMC(String QLRMC) {
        this.QLRMC = QLRMC;
    }

    public String getQLRNumber() {
        return QLRNumber;
    }

    public void setQLRNumber(String QLRNumber) {
        this.QLRNumber = QLRNumber;
    }

    public List<Obligee> getQlrlist() {
        return qlrlist;
    }

    public void setQlrlist(List<Obligee> qlrlist) {
        this.qlrlist = qlrlist;
    }
}
