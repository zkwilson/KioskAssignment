package edu.mccneb;


public class Tracks {
    private int trackId;
    private String name;
    private double unitPrice;



    public Tracks(int trackId, String name, double unitPrice) {
        this.trackId = trackId;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUnitPrice() {

        return unitPrice;
    }




}
