import helpers.Brains;
import models.AppCache;
import view.MainWindow;

public class App {

    public static void main(String[] args) {


        MainWindow mainWindow = new MainWindow();
        Brains brains = new Brains(mainWindow);

    }
}
