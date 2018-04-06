package net.machtalk.xcloud;

/**
 * yumair大气设备小时平均值数据
 * User: zhaop
 * Date: 16-1-15
 * 1:  air_pressure 气压  2：temp 温度  3：humi 湿度  4：wdangle 风向  5：ws 风速 6：pm2_5 pm2.5  7:pm10  8:co  9:NO2 10:SO2  11:O3  12:aqi  13:O3_8h  14:首要污染物
 */
public class AirDeviceData {
    private Float air_pressure;
    private Float temp;
    private Float humi;
    private Float wdangle;
    private Float ws;
    private Float pm2_5;
    private Float pm10;
    private Float co;
    private Float no2;
    private Float so2;
    private Float o3;

    private String device_code; //
    private String create_time; //2015-02-01 01:00:00


    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Float getAir_pressure() {
        return air_pressure;
    }

    public void setAir_pressure(Float air_pressure) {
        this.air_pressure = air_pressure;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getHumi() {
        return humi;
    }

    public void setHumi(Float humi) {
        this.humi = humi;
    }

    public Float getWdangle() {
        return wdangle;
    }

    public void setWdangle(Float wdangle) {
        this.wdangle = wdangle;
    }

    public Float getWs() {
        return ws;
    }

    public void setWs(Float ws) {
        this.ws = ws;
    }

    public Float getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(Float pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public Float getPm10() {
        return pm10;
    }

    public void setPm10(Float pm10) {
        this.pm10 = pm10;
    }

    public Float getCo() {
        return co;
    }

    public void setCo(Float co) {
        this.co = co;
    }

    public Float getNo2() {
        return no2;
    }

    public void setNo2(Float no2) {
        this.no2 = no2;
    }

    public Float getSo2() {
        return so2;
    }

    public void setSo2(Float so2) {
        this.so2 = so2;
    }

    public Float getO3() {
        return o3;
    }

    public void setO3(Float o3) {
        this.o3 = o3;
    }
}
