package com.fly.my.Util.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ERRRECORDS")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrRecordsVo {
    @XmlElement(name = "REC")
    private List<RecVo> recVo;
}
