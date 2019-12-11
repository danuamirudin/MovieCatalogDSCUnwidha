package dicoding1.moviecatalog.model;

import com.google.gson.annotations.SerializedName;

public class ResultGenres {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
