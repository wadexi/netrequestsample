package example.seele.com.netclientapp.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SEELE on 2018/4/6.
 */

public class UserEntity {

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private long age;

    @SerializedName("career")
    private String career;

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", career='" + career + '\'' +
                '}';
    }
}
