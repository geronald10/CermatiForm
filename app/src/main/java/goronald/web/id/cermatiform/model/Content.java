package goronald.web.id.cermatiform.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("content")
    @Expose
    private List<Content_> content = null;
    @SerializedName("heading")
    @Expose
    private String heading;

    public List<Content_> getContent() {
        return content;
    }

    public void setContent(List<Content_> content) {
        this.content = content;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

}