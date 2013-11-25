package akka.lp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Objects;

//import com.wmg.dsp.streams.service.domain.activitystream.Generator;

public class Tile implements Serializable {
    private UUID id;

    private Participant poster;

    private String type;

    private Date updated;

    private Generator generator;

    private Set<Participant> recipients = new HashSet<>();

    private Set<Participant> tags;

    private String customData;

    private List<Comment> comments;

    public Tile() {
        id = UUID.randomUUID();
    }

    public Set<Participant> getTags() {
        return tags;
    }

    public void setTags(Set<Participant> tags) {
        this.tags = tags;
    }

    public Set<Participant> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<Participant> recipients) {
        this.recipients = recipients;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Participant getPoster() {
        return poster;
    }

    public void setPoster(Participant poster) {
        this.poster = poster;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("poster", poster)
                .add("type", type)
                .add("updated", updated)
                .add("generator", generator)
                .add("recipients", recipients)
                .add("tags", tags)
                .add("customData", customData)
                .add("comments", comments)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tile)) {
            return false;
        }

        Tile that = (Tile) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
