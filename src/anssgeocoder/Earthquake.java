package anssgeocoder;

import com.vividsolutions.jts.geom.Point;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Khepry Quixote <khepry.quixote@gmail.com>
 */
public class Earthquake implements Serializable {
    
    private String uniqueId = UUID.randomUUID().toString();

    private String dateTime = "";
    private String latitude = "";
    private String longitude = "";
    private String depth = "";
    private String magnitude = "";
    private String magType = "";
    private String nbrStations = "";
    private String gap = "";
    private String distance = "";
    private String rms = "";
    private String source = "";
    private String eventId = "";
    
    private Date dtgEvent = null;
    private Double dblLatitude = 0D;
    private Double dblLongitude = 0D;
    private Double dblDepth = 0D;
    private Double dblMagnitude = 0D;
    private Integer intNbrStations = 0;
    private Long lngGap = 0L;
    private Double dblDistance = 0D;
    private Double dblRms = 0D;
    
    private Point point = null;
    
    private String stateCode = "";
    private String stateName = "";
    private String countyName = "";
    private String fips = "";
    private String countryName = "";
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.S");

    public Earthquake() {
    }

    /**
     * @return the uniqueId
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @param uniqueId the uniqueId to set
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return the dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
        try {
            dtgEvent = sdf.parse(dateTime);
        }
        catch (Exception ex) {
            dtgEvent = null;
        }
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
        try {
            dblLatitude = Double.parseDouble(latitude);
        }
        catch (Exception ex) {
            dblLatitude = null;
        }
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
        try {
            dblLongitude = Double.parseDouble(longitude);
        }
        catch (Exception ex) {
            dblLongitude = null;
        }
    }

    /**
     * @return the depth
     */
    public String getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(String depth) {
        this.depth = depth;
        try {
            dblDepth = Double.parseDouble(depth);
        }
        catch (Exception ex) {
            dblDepth = null;
        }
    }

    /**
     * @return the magnitude
     */
    public String getMagnitude() {
        return magnitude;
    }

    /**
     * @param magnitude the magnitude to set
     */
    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
        try {
            dblMagnitude = Double.parseDouble(magnitude);
        }
        catch (Exception ex) {
            dblMagnitude = null;
        }
    }

    /**
     * @return the magType
     */
    public String getMagType() {
        return magType;
    }

    /**
     * @param magType the magType to set
     */
    public void setMagType(String magType) {
        this.magType = magType;
    }

    /**
     * @return the nbrStations
     */
    public String getNbrStations() {
        return nbrStations;
    }

    /**
     * @param nbrStations the nbrStations to set
     */
    public void setNbrStations(String nbrStations) {
        this.nbrStations = nbrStations;
        try {
            intNbrStations = Integer.parseInt(nbrStations);
        }
        catch (Exception ex) {
            intNbrStations = null;
        }
    }

    /**
     * @return the gap
     */
    public String getGap() {
        return gap;
    }

    /**
     * @param gap the gap to set
     */
    public void setGap(String gap) {
        this.gap = gap;
        try {
            lngGap = Long.parseLong(gap);
        }
        catch (Exception ex) {
            lngGap = null;
        }
    }

    /**
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(String distance) {
        this.distance = distance;
        try {
            dblDistance = Double.parseDouble(distance);
        }
        catch (Exception ex) {
            dblDistance = null;
        }
    }

    /**
     * @return the rms
     */
    public String getRms() {
        return rms;
    }

    /**
     * @param rms the rms to set
     */
    public void setRms(String rms) {
        this.rms = rms;
        try {
            dblRms = Double.parseDouble(rms);
        }
        catch (Exception ex) {
            dblRms = null;
        }
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the dtgEvent
     */
    public Date getDtgEvent() {
        return dtgEvent;
    }

    /**
     * @return the dblLatitude
     */
    public Double getDblLatitude() {
        return dblLatitude;
    }

    /**
     * @return the dblLongitude
     */
    public Double getDblLongitude() {
        return dblLongitude;
    }

    /**
     * @return the dblDepth
     */
    public Double getDblDepth() {
        return dblDepth;
    }

    /**
     * @return the dblMagnitude
     */
    public Double getDblMagnitude() {
        return dblMagnitude;
    }

    /**
     * @return the intNbrStations
     */
    public Integer getIntNbrStations() {
        return intNbrStations;
    }

    /**
     * @return the lngGap
     */
    public Long getLngGap() {
        return lngGap;
    }

    /**
     * @return the dblDistance
     */
    public Double getDblDistance() {
        return dblDistance;
    }

    /**
     * @return the dblRms
     */
    public Double getDblRms() {
        return dblRms;
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the countyName
     */
    public String getCountyName() {
        return countyName;
    }

    /**
     * @param countyName the countyName to set
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    /**
     * @return the fips
     */
    public String getFips() {
        return fips;
    }

    /**
     * @param fips the fips to set
     */
    public void setFips(String fips) {
        this.fips = fips;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Point point) {
        this.point = point;
    }
    
    public String toCsvString(String separator, String terminator, boolean outputHeader) {
        StringBuilder sb = new StringBuilder();
        if (outputHeader) {
            sb.append("dateTime");
            sb.append(separator);
            sb.append("latitude");
            sb.append(separator);
            sb.append("longitude");
            sb.append(separator);
            sb.append("depth");
            sb.append(separator);
            sb.append("magnitude");
            sb.append(separator);
            sb.append("magType");
            sb.append(separator);
            sb.append("nbrStations");
            sb.append(separator);
            sb.append("gap");
            sb.append(separator);
            sb.append("distance");
            sb.append(separator);
            sb.append("rms");
            sb.append(separator);
            sb.append("source");
            sb.append(separator);
            sb.append("eventId");
            sb.append(separator);
            sb.append("stateCode");
            sb.append(separator);
            sb.append("stateName");
            sb.append(separator);
            sb.append("countyName");
            sb.append(separator);
            sb.append("fips");
            sb.append(separator);
            sb.append("countryName");
            sb.append(terminator);
        }
        sb.append(dateTime);
        sb.append(separator);
        sb.append(latitude);
        sb.append(separator);
        sb.append(longitude);
        sb.append(separator);
        sb.append(depth);
        sb.append(separator);
        sb.append(magnitude);
        sb.append(separator);
        sb.append(magType);
        sb.append(separator);
        sb.append(nbrStations);
        sb.append(separator);
        sb.append(gap);
        sb.append(separator);
        sb.append(distance);
        sb.append(separator);
        sb.append(rms);
        sb.append(separator);
        sb.append(source);
        sb.append(separator);
        sb.append(eventId);
        sb.append(separator);
        sb.append(stateCode);
        sb.append(separator);
        sb.append(stateName);
        sb.append(separator);
        sb.append(countyName);
        sb.append(separator);
        sb.append(fips);
        sb.append(separator);
        sb.append(countryName);
        sb.append(terminator);
        return sb.toString();
    }
}
