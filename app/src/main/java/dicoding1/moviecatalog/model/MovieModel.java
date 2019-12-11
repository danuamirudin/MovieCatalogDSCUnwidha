package dicoding1.moviecatalog.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieModel {


    @SerializedName("results")
    private ArrayList<ResultMovie> results;

    public ArrayList<ResultMovie> getResults() {
        return results;
    }

}
