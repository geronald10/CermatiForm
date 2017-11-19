package goronald.web.id.cermatiform.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Content implements Parcelable {

    private boolean required;
    private String slug;
    private String placeholder;
    private String type;
    private String label;
    private String[] values;

    public Content(JSONObject contentObject) {
        try {
            if (contentObject.has("required")) {
                required = contentObject.getBoolean("required");
                setRequired(required);
            }
            if (contentObject.has("slug")) {
                slug = contentObject.getString("slug");
                setSlug(slug);
            }
            if (contentObject.has("placeholder")) {
                placeholder = contentObject.getString("placeholder");
                setPlaceholder(placeholder);
            }
            if (contentObject.has("type")) {
                type = contentObject.getString("type");
                setType(type);
            }
            if (contentObject.has("label")) {
                label = contentObject.getString("label");
                setLabel(label);
            }
            if (contentObject.has("values")) {
                String[] splittedValues = contentObject.getString("values")
                        .trim().split(",");
                values = new String[splittedValues.length];
                for (int i = 0; i < splittedValues.length; i++) {
                    values[i] = splittedValues[i];
                }
                setValues(values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.required ? (byte) 1 : (byte) 0);
        dest.writeString(this.slug);
        dest.writeString(this.placeholder);
        dest.writeString(this.type);
        dest.writeString(this.label);
        dest.writeStringArray(this.values);
    }

    protected Content(Parcel in) {
        this.required = in.readByte() != 0;
        this.slug = in.readString();
        this.placeholder = in.readString();
        this.type = in.readString();
        this.label = in.readString();
        this.values = in.createStringArray();
    }

    public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
}
