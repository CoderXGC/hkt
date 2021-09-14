package com.example.hkt.info;

import lombok.Data;

import java.io.Serializable;


@Data
public class CameraInfo implements Serializable {

    private static final long serialVersionUID = -5044197375358881001L;


    private int id;


    private String ip;


    private int orgId;

    private int lUserID=1210;


    private int lAlarmHandle;

}
