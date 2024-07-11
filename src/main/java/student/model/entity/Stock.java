package student.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "stock")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

    @JacksonXmlProperty(localName = "code")
    private String code;

    @JacksonXmlProperty(localName = "price")
    private String price;

    @JacksonXmlProperty(localName = "change")
    private String change;

    @JacksonXmlProperty(localName = "price")
    private String peg;

    @JacksonXmlProperty(localName = "dividend")
    private String dividend;
}
