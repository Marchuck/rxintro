package observables.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Poke implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("weight")
    public int weight;

    @SerializedName("height")
    public int height;

    public Poke() {
    }

    @Override
    public String toString() {
        return "Poke{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}
