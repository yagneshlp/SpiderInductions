
package com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackListSearchResponse {

    @SerializedName("message")
    @Expose
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
