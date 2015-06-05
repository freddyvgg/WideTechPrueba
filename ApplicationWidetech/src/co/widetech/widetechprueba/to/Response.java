
package co.widetech.widetechprueba.to;

import java.util.HashMap;
import java.util.Map;

public class Response {
	
	public static final String Code_JSON = "Code";
	public static final String Desc_JSON = "Desc";

    private Integer Code;
    private String Desc;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The Code
     */
    public Integer getCode() {
        return Code;
    }

    /**
     * 
     * @param Code
     *     The Code
     */
    public void setCode(Integer Code) {
        this.Code = Code;
    }

    /**
     * 
     * @return
     *     The Desc
     */
    public String getDesc() {
        return Desc;
    }

    /**
     * 
     * @param Desc
     *     The Desc
     */
    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
