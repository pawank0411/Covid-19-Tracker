package india.coronavirus.fight.utilities;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class DownloadDistrictFile {

    private Context context;

    public DownloadDistrictFile(Context context) {
        this.context = context;
    }

    // DownloadTask for downloding video from URL
    public class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        String fileN = null;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                java.net.URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();
                fileN = "districtData" + ".pdf";
                File filename = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/covid19 India/", fileN);
                output = new FileOutputStream(filename);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("DownloadError", result);
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
            }
            MediaScannerConnection.scanFile(context,
                    new String[]{Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/covid19 India/" + fileN}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String newpath, Uri newuri) {
                            Log.i("ExternalStorage", "Scanned " + newpath + ":");
                            Log.i("ExternalStorage", "-> uri=" + newuri);
                        }
                    });

        }
    }

    //hare you can start downloding video
    public void newDownload(String url) {
        final DownloadTask downloadTask = new DownloadTask(context);
        downloadTask.execute(url);
    }
}
