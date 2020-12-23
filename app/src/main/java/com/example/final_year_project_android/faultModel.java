package com.example.final_year_project_android;

import com.google.firebase.database.PropertyName;

public class faultModel {

    public String Time,Current,Temperature,Voltage;

    public faultModel() {
    }

    public faultModel(String Time, String Current, String Temperature, String Voltage) {
        this.Time = Time;
        this.Current = Current;
        this.Temperature = Temperature;
        this.Voltage = Voltage;
    }

    @PropertyName("Time")
    public String getTime() {
        return Time;
    }
    @PropertyName("Time")
    public void setTime(String Time) {
        this.Time = Time;
    }

    @PropertyName("Current")
    public String getCurrent() {
        return Current;
    }
    @PropertyName("Current")
    public void setCurrent(String Current) {
        this.Current = Current;
    }

    @PropertyName("Temperature")
    public String getTemperature() {
        return Temperature;
    }
    @PropertyName("Temperature")
    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
    }

    @PropertyName("Voltage")
    public String getVoltage() {
        return Voltage;
    }
    @PropertyName("Voltage")
    public void setVoltage(String Voltage) {
        this.Voltage = Voltage;
    }
}
