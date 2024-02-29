import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DownloaderAndZipper {

    private ObservableList<String> urlList;

    public DownloaderAndZipper() {
        this.urlList = FXCollections.observableArrayList();
    }

    public ObservableList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ObservableList<String> urlList) {
        this.urlList = urlList;
    }
}
