package br.canno.ph.car.type;

import java.util.Date;

public class Vehicle {

    private int id;
    private VehicleType vehicleType;
    private String licensePlate;
    private Date date;
    private boolean received;

    public Vehicle(int id, VehicleType vehicleType, String licensePlate, Date date) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.licensePlate = licensePlate;
        this.date = date;

    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Vehicle{" + "id=" + id + ", vehicleType=" + vehicleType + ", licensePlate=" + licensePlate + ", date=" + date + '}';
    }

}
