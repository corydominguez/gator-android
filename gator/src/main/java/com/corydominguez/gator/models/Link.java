package com.corydominguez.gator.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Link implements Parcelable {
    private int linkId;
    private String url;
    private String twitterUrl;
    private String title;
    private String description;
    private String heroImageUrl;
    private String domain;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isBookmarked;
    private Boolean isRead;


    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public Link() {
        super();
        this.updatedAt = new Date();
        this.isBookmarked = false;
        this.isRead = false;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(Boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public void toggleBookmark() {
        isBookmarked = !isBookmarked;
    }

    public void toggleRead() {
        isRead = !isRead;
    }

    public void markRead() {
        isRead = true;
    }
    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    @JsonAnySetter
    public void anySetter(String key, Object value) {
        if (key.equals("createdAt")) {
            try {
                setCreatedAt(parseDate((String) value));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private Date parseDate(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        return df.parse(dateString);
    }

    @Override
    public String toString() {
        return "Link{" +
                "linkId=" + linkId +
                ", url='" + url + '\'' +
                ", twitterUrl='" + twitterUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", heroImageUrl='" + heroImageUrl + '\'' +
                ", domain='" + domain + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isBookmarked=" + isBookmarked +
                ", isRead=" + isRead +
                ", tweets=" + tweets +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.linkId);
        dest.writeString(this.url);
        dest.writeString(this.twitterUrl);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.heroImageUrl);
        dest.writeString(this.domain);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeValue(this.isBookmarked);
        dest.writeValue(this.isRead);
        dest.writeTypedList(this.tweets);
    }

    private Link(Parcel in) {
        this.linkId = in.readInt();
        this.url = in.readString();
        this.twitterUrl = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.heroImageUrl = in.readString();
        this.domain = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.isBookmarked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isRead = (Boolean) in.readValue(Boolean.class.getClassLoader());
        in.readTypedList(this.tweets, Tweet.CREATOR);
    }

    public static Creator<Link> CREATOR = new Creator<Link>() {
        public Link createFromParcel(Parcel source) {
            return new Link(source);
        }

        public Link[] newArray(int size) {
            return new Link[size];
        }
    };
}
