package schemas.gringotts.entities;

import BaseEntityCommonUse.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wizzard_deposits")
public class WizardDeposits extends BaseEntity {


    private String first_name;
    private String last_name;
    private String notes;
    private int age;
    private String magicWandCreator;
    private int magicWandSize;
    private String depositGroup;
    private LocalDateTime depositStartDate;
    private BigDecimal depositAmount;
    private double depositInterest;
    private double depositCharge;
    private LocalDateTime depositExpirationDate;
    private boolean isDepositExpired;


    public WizardDeposits() {
    }

    @Column(name = "first_name", length = 50)
    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Column(name = "last_name", length = 60, nullable = false)
    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Column(name = "notes", length = 1000)
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "age", columnDefinition = "INT UNSIGNED",nullable = false)
    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "magic_wand_creator",length = 100)
    public String getMagicWandCreator() {
        return this.magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }

    @Column(name = "magic_wand_size",columnDefinition = "SMALLINT")
    public int getMagicWandSize() {
        return this.magicWandSize;
    }

    public void setMagicWandSize(int magicWandSize) {
        this.magicWandSize = magicWandSize;
    }

    @Column(name = "deposit_group", length = 20)
    public String getDepositGroup() {
        return this.depositGroup;
    }

    public void setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
    }

    @Column(name = "deposit_start_date")
    public LocalDateTime getDepositStartDate() {
        return this.depositStartDate;
    }

    public void setDepositStartDate(LocalDateTime depositStartDate) {
        this.depositStartDate = depositStartDate;
    }

    @Column(name = "deposit_amount")
    public BigDecimal getDepositAmount() {
        return this.depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Column(name = "deposit_interest")
    public double getDepositInterest() {
        return this.depositInterest;
    }

    public void setDepositInterest(double depositInterest) {
        this.depositInterest = depositInterest;
    }
    @Column(name = "deposit_charge")
    public double getDepositCharge() {
        return this.depositCharge;
    }

    public void setDepositCharge(double depositCharge) {
        this.depositCharge = depositCharge;
    }
    @Column(name = "deposit_expiration_date")
    public LocalDateTime getDepositExpirationDate() {
        return this.depositExpirationDate;
    }

    public void setDepositExpirationDate(LocalDateTime depositExpirationDate) {
        this.depositExpirationDate = depositExpirationDate;
    }

    @Column(name = "is_deposit_expired")
    public boolean isDepositExpired() {
        return this.isDepositExpired;
    }

    public void setDepositExpired(boolean depositExpired) {
        isDepositExpired = depositExpired;
    }
}