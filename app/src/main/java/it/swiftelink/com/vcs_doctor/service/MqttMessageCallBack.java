package it.swiftelink.com.vcs_doctor.service;

public interface MqttMessageCallBack {
    void messageArrived(String topic, String message);

    void reSubscribe(String topic);

    void mqttLoginSuccess();
}
