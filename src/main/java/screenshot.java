import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

public class screenshot {

    IOSDriver driver;

    public void initializeBrowser(){

        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("platformName","iOS");
        capability.setCapability("platformVersion",13.1);
        capability.setCapability("deviceName","iPhone 11 Pro Max");
       // capability.setCapability("browser", "safari");
        capability.setCapability("bundleId", "com.apple.mobilesafari");
        capability.setCapability("automationName","XCUITest");
        capability.setCapability("fullReset", false);
        try {
            driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capability);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    void openUrl(){
        driver.get("https://adobe-inboxable.com/apwe6BQ");

        Set<String> contextNames = driver.getContextHandles();

        for (String contextName : contextNames)
        {
            //System.out.println(contextName);
            if (!contextName.contains("NATIVE_APP"))
            {
                driver.context(contextName);
            }
        //System.out.println(contextNames);
        }
    }

    void closeDriver(){
        driver.quit();
    }

    public void pause(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }
    }

    void takeScreenshot(){

        int scrollTimeout = 500;
        int header = 98;
        int footer = 0;
        float dpr = 3;

        ShootingStrategy iPadShootingStrategy =
                ShootingStrategies.viewportRetina(scrollTimeout, header, footer, dpr);

        Screenshot screenshot = new AShot()
                .shootingStrategy(iPadShootingStrategy)
                .takeScreenshot(driver);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(),"PNG",new File("/Users/abc/Desktop/ScreenshotDemo/Screenshot/Screenshot_"+dtf.format(LocalDateTime.now())+".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        screenshot src=new screenshot();
        src.initializeBrowser();
        src.openUrl();
        //src.pause(2);
        src.takeScreenshot();
        src.closeDriver();
    }
}
