package com.fly.my.Util.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ERR")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrVo {

    @XmlElement(name = "ERRFIELD")
    private String errField;
    @XmlElement(name = "ERRFIELDCN")
    private String errFielDcn;
    @XmlElement(name = "ERRDESC")
    private String errDsc;
}
