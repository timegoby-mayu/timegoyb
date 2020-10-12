package com.fly.my.Util.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "MSG")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlRootBean {

    //List<ErrRecordBean> errRecords = new ArrayList<>();

    @XmlElement(name = "APPTYPE")
    private String appType;
    @XmlElement(name = "CURRENTFILE")
    private String currentFile;
    @XmlElement(name = "INOUT")
    private String inOut;
    @XmlElement(name = "FORMATERRS")
    private String formaterrs;
    @XmlElement(name = "FORMATS")
    private String formats;
    @XmlElement(name = "TOTALRECORDS")
    private String totalRecords;
    @XmlElement(name = "SUCRECORDS")
    private String sucRecords;
    @XmlElement(name = "FALRECORDS")
    private String falRecords;
    @XmlElement(name = "ERRRECORDS")
    private List<ErrRecordsVo> errRecords;;
}
