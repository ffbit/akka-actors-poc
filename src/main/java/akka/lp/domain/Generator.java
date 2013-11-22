package akka.lp.domain;

import java.io.Serializable;

public class Generator implements Serializable {
    private static final long serialVersionUID = 6261414500401862683L;

    private final Serializable id;

    private final String sourceUrl;

    private final String sourceUrlTitle;

    public Generator() {
        // TODO: Implement the Builder with setters
        this(null, null, null);
    }

    public Generator(Serializable id, String sourceUrl, String sourceUrlTitle) {
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
        return "Generator{" +
                "id=" + id +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", sourceUrlTitle='" + sourceUrlTitle + '\'' +
                '}';
    }

}
