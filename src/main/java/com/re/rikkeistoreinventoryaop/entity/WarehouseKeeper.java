package com.re.rikkeistoreinventoryaop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "warehouse_keepers")
public class WarehouseKeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String staffCode;

    protected WarehouseKeeper() {
    }

    public WarehouseKeeper(String fullName, String staffCode) {
        this.fullName = fullName;
        this.staffCode = staffCode;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStaffCode() {
        return staffCode;
    }
}
