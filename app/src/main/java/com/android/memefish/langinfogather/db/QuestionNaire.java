package com.android.memefish.langinfogather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class QuestionNaire {
    @Id
    private Long id;
    //权利人类型
    @NotNull
    private String obligeeType;
    //权利人身份证类型
    @NotNull
    private String obligeeIdentityType;
    //身份证号
    @NotNull
    private String obligeeIdentity;
    //姓名
    @NotNull
    private String name;
    //身份证地址
    @NotNull
    private String identityAddress;
    //发证单位
    @NotNull
    private String certificateUnit;
    //地址名称
    @NotNull
    private String address;
    //联系电话
    @NotNull
    private String phone;
    //土地类别
    @NotNull
    private String landType;
    //权利设定方式
    @NotNull
    private String rightSettingType;
    //建设年份
    @NotNull
    private String buildYear;
    //是否翻改建
    @NotNull
    private Boolean isRebuild;
    //备注
    @NotNull
    private String remark;

    @NotNull
    private String userId;
    @NotNull
    private Long region;
    @NotNull
    private Long obligeeId;
    @NotNull
    private String obligee;

    @Generated(hash = 1006351220)
    public QuestionNaire(Long id, @NotNull String obligeeType,
            @NotNull String obligeeIdentityType, @NotNull String obligeeIdentity,
            @NotNull String name, @NotNull String identityAddress,
            @NotNull String certificateUnit, @NotNull String address,
            @NotNull String phone, @NotNull String landType,
            @NotNull String rightSettingType, @NotNull String buildYear,
            @NotNull Boolean isRebuild, @NotNull String remark,
            @NotNull String userId, @NotNull Long region, @NotNull Long obligeeId,
            @NotNull String obligee) {
        this.id = id;
        this.obligeeType = obligeeType;
        this.obligeeIdentityType = obligeeIdentityType;
        this.obligeeIdentity = obligeeIdentity;
        this.name = name;
        this.identityAddress = identityAddress;
        this.certificateUnit = certificateUnit;
        this.address = address;
        this.phone = phone;
        this.landType = landType;
        this.rightSettingType = rightSettingType;
        this.buildYear = buildYear;
        this.isRebuild = isRebuild;
        this.remark = remark;
        this.userId = userId;
        this.region = region;
        this.obligeeId = obligeeId;
        this.obligee = obligee;
    }

    @Generated(hash = 844976265)
    public QuestionNaire() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObligeeType() {
        return obligeeType;
    }

    public void setObligeeType(String obligeeType) {
        this.obligeeType = obligeeType;
    }

    public String getObligeeIdentityType() {
        return obligeeIdentityType;
    }

    public void setObligeeIdentityType(String obligeeIdentityType) {
        this.obligeeIdentityType = obligeeIdentityType;
    }

    public String getObligeeIdentity() {
        return obligeeIdentity;
    }

    public void setObligeeIdentity(String obligeeIdentity) {
        this.obligeeIdentity = obligeeIdentity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityAddress() {
        return identityAddress;
    }

    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
    }

    public String getCertificateUnit() {
        return certificateUnit;
    }

    public void setCertificateUnit(String certificateUnit) {
        this.certificateUnit = certificateUnit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getRightSettingType() {
        return rightSettingType;
    }

    public void setRightSettingType(String rightSettingType) {
        this.rightSettingType = rightSettingType;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public Boolean getRebuild() {
        return isRebuild;
    }

    public void setRebuild(Boolean rebuild) {
        isRebuild = rebuild;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public Long getObligeeId() {
        return obligeeId;
    }

    public void setObligeeId(Long obligeeId) {
        this.obligeeId = obligeeId;
    }

    public String getObligee() {
        return obligee;
    }

    public void setObligee(String obligee) {
        this.obligee = obligee;
    }

    public Boolean getIsRebuild() {
        return this.isRebuild;
    }

    public void setIsRebuild(Boolean isRebuild) {
        this.isRebuild = isRebuild;
    }
}
