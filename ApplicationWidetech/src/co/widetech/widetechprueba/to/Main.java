
package co.widetech.widetechprueba.to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	
	public static final String GP_JSON = "GP";
	public static final String Response_JSON = "_Response";

    private List<co.widetech.widetechprueba.to.GP> GP = new ArrayList<co.widetech.widetechprueba.to.GP>();
    private co.widetech.widetechprueba.to.Response Response;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The GP
     */
    public List<co.widetech.widetechprueba.to.GP> getGP() {
        return GP;
    }

    /**
     * 
     * @param GP
     *     The GP
     */
    public void setGP(List<co.widetech.widetechprueba.to.GP> GP) {
        this.GP = GP;
    }

    /**
     * 
     * @return
     *     The Response
     */
    public co.widetech.widetechprueba.to.Response getResponse() {
        return Response;
    }

    /**
     * 
     * @param Response
     *     The _Response
     */
    public void setResponse(co.widetech.widetechprueba.to.Response Response) {
        this.Response = Response;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "Main [GP=" + GP + ", Response=" + Response + "]";
	}
    
    

}
