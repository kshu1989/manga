package jxbrowser;

import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserType;
import com.teamdev.jxbrowser.gecko15.download.SilentDownloadHandler;
import com.teamdev.jxbrowser.gecko15.download.DownloadTransaction;
import com.teamdev.jxbrowser.mozilla15.MozillaBrowser;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * The sample demonstrates how to handle all download transactions.
 */
public class DownloadHandlerSample {
    public static void main(String[] args) throws IOException, InterruptedException {
        Browser browser = BrowserFactory.createBrowser(BrowserType.Mozilla15);
        MozillaBrowser mozillaBrowser = (MozillaBrowser) browser;
        final CountDownLatch latch = new CountDownLatch(1);
        mozillaBrowser.setDownloadHandler(new SilentDownloadHandler() {
            public boolean allowDownloadTransaction(DownloadTransaction downloadTransaction) {
                return true;
            }

            public String getDownloadsDirPath(DownloadTransaction downloadTransaction) {
                return "Downloads";
            }

            public String getDestinationFileName(DownloadTransaction downloadTransaction) {
                return "jxbrowser-guide.zip";
            }

            public void downloadFinished(DownloadTransaction downloadTransaction) {
                System.out.println("Download finished: " + downloadTransaction);
                latch.countDown();
            }
        });

        browser.navigate("http://www.teamdev.com/downloads/jxbrowser/docs/JxBrowser-PGuide.zip");

        // Wait until file is downloaded completely
        latch.await();
    }
}