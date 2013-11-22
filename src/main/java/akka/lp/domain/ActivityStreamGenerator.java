package akka.lp.domain;

import java.io.Serializable;

public class ActivityStreamGenerator implements Serializable {
    private static final long serialVersionUID = 6261414500401862683L;

    private final Serializable id;

    private final String sourceUrl;

    private final String sourceUrlTitle;

    public ActivityStreamGenerator() {
        // TODO: Implement the Builder with setters
        this(null, null, null);
    }

    public ActivityStreamGenerator(Serializable id, String sourceUrl, String sourceUrlTitle) {
        this.id = id;
        this.sourceUrl = sourceUrl;
        this.sourceUrlTitle = sourceUrlTitle;
    }

    public Serializable getId() {
        return id;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSourceUrlTitle() {
        return sourceUrlTitle;
    }

    @Override
    public String toString() {
        return "ActivityStreamGenerator{" +
                "id=" + id +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", sourceUrlTitle='" + sourceUrlTitle + '\'' +
                '}';
    }

}
