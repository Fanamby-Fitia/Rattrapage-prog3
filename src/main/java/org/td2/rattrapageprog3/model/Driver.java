package org.td2.rattrapageprog3.model;

import java.time.LocalDate;

public class Driver {
    private String id;
    private String name;
    private LicenseCategory licenseCategory;
    private LocalDate affiliationDate;
    public Driver() {
    }
    public Driver(String id, String name, LicenseCategory licenseCategory,
                  LocalDate affiliationDate) {
        this.id = id;
        this.name = name;
        this.licenseCategory = licenseCategory;
        this.affiliationDate = affiliationDate;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LicenseCategory getLicenseCategory() {
        return licenseCategory;
    }
    public void setLicenseCategory(LicenseCategory licenseCategory) {
        this.licenseCategory = licenseCategory;
    }
    public LocalDate getAffiliationDate() {
        return affiliationDate;
    }
    public void setAffiliationDate(LocalDate affiliationDate) {
        this.affiliationDate = affiliationDate;
    }

}
