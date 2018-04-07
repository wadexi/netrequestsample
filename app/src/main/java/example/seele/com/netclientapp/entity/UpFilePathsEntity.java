package example.seele.com.netclientapp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SEELE on 2018/4/7.
 */

public class UpFilePathsEntity {


    @SerializedName("filepaths")
    private List<FilepathsBean> filepaths;

    public List<FilepathsBean> getFilepaths() {
        return filepaths;
    }

    public void setFilepaths(List<FilepathsBean> filepaths) {
        this.filepaths = filepaths;
    }

    public static class FilepathsBean {
        /**
         * filepath : E:\projects\WebDemo\build\libs\exploded\WebDemo-1.0-SNAPSHOT.war\files\
         */

        @SerializedName("filepath")
        private String filepath;

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        @Override
        public String toString() {
            return "FilepathsBean{" +
                    "filepath='" + filepath + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpFilePathsEntity{" +
                "filepaths=" + filepaths +
                '}';
    }
}
