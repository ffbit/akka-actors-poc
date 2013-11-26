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

    public Generator(Serializable id, String sourceUrl) {
        this(id, sourceUrl, null);
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Generator generator = (Generator) o;

        if (id != null ? !id.equals(generator.id) : generator.id != null) {
            return false;
        }
        if (sourceUrl != null ? !sourceUrl.equals(generator.sourceUrl) : generator.sourceUrl != null) {
            return false;
        }
        if (sourceUrlTitle != null ? !sourceUrlTitle.equals(generator.sourceUrlTitle) : generator.sourceUrlTitle != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0);
        result = 31 * result + (sourceUrlTitle != null ? sourceUrlTitle.hashCode() : 0);
        return result;
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
