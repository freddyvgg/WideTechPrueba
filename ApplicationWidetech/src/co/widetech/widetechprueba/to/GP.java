
package co.widetech.widetechprueba.to;

import java.util.HashMap;
import java.util.Map;

public class GP {

	 public static final String Name_JSON = "Name";
	 public static final String Value_JSON = "Value";
	
    private String Name;
    private String Value;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    
    public GP() {
		super();
	}

	public GP(String name, String value) {
		super();
		Name = name;
		Value = value;
	}

	/**
     * 
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * 
     * @return
     *     The Value
     */
    public String getValue() {
        return Value;
    }

    /**
     * 
     * @param Value
     *     The Value
     */
    public void setValue(String Value) {
        this.Value = Value;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
