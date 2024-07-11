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

    @JacksonXmlProperty(localName = "peg")
    private String peg;

    @JacksonXmlProperty(localName = "dividend")
    private String dividend;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPeg() {
        return peg;
    }

    public void setPeg(String peg) {
        this.peg = peg;
    }

    public String getDividend() {
        return dividend;
    }

    public void setDividend(String dividend) {
        this.dividend = dividend;
    }
}
