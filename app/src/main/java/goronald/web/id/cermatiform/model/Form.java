package goronald.web.id.cermatiform.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Form implements Parcelable {

    private String formHeading;
    private ArrayList<Content> content = null;

    public Form() {
    }

    public Form(JSONObject form) {
        try {
            String heading = form.getString("heading");
            JSONArray content = form.getJSONArray("content");
            ArrayList<Content> contentList = new ArrayList<>();
            for (int i = 0; i < content.length(); i++) {
                JSONObject contentObject = content.getJSONObject(i);
                Content field = new Content(contentObject);
                contentList.add(field);
            }
            setFormHeading(heading);
            setContent(contentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public String getFormHeading() {
        return formHeading;
    }

    public void setFormHeading(String formHeading) {
        this.formHeading = formHeading;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.formHeading);
        dest.writeTypedList(this.content);
    }

    protected Form(Parcel in) {
        this.formHeading = in.readString();
        this.content = in.createTypedArrayList(Content.CREATOR);
    }

    public static final Parcelable.Creator<Form> CREATOR = new Parcelable.Creator<Form>() {
        @Override
        public Form createFromParcel(Parcel source) {
            return new Form(source);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };
}