package org.td2.rattrapageprog3.model;

public class Vehicle {
    private String id;
    private String plateNumber;
    private VehicleType type;
    private double capacityTons;

    public Vehicle() {
    }
    public Vehicle(String id, String plateNumber, VehicleType type,
                   double capacityTons) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.type = type;
        this.capacityTons = capacityTons;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPlateNumber() {
        return plateNumber;
    }
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    public VehicleType getType() {
        return type;
    }
    public void setType(VehicleType type) {
        this.type = type;
    }
    public double getCapacityTons() {
        return capacityTons;
    }
    public void setCapacityTons(double capacityTons) {
        this.capacityTons = capacityTons;
    }
}
