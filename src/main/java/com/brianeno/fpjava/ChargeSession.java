package com.brianeno.fpjava;

import java.util.Arrays;
import java.util.List;

public class ChargeSession {
    private String id;
    private int watts;

    private String make;

    private String model;

    private String vin;

    public ChargeSession(String id, int watts, String make, String model, String vin) {
        this.id = id;
        this.watts = watts;
        this.make = make;
        this.model = model;
        this.vin = vin;
    }

    public String getId() {
        return id;
    }

    public int getWatts() {
        return watts;
    }

    public String getVin() {
        return vin;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getHundredValue() {
        return this.watts / 100;
    }

    public int wattsDifference(ChargeSession other) {
        return this.watts - other.getWatts();
    }

    @Override
    public String toString() {
        return "ChargeSession{" +
            "id='" + id + '\'' +
            ", watts=" + watts +
            ", make=" + make +
            ", model=" + model +
            ", vin='" + vin + '\'' +
            '}';
    }

    public static List<ChargeSession> SESSIONS = Arrays.asList(
        new ChargeSession("11111", 420, "Tesla", "Model S", "KNADE221296399151"),
        new ChargeSession("22222", 380, "Ford", "F150","5XYKT4A62CG191848"),
        new ChargeSession("33333", 405, "KIA",  "EV9","5XYKT3A69DG353356"),
        new ChargeSession("44444", 315, "Tesla", "Model Y","KNAHT8A33E7012172"));
}
