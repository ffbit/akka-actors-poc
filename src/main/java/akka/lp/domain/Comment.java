package akka.lp.domain;

import java.util.Date;

import javax.persistence.Column;

import com.google.common.base.Objects;

//import com.wmg.dsp.utils.cassandra.annotations.Nested;

public class Comment {
    private Participant poster;

    @Column
    private String message;

    @Column
    private Date created;

    public Participant getPoster() {
        return poster;
    }

    public void setPoster(Participant poster) {
        this.poster = poster;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("poster", poster)
                .add("message", message)
                .add("created", created)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }

        Comment that = (Comment) o;
        return Objects.equal(poster, that.poster) &&
                Objects.equal(message, that.message) &&
                Objects.equal(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(poster, message, created);
    }
}
