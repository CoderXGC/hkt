package com.example.hkt.info;

import lombok.Data;

import java.io.Serializable;


@Data


public class CameraInfo implements Serializable {

    private static final long serialVersionUID = -5044197375358881001L;


    private Integer id;


    private String ip;


    private Integer orgId;

    private Integer lUserID;


    private Integer lAlarmHandle;

}
