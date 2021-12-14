package de.rnd7.fritzboxmqttgw.config;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;

public class ConfigFritzbox {

    private String host;
    private String username;
    private String password;
    private BoxType type = BoxType.dsl;


    @SerializedName("polling-interval")
    private Duration pollingInterval = Duration.ofSeconds(60);

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }

    public BoxType getBoxType() {
        return type;
    }
}
